package com.jobhunt.applicationservice.controller;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.jobhunt.applicationservice.dto.APIReponse;
import com.jobhunt.applicationservice.dto.ApplicationDto;
import com.jobhunt.applicationservice.entity.Application;
import com.jobhunt.applicationservice.entity.ApplicationStatus;
import com.jobhunt.applicationservice.repository.ApplicationRepository;

/**
 * Integration Test for ApplicationController - List Endpoint
 * THIS TEST USES:
 * - Real Spring beans (controllers, services, repositories)
 * - Real database connection (PostgreSQL configured in application.properties)
 * - Real HTTP server (embedded Tomcat on random port)
 * - Full HTTP request/response cycle
 * 
 * WHAT GETS TESTED:
 * ✅ Controller receives HTTP request correctly
 * ✅ Service layer business logic executes
 * ✅ Repository queries the actual database
 * ✅ Database returns real data
 * ✅ JSON serialization works (snake_case conversion)
 * ✅ HTTP response is formatted correctly
 * 
 * COMPARISON WITH UNIT TEST:
 * Unit Test (@WebMvcTest):
 * - Mocks the service layer
 * - No database queries
 * - Fast (milliseconds)
 * - Tests controller logic only
 * 
 * Integration Test (@SpringBootTest):
 * - Uses real service and repository
 * - Actual database queries
 * - Slower (seconds)
 * - Tests the complete flow
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
// ↑ Starts the full Spring application on a random port (avoids port conflicts)
// This creates a real embedded server to handle HTTP requests

@DisplayName("ApplicationController Integration Tests")
class ApplicationControllerIntegrationTest {

        // TestRestTemplate automatically configured to connect to the random port
        @Autowired
        private TestRestTemplate restTemplate;

        // We inject the REAL repository to set up test data and verify database state
        // This is the actual JPA repository that talks to the database
        @Autowired
        private ApplicationRepository applicationRepository;

        private String testUserSupabaseId1;
        private String testUserSupabaseId2;

        @BeforeEach
        void setUp() {
                // Clean the database before each test to ensure isolation
                // Without this, data from previous tests could interfere
                applicationRepository.deleteAll();

                testUserSupabaseId1 = "integration-test-user-1";
                testUserSupabaseId2 = "integration-test-user-2";

                // Create real entities and save them to the actual database
                // These will be available for our tests to query
                Application app1 = Application.builder()
                                .userSupabaseId(testUserSupabaseId1)
                                .jobTitle("Backend Engineer")
                                .companyName("TechCorp")
                                .status(ApplicationStatus.APPLIED)
                                .location("Remote")
                                .postUrl("https://example.com/job1")
                                .notes("Great opportunity")
                                .appliedOn(Instant.now())
                                .build();

                Application app2 = Application.builder()
                                .userSupabaseId(testUserSupabaseId1) // Same user as app1
                                .jobTitle("Full Stack Developer")
                                .companyName("StartupXYZ")
                                .status(ApplicationStatus.INTERVIEWING)
                                .location("New York, NY")
                                .postUrl("https://example.com/job2")
                                .notes("Exciting startup")
                                .appliedOn(Instant.now())
                                .build();

                Application app3 = Application.builder()
                                .userSupabaseId(testUserSupabaseId2) // Different user
                                .jobTitle("DevOps Engineer")
                                .companyName("CloudServices Inc")
                                .status(ApplicationStatus.APPLIED)
                                .location("San Francisco, CA")
                                .postUrl("https://example.com/job3")
                                .notes("Cloud focused")
                                .appliedOn(Instant.now())
                                .build();

                applicationRepository.saveAll(List.of(app1, app2, app3));
        }

        @Test
        @DisplayName("Integration: Should return filtered applications for specific user")
        void testList_WithUserFilter_ReturnsOnlyUserApplications() {
                // ARRANGE: Database has 2 apps for user1, 1 app for user2

                // ACT: Make HTTP GET request with query parameter
                // This tests the full flow: controller → service → repository → database
                ResponseEntity<APIReponse<List<ApplicationDto>>> response = restTemplate.exchange(
                                "/api/v1/applications?supabaseUserId=" + testUserSupabaseId1,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<APIReponse<List<ApplicationDto>>>() {
                                });

                // ASSERT: Verify filtering actually works at the database level
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();

                List<ApplicationDto> applications = response.getBody().getData();

                // Should only return the 2 applications for testUserSupabaseId1
                assertThat(applications).hasSize(2);

                // Verify all returned apps belong to the requested user
                assertThat(applications)
                                .allMatch(app -> app.userSupabaseId().equals(testUserSupabaseId1));

                // Verify the correct applications were returned
                assertThat(applications)
                                .extracting(ApplicationDto::jobTitle)
                                .containsExactlyInAnyOrder("Backend Engineer", "Full Stack Developer");
        }

        @Test
        @DisplayName("Integration: Should return empty list for user with no applications")
        void testList_UserWithNoApplications_ReturnsEmptyList() {
                // ARRANGE: We have a user ID that doesn't exist in the database
                String nonExistentUserId = "user-with-no-apps";

                // ACT: Query for applications of a user that has none
                ResponseEntity<APIReponse<List<ApplicationDto>>> response = restTemplate.exchange(
                                "/api/v1/applications?supabaseUserId=" + nonExistentUserId,
                                HttpMethod.GET,
                                null,
                                new ParameterizedTypeReference<APIReponse<List<ApplicationDto>>>() {
                                });

                // ASSERT: Should return success with empty array (not an error)
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                assertThat(response.getBody()).isNotNull();
                assertThat(response.getBody().getData()).isEmpty();
        }

        @Test
        @DisplayName("Integration: Should verify JSON serialization uses snake_case")
        void testList_VerifyJsonSerialization() {
                // This test verifies the actual JSON response format
                // Unlike unit tests, this checks the real serialization process

                // ACT: Get raw response as String to inspect actual JSON
                ResponseEntity<String> response = restTemplate.getForEntity(
                                "/api/v1/applications",
                                String.class);

                // ASSERT: Verify the actual JSON response structure
                assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
                String jsonResponse = response.getBody();

                // Check that JSON uses snake_case (as configured in application.properties)
                assertThat(jsonResponse).contains("\"job_title\"");
                assertThat(jsonResponse).contains("\"company_name\"");
                assertThat(jsonResponse).contains("\"user_supabase_id\"");

                // Verify it doesn't use camelCase
                assertThat(jsonResponse).doesNotContain("\"jobTitle\"");
                assertThat(jsonResponse).doesNotContain("\"companyName\"");
        }
}

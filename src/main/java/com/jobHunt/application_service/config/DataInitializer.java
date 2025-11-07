package com.job_hunt.application_service.config;

import com.job_hunt.application_service.entity.Application;
import com.job_hunt.application_service.entity.ApplicationStatus;
import com.job_hunt.application_service.entity.Contact;
import com.job_hunt.application_service.repository.ApplicationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements CommandLineRunner {

    private final ApplicationRepository applicationRepository;

    @Override
    public void run(String... args) {
        if (applicationRepository.count() == 0) {
            log.info("Initializing mock data...");
            createMockApplications();
            log.info("Mock data initialized successfully!");
        } else {
            log.info("Database already contains data. Skipping initialization.");
        }
    }

    private void createMockApplications() {
        List<Application> mockApplications = Arrays.asList(
                Application.builder()
                        .userSupabaseId("user_123456")
                        .jobTitle("Senior Java Developer")
                        .companyName("TechCorp Solutions")
                        .status(ApplicationStatus.APPLIED)
                        .jobId("job_001")
                        .location("San Francisco, CA")
                        .postUrl("https://example.com/jobs/senior-java-dev")
                        .notes("Great company culture, competitive salary. Interview scheduled for next week.")
                        .appliedOn(Instant.now().minus(5, ChronoUnit.DAYS))
                        .contacts(Arrays.asList(
                                Contact.builder()
                                        .name("John Smith")
                                        .role("Engineering Manager")
                                        .email("john.smith@techcorp.com")
                                        .phone("+1-555-0101")
                                        .linkedinUrl("https://linkedin.com/in/johnsmith")
                                        .build(),
                                Contact.builder()
                                        .name("Sarah Johnson")
                                        .role("HR Recruiter")
                                        .email("sarah.j@techcorp.com")
                                        .phone("+1-555-0102")
                                        .build()))
                        .build(),

                Application.builder()
                        .userSupabaseId("user_123456")
                        .jobTitle("Full Stack Developer")
                        .companyName("StartupX")
                        .status(ApplicationStatus.INTERVIEWING)
                        .jobId("job_002")
                        .location("New York, NY")
                        .postUrl("https://example.com/jobs/fullstack-dev")
                        .notes("Fast-paced startup environment. Second round interview completed.")
                        .appliedOn(Instant.now().minus(10, ChronoUnit.DAYS))
                        .contacts(Arrays.asList(
                                Contact.builder()
                                        .name("Mike Chen")
                                        .role("CTO")
                                        .email("mike@startupx.io")
                                        .linkedinUrl("https://linkedin.com/in/mikechen")
                                        .build()))
                        .build(),

                Application.builder()
                        .userSupabaseId("user_123456")
                        .jobTitle("Backend Engineer")
                        .companyName("CloudBase Inc")
                        .status(ApplicationStatus.OFFER_RECEIVED)
                        .jobId("job_003")
                        .location("Austin, TX")
                        .postUrl("https://example.com/jobs/backend-engineer")
                        .notes("Received offer: $130k base + equity. Need to respond by end of week.")
                        .appliedOn(Instant.now().minus(20, ChronoUnit.DAYS))
                        .contacts(Arrays.asList(
                                Contact.builder()
                                        .name("Emily Davis")
                                        .role("Head of Engineering")
                                        .email("emily.davis@cloudbase.com")
                                        .phone("+1-555-0103")
                                        .linkedinUrl("https://linkedin.com/in/emilydavis")
                                        .build(),
                                Contact.builder()
                                        .name("Robert Taylor")
                                        .role("HR Director")
                                        .email("robert.t@cloudbase.com")
                                        .phone("+1-555-0104")
                                        .build()))
                        .build(),

                Application.builder()
                        .userSupabaseId("user_123456")
                        .jobTitle("Software Engineer")
                        .companyName("MegaCorp")
                        .status(ApplicationStatus.REJECTED)
                        .jobId("job_004")
                        .location("Seattle, WA")
                        .postUrl("https://example.com/jobs/software-engineer")
                        .notes("Position filled internally. Good interview experience though.")
                        .appliedOn(Instant.now().minus(30, ChronoUnit.DAYS))
                        .contacts(Arrays.asList(
                                Contact.builder()
                                        .name("Lisa Anderson")
                                        .role("Technical Recruiter")
                                        .email("l.anderson@megacorp.com")
                                        .build()))
                        .build(),

                Application.builder()
                        .userSupabaseId("user_123456")
                        .jobTitle("Java Architect")
                        .companyName("Enterprise Solutions Ltd")
                        .status(ApplicationStatus.DRAFT)
                        .jobId("job_005")
                        .location("Boston, MA")
                        .postUrl("https://example.com/jobs/java-architect")
                        .notes("Still preparing cover letter and portfolio. Strong match for requirements.")
                        .build(),

                Application.builder()
                        .userSupabaseId("user_789012")
                        .jobTitle("DevOps Engineer")
                        .companyName("Cloud Native Co")
                        .status(ApplicationStatus.APPLIED)
                        .jobId("job_006")
                        .location("Remote")
                        .postUrl("https://example.com/jobs/devops-engineer")
                        .notes("100% remote position. Kubernetes and AWS experience required.")
                        .appliedOn(Instant.now().minus(3, ChronoUnit.DAYS))
                        .contacts(Arrays.asList(
                                Contact.builder()
                                        .name("David Wilson")
                                        .role("DevOps Lead")
                                        .email("david.w@cloudnative.co")
                                        .phone("+1-555-0105")
                                        .linkedinUrl("https://linkedin.com/in/davidwilson")
                                        .build()))
                        .build(),

                Application.builder()
                        .userSupabaseId("user_789012")
                        .jobTitle("Lead Software Engineer")
                        .companyName("FinTech Innovations")
                        .status(ApplicationStatus.WITHDRAWN)
                        .jobId("job_007")
                        .location("Chicago, IL")
                        .postUrl("https://example.com/jobs/lead-software-engineer")
                        .notes("Withdrew application after accepting another offer.")
                        .appliedOn(Instant.now().minus(15, ChronoUnit.DAYS))
                        .build(),

                Application.builder()
                        .userSupabaseId("user_789012")
                        .jobTitle("Principal Engineer")
                        .companyName("Tech Giants Corp")
                        .status(ApplicationStatus.ACCEPTED)
                        .jobId("job_008")
                        .location("Palo Alto, CA")
                        .postUrl("https://example.com/jobs/principal-engineer")
                        .notes("Accepted offer! Start date: Next month. Relocation package included.")
                        .appliedOn(Instant.now().minus(45, ChronoUnit.DAYS))
                        .contacts(Arrays.asList(
                                Contact.builder()
                                        .name("Jennifer Martinez")
                                        .role("VP of Engineering")
                                        .email("j.martinez@techgiants.com")
                                        .phone("+1-555-0106")
                                        .linkedinUrl("https://linkedin.com/in/jennifermartinez")
                                        .build(),
                                Contact.builder()
                                        .name("Thomas Lee")
                                        .role("Hiring Manager")
                                        .email("thomas.lee@techgiants.com")
                                        .phone("+1-555-0107")
                                        .linkedinUrl("https://linkedin.com/in/thomaslee")
                                        .build(),
                                Contact.builder()
                                        .name("Amanda White")
                                        .role("Talent Acquisition")
                                        .email("a.white@techgiants.com")
                                        .build()))
                        .build());

        applicationRepository.saveAll(mockApplications);
        log.info("Created {} mock applications", mockApplications.size());
    }
}

package com.jobhunt.applicationservice.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

import com.jobhunt.applicationservice.dto.ApplicationDto;
import com.jobhunt.applicationservice.dto.ContactCreateRequest;
import com.jobhunt.applicationservice.dto.CreateApplicationRequest;
import com.jobhunt.applicationservice.dto.UpdateApplicationRequest;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.Version;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "application")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Application {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Version
    private Long version;

    @Column(nullable = false, updatable = false)
    private Instant createdOn;

    @Column(nullable = false)
    private Instant updatedOn;

    @Column(nullable = false, updatable = false)
    private String userSupabaseId;

    @Column(nullable = false)
    private String jobTitle;

    @Column(nullable = false)
    private String companyName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;

    private String jobId;
    private String location;
    private String postUrl;
    private String notes;

    private Instant appliedOn;

    @ElementCollection
    private List<Contact> contacts;

    @PrePersist
    protected void onCreate() {
        createdOn = Instant.now();
        updatedOn = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedOn = Instant.now();
    }

    // Convert CreateApplicationRequest to Application entity
    public static Application toEntity(CreateApplicationRequest request) {
        return builder()
                .jobId(request.jobId())
                .userSupabaseId(request.userSupabaseId())
                .location(request.location())
                .jobTitle(request.jobTitle())
                .companyName(request.companyName())
                .postUrl(request.postUrl())
                .notes(request.notes())
                .status(ApplicationStatus.APPLIED)
                .appliedOn(Instant.now())
                .contacts(request.contacts().stream()
                        .map(ContactCreateRequest::toEntity)
                        .toList())
                .build();
    }

    // Convert UpdateApplicationRequest to existing Application entity
    public static Application toEntity(UpdateApplicationRequest request, Application application) {
        application.setLocation(request.location() == null || request.location().isEmpty()
                ? application.getLocation()
                : request.location());

        application.setJobTitle(request.jobTitle() == null || request.jobTitle().isEmpty()
                ? application.getJobTitle()
                : request.jobTitle());

        application.setCompanyName(request.companyName() == null || request.companyName().isEmpty()
                ? application.getCompanyName()
                : request.companyName());

        application.setPostUrl(request.postUrl() == null || request.postUrl().isEmpty()
                ? application.getPostUrl()
                : request.postUrl());

        application.setNotes(request.notes() == null || request.notes().isEmpty()
                ? application.getNotes()
                : request.notes());

        application.setContacts(request.contacts() == null || request.contacts().isEmpty()
                ? application.getContacts()
                : request.contacts().stream()
                        .map(ContactCreateRequest::toEntity)
                        .toList());

        return application;

    }

    // Convert Application entity to ApplicationDto
    public static ApplicationDto toDto(Application application) {
        return ApplicationDto.builder()
                .id(application.getId())
                .jobId(application.getJobId())
                .userSupabaseId(application.getUserSupabaseId())
                .location(application.getLocation())
                .jobTitle(application.getJobTitle())
                .companyName(application.getCompanyName())
                .postUrl(application.getPostUrl())
                .notes(application.getNotes())
                .status(application.getStatus().name())
                .appliedOn(application.getAppliedOn())
                .createdOn(application.getCreatedOn())
                .build();
    }

}

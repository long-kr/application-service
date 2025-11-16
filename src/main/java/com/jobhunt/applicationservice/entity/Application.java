package com.jobhunt.applicationservice.entity;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

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
}

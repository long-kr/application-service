package com.jobhunt.application_service.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import com.jobhunt.application_service.entity.Application;
import com.jobhunt.application_service.entity.ApplicationStatus;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record CreateApplicationRequest(
                @Valid @NotBlank String jobId,
                @Valid @NotBlank String userSupabaseId,
                @Valid @NotBlank @Length(min = 2, max = 100) String location,
                @Valid @NotBlank @Length(min = 2, max = 100) String jobTitle,
                @Valid @NotBlank @Length(min = 2, max = 100) String companyName,
                @Valid @URL String postUrl,
                @Valid @Length(min = 1, max = 5000) String notes,
                @Valid List<ContactCreateRequest> contacts

) implements Serializable {

        private static final long serialVersionUID = 1L;

        public static Application toEntity(CreateApplicationRequest request) {
                return Application.builder()
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
}

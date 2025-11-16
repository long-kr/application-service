package com.jobhunt.applicationservice.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import com.jobhunt.applicationservice.entity.Application;
import com.jobhunt.applicationservice.entity.ApplicationStatus;

import jakarta.validation.constraints.NotBlank;

public record CreateApplicationRequest(
                @NotBlank String jobId,
                @NotBlank String userSupabaseId,
                @NotBlank @Length(min = 2, max = 100) String location,
                @NotBlank @Length(min = 2, max = 100) String jobTitle,
                @NotBlank @Length(min = 2, max = 100) String companyName,
                @URL String postUrl,
                @Length(min = 1, max = 5000) String notes,
                List<ContactCreateRequest> contacts

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

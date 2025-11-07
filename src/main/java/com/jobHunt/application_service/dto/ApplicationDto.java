package com.jobhunt.application_service.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

import com.jobhunt.application_service.entity.Application;

import lombok.Builder;

@Builder
public record ApplicationDto(
        UUID id,
        String jobId,
        String userSupabaseId,
        String location,
        String jobTitle,
        String companyName,
        String postUrl,
        String notes,
        String status,
        Instant appliedOn,
        Instant createOn) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

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
                .createOn(application.getCreatedOn())
                .build();
    }
}
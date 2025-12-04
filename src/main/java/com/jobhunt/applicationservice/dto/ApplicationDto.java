package com.jobhunt.applicationservice.dto;

import java.io.Serial;
import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

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
        Instant createdOn) implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
}
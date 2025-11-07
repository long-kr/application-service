package com.job_hunt.application_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Embeddable
public class Contact {

    private String name;

    private String role;

    private String email;

    private String phone;

    private String linkedinUrl;

}
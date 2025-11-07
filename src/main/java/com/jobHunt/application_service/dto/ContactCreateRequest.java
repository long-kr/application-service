package com.job_hunt.application_service.dto;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import com.job_hunt.application_service.entity.Contact;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public record ContactCreateRequest(
        @Valid @Length(min = 1, max = 100) String name,
        @Valid @Length(min = 1, max = 100) String role,
        @Valid @Length(min = 2, max = 200) @Email String email,
        @Valid @Length(min = 10, max = 15) String phone,
        @Valid @URL String linkedinUrl

) implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public static Contact toEntity(ContactCreateRequest request) {
        return Contact.builder()
                .name(request.name())
                .role(request.role())
                .email(request.email())
                .phone(request.phone())
                .linkedinUrl(request.linkedinUrl())
                .build();
    }

}

package com.job_hunt.application_service.dto;

import java.io.Serializable;
import java.util.List;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.URL;

import com.job_hunt.application_service.entity.Application;

import jakarta.validation.Valid;

public record UpdateApplicationRequest(
                @Valid @Length(min = 2, max = 100) String location,
                @Valid @Length(min = 2, max = 100) String jobTitle,
                @Valid @Length(min = 2, max = 100) String companyName,
                @Valid @URL String postUrl,
                @Valid @Length(min = 1, max = 5000) String notes,
                @Valid List<ContactCreateRequest> contacts) implements Serializable {

        private static final long serialVersionUID = 1L;

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
}

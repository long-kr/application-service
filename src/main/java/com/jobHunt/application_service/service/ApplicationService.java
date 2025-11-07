package com.jobHunt.application_service.service;

import java.util.List;
import java.util.UUID;

import com.jobHunt.application_service.dto.ApplicationDto;
import com.jobHunt.application_service.dto.CreateApplicationRequest;
import com.jobHunt.application_service.dto.UpdateApplicationRequest;

public interface ApplicationService {

    List<ApplicationDto> findAll();

    ApplicationDto getById(UUID id);

    ApplicationDto create(CreateApplicationRequest body);

    ApplicationDto update(UUID id, UpdateApplicationRequest body);

}

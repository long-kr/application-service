package com.job_hunt.application_service.service;

import java.util.List;
import java.util.UUID;

import com.job_hunt.application_service.dto.ApplicationDto;
import com.job_hunt.application_service.dto.CreateApplicationRequest;
import com.job_hunt.application_service.dto.UpdateApplicationRequest;

public interface ApplicationService {

    List<ApplicationDto> findAll();

    ApplicationDto getById(UUID id);

    ApplicationDto create(CreateApplicationRequest body);

    ApplicationDto update(UUID id, UpdateApplicationRequest body);

}

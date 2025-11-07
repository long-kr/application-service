package com.job_hunt.application_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.job_hunt.application_service.dto.ApplicationDto;
import com.job_hunt.application_service.dto.CreateApplicationRequest;
import com.job_hunt.application_service.dto.UpdateApplicationRequest;
import com.job_hunt.application_service.entity.Application;
import com.job_hunt.application_service.repository.ApplicationRepository;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@AllArgsConstructor
@Slf4j
public class ApplicationServiceImp implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Override
    public List<ApplicationDto> findAll() {
        log.info("===========> findAll");

        return applicationRepository.findAll().stream()
                .map(ApplicationDto::toDto)
                .toList();
    }

    @Override
    public ApplicationDto getById(UUID id) {
        log.info("===========> getApplicationById: {}", id);

        return applicationRepository.findById(id)
                .map(ApplicationDto::toDto)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Application not found with id: " + id);
                });

    }

    @Override
    public ApplicationDto create(CreateApplicationRequest body) {
        log.info("===========> create: {}", body);

        Application application = CreateApplicationRequest.toEntity(body);
        applicationRepository.save(application);
        return ApplicationDto.toDto(application);

    }

    @Override
    public ApplicationDto update(UUID id, UpdateApplicationRequest body) {
        log.info("===========> update: {}, {}", id, body);

        Application application = applicationRepository.findById(id)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Application not found with id: " + id);
                });

        applicationRepository.save(UpdateApplicationRequest.toEntity(body, application));

        return ApplicationDto.toDto(application);
    }
}

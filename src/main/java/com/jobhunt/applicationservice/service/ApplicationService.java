package com.jobhunt.applicationservice.service;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.jobhunt.applicationservice.dto.ApplicationDto;
import com.jobhunt.applicationservice.dto.CreateApplicationRequest;
import com.jobhunt.applicationservice.dto.UpdateApplicationRequest;
import com.jobhunt.applicationservice.entity.Application;
import com.jobhunt.applicationservice.repository.ApplicationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationService {

    private final ApplicationRepository applicationRepository;

    public List<ApplicationDto> findAll(String supabaseUserId) {
        log.info("===========> findAll by userSupabaseId: {}", supabaseUserId);

        if (supabaseUserId != null && !supabaseUserId.isEmpty()) {
            log.info("===========> findAll by userSupabaseId: {}", supabaseUserId);
            return applicationRepository.findAllByUserSupabaseId(supabaseUserId).stream()
                    .map(ApplicationDto::toDto)
                    .toList();
        }

        log.info("===========> findAll");
        return applicationRepository.findAll().stream()
                .map(ApplicationDto::toDto)
                .toList();
    }

    public ApplicationDto getById(UUID id) {
        log.info("===========> getApplicationById: {}", id);

        return applicationRepository.findById(id)
                .map(ApplicationDto::toDto)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Application not found with id: " + id);
                });

    }

    public ApplicationDto create(CreateApplicationRequest body) {
        log.info("===========> create: {}", body);

        Application application = CreateApplicationRequest.toEntity(body);

        applicationRepository.save(application);
        return ApplicationDto.toDto(application);

    }

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

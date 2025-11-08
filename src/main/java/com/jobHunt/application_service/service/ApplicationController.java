package com.jobhunt.application_service.service;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.jobhunt.application_service.dto.APIReponse;
import com.jobhunt.application_service.dto.ApplicationDto;
import com.jobhunt.application_service.dto.CreateApplicationRequest;
import com.jobhunt.application_service.dto.UpdateApplicationRequest;

import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class ApplicationController {

    public static final String APPLICATION = "/applications";
    public static final String APPLICATION_ID = "/applications/{id}";

    private final ApplicationService applicationService;

    @GetMapping(APPLICATION)
    public APIReponse<List<ApplicationDto>> list() {
        return APIReponse.success("success", applicationService.findAll());
    }

    @GetMapping(APPLICATION_ID)
    public APIReponse<ApplicationDto> get(@PathVariable UUID id) {
        return APIReponse.success("success", applicationService.getById(id));
    }

    @PostMapping(APPLICATION)
    public APIReponse<ApplicationDto> create(@Valid @RequestBody CreateApplicationRequest body) {
        return APIReponse.success("created", applicationService.create(body));
    }

    @PutMapping(APPLICATION_ID)
    public APIReponse<ApplicationDto> update(@PathVariable String id,
            @Valid @RequestBody UpdateApplicationRequest body) {
        return APIReponse.success("updated", applicationService.update(UUID.fromString(id), body));

    }

}

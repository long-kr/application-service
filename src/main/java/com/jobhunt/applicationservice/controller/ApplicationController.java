package com.jobhunt.applicationservice.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jobhunt.applicationservice.dto.APIReponse;
import com.jobhunt.applicationservice.dto.ApplicationDto;
import com.jobhunt.applicationservice.dto.CreateApplicationRequest;
import com.jobhunt.applicationservice.dto.UpdateApplicationRequest;
import com.jobhunt.applicationservice.service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/{api-version}")
@RequiredArgsConstructor
public class ApplicationController {

    public static final String APPLICATION = "/applications";
    public static final String APPLICATION_ID = "/applications/{id}";

    private final ApplicationService applicationService;

    /**
     * List applications, optionally filtered by supabaseUserId
     * 
     * @param supabaseUserId
     * @return List of ApplicationDto
     */
    @GetMapping(APPLICATION)
    public APIReponse<List<ApplicationDto>> list(
            @RequestParam(required = false) String supabaseUserId) {
        return APIReponse.success("success", applicationService.findAll(supabaseUserId));
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

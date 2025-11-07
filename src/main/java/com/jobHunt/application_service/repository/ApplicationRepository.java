package com.jobHunt.application_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobHunt.application_service.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

}

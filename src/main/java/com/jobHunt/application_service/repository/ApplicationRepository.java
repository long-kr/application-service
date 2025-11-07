package com.jobhunt.application_service.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobhunt.application_service.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

}

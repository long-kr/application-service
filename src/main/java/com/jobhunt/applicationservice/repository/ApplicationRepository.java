package com.jobhunt.applicationservice.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobhunt.applicationservice.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, UUID> {

    Optional<Application> findByUserSupabaseId(String userSupabaseId);

    List<Application> findAllByUserSupabaseId(String userSupabaseId);

}

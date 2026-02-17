package com.example.java_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.java_backend.models.ChatData;

public interface chatDataRepository extends JpaRepository<ChatData, Long> {
    
}

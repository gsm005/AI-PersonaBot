package com.example.java_backend.models;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "chat_data")
public class ChatData {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long uid;
    private String incomingMessage;
    private String responseMessage;
    @Column(name = "sender")
    private String from;
    @Column(name = "recipient")
    private String to;
    private Instant responseTimestamp;
    private Instant incomingTimestamp;

}

package com.cyberincident.backend.incident.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "incidents")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Incident {

    @Id
    private String id;

    private String title;

    private String description;

    private String category;

    private Severity severity;


    private String reportedBy;

    private List<String> attachments;

    private LocalDateTime createdAt;

    private IncidentStatus status;


}
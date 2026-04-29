package com.cyberincident.backend.incident.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateIncidentRequest {
    @NotBlank(message ="Title Required")
    private String title;

    @NotBlank(message ="Description Is Required")
    private String description;

    private String category;


}

package com.cyberincident.backend.alert.model;



import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SecurityAlert {

    private String type;

    private String message;

    private LocalDateTime timestamp;

}

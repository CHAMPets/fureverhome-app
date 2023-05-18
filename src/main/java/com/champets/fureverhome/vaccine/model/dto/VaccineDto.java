package com.champets.fureverhome.vaccine.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class VaccineDto {
    private Long id;
    private String name;
    private String type;
    private String description;
}

package com.technicalassignment.ebi01951.transport;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MouseToHumanGeneRelationResponse {
    private Long supportCount;
    private String humanGeneSymbol;
    private String errorMessage;
}

package com.technicalassignment.ebi01951.transport;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MouseGeneResponse {
    @JsonIgnore
    private Long id;
    private String symbol;
    private String identifier;
    private List<String> synonyms;
    private String errorMessage;
}

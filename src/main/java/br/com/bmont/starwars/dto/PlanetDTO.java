package br.com.bmont.starwars.dto;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class PlanetDTO {
    @NotEmpty(message = "The planet name cannot be empty")
    private String name;
    @NotEmpty(message = "The planet climate cannot be empty")
    private String climate;
    @NotEmpty(message = "The planet terrain cannot be empty")
    private String terrain;
}

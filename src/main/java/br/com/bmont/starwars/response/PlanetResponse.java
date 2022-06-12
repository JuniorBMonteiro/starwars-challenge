package br.com.bmont.starwars.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlanetResponse {
    private Long id;
    private String name;
    private String climate;
    private String terrain;
    private int movieAppearances;
}

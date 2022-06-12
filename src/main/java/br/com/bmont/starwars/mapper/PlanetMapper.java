package br.com.bmont.starwars.mapper;

import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.response.PlanetResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.stream.Collectors;

public class PlanetMapper {

    public static PlanetResponse toPlanetResponse(Planet planet){
        return PlanetResponse.builder()
                .id(planet.getId())
                .name(planet.getName())
                .climate(planet.getClimate())
                .terrain(planet.getTerrain())
                .movieAppearances(planet.getMovieAppearances())
                .build();
    }

    public static Page<PlanetResponse> toPlanetResponse(Page<Planet> planet){
        return new PageImpl<>(planet.stream().map(PlanetMapper::toPlanetResponse)
                .collect(Collectors.toList()));
    }
}

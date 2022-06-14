package br.com.bmont.starwars.mapper;

import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.request.PlanetRequest;
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
        return planet.map(PlanetMapper::toPlanetResponse);
    }

    public static Planet toPlanet(PlanetRequest planetRequest, int movieAppearances){
        return Planet.builder()
                .name(planetRequest.getName())
                .climate(planetRequest.getClimate())
                .terrain(planetRequest.getTerrain())
                .movieAppearances(movieAppearances)
                .build();
    }
}

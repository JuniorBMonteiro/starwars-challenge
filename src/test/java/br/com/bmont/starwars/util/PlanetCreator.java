package br.com.bmont.starwars.util;

import br.com.bmont.starwars.request.PlanetRequest;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.response.PlanetResponse;

public class PlanetCreator {

    public static Planet createPlanetWithId(){
        return Planet.builder()
                .id(1L)
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .movieAppearances(0)
                .build();
    }

    public static PlanetResponse createPlanetResponseWithId(){
        return PlanetResponse.builder()
                .id(1L)
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .movieAppearances(0)
                .build();
    }


    public static Planet createPlanetWithoutId(){
        return Planet.builder()
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .movieAppearances(0)
                .build();
    }

    public static PlanetResponse createPlanetResponseWithoutId(){
        return PlanetResponse.builder()
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .movieAppearances(0)
                .build();
    }

    public static PlanetRequest createPlanetRequest(){
        return PlanetRequest.builder()
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .build();
    }
}

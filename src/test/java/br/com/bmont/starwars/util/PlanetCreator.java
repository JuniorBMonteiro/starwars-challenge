package br.com.bmont.starwars.util;

import br.com.bmont.starwars.dto.PlanetDTO;
import br.com.bmont.starwars.model.Planet;

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

    public static Planet createPlanetWithoutId(){
        return Planet.builder()
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .movieAppearances(0)
                .build();
    }

    public static PlanetDTO createPlanetDTO(){
        return PlanetDTO.builder()
                .name("test")
                .climate("temperate")
                .terrain("mountains")
                .build();
    }
}

package br.com.bmont.starwars.service;

import br.com.bmont.starwars.dto.PlanetDTO;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.repository.PlanetRepository;
import com.sun.xml.bind.v2.TODO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class PlanetService {
    private final PlanetRepository planetRepository;

    public void addPlanet(PlanetDTO planetDTO) {
        //TODO: 06/05/2022 create restTemplate to get movieAppearences
        int movieAppearances = 2;
        Planet planet = Planet.builder()
                .name(planetDTO.getName())
                .climate(planetDTO.getClimate())
                .terrain(planetDTO.getTerrain())
                .movieAppearances(movieAppearances)
                .build();
        planetRepository.save(planet);
    }
    
    public Page<Planet> listAll(Pageable pageable){
        return planetRepository.findAll(pageable);
    }

    public Planet getByName(String name) {
        return planetRepository.findByName(name);
    }
    public Planet getById(long id) {
        return planetRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Planet not found"));
    }

    public void removePlanetById(long id) {
        planetRepository.delete(getById(id));
    }

    public void removePlanetByName(String name) {
        planetRepository.delete(getByName(name));
    }
}

package br.com.bmont.starwars.service;

import br.com.bmont.starwars.dto.PlanetDTO;
import br.com.bmont.starwars.exception.BadRequestException;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.repository.PlanetRepository;
import br.com.bmont.starwars.request.RestConsumer;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final RestConsumer restConsumer;

    @Transactional
    public Planet addPlanet(PlanetDTO planetDTO) {
        int movieAppearances = restConsumer.getMovieAppearancesByName(planetDTO.getName());
        Planet planet = Planet.builder()
                .name(planetDTO.getName())
                .climate(planetDTO.getClimate())
                .terrain(planetDTO.getTerrain())
                .movieAppearances(movieAppearances)
                .build();
        return planetRepository.save(planet);
    }
    
    public Page<Planet> listAll(Pageable pageable){
        return planetRepository.findAll(pageable);
    }

    public Planet getByName(String name) {
        return planetRepository.findByName(name);
    }

    public Planet getById(long id) {
        return planetRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Planet not found"));
    }

    public void deletePlanetById(long id) {
        planetRepository.delete(getById(id));
    }
}

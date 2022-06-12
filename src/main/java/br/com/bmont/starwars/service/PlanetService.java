package br.com.bmont.starwars.service;

import br.com.bmont.starwars.mapper.PlanetMapper;
import br.com.bmont.starwars.request.PlanetRequest;
import br.com.bmont.starwars.exception.BadRequestException;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.repository.PlanetRepository;
import br.com.bmont.starwars.client.RestConsumer;
import br.com.bmont.starwars.response.PlanetResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class PlanetService {
    private final PlanetRepository planetRepository;
    private final RestConsumer restConsumer = new RestConsumer();

    @Transactional
    public PlanetResponse addPlanet(PlanetRequest planetRequest) {
        int movieAppearances = restConsumer.getMovieAppearancesByName(planetRequest.getName());
        Planet planet = Planet.builder()
                .name(planetRequest.getName())
                .climate(planetRequest.getClimate())
                .terrain(planetRequest.getTerrain())
                .movieAppearances(movieAppearances)
                .build();
        Planet planetSaved = planetRepository.save(planet);
        return PlanetMapper.toPlanetResponse(planetSaved);
    }
    
    public Page<PlanetResponse> listAll(Pageable pageable){
        Page<Planet> planets = planetRepository.findAll(pageable);
        return PlanetMapper.toPlanetResponse(planets);
    }

    public PlanetResponse getByName(String name) {
        Planet planet = planetRepository.findByName(name)
                .orElseThrow(() -> new BadRequestException("Planet not found"));
        return PlanetMapper.toPlanetResponse(planet);
    }

    public PlanetResponse getById(long id) {
        Planet planet = planetRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Planet not found"));
        return PlanetMapper.toPlanetResponse(planet);
    }

    public void deletePlanetById(long id) {
        getById(id);
        planetRepository.deleteById(id);
    }
}

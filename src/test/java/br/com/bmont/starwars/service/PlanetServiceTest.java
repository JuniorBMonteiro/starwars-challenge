package br.com.bmont.starwars.service;

import br.com.bmont.starwars.dto.PlanetDTO;
import br.com.bmont.starwars.exception.BadRequestException;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.repository.PlanetRepository;
import br.com.bmont.starwars.request.RestConsumer;
import br.com.bmont.starwars.util.PlanetCreator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Planet Service")
class PlanetServiceTest {

    @InjectMocks
    private PlanetService planetService;

    @Mock
    private PlanetRepository planetRepository;
    @Mock
    private RestConsumer restConsumer;

    @BeforeEach
    void init(){
        Planet planet = PlanetCreator.createPlanetWithId();
        int movieAppearances = 1;

        BDDMockito.when(restConsumer.getMovieAppearancesByName(ArgumentMatchers.anyString()))
                .thenReturn(movieAppearances);

        BDDMockito.when(planetRepository.save(ArgumentMatchers.any(Planet.class)))
                .thenReturn(planet);

        BDDMockito.when(planetRepository.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(planet));

        BDDMockito.when(planetService.getByName(ArgumentMatchers.anyString()))
                .thenReturn(planet);

        BDDMockito.when(planetService.listAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(planet)));

        BDDMockito.doNothing().when(planetRepository).delete(ArgumentMatchers.any(Planet.class));
    }

    @Test
    void addPlanet_ReturnsPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        Planet planetSaved = planetService.addPlanet(PlanetCreator.createPlanetDTO());
        Assertions.assertNotNull(planetSaved);
        Assertions.assertEquals(expectedPlanet, planetSaved);
    }

    @Test
    void getById_ReturnsPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        Planet planetFound = planetService.getById(1);
        Assertions.assertNotNull(planetFound);
        Assertions.assertEquals(expectedPlanet, planetFound);
    }

    @Test
    void getById_ThrowsBadRequestException_WhenPlanetIsNotFound(){
        BDDMockito.when(planetRepository.findById(ArgumentMatchers.anyLong()))
                .thenThrow(new BadRequestException("Planet Not Found"));
        Assertions.assertThrows(BadRequestException.class, () -> planetService.getById(1));
    }

    @Test
    void getByName_ReturnsPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        Planet planetFound = planetService.getByName(expectedPlanet.getName());
        Assertions.assertNotNull(planetFound);
        Assertions.assertEquals(expectedPlanet, planetFound);
    }

    @Test
    void listAll_ReturnsPageOfPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        Page<Planet> page = planetService.listAll(PageRequest.of(0, 10));
        Assertions.assertFalse(page.isEmpty());
        Assertions.assertEquals(1, page.getTotalElements());
        Assertions.assertEquals(expectedPlanet, page.toList().get(0));
    }

    @Test
    void delete_RemovesPlanet_WhenSuccessful(){
        Assertions.assertDoesNotThrow(() -> planetService.deletePlanetById(1));
    }
}
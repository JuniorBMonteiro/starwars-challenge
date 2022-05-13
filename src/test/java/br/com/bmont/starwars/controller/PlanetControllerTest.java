package br.com.bmont.starwars.controller;

import br.com.bmont.starwars.dto.PlanetDTO;
import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.service.PlanetService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@ExtendWith(SpringExtension.class)
@DisplayName("Tests for Planet Controller")
class PlanetControllerTest {

    @InjectMocks
    private PlanetController planetController;

    @Mock
    private PlanetService planetService;

    @BeforeEach
    void init(){
        Planet planet = PlanetCreator.createPlanetWithId();
        BDDMockito.when(planetService.addPlanet(ArgumentMatchers.any(PlanetDTO.class)))
                .thenReturn(planet);

        BDDMockito.when(planetService.getById(ArgumentMatchers.anyLong()))
                .thenReturn(planet);

        BDDMockito.when(planetService.getByName(ArgumentMatchers.anyString()))
                .thenReturn(planet);

        BDDMockito.when(planetService.listAll(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(planet)));

        BDDMockito.doNothing().when(planetService).deletePlanetById(ArgumentMatchers.anyLong());
    }

    @Test
    void addPlanet_ReturnsPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        ResponseEntity<Planet> response = planetController.addPlanet(PlanetCreator.createPlanetDTO());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
        Assertions.assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    }

    @Test
    void getById_ReturnsPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        ResponseEntity<Planet> response = planetController.getById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByName_ReturnsPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        ResponseEntity<Planet> response = planetController.getByName(expectedPlanet.getName());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listAll_ReturnsPageOfPlanet_WhenSuccessful(){
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        ResponseEntity<Page<Planet>> response = planetController.listAll(PageRequest.of(0, 10));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody().toList().get(0));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete_RemovesPlanet_WhenSuccessful(){
        Assertions.assertDoesNotThrow(() -> planetController.deletePlanet(1));
        ResponseEntity<Void> response = planetController.deletePlanet(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
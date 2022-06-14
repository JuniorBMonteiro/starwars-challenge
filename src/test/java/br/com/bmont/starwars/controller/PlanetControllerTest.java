package br.com.bmont.starwars.controller;

import br.com.bmont.starwars.request.PlanetRequest;
import br.com.bmont.starwars.response.PlanetResponse;
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
        PlanetResponse planetResponse = PlanetCreator.createPlanetResponseWithId();
        BDDMockito.when(planetService.addPlanet(ArgumentMatchers.any(PlanetRequest.class)))
                .thenReturn(planetResponse);

        BDDMockito.when(planetService.getPlanetById(ArgumentMatchers.anyLong()))
                .thenReturn(planetResponse);

        BDDMockito.when(planetService.getPlanetByName(ArgumentMatchers.anyString()))
                .thenReturn(planetResponse);

        BDDMockito.when(planetService.listAllPlanets(ArgumentMatchers.any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(planetResponse)));

        BDDMockito.doNothing().when(planetService).deletePlanetById(ArgumentMatchers.anyLong());
    }

    @Test
    void addPlanet_ReturnsPlanet_WhenSuccessful(){
        PlanetResponse expectedPlanet = PlanetCreator.createPlanetResponseWithId();
        ResponseEntity<PlanetResponse> response = planetController.addPlanet(PlanetCreator.createPlanetRequest());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
        Assertions.assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void getById_ReturnsPlanet_WhenSuccessful(){
        PlanetResponse expectedPlanet = PlanetCreator.createPlanetResponseWithId();
        ResponseEntity<PlanetResponse> response = planetController.getPlanetById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void getByName_ReturnsPlanet_WhenSuccessful(){
        PlanetResponse expectedPlanet = PlanetCreator.createPlanetResponseWithId();
        ResponseEntity<PlanetResponse> response = planetController.getPlanetByName(expectedPlanet.getName());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void listAll_ReturnsPageOfPlanet_WhenSuccessful(){
        PlanetResponse expectedPlanet = PlanetCreator.createPlanetResponseWithId();
        ResponseEntity<Page<PlanetResponse>> response = planetController.listAllPlanets(PageRequest.of(0, 10));
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody().toList().get(0));
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void delete_RemovesPlanet_WhenSuccessful(){
        Assertions.assertDoesNotThrow(() -> planetController.deletePlanetById(1));
        ResponseEntity<Void> response = planetController.deletePlanetById(1);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
package br.com.bmont.starwars.integration;

import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.repository.PlanetRepository;
import br.com.bmont.starwars.response.PlanetResponse;
import br.com.bmont.starwars.util.PlanetCreator;
import br.com.bmont.starwars.wrapper.PageableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

@AutoConfigureTestDatabase
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class PlanetControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private PlanetRepository planetRepository;

    @Test
    void addPlanet_ReturnsPlanet_WhenSuccessful(){
        PlanetResponse expectedPlanet = PlanetCreator.createPlanetResponseWithId();
        ResponseEntity<PlanetResponse> response = testRestTemplate
                .postForEntity("/planets", PlanetCreator.createPlanetRequest(), PlanetResponse.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
    }

    @Test
    void getById_ReturnsPlanet_WhenSuccessful() {
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        PlanetResponse planet = testRestTemplate.getForObject("/planets/{id}", PlanetResponse.class, planetSaved.getId());
        Assertions.assertNotNull(planet);
        Assertions.assertEquals(planetSaved.getId(), planet.getId());
        Assertions.assertEquals(planetSaved.getName(), planet.getName());
    }

    @Test
    void getByName_ReturnsPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        PlanetResponse planet = testRestTemplate.getForObject("/planets/name/test", PlanetResponse.class);
        Assertions.assertNotNull(planet);
        Assertions.assertEquals(planetSaved.getId(), planet.getId());
        Assertions.assertEquals(planetSaved.getName(), planet.getName());
    }

    @Test
    void listAll_ReturnsPageOfPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        PageableResponse<PlanetResponse> pageableResponse = testRestTemplate.exchange("/planets", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<PlanetResponse>>() {
                }).getBody();
        Assertions.assertNotNull(pageableResponse);
        Assertions.assertEquals(pageableResponse.toList().get(0).getId(), planetSaved.getId());
    }

    @Test
    void delete_RemovesPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        ResponseEntity<Void> response = testRestTemplate
                .exchange("/planets/{id}", HttpMethod.DELETE, null, Void.class, planetSaved.getId());
        Assertions.assertNotNull(response);
        Assertions.assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());

    }

}

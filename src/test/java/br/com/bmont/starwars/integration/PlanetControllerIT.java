package br.com.bmont.starwars.integration;

import br.com.bmont.starwars.model.Planet;
import br.com.bmont.starwars.repository.PlanetRepository;
import br.com.bmont.starwars.util.PlanetCreator;
import br.com.bmont.starwars.wrapper.PageableResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
        Planet expectedPlanet = PlanetCreator.createPlanetWithId();
        ResponseEntity<Planet> response = testRestTemplate
                .postForEntity("/planets", PlanetCreator.createPlanetDTO(), Planet.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(expectedPlanet, response.getBody());
    }

    @Test
    void getById_ReturnsPlanet_WhenSuccessful() {
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        Planet planet = testRestTemplate.getForObject("/planets/{id}", Planet.class, planetSaved.getId());
        Assertions.assertNotNull(planet);
        Assertions.assertEquals(planetSaved, planet);
    }

    @Test
    void getByName_ReturnsPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        Planet response = testRestTemplate.getForObject("/planets/get?name=test", Planet.class);
        Assertions.assertNotNull(response);
        Assertions.assertEquals(planetSaved, response);
    }

    @Test
    void listAll_ReturnsPageOfPlanet_WhenSuccessful(){
        Planet planetSaved = planetRepository.save(PlanetCreator.createPlanetWithoutId());
        PageableResponse<Planet> pageableResponse = testRestTemplate.exchange("/planets", HttpMethod.GET, null,
                new ParameterizedTypeReference<PageableResponse<Planet>>() {
                }).getBody();
        Assertions.assertNotNull(pageableResponse);
        Assertions.assertEquals(pageableResponse.toList().get(0), planetSaved);
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

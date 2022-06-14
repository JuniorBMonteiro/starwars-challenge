package br.com.bmont.starwars.controller;

import br.com.bmont.starwars.request.PlanetRequest;
import br.com.bmont.starwars.response.PlanetResponse;
import br.com.bmont.starwars.service.PlanetService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/planets")
@RequiredArgsConstructor
public class PlanetController {
    private final PlanetService planetService;

    @Operation(summary = "Add Planet")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When the Planet already exists")
    })
    @PostMapping
    public ResponseEntity<PlanetResponse> addPlanet(@RequestBody @Valid PlanetRequest planetRequest){
        return new ResponseEntity<>(planetService.addPlanet(planetRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Get All Planets Paginated")
    @ApiResponse(responseCode = "200", description = "Successful Operation")
    @GetMapping
    public ResponseEntity<Page<PlanetResponse>> listAllPlanets(@ParameterObject Pageable pageable){
        return new ResponseEntity<>(planetService.listAllPlanets(pageable), HttpStatus.OK);
    }

    @Operation(summary = "Get Planet By Name")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Planet does not exist")
    })
    @GetMapping("/name/{name}")
    public ResponseEntity<PlanetResponse> getPlanetByName(@PathVariable String name){
        return new ResponseEntity<>(planetService.getPlanetByName(name), HttpStatus.OK);
    }

    @Operation(summary = "Get Planet By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Planet does not exist")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PlanetResponse> getPlanetById(@PathVariable long id){
        return new ResponseEntity<>(planetService.getPlanetById(id), HttpStatus.OK);
    }

    @Operation(summary = "Delete Planet By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Planet does not exist")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePlanetById(@PathVariable long id){
        planetService.deletePlanetById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

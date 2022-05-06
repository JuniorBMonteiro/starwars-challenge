package br.com.bmont.starwars.repository;

import br.com.bmont.starwars.model.Planet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlanetRepository extends JpaRepository<Planet, Long> {
    Planet findByName(String name);
}

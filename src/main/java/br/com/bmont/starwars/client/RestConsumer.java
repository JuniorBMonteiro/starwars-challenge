package br.com.bmont.starwars.client;

import br.com.bmont.starwars.client.response.PlanetResponse;
import br.com.bmont.starwars.client.response.SwAPIResponse;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@NoArgsConstructor
public class RestConsumer {

    public int getMovieAppearancesByName(String name){
        final int zeroAppearances = 0;
        String url = "https://swapi.dev/api/planets/?search={name}";
        ResponseEntity<SwAPIResponse> response = new RestTemplate()
                .getForEntity(url, SwAPIResponse.class, name);
        Optional<PlanetResponse> planetResponse = response.getBody().getResults().stream().findFirst();
        return planetResponse.isEmpty() ? zeroAppearances : planetResponse.get().getCountFilms();
    }
}

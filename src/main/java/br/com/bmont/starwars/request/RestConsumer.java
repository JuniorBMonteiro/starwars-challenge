package br.com.bmont.starwars.request;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class RestConsumer {

    public int getMovieAppearancesByName(String name){
        ResponseEntity<String> results = new RestTemplate()
                .getForEntity("https://swapi.dev/api/planets/?search={name}", String.class, name);
        if (!results.getBody().contains("films"))
            return 0;
        return filterFilmsNumber(results.getBody());
    }

    private int filterFilmsNumber(String body){
        int startIndex = body.indexOf("\"films");
        int endIndex = body.indexOf("\"created");
        String filter = body.substring(startIndex, endIndex);
        String[] countFilms = filter.split("https");
        return countFilms.length - 1;
    }
}

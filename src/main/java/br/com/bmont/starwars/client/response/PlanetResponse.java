package br.com.bmont.starwars.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PlanetResponse {
    private List<String> films;

    public int getCountFilms(){
        return films.size();
    }
}
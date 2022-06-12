package br.com.bmont.starwars.client.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SwAPIResponse {
    private List<PlanetResponse> results;

    public List<PlanetResponse> getResults() {
        return results;
    }

    public void setResults(List<PlanetResponse> results) {
        this.results = results;
    }
}

package br.com.bmont.starwars.client;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class RestConsumerTest {
    private final RestConsumer restConsumer = new RestConsumer();

    @Test
    void getMovieAppearancesByName_ReturnsCount_WhenSuccessful(){
        int expectedCount = 2;
        int movieAppearancesCount = restConsumer.getMovieAppearancesByName("Alderaan");
        Assertions.assertEquals(movieAppearancesCount, expectedCount);
    }

}

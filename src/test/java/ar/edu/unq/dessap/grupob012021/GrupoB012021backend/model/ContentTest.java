package ar.edu.unq.dessap.grupob012021.GrupoB012021backend.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;


@SpringBootTest
public class ContentTest {

    @Test
    public void testCreateContent() {
        Content content = new Content();
        content.setId(1);
        content.setTitle("title");
        content.setTitleType(TitleType.MOVIE);
        content.setAdult(true);
        content.setStartYear(1990);
        content.setEndYear(1990);
        content.setRuntimeMinutes(150);
        content.setOriginalTitle(true);
        content.setRegion("Argentina");
        content.setGenres(new ArrayList<>());
        content.setEpisodes(new ArrayList<>());
        content.setReviews(new ArrayList<>());
        assertTrue(content.getId() == 1);
        assertTrue(content.getTitle().equals("title"));
        assertTrue(content.getTitleType().equals(TitleType.MOVIE));
        assertTrue(content.isAdult());
        assertTrue(content.getStartYear() == 1990);
        assertTrue(content.getEndYear() == 1990);
        assertTrue(content.getRuntimeMinutes() == 150);
        assertTrue(content.isOriginalTitle());
        assertTrue(content.getRegion().equals("Argentina"));
        assertTrue(content.getGenres().size() == 0);
        assertTrue(content.getEpisodes().size() == 0);
        assertTrue(content.getReviews().size() == 0);

    }
}
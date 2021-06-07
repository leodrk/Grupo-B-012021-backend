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


        assertTrue(Integer.class.isInstance(content.getId()));
        assertTrue(String.class.isInstance(("title")));
        assertTrue(TitleType.class.isInstance((TitleType.MOVIE)));
        assertTrue(Boolean.class.isInstance(content.isAdult()));
        assertTrue(Integer.class.isInstance(content.getStartYear()));
        assertTrue(Integer.class.isInstance(content.getEndYear()));
        assertTrue(Integer.class.isInstance(content.getRuntimeMinutes()));
        assertTrue(Boolean.class.isInstance(content.isOriginalTitle()));
        assertTrue(String.class.isInstance(content.getRegion()));
        assertTrue(ArrayList.class.isInstance(content.getGenres()));
        assertTrue(ArrayList.class.isInstance(content.getEpisodes()));
        assertTrue(ArrayList.class.isInstance(content.getReviews()));

    }
}
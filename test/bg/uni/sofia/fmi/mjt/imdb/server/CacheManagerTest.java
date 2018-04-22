package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Movie;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


public class CacheManagerTest {

    private final static Path MOVIES_DIR = Paths.get("C:\\Users\\User\\IdeaProjects\\IMDb-ClientServer\\cache");
    private final static Path SERIES_DIR = Paths.get("C:\\Users\\User\\IdeaProjects\\IMDb-ClientServer\\cacheSeries");

    private String movieName = "Titanic";
    private Path examplePathToMovieTitanic = Paths.get(MOVIES_DIR + "\\" + movieName + ".json");

    @Test
    public void getPathToExistingFile() {
        Path expected = Paths.get(MOVIES_DIR + "\\" + movieName + ".json");
        Path test = CacheManager.getPathToMovieFile(movieName);
        assertTrue(test.toString().equalsIgnoreCase(expected.toString()));
    }

    @Test
    public void getPathToNonExistingMovie() {
        String movie = "N/A";
        Path test = CacheManager.getPathToMovieFile(movie);
        assertNull(test);
    }

    @Test
    public void getPathToTvSeriesFile() {
        int season = 2;
        String tvSeries = "friends";
        Path expected = Paths.get(SERIES_DIR + "\\" + tvSeries + "_" + season + ".json");
        Path test = CacheManager.getPathToSeriesFile(tvSeries, season);
        assertTrue(test.toString().equalsIgnoreCase(expected.toString()));
    }

    @Test
    public void getPathToNonExitingTvSeries() {
        int season = 2;
        String series = "N/A";
        Path test = CacheManager.getPathToSeriesFile(series, season);
       assertNull(test);
    }


    @Test
    public void readFieldsOfFile() {
        Path path = examplePathToMovieTitanic;
        List<String> fields = new ArrayList<>();
        fields.add("Year");
        try {
            String res = CacheManager.readFieldsOfFile(path, fields);
            assertTrue(res.contains("1997"));
        } catch (IOException e) {
           assertTrue(false);
        }
    }

    @Test
    public void readFieldsOfFileWith2Fields() {
        String searchedMovieName = "Passengers";
        List<Movie> test = CacheManager.getMovies();
        boolean containsMovie = false;
        for(Movie movie : test) {
            if(movie.getName().equalsIgnoreCase(searchedMovieName)) {
                containsMovie = true;
            }
        }
        assertTrue(containsMovie);
    }


}

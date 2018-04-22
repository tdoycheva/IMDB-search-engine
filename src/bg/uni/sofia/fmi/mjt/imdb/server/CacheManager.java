package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Movie;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

class CacheManager {
    private final static Path MOVIES_DIR = Paths.get("C:\\Users\\User\\IdeaProjects\\IMDb-ClientServer\\cacheMovies");
    private final static Path SERIES_DIR = Paths.get("C:\\Users\\User\\IdeaProjects\\IMDb-ClientServer\\cacheSeries");
    private final static String JSON_GLOB = "*.json";
    private final static String JSON_FILE = ".json";
    private final static String JPG_FILE = ".jpg";
    private final static String TITLE_FIELD = "Title";
    private final static String RATING_FIELD = "imdbRating";
    private final static String GENRE_FIELD = "Genre";
    private final static String ACTORS_FIELD = "Actors";


    static Path getPathToMovieFile(String movieName) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(MOVIES_DIR, JSON_GLOB)) {
            for (Path fileOrSubDir : stream) {
                if (fileOrSubDir.getFileName().toString().equalsIgnoreCase(movieName + JSON_FILE)) {
                    return fileOrSubDir.toAbsolutePath();
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            x.printStackTrace();
        }
        return null;
    }

    static Path getPathToSeriesFile(String movieName, int season) {
        String searchedFile = movieName + "_" + season + JSON_FILE;
        ;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(SERIES_DIR, JSON_GLOB)) {
            for (Path file : stream) {
                if (file.getFileName().toString().equalsIgnoreCase(searchedFile)) {
                    return file.toAbsolutePath();
                }
            }
        } catch (IOException | DirectoryIteratorException x) {
            x.printStackTrace();
        }
        return null;
    }


    static String readFieldsOfFile(Path filePath, List<String> fields) throws IOException {
        StringBuilder text = new StringBuilder();
        JSONParser parser = new JSONParser();

        try {
            JSONObject obj = (JSONObject) parser.parse(new FileReader(filePath.toString()));
            for (String field : fields) {
                String jsonValue = (String) obj.get(field);
                if (jsonValue != null) {
                    String currentField = field.toUpperCase() + ": " + jsonValue + " ";
                    text.append(currentField);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return text.toString();
    }

    //collects all movies in the cache folder in a list of objects
    static List<Movie> getMovies() {
        List<Movie> movies = new ArrayList<>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(MOVIES_DIR, JSON_GLOB)) {
            for (Path file : stream) {
                Movie movie = new Movie();
                JSONParser parser = new JSONParser();
                JSONObject obj = (JSONObject) parser.parse(new FileReader(file.toString()));
                movie.setName((String) obj.get(TITLE_FIELD));
                movie.setGenre((String) obj.get(GENRE_FIELD));
                String jsonRating = (String) obj.get(RATING_FIELD);
                if (!jsonRating.equalsIgnoreCase("N/A")) {
                    movie.setRating(Double.parseDouble(jsonRating));
                }
                movie.setActors((String) obj.get(ACTORS_FIELD));
                movies.add(movie);
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return movies;
    }

    //caches tv-series file
    static void createNewTvSeriesFile(String content, String movieName, int season) throws IOException {
        String name = "\\" + movieName + "_" + season + JSON_FILE;
        PrintWriter writer = new PrintWriter(SERIES_DIR.toString() + name, "UTF-8");
        writer.print(content);
        writer.close();
    }

    //caches movie file
    static void createNewMovieFile(String content, String movieName) throws IOException {
        String name = "\\" + movieName + JSON_FILE;
        PrintWriter writer = new PrintWriter(MOVIES_DIR.toString() + name, "UTF-8");
        writer.print(content);
        writer.close();
    }

}


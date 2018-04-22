package bg.uni.sofia.fmi.mjt.imdb.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Movie implements IMovie {

    private static final String COMMA = ",";
    private static final String SPACE = " ";
    private static final String EMPTY = "";
    private String name;
    private double rating;
    private List<String> genres;
    private List<String> actors;

    public Movie() {
        actors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public void setGenre(String genreStr) {
        //the format of genreStr = "Comedy, Drama, Music"
        String genresRemovedCommas = genreStr.replaceAll(COMMA, EMPTY);
        String[] genresArray = genresRemovedCommas.split(SPACE);
        Collections.addAll(genres, genresArray);
    }

    public void setActors(String actorsStr) {
        //the format of actorsStr = "Ryan Gosling, Emma Stone, Terry Walters"
        String[] buff = actorsStr.split(COMMA);
        for (String actor : buff) {
            actors.add(actor.trim());
        }
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public List<String> getGenre() {
        return genres;
    }

    public List<String> getActors() {
        return actors;
    }

    @Override
    public String toString() {
        return name + ", Rating = " + rating +
                ", Genres = " + genres + ", Actors = " + actors;
    }
}

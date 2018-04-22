package bg.uni.sofia.fmi.mjt.imdb.core;

import java.util.List;

public interface IMovie {

    String getName();
    double getRating();
    List<String> getGenre();
    List<String> getActors();
}

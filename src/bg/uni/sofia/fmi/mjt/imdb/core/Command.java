package bg.uni.sofia.fmi.mjt.imdb.core;

public enum Command {
    GET_MOVIE("get-movie"),
    GET_MOVIES("get-movies"),
    GET_TV_SERIES("get-tv-series"),
    QUIT("quit");

    private final String semantics;

    Command(String semantics) {
        this.semantics = semantics;
    }


    @Override
    public String toString() {
        return semantics;
    }

}

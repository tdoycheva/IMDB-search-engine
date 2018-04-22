package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Command;
import bg.uni.sofia.fmi.mjt.imdb.core.Movie;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

class AllMoviesCommand implements ICommand {

    private List<String> actors;
    private List<String> genres;
    private String order;

    private static final String FIELD_ORDER = "order";
    private static final String FIELD_GENRES = "genres";
    private static final String FIELD_ACTORS = "actors";
    private static final int LENGTH_OF_ORDER = 7;
    private static final int LENGTH_OF_GENRES = 8;
    private static final int LENGTH_OF_ACTORS = 8;
    private static final String ASCENDING_ORDER = "asc";
    private static final String DESCENDING_ORDER = "desc";
    private static final char CLOSING_BRACKET = ']';
    private static final String DELIMITER = "  |  ";


    public AllMoviesCommand(String commandWithoutID) throws CommandTypoException {
        genres = new ArrayList<>();
        actors = new ArrayList<>();
        //fills in actors
        int actorsStart = commandWithoutID.indexOf(FIELD_ACTORS);
        if (actorsStart == -1) {
            throw new CommandTypoException("get-movies command must has actors=[]! ");
        }
        int actorsEnd = commandWithoutID.length() - 1;
        String[] actorsArray = commandWithoutID.substring(actorsStart + LENGTH_OF_ACTORS, actorsEnd).split(COMMA);
        for (String actor : actorsArray) {
            actors.add(actor.trim());
        }
        //gets order
        if (commandWithoutID.contains(FIELD_ORDER)) {
            int firstLetterIndex = commandWithoutID.indexOf(FIELD_ORDER) + LENGTH_OF_ORDER;
            order = commandWithoutID.charAt(firstLetterIndex) == 'a' ? ASCENDING_ORDER : DESCENDING_ORDER;
        }
        //fills in genres
        if (commandWithoutID.contains(FIELD_GENRES)) {
            int istart = commandWithoutID.indexOf(FIELD_GENRES) + LENGTH_OF_GENRES;
            int iend = istart;
            while (commandWithoutID.charAt(iend) != CLOSING_BRACKET) {
                iend++;
            }
            String genresStr = commandWithoutID.substring(istart, iend).replaceAll(COMMA, EMPTY);
            String[] genresArray = genresStr.split(SPACE);
            Collections.addAll(genres, genresArray);
        }

    }

    @Override
    public String getCommandId() {
        return Command.GET_MOVIES.toString();
    }

    @Override
    public byte[] process() throws IOException {
        List<Movie> movies = CacheManager.getMovies();
        int multiplier = DESCENDING_ORDER.equals(order) ? -1 : 1;
        Predicate<Movie> filterByActors = (Movie m) -> m.getActors().stream().anyMatch(actors::contains);

        List<Movie> filteredActors = movies.stream().
                filter(filterByActors).
                sorted((e1, e2) -> Double.compare(multiplier * e1.getRating(), multiplier * e2.getRating())).
                collect(Collectors.toList());

        List<Movie> finalList = filteredActors;
        if (!genres.isEmpty()) {
            finalList = filterByGenres(filteredActors);
        }
        return finalList.stream().map(Movie::toString).
                collect(Collectors.joining(DELIMITER)).getBytes();
    }

    private List<Movie> filterByGenres(List<Movie> movies) {
        Predicate<Movie> filterByGenres = (Movie m) -> m.getGenre().stream().anyMatch(genres::contains);
        return movies.stream().
                filter(filterByGenres).
                collect(Collectors.toList());
    }

    public List<String> getActors() {
        return actors;
    }
}

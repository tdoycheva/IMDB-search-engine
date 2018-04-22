package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Command;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.UnknownCommandException;


public class CommandFactory {

    private static final String REGEX_SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final int indexOfCommandID = 0;

    public static ICommand factory(String clientRequest) throws CommandTypoException, UnknownCommandException {
        String[] parseInput = clientRequest.split(REGEX_SPACE);

        String commandId = parseInput[indexOfCommandID];
        String commandWithoutID = clientRequest.replace(commandId, EMPTY_STRING).trim();

        if (Command.GET_MOVIE.toString().equalsIgnoreCase(commandId)) {
            return new MovieCommand(commandWithoutID);
        } else if (Command.GET_MOVIES.toString().equalsIgnoreCase(commandId)) {
            return new AllMoviesCommand(commandWithoutID);
        } else if (Command.GET_TV_SERIES.toString().equalsIgnoreCase(commandId)) {
            return new TvSeriesCommand(commandWithoutID);
        } else {
            throw new UnknownCommandException("Unknown command!");
        }

    }
}

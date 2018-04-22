package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Command;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class MovieCommand implements ICommand {

    private String movieName;
    private List<String> fields;
    private static final String OPENING_BRACKET = "[";
    private static final String CLOSING_BRACKET = "]";


    MovieCommand(String commandWithoutID) throws CommandTypoException {
        movieName = parseMovieName(commandWithoutID);
        fields = new ArrayList<>();
        //fill in fields
        if (commandWithoutID.contains(OPENING_BRACKET)) {
            int istart = commandWithoutID.indexOf(OPENING_BRACKET);
            int iend = commandWithoutID.indexOf(CLOSING_BRACKET);
            String fieldsString = commandWithoutID.substring(istart + 1, iend).replaceAll(COMMA, EMPTY);
            String[] fieldsArray = fieldsString.split(SPACE);
            Collections.addAll(fields, fieldsArray);
        }
    }

     private String parseMovieName(String commandWithoutId) throws CommandTypoException {
        int startOfFields = commandWithoutId.indexOf(OPENING_BRACKET);
        int endOfName = startOfFields != -1 ? startOfFields - 1 : commandWithoutId.length();
        return commandWithoutId.substring(0, endOfName);
    }

    @Override
    public String getCommandId() {
        return Command.GET_MOVIE.toString();
    }

    public String getMovieName() {
        return movieName;
    }

    public List<String> getFields() {
        return fields;
    }

    @Override
    public  byte[] process() throws IOException {
        Path filePath = CacheManager.getPathToMovieFile(movieName);
        String movie;
        if (filePath != null) {
            //search in cache
            if (fields.isEmpty()) {
                movie = new String(Files.readAllBytes(filePath));
            } else {
                movie = CacheManager.readFieldsOfFile(filePath, fields);
            }
        } else {
            //search in imdb
            movie = OmdbApiManager.getMovieInfo(this);
            CacheManager.createNewMovieFile(movie, movieName);
        }
        return movie.getBytes();
    }


}


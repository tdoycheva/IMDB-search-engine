package bg.uni.sofia.fmi.mjt.imdb.server;


import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class MovieCommandTest {


    @Test
    public void parseFieldsForCommand() {
        String commandWithoutId = "La la land [Actors, Rated]";
        String movieName = "La la land";
        List<String> fields = new ArrayList<>();
        fields.add("Actors");
        fields.add("Rated");
        try {
            MovieCommand command = new MovieCommand(commandWithoutId);
            List<String> test = command.getFields();
            assertTrue(test.containsAll(fields));
        } catch (CommandTypoException e) {
           fail();
        }
    }

    @Test
    public void parseMovieNameFromCommandWithoutId() {
        String commandWithoutId = "La la land [Actors]";
        String movieName = "La la land";
        try {
            MovieCommand command = new MovieCommand(commandWithoutId);
            int n = 1;
            assertTrue(command.getMovieName().equals(movieName));
        } catch (CommandTypoException e) {
            fail();
        }
    }

}

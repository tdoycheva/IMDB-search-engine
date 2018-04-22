package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;
import org.junit.Test;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertTrue;

public class AllMoviesCommandTest {


    // //get-movies actors=[]

    @Test
    public void parseSeriesNameFromCommandWithoutId() {
        String commandWithoutId = "actors=[Emma Stone, Ryan Gosling]";
        String actor = "Emma Stone";
        try {
            AllMoviesCommand command = new AllMoviesCommand(commandWithoutId);
            assertTrue(command.getActors().contains("Emma Stone"));

        } catch (CommandTypoException e) {
            assertTrue(false);
        }
    }

    @Test
    public void commandWithoutActorsShouldThrowException() {
        String commandWithoutId = "order=[asc] genre=[Drama]";

        try {
            AllMoviesCommand command = new AllMoviesCommand(commandWithoutId);
            fail();

        } catch (CommandTypoException e) {
            assertTrue(true);
        }
    }



}

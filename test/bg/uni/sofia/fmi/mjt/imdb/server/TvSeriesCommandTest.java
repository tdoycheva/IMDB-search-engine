package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class TvSeriesCommandTest {

    //get-tv-series <name> season=1

    @Test
    public void parseSeriesNameFromCommandWithoutId() {
        String commandWithoutId = "friends season=5";
        String name = "friends";
        try {
            TvSeriesCommand command = new TvSeriesCommand(commandWithoutId);

            assertTrue(command.getMovieName().equals(name));
        } catch (CommandTypoException e) {
            fail();
        }
    }

    @Test
    public void parseSeasonFromCommandWithoutId() {
        String commandWithoutId = "friends season=5";
        int season = 5;
        try {
            TvSeriesCommand command = new TvSeriesCommand(commandWithoutId);
            assertEquals(command.getSeason(), season);
        } catch (CommandTypoException e) {
            fail();
        }
    }

    @Test
    public void commandWithoutSeasonFieldShouldThrowException() {
        String commandWithoutId = "friends";
        try {
            TvSeriesCommand command = new TvSeriesCommand(commandWithoutId);
            fail();
        } catch (CommandTypoException e) {
            //Should come here
            assertTrue(true);
        }
    }
}
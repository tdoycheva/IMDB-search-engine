package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.UnknownCommandException;
import org.junit.Test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class CommandFactoryTest {


    @Test
    public void testCreatingGetMovieCommand() {
        String movieCommand = "get-movie La la land fields=[field_1, field_2]";
        try {
            ICommand command = CommandFactory.factory(movieCommand);
            assertTrue("get-movie".equals(command.getCommandId()));
        } catch (CommandTypoException | UnknownCommandException e) {
           fail();
        }
    }

    @Test
    public void testCreatingGetMoviesCommand() {
        String moviesCommand = "get-movies actors=[actor_1, actor_2]";
        try {
            ICommand command = CommandFactory.factory(moviesCommand);
            assertTrue("get-movies".equals(command.getCommandId()));
        } catch (CommandTypoException | UnknownCommandException e) {
            fail();
        }
    }

    @Test
    public void testCreatingGetTvSeriesCommand() {
        String seriesCommand = "get-tv-series movieName season=10";
        try {
            ICommand command = CommandFactory.factory(seriesCommand);
            assertTrue("get-tv-series".equals(command.getCommandId()));
        } catch (CommandTypoException | UnknownCommandException e) {
            assertTrue(false);
        }
    }

    @Test
    public void testCreatingGetPosterCommand() {
        String posterCommand = "get-movie-poster movieName";
        try {
            ICommand command = CommandFactory.factory(posterCommand);
            assertTrue("get-movie-poster".equals(command.getCommandId()));
        } catch (CommandTypoException | UnknownCommandException e) {
            fail();
        }
    }

    @Test
    public void testCreatingUnknownCommand() {
        String unknownCommand = "N/A";
        try {
            ICommand command = CommandFactory.factory(unknownCommand);
            fail();
        } catch (CommandTypoException | UnknownCommandException e) {
            assertTrue(true);
        }
    }

}

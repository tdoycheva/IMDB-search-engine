package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.core.Command;
import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

class TvSeriesCommand implements ICommand {


    private String movieName;
    private int season;
    private static final String FIELD_IDENTIFIER = "=";
    private static final String SEASON_FIELD = "season";

     TvSeriesCommand(String commandWithoutID) throws CommandTypoException {

        movieName = parseMovieName(commandWithoutID);

        int startOfSeason = commandWithoutID.indexOf(FIELD_IDENTIFIER);
        if (startOfSeason == -1) {
            throw new CommandTypoException("get-tv-series command must has season=<value>! ");
        }
        String seasonStr = commandWithoutID.substring(startOfSeason + 1);
        season = Integer.parseInt(seasonStr);
    }


    private String parseMovieName(String commandWithoutId) throws CommandTypoException {
        int startOfSeason = commandWithoutId.indexOf(SEASON_FIELD);
        if (startOfSeason == -1) {
            throw new CommandTypoException("get-tv-series command must has season=<value>! ");
        }
        int endOfName = startOfSeason - 1;
        return commandWithoutId.substring(0, endOfName);
    }

    @Override
    public String getCommandId() {
        return Command.GET_TV_SERIES.toString();
    }

    @Override
    public byte[] process() throws IOException {
        Path filePath = CacheManager.getPathToSeriesFile(movieName, season);
        String episodes;
        if (filePath != null) {
            episodes = new String(Files.readAllBytes(filePath));
        } else {
            episodes = OmdbApiManager.getTvSeriesInfo(this);
            CacheManager.createNewTvSeriesFile(episodes, movieName, season);
        }
        return episodes.getBytes();
    }

    public String getMovieName() {
        return movieName;
    }

    public int getSeason() {
        return season;
    }
}

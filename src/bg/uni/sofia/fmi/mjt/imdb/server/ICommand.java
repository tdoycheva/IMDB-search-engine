package bg.uni.sofia.fmi.mjt.imdb.server;

import bg.uni.sofia.fmi.mjt.imdb.exceptions.CommandTypoException;

import java.io.IOException;

interface ICommand {
    String COMMA = ",";
    String EMPTY = "";
    String SPACE = " ";

    String getCommandId();

    byte[] process() throws IOException, CommandTypoException;

}

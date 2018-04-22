package bg.uni.sofia.fmi.mjt.imdb.server;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

import static bg.uni.sofia.fmi.mjt.imdb.server.ICommand.SPACE;

class OmdbApiManager {
    private static final String ACCESS_KEY = "ffff";
    private static final String BASE_URL = "http://www.omdbapi.com/?t=";
    private static final String EPISODES_FIELD = "Episodes";


   private static CloseableHttpClient httpClient = HttpClients.createDefault();

   //format movie names which contain whitespaces
     static String removeSpacesFromName(String movieName) {
        if (movieName.contains(SPACE)) {
            String[] buff = movieName.split(SPACE);
            StringBuilder newName = new StringBuilder();
            for (int i = 0; i < buff.length - 1; i++) {
                newName.append(buff[i]);
                newName.append("+");
            }
            newName.append(buff[buff.length - 1]);
            return newName.toString();
        }
        return movieName;
    }

     static String getMovieInfo(MovieCommand command) {
        String validMovieName = OmdbApiManager.removeSpacesFromName(command.getMovieName());
        String movie = null;
        HttpGet get = new HttpGet(BASE_URL + validMovieName + "&apikey=" + ACCESS_KEY);
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONParser parser = new JSONParser();

            JSONObject obj = (JSONObject) parser.parse(EntityUtils.toString(entity));
            movie = obj.toJSONString();

        } catch (IOException | org.apache.http.ParseException | JSONException | org.json.simple.parser.ParseException e) {

            e.printStackTrace();
        }
        return movie;
    }


     static String getTvSeriesInfo(TvSeriesCommand command) {
        String content = null;
        String validMovieName = OmdbApiManager.removeSpacesFromName(command.getMovieName());
        HttpGet get = new HttpGet(BASE_URL + validMovieName + "&season=" + command.getSeason() + "&apikey=" + ACCESS_KEY);
        try {
            CloseableHttpResponse response = httpClient.execute(get);
            HttpEntity entity = response.getEntity();
            JSONParser parser = new JSONParser();

            JSONObject obj = (JSONObject) parser.parse(EntityUtils.toString(entity));
            JSONArray jsonArr = (JSONArray) obj.get(EPISODES_FIELD);
            content = jsonArr.toJSONString();
        } catch (ParseException | IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
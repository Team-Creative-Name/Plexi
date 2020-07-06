package com.github.tcn.plexi.ombi;


import com.github.tcn.plexi.ombi.templateClasses.movies.moreInfo.MovieInfo;
import com.github.tcn.plexi.ombi.templateClasses.movies.search.MovieSearch;
import com.github.tcn.plexi.ombi.templateClasses.requests.movie.MovieRequest;
import com.github.tcn.plexi.ombi.templateClasses.requests.tv.jsonTemplate.TvRequestTemplate;
import com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.github.tcn.plexi.ombi.templateClasses.tv.search.TvSearch;
import com.github.tcn.plexi.ombi.templateClasses.tv.tvLite.TvLite;
import com.github.tcn.plexi.settingsManager.Settings;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;

/**
 * OmbiCallers is the heart of Plexi.
 * <br>It is responsible for making almost every request to the ombi API. Contained in this class are methods that call
 * Ombi endpoints and return objects of the resulting data
 */
public class OmbiCallers {

    Settings settings;

    public OmbiCallers() {
        //get Settings reference
        settings = Settings.getInstance();
    }

    /**
     * Forms a connection to the Ombi API and performs a search for the string passed via the toSearch parameter.
     * The downloaded JSON is then converted into a TvSearch array via {@link Gson#fromJson(String downloadedJSON, Type TvSearch[].class)}
     *
     * @param toSearch The String that should be passed to the Ombi search API
     * @return An array of {@link TvSearch TvSearch} objects. Each object is one search result
     * @implNote This method gets its info via the {@code /api/v1/Search/tv/{string}} Ombi endpoint
     */
    //This method returns an Object array that contains the value returned by the Ombi Api
    public TvSearch[] ombiTvSearch(String toSearch) {

        //Create new objects
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        //write request to console
        System.out.println("Searching Ombi TV for: " + toSearch);

        //Create the request
        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/Search/tv/" + formatSearchTerm(toSearch))
                .addHeader("accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            //Download the Json returned by the API and make it into a string
            String downloadedJson = response.body().string();

            //Pass the String to Gson and have it turned into a TvSearch Array
            TvSearch[] result = gson.fromJson(downloadedJson, TvSearch[].class);
            //Log the number of items in the array
            System.out.println("The search result  is " + result.length + " page(s) long");
            //return the array
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("error");
        return null;
    }

    /**
     * Forms a connection to the Ombi API and performs a search for the string passed via the toSearch parameter.
     * The downloaded JSON is then converted into a MovieSearch array via {@link Gson#fromJson(String downloadedJSON, Type MovieSearch[].class)}
     *
     * @param toSearch The String that should be passed to the Ombi search API
     * @return An array of {@link MovieSearch MovieSearch} objects. Each object is one search result
     * @implNote This method gets its info via the {@code /api/v1/Search/movie/{string}} Ombi endpoint
     */
    public MovieSearch[] ombiMovieSearch(String toSearch) {
        //Create new objects
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        //write request to console
        System.out.println("Searching Ombi Movie for: " + toSearch);

        //Create the request
        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/Search/movie/" + formatSearchTerm(toSearch))
                .addHeader("accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {

            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            //Download the Json returned by the API and make it into a string
            String downloadedJson = response.body().string();
            //Pass the String to Gson and have it turned into a TvSearch Array
            MovieSearch[] result = gson.fromJson(downloadedJson, MovieSearch[].class);
            //Log the number of items in the array
            System.out.println("The result is " + result.length + " page(s) long");
            //return the array
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("error");
        return null;
    }

    /**
     * Forms a connection to the Ombi API and requests more info about a tv show given its TVDb id number.
     * The downloaded JSON is then converted into a TVInfo object via {@link Gson#fromJson(String downloadedJSON, Type TvInfo.class)}
     *
     * @param id The TVDb ID number of the show you want more info on
     * @return A {@link TvInfo} object
     * @implNote This method gets its info via the {@code /api/v1/search/tv/info/{id}} endpoint
     */
    //Methods to get the JSON for the more info endpoint of the Ombi API
    public TvInfo ombiTvInfo(String id) {

        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        //write request to console
        System.out.println("Getting More TV Info for: " + id);

        //create the request
        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/search/tv/info/" + id)
                .addHeader("accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code: " + response);
            }

            String downloadedJSON = response.body().string();

            return gson.fromJson(downloadedJSON, TvInfo.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Forms a connection to the Ombi API and requests more info about a movie given its TMDb id number.
     * The downloaded JSON is then converted into a MovieInfo object via {@link Gson#fromJson(String downloadedJSON, Type MovieInfo.class)}
     *
     * @param id The TMDb id number of the movie you want more info on
     * @return A {@link MovieInfo} object
     * @implNote This method gets its info via the {@code /api/v1/search/movie/info/{id}} endpoint
     */
    public MovieInfo ombiMovieInfo(String id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        //write request to console
        System.out.println("Making A Movie Info Request for: " + id);

        //create the request
        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/search/movie/info/" + id)
                .addHeader("accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code: " + response);
            }

            String downloadedJSON = response.body().string();
            return gson.fromJson(downloadedJSON, MovieInfo.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Forms a connection to the Ombi API and requests a movie given its TMDb id number.
     * The downloaded JSON is converted into an object internally and the response is extracted from that.
     *
     * @param id The TMDb id number of the movie to request
     * @return The string response from the API
     * @implNote This method performs the request via the {@code /api/v1/request/movie} endpoint
     */
    public String requestMovie(String id) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();


        RequestBody requestBody = RequestBody.create("{\"theMovieDbId\": " + id + ",\"languageCode\":\"string\"}", MediaType.parse("text"));
        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/request/movie")
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .addHeader("Content-Type", "text/json")
                .build();

        try {
            Response response = client.newCall(request).execute();

            String downloadedJSON = response.body().string();

            //write request to console
            System.out.println("Making a movie request for: " + id);


            MovieRequest requestObj = gson.fromJson(downloadedJSON, MovieRequest.class);

            if (requestObj.getIsError()) {
                return requestObj.getErrorMessage().toString();
            } else {
                return requestObj.getMessage().substring(0, requestObj.getMessage().length() - 1) + " to the request list";
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Unable to request media";
    }

    /**
     * Forms a connection to the Ombi API and requests a TV show given its TVDb id number, tvInfo object, and if you want to request
     * only the latest episodes.
     *
     * @param latest A boolean value that states if the request should only request the latest episodes of a show
     * @param tvInfo A {@link TvInfo} object build from the show that you want to request
     * @return A string stating request success status
     * @implNote This method performs the request via the {@code /api/v1/request/tv} endpoint
     */
    public String requestTv(boolean latest, TvInfo tvInfo) {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new Gson();
        RequestBody requestBody;

        //get a reference to the TMDB id for easy access later
        String id = tvInfo.getTheTvDbId();

        //fully available == 2 partial == 1 not == 0

        //We need to form the request differently depending on if the show is available already or not. We also need to check to see if it has been requested.
        //if this check passes, there are no episodes on plex and there are no existing requests for a show.
        if (tvInfo.getPlexAvailabilityInt() == 0 && !tvInfo.getRequested()) {
            //Check to see if the user only wants to request the latest season
            if (latest) {
                //In this case, nothing is available and the user wants to request everything
                requestBody = RequestBody.create("{" + "\"latestSeason\": " + "\"true\"" +
                        ",\"tvDbId\": " + id +
                        ",\"languageCode\":\"string\"}", MediaType.parse("text"));
            } else {
                //In this case, nothing is available and the user wants to request everything
                requestBody = RequestBody.create("{" + "\"requestAll\": " + "\"true\"" +
                                ",\"tvDbId\": " + id +
                                ",\"languageCode\":\"string\"}",
                        MediaType.parse("text"));
            }

        }else{


            //here, we have a show that already has some episodes on plex or has requests on ombi. We need to determine which ones they are and request the others
            String toJSON;


            //check to see if the user only wants the latest season
            TvRequestTemplate requestTemplate;
            if (latest) {
                requestTemplate = tvInfo.getLatestMissingSeasonArray();
                requestTemplate.setLatestSeason(true);

            } else {
                requestTemplate = tvInfo.getMissingEpisodeArray();

            }
            requestTemplate.setTvDbId(Integer.decode(id));
            toJSON = gson.toJson(requestTemplate);
            requestBody = RequestBody.create(toJSON, MediaType.parse("text"));
        }

        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/request/tv")
                .post(requestBody)
                .addHeader("Accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .addHeader("Content-Type", "text/json")

                .build();

        try {
            Response response = client.newCall(request).execute();

            String downloadedJSON = response.body().string();

            //write request to console
            System.out.println("Making a tv request for: " + id);

            MovieRequest requestObj = gson.fromJson(downloadedJSON, MovieRequest.class);

            if (requestObj.getIsError()) {
                return requestObj.getErrorMessage().toString();
            } else {
                //inform the user that media was requested
                if (latest) {
                    return "The latest season from " + tvInfo.getTitle() + " has been requested!";
                } else {
                    return tvInfo.getTitle() + " has been successfully requested!";
                }
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "Unable to add media to request list";
        }

    }

    /**
     * Creates a String ArrayList containing all episodes that are not marked as available and are not requested in ombi.
     * If an episode is missing and requested, it WILL NOT be in this list.
     *
     * @implNote
     *          This method gets its information from {@link OmbiCallers#ombiTvInfo(String id)}
     *
     * @param id
     *          The TVDb id number of the show
     * @return
     *          An ArrayList containing all missing && not requested episodes of a show
     */
    public ArrayList<String> getMissingEpisodeArray(String id) {

        //get the TvInfo Object
        TvInfo tvInfo = ombiTvInfo(id);

        //get the TvRequest Template object that contains the missing episodes
        TvRequestTemplate missingEpisodes = tvInfo.getMissingEpisodeArray();

        ArrayList<String> missingEpisodeslist = new ArrayList<>();


        for (int i = 0; i < missingEpisodes.getSeasons().size(); i++) {
            for (int j = 0; j < missingEpisodes.getSeasons().get(i).getRequestEpisodes().size(); j++) {
                missingEpisodeslist.add("Missing S" + (i + 1) + "E" + missingEpisodes.getSeasons().get(i).getRequestEpisodes().get(j).getEpisodeNumber());
            }
        }

        return missingEpisodeslist;

    }


    /**
     * Forms a connection to Ombi and requests a list of all requested TV shows in the form of a TvLite object array. The downloaded
     * JSON is then converted into a {@link TvLite TvLite} array via {@link Gson#fromJson(String downloadedJSON, Type TvLite[].class)}
     *
     * @return An Array of TvLite objects
     * @implNote This method performs the request via the {@code /api/v1/request/tvlite} endpoint
     */
    public TvLite[] getTvLiteArray() {
        OkHttpClient client = new OkHttpClient();
        Gson gson = new GsonBuilder().disableHtmlEscaping().create();

        //create the request
        Request request = new Request.Builder()
                .url(settings.getOmbiUrl() + "/api/v1/Request/tvlite")
                .get()
                .addHeader("accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected Code: " + response);
            }
            String downloadedJSON = response.body().string();
            return gson.fromJson(downloadedJSON, TvLite[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * Forms a connection to Ombi and counts the amount of time it took for it to respond to an API request.
     *
     * @implNote
     *      This method gets info via the {@code /api/v1/Status} endpoint which takes ombi very little time to calculate.
     *      This means that this method will return the best case response time. Other endpoints in Ombi will take a much
     *      longer time to respond.
     * @return
     *      the amount of time it took for Ombi to respond in milliseconds
     */
    //Get the amount of time that it takes to communicate with the ombi AP in MS
    public long getPingTime() {
        long responseTime = -1;
        Response response = null;
        try {
            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url(settings.getOmbiUrl() + "/api/v1/Status")
                    .addHeader("Accept", "application/json")
                    .addHeader("ApiKey", settings.getOmbiKey())
                    .build();

            response = client.newCall(request).execute();
            //if that call didnt fail, we were able to connect

        } catch (Exception e) {
            //if that call failed, we couldn't connect to ombi
            return -1;
        } finally {
            if (response != null) {
                response.close();
                responseTime = (response.receivedResponseAtMillis() - response.sentRequestAtMillis());
            }
        }
        return responseTime;
    }

    /**
     * Given a request id and the media type, this method removes a request from Ombi. This CANNOT be passed the media ID,
     * which means that you must get the request ID from another API call.
     * <p><br></p>
     * For movies, see {@link MovieInfo#getId()}
     * <p><br></p>
     * For TV, the TvInfo API call seems to provide an invalid request id. This means that you MUST get it another way.
     * One way is via looping through {@link OmbiCallers#getTvLiteArray()} until you find a matching TVDb ID. (Annoying, I know)
     * <p><br></p>
     * @implNote This method completes the Delete action via the {@code /api/v1/Request/tv/{REQUEST_ID}} and {@code /api/v1/Request/movie/{REQUEST_ID}} endpoints
     * @param requestID
     *          The internal Ombi request ID for a movie or show
     * @param mediaType
     *          The media type of the object. 1 == TV Show || 2 == Movie
     * @return
     *          {@code false} if the API fails to remove the request, {@code true} otherwise.
     */
    //remove media request
    public boolean removeMediaRequest(int requestID, int mediaType) {
        OkHttpClient client = new OkHttpClient();
        String url = null;


        if (mediaType == 1) {//tv
            url = settings.getOmbiUrl() + "/api/v1/Request/tv/" + requestID;
        } else if (mediaType == 2) {
            url = settings.getOmbiUrl() + "/api/v1/Request/movie/" + requestID;
        }

        Request request = new Request.Builder()
                .url(url) //this has a possibility to be null if passed an invalid media type. Should still return false in this case
                .delete()
                .addHeader("Accept", "application/json")
                .addHeader("ApiKey", settings.getOmbiKey())
                .addHeader("Content-Type", "text/json")
                .build();

        try {
            Response response = client.newCall(request).execute();

            //write request to console
            System.out.println("removing request ID" + requestID);
            response.close();

            //return true if delete was successful
            return response.isSuccessful();

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }


    /**
     * Changes a string to be more URL friendly
     * @param query
     *          The String to format
     * @return
     *          The formatted String
     */
    //Helper methods
    private String formatSearchTerm(String query) {
        String formattedString = query;

        formattedString = formattedString.toLowerCase().replaceAll(" ", "%20");
        formattedString = formattedString.replaceAll("\"", " ");
        formattedString = formattedString.replaceAll("/", " ");
        //format searchQuery
        return formattedString;
    }

}

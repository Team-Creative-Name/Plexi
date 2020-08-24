package com.github.tcn.plexi.discordBot;

import com.github.tcn.plexi.ombi.OmbiCallers;
import com.github.tcn.plexi.ombi.templateClasses.movies.moreInfo.MovieInfo;
import com.github.tcn.plexi.ombi.templateClasses.movies.requestList.MovieRequestList;
import com.github.tcn.plexi.ombi.templateClasses.movies.search.MovieSearch;
import com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.github.tcn.plexi.ombi.templateClasses.tv.search.TvSearch;
import com.github.tcn.plexi.settingsManager.Settings;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

//This class will create the embeds and post them
public class EmbedManager {

    /**
     * Creates a TvSearch Embed
     * <br>
     * Takes in a {@link TvSearch TvSearch} array and the desired index number to form a {@link EmbedBuilder} object containing a show's:
     * <ul>
     * <li>Name
     * <li>Description
     * <li>TVDb ID
     * <li>Original Airing Network
     * <li>Airing status {Running | Ended}
     * <li>Cover art
     * <li>Page Number (place in array + 1)
     * </ul>
     *
     * @param resultArray A {@link TvSearch TvSearch} array with all values filled
     * @param resultNum   The index of the show that needs to be put into an embed.
     * @return A {@link EmbedBuilder} object containing above information.
     */
    //search methods
    public EmbedBuilder createTVSearchEmbed(TvSearch[] resultArray, int resultNum) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00AE86));
        eb.setTitle(stringVerifier(resultArray[resultNum].getTitle(), 1), getTvDbUrl(resultArray[resultNum].getId()));
        eb.setDescription(stringVerifier(resultArray[resultNum].getOverview(), 2));
        eb.addField("TVDB ID:", stringVerifier(resultArray[resultNum].getId().toString(), 3), true);
        eb.addField("Network:", stringVerifier(resultArray[resultNum].getNetwork(), 4), true);
        eb.addField("Status:", stringVerifier(resultArray[resultNum].getStatus(), 5), true);
        if (resultArray[resultNum].getBanner() != null) {
            eb.setImage(stringVerifier(resultArray[resultNum].getBanner(), 1));
        } else {
            eb.setImage("https://cdn.discordapp.com/attachments/592540131097837578/656822685912793088/poster.png");
        }
        //eb.setFooter(getRandFooter(settings.getFooterMax()));
        eb.setFooter(stringVerifier("Page " + (resultNum + 1) + " of " + (resultArray.length), 7));
        return eb;
    }

    /**
     * Creates a MovieSearch Embed
     * <br>
     * Takes in a {@link MovieSearch} array and the desired index number to form a {@link EmbedBuilder} object containing a movie's:
     * <ul>
     * <li>Name
     * <li>Description
     * <li>TMDb ID
     * <li>Release Date
     * <li>Cover art
     * <li>Page Number (place in array + 1)
     * </ul>
     *
     * @param resultArray A {@link MovieSearch MovieSearch} array with all values filled
     * @param resultNum   The index of the movie that needs to be put into an embed.
     * @return A {@link EmbedBuilder} object containing above information.
     */
    public EmbedBuilder createMovieSearchEmbed(MovieSearch[] resultArray, int resultNum) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00AE86));
        eb.setTitle(stringVerifier(resultArray[resultNum].getTitle(), 1), getTMDbURL(resultArray[resultNum].getTheMovieDbId()));
        eb.setDescription(stringVerifier(resultArray[resultNum].getOverview(), 2));
        eb.addField("TMDb ID:", stringVerifier(resultArray[resultNum].getTheMovieDbId(), 3), true);
        eb.addField("Release date", stringVerifier(resultArray[resultNum].getReleaseDate(), 8), true);
        if (resultArray[resultNum].getPosterPath() != null) {
            eb.setImage(stringVerifier("https://image.tmdb.org/t/p/original" + resultArray[resultNum].getPosterPath(), 1));
        } else {
            eb.setImage("https://cdn.discordapp.com/attachments/592540131097837578/656822685912793088/poster.png");
        }
        //eb.setFooter(getRandFooter(settings.getFooterMax()));
        eb.setFooter(stringVerifier("Page " + (resultNum + 1) + " of " + (resultArray.length), 7));
        return eb;
    }

    /**
     * Creates a TvInfo Embed
     * <br>
     * Takes in a {@link TvInfo} object to form a {@link EmbedBuilder} object containing a show's:
     * <ul>
     * <li>Name
     * <li>Description
     * <li>Plex Availability
     * <li>Number of Episodes
     * <li>Airing status {Running | Ended}
     * <li>Release Date
     * <li>Avg. Run Time
     * <li>Ombi Request Status
     * <li>Original Airing Network
     * <li>TVDb ID
     * <li>Last Episode Air Date
     * <li>Cover art
     * </ul>
     *
     * @param info A {@link TvInfo TvInfo} object with all values filled
     * @return A {@link EmbedBuilder} object containing above information.
     */
    //more info methods
    public EmbedBuilder createTvMoreInfoEmbed(TvInfo info) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00AE86));
        eb.setTitle(stringVerifier(info.getTitle(), 1), getTvDbUrl(info.getId()));
        eb.setDescription(stringVerifier(info.getOverview(), 2));
        eb.addField("Availability", stringVerifier(info.getPlexAvailability(), 5), true);
        eb.addField("Number of Episodes", stringVerifier(String.valueOf(info.getNumOfEpisodes()), 5), true);
        eb.addField("Status", stringVerifier(info.getStatus(), 5), true);
        eb.addField("Release Date", stringVerifier(info.getFirstAired(), 8), true);
        eb.addField("RunTime", stringVerifier(info.getRuntime(), 5) + " minutes", true);
        eb.addField("Requested", info.getEpisodeRequestStatus(), true);
        eb.addField("Network", stringVerifier(info.getNetwork(), 4), true);
        eb.addField("TVDb ID", stringVerifier(info.getId().toString(), 3), true);
        eb.addField("Last Episode Air Date", stringVerifier(info.getLatestEpisodeDate(), 8), true);
        eb.setThumbnail(info.getBanner());
        return eb;
    }

    /**
     * Creates a MovieInfo Embed
     * <br>
     * Takes in a {@link MovieInfo} object to form a {@link EmbedBuilder} object containing a movie's:
     * <ul>
     * <li>Name
     * <li>Description
     * <li>Plex Availability
     * <li>Original Language
     * <li>Premiere Date
     * <li>Website
     * <li>TMDb ID
     * <li>Request Status
     * <li>Cover art
     * </ul>
     *
     * @param info A {@link MovieInfo} object with all values filled
     * @return A {@link EmbedBuilder} object containing above information.
     */
    public EmbedBuilder createMovieMoreInfoEmbed(MovieInfo info) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00Ae86));
        eb.setTitle(stringVerifier((info.getTitle()), 5), getTMDbURL(String.valueOf(info.getId())));
        eb.setDescription(stringVerifier(info.getOverview(), 2));
        eb.addField("Availability", stringVerifier(info.getAvailability(), 5), true);
        eb.addField("Original Language", stringVerifier(info.getOriginalLanguage(), 5), true);
        eb.addField("Originally Available", stringVerifier(info.getReleaseDate(), 8), true);
        eb.addField("Website", stringVerifier("[Homepage](" + info.getHomepage() + ")", 5), true);
        eb.addField("TMDb ID", stringVerifier(String.valueOf(info.getId()), 3), true);
        eb.addField("Requested", stringVerifier(String.valueOf(info.getRequested()), 9), true);
        if (info.getPosterPath() != null) {
            eb.setThumbnail(stringVerifier("https://image.tmdb.org/t/p/original" + info.getPosterPath(), 1));
        } else {
            eb.setThumbnail("https://cdn.discordapp.com/attachments/592540131097837578/656822685912793088/poster.png");
        }
        return eb;
    }

    /**
     * Creates an embed out of an array of missing episodes
     * <br>
     * Takes in a String Arraylist and puts the first 10 elements of the array into a {@link EmbedBuilder} object.
     *
     * @param missingEpisodeArray A String ArrayList where each string is information about a missing episode.
     * @return A {@link EmbedBuilder} object containing the first 10 items in missingEpisodeArray
     */
    public EmbedBuilder createMissingEpisodeEmbed(ArrayList<String> missingEpisodeArray) {

        String episodes = "";
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00Ae86));
        eb.setTitle("Missing Episodes");
        if (missingEpisodeArray.size() == 0) {
            eb.setDescription("No Missing Episodes!");
        } else {
            if (missingEpisodeArray.size() >= 10) {
                for (int i = 0; i < 9; i++) {
                    episodes += missingEpisodeArray.get(i) + "\n";
                }
                episodes += "Plus " + (missingEpisodeArray.size() - 9) + "more episodes";
            } else {
                for (int i = 0; i < missingEpisodeArray.size(); i++) {
                    episodes += missingEpisodeArray.get(i) + "\n";
                }
            }

            eb.setDescription(
                    episodes
            );
        }


        return eb;
    }

    /**
     * Creates an embed out of ping times
     * <br>
     * Takes in multiple ping times and returns a A {@link EmbedBuilder} object containing them.
     * @param gatewayPing
     *          The ping time from {@link JDA#getGatewayPing()}
     * @param discordPing
     *          The ping time from {@link JDA#getRestPing()}
     * @param ombiPing
     *          The ping time from {@link OmbiCallers#getPingTime()}
     * @return
     *          A {@link EmbedBuilder} object containing the above ping times
     */
    public EmbedBuilder createPingEmbed(long gatewayPing, long discordPing, long ombiPing) {
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00Ae86));
        eb.setTitle("Ping Times");
        eb.setDescription("Current ping times for all enabled APIs");
        eb.addField("Gateway", stringVerifier(gatewayPing + "ms", 5), true);
        eb.addField("Discord", stringVerifier(String.valueOf(discordPing), 5) + "ms", true);
        eb.addField("Ombi", stringVerifier(String.valueOf(ombiPing), 5) + "ms", true);

        return eb;
    }

    /**
     * Creates an ArrayList of MovieSearch EmbedBuilder Objects
     * <br>
     * Takes in a {@link MovieSearch} array and calls {@link EmbedManager#createMovieSearchEmbed(MovieSearch[], int) createMovieSearchEmbed()} for each index
     * of the given array.
     *
     * @param movieSearchArray The non-empty {@link MovieSearch} array
     * @return An ArrayList of EmbedBuilder objects from {@link EmbedManager#createMovieSearchEmbed(MovieSearch[], int) createMovieSearchEmbed()}
     */
    public ArrayList<EmbedBuilder> getMovieSearchEmbedArray(MovieSearch[] movieSearchArray) {

        ArrayList<EmbedBuilder> newArray = new ArrayList<>();
        for (int i = 0; i < movieSearchArray.length; i++) {
            newArray.add(i, createMovieSearchEmbed(movieSearchArray, i));
        }

        return newArray;
    }

    /**
     * Creates an ArrayList of TvSearch EmbedBuilder objects
     * <br>
     * Takes in a {@link TvSearch} array and calls {@link EmbedManager#createTVSearchEmbed(TvSearch[], int) createTVSearchEmbed()} for each index
     * of the given array.
     *
     * @param tvSearchArray The non-empty {@link TvSearch} array
     * @return An ArrayList of EmbedBuilder objects from {@link EmbedManager#createTVSearchEmbed(TvSearch[], int) createTVSearchEmbed()}
     */
    public ArrayList<EmbedBuilder> getTvSearchEmbedArray(TvSearch[] tvSearchArray) {
        ArrayList<EmbedBuilder> newArray = new ArrayList<>();
        for (int i = 0; i < tvSearchArray.length; i++) {
            newArray.add(i, createTVSearchEmbed(tvSearchArray, i));
        }

        return newArray;
    }


    /**
     * Create TvInfo embed with only the TVDb ID number
     * <br>
     * Calls {@link EmbedManager#createTvMoreInfoEmbed(TvInfo)} and passes that the TvInfo object obtained by calling
     * {@link OmbiCallers#ombiTvInfo(String)}
     *
     * @param id The TVDb ID number of the show
     * @return A TvInfo EmbedBuilder from {@link EmbedManager#createTvMoreInfoEmbed(TvInfo)}
     */
    public EmbedBuilder getTvInfoEmbed(int id) {
        OmbiCallers caller = new OmbiCallers();
        return createTvMoreInfoEmbed(caller.ombiTvInfo(String.valueOf(id)));
    }

    public EmbedBuilder getMovieRequestListEmbed(MovieRequestList[] movieRequestArray, int pageNum){
        EmbedBuilder eb = new EmbedBuilder();
        eb.setColor(new Color(0x00Ae86));
        eb.setTitle("Requested Movies");
        eb.setFooter("Page " + (pageNum + 1));
        int numOfItems = 10 * (pageNum + 1); //sets the number of items per page

        if(pageNum  == (int)Math.floor(movieRequestArray.length / 10.0) ){ //last page in array
            numOfItems = movieRequestArray.length;
        }

        //generate the string required for the embed description
        String descString = "";
        for(int i = pageNum * 10;    i <  numOfItems     ; i++){ //<--- this line
            descString += movieRequestArray[i].getTitle() + "\n";
        }

        eb.setDescription(descString);
        return eb;
    }

    public ArrayList<EmbedBuilder> getMovieRequestListArray(MovieRequestList[] movieRequestArray){
        //first determine the number of pages that we will need to present with 10 movies per page
        int numOfPages = (int)Math.ceil(movieRequestArray.length / 10.0) ;


        //create the embed array
        ArrayList<EmbedBuilder> newArray = new ArrayList<>();

        for(int i = 0; i < numOfPages; i++){
            newArray.add(getMovieRequestListEmbed(movieRequestArray, i));
        }

        //return newArray
        return newArray;
    }

    public ArrayList<EmbedBuilder> getUnfilledMovieRequestListArray(MovieRequestList[] movieRequestArray){
        //run through the movie array and strip it of available movies
        ArrayList<MovieRequestList> tempMovieArray = new ArrayList<>();

        for (int i = 0; i < movieRequestArray.length; i++){
            if (!movieRequestArray[i].getAvailable()) {
                tempMovieArray.add(movieRequestArray[i]);
            }
        }

        MovieRequestList[] fixedMovieArray = tempMovieArray.toArray(new MovieRequestList[tempMovieArray.size()]);

        return getMovieRequestListArray(fixedMovieArray);
    }


    /**
     * wraps EmbedBuilder object into an ArrayList
     *
     * @param toAdd The EmbedBuilder object to be wrapped
     * @return An EmbedBuilder ArrayList containing the passed EmbedBuilder object
     */
    //wraps EmbedBuilder in an arrayList
    public ArrayList<EmbedBuilder> toArrayList(EmbedBuilder toAdd) {
        ArrayList<EmbedBuilder> newArrayList = new ArrayList<>();
        newArrayList.add(toAdd);
        return newArrayList;
    }


//private helper methods

    //Methods that clean up information
    //Field IDs-------------
    // 1. Title
    // 2. Description
    // 3. TVDB ID
    // 4. Original Network
    // 5. Status - Original Language - URL - number
    // 6. Monitored
    // 7. Footer
    // 8. Date
    // 9. general error
    private String stringVerifier(String toCheck, int fieldID) {
        if (toCheck == null) {
            switch (fieldID) {
                case 1:
                    return "TITLE";
                case 2:
                    return "Error retrieving description.";
                case 3:
                    return "No TVDB ID";
                case 4:
                    return "N/A";
                case 5:
                    return "Unknown";
                case 6:
                    return "ok, how did you get this one?";
                case 7:
                    return " ";
                case 8:
                    return "unknown";
            }
            //shouldn't ever get here unless some idiot (me) forgets to add a case for a new function
            return "N/A";
        } else {
            if (fieldID == 8) {
                try {
                    Date date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(toCheck);
                    return new SimpleDateFormat("MM/dd/yyyy").format(date);
                } catch (Exception e) {
                    return "Unknown";
                }

            }
        }
        return toCheck;
    }

    //this method returns a string with the formattedName added. This is used to get the URL for a show on the TVDB using the "title slug" field
    private String getTvDbUrl(int id) {
        return "https://www.thetvdb.com/?tab=series&id=" + id;
    }

    private String getTMDbURL(String tmdbId) {
        return "https://www.themoviedb.org/movie/" + tmdbId;
    }

    private String formatStatus(String toFormat) {
        try {
            return Character.toUpperCase(toFormat.charAt(0)) + toFormat.substring(1).replaceAll("(?=[A-Z])", " ");
        } catch (NullPointerException e) {
            return null;
        }
    }


    //random footer messages
    @Deprecated
    private String getRandFooter(int max) {
        Random rand = new Random();

        //make sure the max value is legal
        if (max <= 0) {
            throw new IllegalArgumentException();
        }

        //generate random value
        int randomNumber = rand.ints(0, max + 1).findFirst().getAsInt();

        //get path of footer file

        URI footerPath;
        try {
            footerPath = getClass().getResource("/assets/footer.plexi").toURI();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            //log that the command was used
            Settings.getInstance().getLogger().error("Error while generating a random footer: " + e.getLocalizedMessage());
            Settings.getInstance().getLogger().trace(Arrays.toString(e.getStackTrace()));
            return "Missing Footer File!";
        }

        //create array of Strings out of footer.plexi file
        try {
            List<String> footerList = Files.readAllLines(Paths.get(footerPath));
            return footerList.get(randomNumber);
        } catch (IOException e) {
            e.printStackTrace();
            return "Unable to generate footer!";
        }

    }



}

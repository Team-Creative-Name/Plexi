package discordBot;

import apis.ombi.OmbiCallers;
import apis.ombi.templateClasses.movies.moreInfo.MovieInfo;
import apis.ombi.templateClasses.movies.search.MovieSearch;
import apis.ombi.templateClasses.tv.moreInfo.TvInfo;
import apis.ombi.templateClasses.tv.search.TvSearch;
import net.dv8tion.jda.api.EmbedBuilder;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

//This class will create the embeds and post them
public class EmbedManager {



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
        eb.addField("Requested", stringVerifier(info.getRequestAll().toString(), 9), true);
        eb.addField("Network", stringVerifier(info.getNetwork(), 4), true);
        eb.addField("TVDb ID", stringVerifier(info.getId().toString(), 3), true);
        eb.addField("Last Episode Air Date", stringVerifier(info.getLatestEpisodeDate(), 8), true);
        eb.setThumbnail(info.getBanner());
        return eb;
    }

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

    //Methods that clean up information
    //Field IDs-------------
    // 1. Title
    // 2. Description
    // 3. TVDB ID
    // 4. Original Network
    // 5. Status - Original Language - URL
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
            System.out.println(e);
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

    public ArrayList<EmbedBuilder> getPostMovieSearchEmbed(MovieSearch[] tester) {

        ArrayList<EmbedBuilder> newArray = new ArrayList<>();
        for (int i = 0; i < tester.length; i++) {
            newArray.add(i, createMovieSearchEmbed(tester, i));
        }

        return newArray;
    }

    public ArrayList<EmbedBuilder> getPostTvSearchEmbed(TvSearch[] tester) {
        ArrayList<EmbedBuilder> newArray = new ArrayList<>();
        for (int i = 0; i < tester.length; i++) {
            newArray.add(i, createTVSearchEmbed(tester, i));
        }

        return newArray;
    }

    public EmbedBuilder getTvInfoEmbed(int id) {
        OmbiCallers caller = new OmbiCallers();
        return createTvMoreInfoEmbed(caller.ombiTvInfo(String.valueOf(id)));
    }

}

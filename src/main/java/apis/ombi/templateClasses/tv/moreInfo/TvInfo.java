
package apis.ombi.templateClasses.tv.moreInfo;

import apis.ombi.templateClasses.requests.tv.jsonTemplate.RequestEpisode;
import apis.ombi.templateClasses.requests.tv.jsonTemplate.Season;
import apis.ombi.templateClasses.requests.tv.jsonTemplate.TvRequestTemplate;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TvInfo {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("aliases")
    @Expose
    private Object aliases;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("seriesId")
    @Expose
    private Integer seriesId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("firstAired")
    @Expose
    private String firstAired;
    @SerializedName("network")
    @Expose
    private String network;
    @SerializedName("networkId")
    @Expose
    private String networkId;
    @SerializedName("runtime")
    @Expose
    private String runtime;
    @SerializedName("genre")
    @Expose
    private Object genre;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("lastUpdated")
    @Expose
    private Integer lastUpdated;
    @SerializedName("airsDayOfWeek")
    @Expose
    private Object airsDayOfWeek;
    @SerializedName("airsTime")
    @Expose
    private Object airsTime;
    @SerializedName("rating")
    @Expose
    private String rating;
    @SerializedName("siteRating")
    @Expose
    private Integer siteRating;
    @SerializedName("trailer")
    @Expose
    private Object trailer;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("seasonRequests")
    @Expose
    private List<SeasonRequest> seasonRequests = null;
    @SerializedName("requestAll")
    @Expose
    private Boolean requestAll;
    @SerializedName("firstSeason")
    @Expose
    private Boolean firstSeason;
    @SerializedName("latestSeason")
    @Expose
    private Boolean latestSeason;
    @SerializedName("fullyAvailable")
    @Expose
    private Boolean fullyAvailable;
    @SerializedName("partlyAvailable")
    @Expose
    private Boolean partlyAvailable;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("requested")
    @Expose
    private Boolean requested;
    @SerializedName("requestId")
    @Expose
    private Integer requestId;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("plexUrl")
    @Expose
    private String plexUrl;
    @SerializedName("embyUrl")
    @Expose
    private Object embyUrl;
    @SerializedName("quality")
    @Expose
    private Object quality;
    @SerializedName("imdbId")
    @Expose
    private String imdbId;
    @SerializedName("theTvDbId")
    @Expose
    private String theTvDbId;
    @SerializedName("theMovieDbId")
    @Expose
    private Object theMovieDbId;
    @SerializedName("subscribed")
    @Expose
    private Boolean subscribed;
    @SerializedName("showSubscribe")
    @Expose
    private Boolean showSubscribe;

    ArrayList<Episode> MissingEpisodeList = new ArrayList<>();
    private int numOfEpisodes = -1;

    public int getMissingEpisodes() {
        if (missingEpisodes == -1) {
            populateMissingData();
        }
        return missingEpisodes;
    }

    public int getNumOfEpisodes() {
        if (missingEpisodes == -1) {
            populateMissingData();
        }
        return numOfEpisodes;
    }

    public void setNumOfEpisodes(int numOfEpisodes) {
        this.numOfEpisodes = numOfEpisodes;
    }

    //Extra variable that gets the number of missing episodes
    private int missingEpisodes = -1;

    public ArrayList<Episode> getMissingEpisodeList() {
        if (MissingEpisodeList == null) {
            populateMissingData();
        }
        return MissingEpisodeList;
    }

    public void setMissingEpisodeList(ArrayList<Episode> missingEpisodeList) {
        MissingEpisodeList = missingEpisodeList;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getAliases() {
        return aliases;
    }

    public void setAliases(Object aliases) {
        this.aliases = aliases;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Integer getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Integer seriesId) {
        this.seriesId = seriesId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFirstAired() {
        return firstAired + "T17:00:00-00:00";
    }

    public void setFirstAired(String firstAired) {
        this.firstAired = firstAired;
    }

    public String getNetwork() {
        return network;
    }

    public void setNetwork(String network) {
        this.network = network;
    }

    public String getNetworkId() {
        return networkId;
    }

    public void setNetworkId(String networkId) {
        this.networkId = networkId;
    }

    public String getRuntime() {
        return runtime;
    }

    public void setRuntime(String runtime) {
        this.runtime = runtime;
    }

    public Object getGenre() {
        return genre;
    }

    public void setGenre(Object genre) {
        this.genre = genre;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Integer getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Integer lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Object getAirsDayOfWeek() {
        return airsDayOfWeek;
    }

    public void setAirsDayOfWeek(Object airsDayOfWeek) {
        this.airsDayOfWeek = airsDayOfWeek;
    }

    public Object getAirsTime() {
        return airsTime;
    }

    public void setAirsTime(Object airsTime) {
        this.airsTime = airsTime;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public Integer getSiteRating() {
        return siteRating;
    }

    public void setSiteRating(Integer siteRating) {
        this.siteRating = siteRating;
    }

    public Object getTrailer() {
        return trailer;
    }

    public void setTrailer(Object trailer) {
        this.trailer = trailer;
    }

    public Object getHomepage() {
        return homepage;
    }

    public void setHomepage(Object homepage) {
        this.homepage = homepage;
    }

    public List<SeasonRequest> getSeasonRequests() {
        return seasonRequests;
    }

    public void setSeasonRequests(List<SeasonRequest> seasonRequests) {
        this.seasonRequests = seasonRequests;
    }

    public Boolean getRequestAll() {
        return requestAll;
    }

    public void setRequestAll(Boolean requestAll) {
        this.requestAll = requestAll;
    }

    public Boolean getFirstSeason() {
        return firstSeason;
    }

    public void setFirstSeason(Boolean firstSeason) {
        this.firstSeason = firstSeason;
    }

    public Boolean getLatestSeason() {
        return latestSeason;
    }

    public void setLatestSeason(Boolean latestSeason) {
        this.latestSeason = latestSeason;
    }

    public Boolean getFullyAvailable() {
        return fullyAvailable;
    }

    public void setFullyAvailable(Boolean fullyAvailable) {
        this.fullyAvailable = fullyAvailable;
    }

    public Boolean getPartlyAvailable() {
        return partlyAvailable;
    }

    public void setPartlyAvailable(Boolean partlyAvailable) {
        this.partlyAvailable = partlyAvailable;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Boolean getRequested() {
        return requested;
    }

    public void setRequested(Boolean requested) {
        this.requested = requested;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getPlexUrl() {
        return plexUrl;
    }

    public void setPlexUrl(String plexUrl) {
        this.plexUrl = plexUrl;
    }

    public Object getEmbyUrl() {
        return embyUrl;
    }

    public void setEmbyUrl(Object embyUrl) {
        this.embyUrl = embyUrl;
    }

    public Object getQuality() {
        return quality;
    }

    public void setQuality(Object quality) {
        this.quality = quality;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getTheTvDbId() {
        return theTvDbId;
    }

    public void setTheTvDbId(String theTvDbId) {
        this.theTvDbId = theTvDbId;
    }

    public Object getTheMovieDbId() {
        return theMovieDbId;
    }

    public void setTheMovieDbId(Object theMovieDbId) {
        this.theMovieDbId = theMovieDbId;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Boolean getShowSubscribe() {
        return showSubscribe;
    }

    public void setShowSubscribe(Boolean showSubscribe) {
        this.showSubscribe = showSubscribe;
    }

    public void setMissingEpisodes(int missingEpisodes) {
        this.missingEpisodes = missingEpisodes;
    }

    public void populateMissingData() {

        //temp variables for new values
        int missingCount = 0;
        int numOfEpisodes = 0;
        ArrayList<Episode> missingEpisodes = new ArrayList<>();


        //loop that runs through the episodes to extract data
        for (int i = 0; i < seasonRequests.size(); i++) {

            for (int j = 0; j < seasonRequests.get(i).getEpisodes().size(); j++) {

                //add one to numOfEpisodes for each loop here
                numOfEpisodes++;
                //operations for the missing episodes
                if (seasonRequests.get(i).getEpisodes().get(j).getAvailable() == false) {
                    //Increment the number of missing episodes
                    missingCount++;

                    //add episode information to the MissingEpisode ArrayList
                    missingEpisodes.add(seasonRequests.get(i).getEpisodes().get(j));

                }
            }
        }
        //set the global variable in case we need to call this again
        setMissingEpisodes(missingCount);
        setNumOfEpisodes(numOfEpisodes);
        setMissingEpisodeList(missingEpisodes);
    }

    public String getPlexAvailability() {
        if (fullyAvailable) {
            return "[Fully Available on Plex!](" + getPlexUrl() + ")";
        } else if (!fullyAvailable && available) {
            return "[" + getMissingEpisodes() + " episode(s) Missing!](" + getPlexUrl() + ")";
        } else {
            return "Not available on Plex!";
        }
    }

    public String getLatestEpisodeDate() {
        int seasonNum = 0;
        int episodeNum = 0;


        seasonNum = seasonRequests.size() - 1;
        episodeNum = seasonRequests.get(seasonNum).getEpisodes().size() - 1;

        return seasonRequests.get(seasonNum).getEpisodes().get(episodeNum).getAirDate();
    }

    public TvRequestTemplate getMissingEpisodeArray() {
        //Create a new requestTemplate obj
        TvRequestTemplate missing = new TvRequestTemplate();
        //Create a new Season ArrayList
        ArrayList<Season> missingSeasons = new ArrayList<>();

        //loop through the seasons in a show
        for (int i = 0; i < seasonRequests.size(); i++) {

            //Create a new season
            Season season = new Season();
            season.setSeasonNumber(i + 1);
            //Create a new Episode ArrayList
            ArrayList<RequestEpisode> missingEpisodes = new ArrayList<>();
            //loop through the episodes in a season
            for (int j = 0; j < seasonRequests.get(i).getEpisodes().size(); j++) {
                if (seasonRequests.get(i).getEpisodes().get(j).getAvailable() == false) {
                    //create new Episode object
                    System.out.println("Season " + i + " Episode " + j + " is missing!");
                    RequestEpisode episode = new RequestEpisode();
                    //set the episode number
                    episode.setEpisodeNumber(seasonRequests.get(i).getEpisodes().get(j).getEpisodeNumber());
                    //add episode to the missingEpisodes array
                    missingEpisodes.add(episode);
                }
            }
            //add the missing episodes to the season
            season.setRequestEpisodes(missingEpisodes);
            missingSeasons.add(season);
        }

        //finally add the missing seasons array to the TvRequestTemplate obj
        missing.setSeasons(missingSeasons);
        //and return it
        return missing;
    }


}
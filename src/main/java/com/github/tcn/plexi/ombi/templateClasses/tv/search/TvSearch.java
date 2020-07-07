
package com.github.tcn.plexi.ombi.templateClasses.tv.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class representing data returned via Ombi's {@code /api/v1/Search/tv/{query}} endpoint
 * <br>
 * There are a couple of fields in this class that always seem to be null. They are:
 * <ul>
 * <li>aliases
 * <li>genre
 * <li>airDaysOfWeek
 * <li>airsTime
 * <li>trailer
 * <li>homepage
 * <li>theMovieDbId
 * <li>theTvDbId
 * </ul>
 * While these fields might not always be null, I have not come across a show with them filled during testing
 * and should probably not be required for something to work.
 *
 * <br><br>
 * To get an array of this object, use {@link com.github.tcn.plexi.ombi.OmbiCallers#ombiTvSearch(String)}
 */
public class TvSearch {

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
    private List<Object> seasonRequests = null;
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
    private Object plexUrl;
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
    private Object theTvDbId;
    @SerializedName("theMovieDbId")
    @Expose
    private Object theMovieDbId;
    @SerializedName("subscribed")
    @Expose
    private Boolean subscribed;
    @SerializedName("showSubscribe")
    @Expose
    private Boolean showSubscribe;

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
        return firstAired;
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

    public List<Object> getSeasonRequests() {
        return seasonRequests;
    }

    public void setSeasonRequests(List<Object> seasonRequests) {
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

    public Object getPlexUrl() {
        return plexUrl;
    }

    public void setPlexUrl(Object plexUrl) {
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

    public Object getTheTvDbId() {
        return theTvDbId;
    }

    public void setTheTvDbId(Object theTvDbId) {
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

}


package com.github.tcn.plexi.ombi.templateClasses.movies.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class representing data returned via Ombi's {@code /api/v1/Search/movie/{QUERY}} endpoint
 * <br>
 * There are a couple of fields in this class that always seem to be null. They are:
 * <p><ul>
 * <li>backdropPath
 * <li>genreIds
 * <li>trailer
 * <li>homepage
 * <li>releaseDates -- Please see releaseDate for that information
 * <li>digitalReleaseDate
 * <li>theTvDbID
 * </ul>
 * While these fields might not always be null, I have not come across a movie with them filled during testing
 * and should probably not be required for something to work.
 * </p>
 * <br><br>
 * To get an array of this object, use {@link com.github.tcn.plexi.ombi.OmbiCallers#ombiMovieSearch(String)}
 */
public class MovieSearch {

    @SerializedName("adult")
    @Expose
    private Boolean adult;
    @SerializedName("backdropPath")
    @Expose
    private String backdropPath;
    @SerializedName("genreIds")
    @Expose
    private List<Object> genreIds = null;
    @SerializedName("originalLanguage")
    @Expose
    private String originalLanguage;
    @SerializedName("originalTitle")
    @Expose
    private String originalTitle;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("popularity")
    @Expose
    private Double popularity;
    @SerializedName("posterPath")
    @Expose
    private String posterPath;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("video")
    @Expose
    private Boolean video;
    @SerializedName("voteAverage")
    @Expose
    private Double voteAverage;
    @SerializedName("voteCount")
    @Expose
    private Integer voteCount;
    @SerializedName("alreadyInCp")
    @Expose
    private Boolean alreadyInCp;
    @SerializedName("trailer")
    @Expose
    private Object trailer;
    @SerializedName("homepage")
    @Expose
    private Object homepage;
    @SerializedName("rootPathOverride")
    @Expose
    private Integer rootPathOverride;
    @SerializedName("qualityOverride")
    @Expose
    private Integer qualityOverride;
    @SerializedName("type")
    @Expose
    private Integer type;
    @SerializedName("releaseDates")
    @Expose
    private Object releaseDates;
    @SerializedName("digitalReleaseDate")
    @Expose
    private Object digitalReleaseDate;
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
    private Object imdbId;
    @SerializedName("theTvDbId")
    @Expose
    private Object theTvDbId;
    @SerializedName("theMovieDbId")
    @Expose
    private String theMovieDbId;
    @SerializedName("subscribed")
    @Expose
    private Boolean subscribed;
    @SerializedName("showSubscribe")
    @Expose
    private Boolean showSubscribe;

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public List<Object> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Object> genreIds) {
        this.genreIds = genreIds;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public Double getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(Double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public Integer getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(Integer voteCount) {
        this.voteCount = voteCount;
    }

    public Boolean getAlreadyInCp() {
        return alreadyInCp;
    }

    public void setAlreadyInCp(Boolean alreadyInCp) {
        this.alreadyInCp = alreadyInCp;
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

    public Integer getRootPathOverride() {
        return rootPathOverride;
    }

    public void setRootPathOverride(Integer rootPathOverride) {
        this.rootPathOverride = rootPathOverride;
    }

    public Integer getQualityOverride() {
        return qualityOverride;
    }

    public void setQualityOverride(Integer qualityOverride) {
        this.qualityOverride = qualityOverride;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Object getReleaseDates() {
        return releaseDates;
    }

    public void setReleaseDates(Object releaseDates) {
        this.releaseDates = releaseDates;
    }

    public Object getDigitalReleaseDate() {
        return digitalReleaseDate;
    }

    public void setDigitalReleaseDate(Object digitalReleaseDate) {
        this.digitalReleaseDate = digitalReleaseDate;
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

    public Object getImdbId() {
        return imdbId;
    }

    public void setImdbId(Object imdbId) {
        this.imdbId = imdbId;
    }

    public Object getTheTvDbId() {
        return theTvDbId;
    }

    public void setTheTvDbId(Object theTvDbId) {
        this.theTvDbId = theTvDbId;
    }

    public String getTheMovieDbId() {
        return theMovieDbId;
    }

    public void setTheMovieDbId(String theMovieDbId) {
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

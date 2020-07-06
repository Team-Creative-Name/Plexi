
package com.github.tcn.plexi.ombi.templateClasses.tv.recentlyAdded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentTv {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("overview")
    @Expose
    private Object overview;
    @SerializedName("imdbId")
    @Expose
    private Object imdbId;
    @SerializedName("tvDbId")
    @Expose
    private String tvDbId;
    @SerializedName("theMovieDbId")
    @Expose
    private Object theMovieDbId;
    @SerializedName("releaseYear")
    @Expose
    private String releaseYear;
    @SerializedName("addedAt")
    @Expose
    private String addedAt;
    @SerializedName("quality")
    @Expose
    private Object quality;
    @SerializedName("seasonNumber")
    @Expose
    private Integer seasonNumber;
    @SerializedName("episodeNumber")
    @Expose
    private Integer episodeNumber;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getOverview() {
        return overview;
    }

    public void setOverview(Object overview) {
        this.overview = overview;
    }

    public Object getImdbId() {
        return imdbId;
    }

    public void setImdbId(Object imdbId) {
        this.imdbId = imdbId;
    }

    public String getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(String tvDbId) {
        this.tvDbId = tvDbId;
    }

    public Object getTheMovieDbId() {
        return theMovieDbId;
    }

    public void setTheMovieDbId(Object theMovieDbId) {
        this.theMovieDbId = theMovieDbId;
    }

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public String getAddedAt() {
        return addedAt;
    }

    public void setAddedAt(String addedAt) {
        this.addedAt = addedAt;
    }

    public Object getQuality() {
        return quality;
    }

    public void setQuality(Object quality) {
        this.quality = quality;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

}

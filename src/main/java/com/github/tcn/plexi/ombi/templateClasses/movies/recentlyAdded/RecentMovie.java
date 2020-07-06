
package com.github.tcn.plexi.ombi.templateClasses.movies.recentlyAdded;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RecentMovie {

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
    private String imdbId;
    @SerializedName("tvDbId")
    @Expose
    private Object tvDbId;
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
    private String quality;

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

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Object getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(Object tvDbId) {
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

    public String getQuality() {
        return quality;
    }

    public void setQuality(String quality) {
        this.quality = quality;
    }

}

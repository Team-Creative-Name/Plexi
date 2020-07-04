
package com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Episode {

    @SerializedName("episodeNumber")
    @Expose
    private Integer episodeNumber;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("airDate")
    @Expose
    private String airDate;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("requested")
    @Expose
    private Boolean requested;
    @SerializedName("seasonId")
    @Expose
    private Integer seasonId;
    @SerializedName("season")
    @Expose
    private Object season;
    @SerializedName("airDateDisplay")
    @Expose
    private String airDateDisplay;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
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

    public Integer getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(Integer seasonId) {
        this.seasonId = seasonId;
    }

    public Object getSeason() {
        return season;
    }

    public void setSeason(Object season) {
        this.season = season;
    }

    public String getAirDateDisplay() {
        return airDateDisplay;
    }

    public void setAirDateDisplay(String airDateDisplay) {
        this.airDateDisplay = airDateDisplay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}


package com.github.tcn.plexi.ombi.templateClasses.tv.tvRequests;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SeasonRequest {

    @SerializedName("seasonNumber")
    @Expose
    private Integer seasonNumber;
    @SerializedName("episodes")
    @Expose
    private List<Episode> episodes = null;
    @SerializedName("childRequestId")
    @Expose
    private Integer childRequestId;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public Integer getChildRequestId() {
        return childRequestId;
    }

    public void setChildRequestId(Integer childRequestId) {
        this.childRequestId = childRequestId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

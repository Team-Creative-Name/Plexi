
package com.github.tcn.plexi.ombi.templateClasses.requests.tv.jsonTemplate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Season {

    @SerializedName("seasonNumber")
    @Expose
    private Integer seasonNumber;
    @SerializedName("episodes")
    @Expose
    private List<RequestEpisode> requestEpisodes = null;

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public List<RequestEpisode> getRequestEpisodes() {
        return requestEpisodes;
    }

    public void setRequestEpisodes(List<RequestEpisode> requestEpisodes) {
        this.requestEpisodes = requestEpisodes;
    }

}

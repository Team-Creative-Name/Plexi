
package com.github.tcn.plexi.ombi.templateClasses.tv.requestTemplate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestEpisode {

    @SerializedName("episodeNumber")
    @Expose
    private Integer episodeNumber;

    public Integer getEpisodeNumber() {
        return episodeNumber;
    }

    public void setEpisodeNumber(Integer episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

}


package com.github.tcn.plexi.ombi.templateClasses.tv.requestTemplate;

import com.github.tcn.plexi.ombi.templateClasses.tv.moreInfo.TvInfo;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * A class representing data sent to Ombi's {@code /api/v1/request/tv} endpoint
 * <br>
 * This class is used internally by {@link com.github.tcn.plexi.ombi.OmbiCallers#requestTv(boolean, TvInfo)} to request a tv show.
 * <br>
 * There is no way to get a completed version of this object.
 */
public class TvRequestTemplate {

    @SerializedName("requestAll")
    @Expose
    private Boolean requestAll;
    @SerializedName("latestSeason")
    @Expose
    private Boolean latestSeason;
    @SerializedName("firstSeason")
    @Expose
    private Boolean firstSeason;
    @SerializedName("tvDbId")
    @Expose
    private Integer tvDbId;
    @SerializedName("seasons")
    @Expose
    private List<Season> seasons = null;

    public Boolean getRequestAll() {
        return requestAll;
    }

    public void setRequestAll(Boolean requestAll) {
        this.requestAll = requestAll;
    }

    public Boolean getLatestSeason() {
        return latestSeason;
    }

    public void setLatestSeason(Boolean latestSeason) {
        this.latestSeason = latestSeason;
    }

    public Boolean getFirstSeason() {
        return firstSeason;
    }

    public void setFirstSeason(Boolean firstSeason) {
        this.firstSeason = firstSeason;
    }

    public Integer getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(Integer tvDbId) {
        this.tvDbId = tvDbId;
    }

    public List<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(List<Season> seasons) {
        this.seasons = seasons;
    }

}

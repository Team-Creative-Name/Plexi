
package apis.ombi.templateClasses.requests.tv.jsonTemplate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

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

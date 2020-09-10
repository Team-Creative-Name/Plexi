
package com.github.tcn.plexi.ombi.templateClasses.tv.tvRequests;

import java.util.List;

import com.github.tcn.plexi.settingsManager.Settings;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvRequestList {

    @SerializedName("tvDbId")
    @Expose
    private Integer tvDbId;
    @SerializedName("imdbId")
    @Expose
    private String imdbId;
    @SerializedName("qualityOverride")
    @Expose
    private Object qualityOverride;
    @SerializedName("rootFolder")
    @Expose
    private Object rootFolder;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("posterPath")
    @Expose
    private String posterPath;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("totalSeasons")
    @Expose
    private Integer totalSeasons;
    @SerializedName("childRequests")
    @Expose
    private List<ChildRequest> childRequests = null;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(Integer tvDbId) {
        this.tvDbId = tvDbId;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public Object getQualityOverride() {
        return qualityOverride;
    }

    public void setQualityOverride(Object qualityOverride) {
        this.qualityOverride = qualityOverride;
    }

    public Object getRootFolder() {
        return rootFolder;
    }

    public void setRootFolder(Object rootFolder) {
        this.rootFolder = rootFolder;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public List<ChildRequest> getChildRequests() {
        return childRequests;
    }

    public void setChildRequests(List<ChildRequest> childRequests) {
        this.childRequests = childRequests;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //helper methods
    public boolean getFullyAvailable(){
        boolean isAvailable = true;
        for (int i = 0; i < childRequests.get(0).getSeasonRequests().size() - 1; i++ ){
            if(!isAvailable){//no need to loop if the show has an episode not available
                break;
            }
            for(int j = 0; j < childRequests.get(0).getSeasonRequests().get(i).getEpisodes().size() - 1; i++){
                if(!isAvailable){//same as above
                    break;
                }
                if(!childRequests.get(0).getSeasonRequests().get(i).getEpisodes().get(j).getAvailable()){
                    isAvailable = false;
                }
            }
        }
        return isAvailable;
    }

}

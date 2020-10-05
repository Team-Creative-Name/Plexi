package com.github.tcn.plexi.ombi.templateClasses.tv.tvLite;

import com.github.tcn.plexi.ombi.OmbiCallers;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * A stripped down object of the one returned via Ombi's {@code /api/v1/Request/tvlite} endpoint
 * <br>
 * This class is, as far as I know, the only way to obtain the internal OMBI request ID number
 * <br><br>
 * To get an array of this object, use {@link OmbiCallers#getTvLiteArray()}
 */
//this is a super stripped down version of this request. The API supports more, but this is all I need rn.
public class TvLite {
    @SerializedName("tvDbId")
    @Expose
    private Integer tvDbId;
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("available")
    @Expose
    private Boolean available;

    public Integer getTvDbId() {
        return tvDbId;
    }

    public void setTvDbId(Integer tvDbId) {
        this.tvDbId = tvDbId;
    }

    public Integer getRequestId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}

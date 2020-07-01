package com.github.tcn.plexi.ombi.templateClasses.tv.tvLite;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//this is a super stripped down version of this request. The API supports more, but this is all I need rn.
public class TvLite {
    @SerializedName("tvDbId")
    @Expose
    private Integer tvDbId;
    @SerializedName("id")
    @Expose
    private Integer id;

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
}

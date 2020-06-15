
package com.github.tcn.plexi.ombi.templateClasses.movies.moreInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("isoCode")
    @Expose
    private String isoCode;
    @SerializedName("releaseDate")
    @Expose
    private List<ReleaseDate> releaseDate = null;

    public String getIsoCode() {
        return isoCode;
    }

    public void setIsoCode(String isoCode) {
        this.isoCode = isoCode;
    }

    public List<ReleaseDate> getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(List<ReleaseDate> releaseDate) {
        this.releaseDate = releaseDate;
    }

}

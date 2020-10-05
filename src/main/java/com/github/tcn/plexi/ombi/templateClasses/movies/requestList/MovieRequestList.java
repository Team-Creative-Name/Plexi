
package com.github.tcn.plexi.ombi.templateClasses.movies.requestList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieRequestList {

    @SerializedName("theMovieDbId")
    @Expose
    private Integer theMovieDbId;
    @SerializedName("issueId")
    @Expose
    private Object issueId;
    @SerializedName("issues")
    @Expose
    private Object issues;
    @SerializedName("subscribed")
    @Expose
    private Boolean subscribed;
    @SerializedName("showSubscribe")
    @Expose
    private Boolean showSubscribe;
    @SerializedName("rootPathOverride")
    @Expose
    private Integer rootPathOverride;
    @SerializedName("qualityOverride")
    @Expose
    private Integer qualityOverride;
    @SerializedName("langCode")
    @Expose
    private String langCode;
    @SerializedName("imdbId")
    @Expose
    private String imdbId;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("posterPath")
    @Expose
    private String posterPath;
    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("digitalReleaseDate")
    @Expose
    private String digitalReleaseDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("background")
    @Expose
    private String background;
    @SerializedName("released")
    @Expose
    private Boolean released;
    @SerializedName("digitalRelease")
    @Expose
    private Boolean digitalRelease;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("approved")
    @Expose
    private Boolean approved;
    @SerializedName("markedAsApproved")
    @Expose
    private String markedAsApproved;
    @SerializedName("requestedDate")
    @Expose
    private String requestedDate;
    @SerializedName("available")
    @Expose
    private Boolean available;
    @SerializedName("markedAsAvailable")
    @Expose
    private String markedAsAvailable;
    @SerializedName("requestedUserId")
    @Expose
    private String requestedUserId;
    @SerializedName("denied")
    @Expose
    private Boolean denied;
    @SerializedName("markedAsDenied")
    @Expose
    private String markedAsDenied;
    @SerializedName("deniedReason")
    @Expose
    private Object deniedReason;
    @SerializedName("requestType")
    @Expose
    private Integer requestType;
    @SerializedName("requestedByAlias")
    @Expose
    private Object requestedByAlias;
    @SerializedName("canApprove")
    @Expose
    private Boolean canApprove;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getTheMovieDbId() {
        return theMovieDbId;
    }

    public void setTheMovieDbId(Integer theMovieDbId) {
        this.theMovieDbId = theMovieDbId;
    }

    public Object getIssueId() {
        return issueId;
    }

    public void setIssueId(Object issueId) {
        this.issueId = issueId;
    }

    public Object getIssues() {
        return issues;
    }

    public void setIssues(Object issues) {
        this.issues = issues;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Boolean getShowSubscribe() {
        return showSubscribe;
    }

    public void setShowSubscribe(Boolean showSubscribe) {
        this.showSubscribe = showSubscribe;
    }

    public Integer getRootPathOverride() {
        return rootPathOverride;
    }

    public void setRootPathOverride(Integer rootPathOverride) {
        this.rootPathOverride = rootPathOverride;
    }

    public Integer getQualityOverride() {
        return qualityOverride;
    }

    public void setQualityOverride(Integer qualityOverride) {
        this.qualityOverride = qualityOverride;
    }

    public String getLangCode() {
        return langCode;
    }

    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public String getImdbId() {
        return imdbId;
    }

    public void setImdbId(String imdbId) {
        this.imdbId = imdbId;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getDigitalReleaseDate() {
        return digitalReleaseDate;
    }

    public void setDigitalReleaseDate(String digitalReleaseDate) {
        this.digitalReleaseDate = digitalReleaseDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public Boolean getReleased() {
        return released;
    }

    public void setReleased(Boolean released) {
        this.released = released;
    }

    public Boolean getDigitalRelease() {
        return digitalRelease;
    }

    public void setDigitalRelease(Boolean digitalRelease) {
        this.digitalRelease = digitalRelease;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getMarkedAsApproved() {
        return markedAsApproved;
    }

    public void setMarkedAsApproved(String markedAsApproved) {
        this.markedAsApproved = markedAsApproved;
    }

    public String getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(String requestedDate) {
        this.requestedDate = requestedDate;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public String getMarkedAsAvailable() {
        return markedAsAvailable;
    }

    public void setMarkedAsAvailable(String markedAsAvailable) {
        this.markedAsAvailable = markedAsAvailable;
    }

    public String getRequestedUserId() {
        return requestedUserId;
    }

    public void setRequestedUserId(String requestedUserId) {
        this.requestedUserId = requestedUserId;
    }

    public Boolean getDenied() {
        return denied;
    }

    public void setDenied(Boolean denied) {
        this.denied = denied;
    }

    public String getMarkedAsDenied() {
        return markedAsDenied;
    }

    public void setMarkedAsDenied(String markedAsDenied) {
        this.markedAsDenied = markedAsDenied;
    }

    public Object getDeniedReason() {
        return deniedReason;
    }

    public void setDeniedReason(Object deniedReason) {
        this.deniedReason = deniedReason;
    }

    public Integer getRequestType() {
        return requestType;
    }

    public void setRequestType(Integer requestType) {
        this.requestType = requestType;
    }

    public Object getRequestedByAlias() {
        return requestedByAlias;
    }

    public void setRequestedByAlias(Object requestedByAlias) {
        this.requestedByAlias = requestedByAlias;
    }

    public Boolean getCanApprove() {
        return canApprove;
    }

    public void setCanApprove(Boolean canApprove) {
        this.canApprove = canApprove;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

}

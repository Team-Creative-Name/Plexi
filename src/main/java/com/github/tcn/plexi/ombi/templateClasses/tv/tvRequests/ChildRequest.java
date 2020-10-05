
package com.github.tcn.plexi.ombi.templateClasses.tv.tvRequests;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ChildRequest {

    @SerializedName("parentRequestId")
    @Expose
    private Integer parentRequestId;
    @SerializedName("issueId")
    @Expose
    private Object issueId;
    @SerializedName("seriesType")
    @Expose
    private Integer seriesType;
    @SerializedName("subscribed")
    @Expose
    private Boolean subscribed;
    @SerializedName("showSubscribe")
    @Expose
    private Boolean showSubscribe;
    @SerializedName("releaseYear")
    @Expose
    private String releaseYear;
    @SerializedName("issues")
    @Expose
    private Object issues;
    @SerializedName("seasonRequests")
    @Expose
    private List<SeasonRequest> seasonRequests = null;
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
    private Object markedAsAvailable;
    @SerializedName("requestedUserId")
    @Expose
    private String requestedUserId;
    @SerializedName("denied")
    @Expose
    private Object denied;
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
    @SerializedName("requestedUser")
    @Expose
    private RequestedUser requestedUser;
    @SerializedName("canApprove")
    @Expose
    private Boolean canApprove;
    @SerializedName("id")
    @Expose
    private Integer id;

    public Integer getParentRequestId() {
        return parentRequestId;
    }

    public void setParentRequestId(Integer parentRequestId) {
        this.parentRequestId = parentRequestId;
    }

    public Object getIssueId() {
        return issueId;
    }

    public void setIssueId(Object issueId) {
        this.issueId = issueId;
    }

    public Integer getSeriesType() {
        return seriesType;
    }

    public void setSeriesType(Integer seriesType) {
        this.seriesType = seriesType;
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

    public String getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Object getIssues() {
        return issues;
    }

    public void setIssues(Object issues) {
        this.issues = issues;
    }

    public List<SeasonRequest> getSeasonRequests() {
        return seasonRequests;
    }

    public void setSeasonRequests(List<SeasonRequest> seasonRequests) {
        this.seasonRequests = seasonRequests;
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

    public Object getMarkedAsAvailable() {
        return markedAsAvailable;
    }

    public void setMarkedAsAvailable(Object markedAsAvailable) {
        this.markedAsAvailable = markedAsAvailable;
    }

    public String getRequestedUserId() {
        return requestedUserId;
    }

    public void setRequestedUserId(String requestedUserId) {
        this.requestedUserId = requestedUserId;
    }

    public Object getDenied() {
        return denied;
    }

    public void setDenied(Object denied) {
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

    public RequestedUser getRequestedUser() {
        return requestedUser;
    }

    public void setRequestedUser(RequestedUser requestedUser) {
        this.requestedUser = requestedUser;
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

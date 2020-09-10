
package com.github.tcn.plexi.ombi.templateClasses.tv.tvRequests;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RequestedUser {

    @SerializedName("alias")
    @Expose
    private Object alias;
    @SerializedName("userType")
    @Expose
    private Integer userType;
    @SerializedName("providerUserId")
    @Expose
    private Object providerUserId;
    @SerializedName("lastLoggedIn")
    @Expose
    private Object lastLoggedIn;
    @SerializedName("embyConnectUserId")
    @Expose
    private Object embyConnectUserId;
    @SerializedName("movieRequestLimit")
    @Expose
    private Object movieRequestLimit;
    @SerializedName("episodeRequestLimit")
    @Expose
    private Object episodeRequestLimit;
    @SerializedName("musicRequestLimit")
    @Expose
    private Object musicRequestLimit;
    @SerializedName("userAccessToken")
    @Expose
    private Object userAccessToken;
    @SerializedName("notificationUserIds")
    @Expose
    private Object notificationUserIds;
    @SerializedName("userNotificationPreferences")
    @Expose
    private Object userNotificationPreferences;
    @SerializedName("isEmbyConnect")
    @Expose
    private Boolean isEmbyConnect;
    @SerializedName("userAlias")
    @Expose
    private String userAlias;
    @SerializedName("emailLogin")
    @Expose
    private Boolean emailLogin;
    @SerializedName("isSystemUser")
    @Expose
    private Boolean isSystemUser;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("userName")
    @Expose
    private String userName;
    @SerializedName("normalizedUserName")
    @Expose
    private String normalizedUserName;
    @SerializedName("email")
    @Expose
    private Object email;
    @SerializedName("normalizedEmail")
    @Expose
    private Object normalizedEmail;
    @SerializedName("emailConfirmed")
    @Expose
    private Boolean emailConfirmed;
    @SerializedName("phoneNumber")
    @Expose
    private Object phoneNumber;
    @SerializedName("phoneNumberConfirmed")
    @Expose
    private Boolean phoneNumberConfirmed;
    @SerializedName("twoFactorEnabled")
    @Expose
    private Boolean twoFactorEnabled;
    @SerializedName("lockoutEnd")
    @Expose
    private Object lockoutEnd;
    @SerializedName("lockoutEnabled")
    @Expose
    private Boolean lockoutEnabled;
    @SerializedName("accessFailedCount")
    @Expose
    private Integer accessFailedCount;

    public Object getAlias() {
        return alias;
    }

    public void setAlias(Object alias) {
        this.alias = alias;
    }

    public Integer getUserType() {
        return userType;
    }

    public void setUserType(Integer userType) {
        this.userType = userType;
    }

    public Object getProviderUserId() {
        return providerUserId;
    }

    public void setProviderUserId(Object providerUserId) {
        this.providerUserId = providerUserId;
    }

    public Object getLastLoggedIn() {
        return lastLoggedIn;
    }

    public void setLastLoggedIn(Object lastLoggedIn) {
        this.lastLoggedIn = lastLoggedIn;
    }

    public Object getEmbyConnectUserId() {
        return embyConnectUserId;
    }

    public void setEmbyConnectUserId(Object embyConnectUserId) {
        this.embyConnectUserId = embyConnectUserId;
    }

    public Object getMovieRequestLimit() {
        return movieRequestLimit;
    }

    public void setMovieRequestLimit(Object movieRequestLimit) {
        this.movieRequestLimit = movieRequestLimit;
    }

    public Object getEpisodeRequestLimit() {
        return episodeRequestLimit;
    }

    public void setEpisodeRequestLimit(Object episodeRequestLimit) {
        this.episodeRequestLimit = episodeRequestLimit;
    }

    public Object getMusicRequestLimit() {
        return musicRequestLimit;
    }

    public void setMusicRequestLimit(Object musicRequestLimit) {
        this.musicRequestLimit = musicRequestLimit;
    }

    public Object getUserAccessToken() {
        return userAccessToken;
    }

    public void setUserAccessToken(Object userAccessToken) {
        this.userAccessToken = userAccessToken;
    }

    public Object getNotificationUserIds() {
        return notificationUserIds;
    }

    public void setNotificationUserIds(Object notificationUserIds) {
        this.notificationUserIds = notificationUserIds;
    }

    public Object getUserNotificationPreferences() {
        return userNotificationPreferences;
    }

    public void setUserNotificationPreferences(Object userNotificationPreferences) {
        this.userNotificationPreferences = userNotificationPreferences;
    }

    public Boolean getIsEmbyConnect() {
        return isEmbyConnect;
    }

    public void setIsEmbyConnect(Boolean isEmbyConnect) {
        this.isEmbyConnect = isEmbyConnect;
    }

    public String getUserAlias() {
        return userAlias;
    }

    public void setUserAlias(String userAlias) {
        this.userAlias = userAlias;
    }

    public Boolean getEmailLogin() {
        return emailLogin;
    }

    public void setEmailLogin(Boolean emailLogin) {
        this.emailLogin = emailLogin;
    }

    public Boolean getIsSystemUser() {
        return isSystemUser;
    }

    public void setIsSystemUser(Boolean isSystemUser) {
        this.isSystemUser = isSystemUser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNormalizedUserName() {
        return normalizedUserName;
    }

    public void setNormalizedUserName(String normalizedUserName) {
        this.normalizedUserName = normalizedUserName;
    }

    public Object getEmail() {
        return email;
    }

    public void setEmail(Object email) {
        this.email = email;
    }

    public Object getNormalizedEmail() {
        return normalizedEmail;
    }

    public void setNormalizedEmail(Object normalizedEmail) {
        this.normalizedEmail = normalizedEmail;
    }

    public Boolean getEmailConfirmed() {
        return emailConfirmed;
    }

    public void setEmailConfirmed(Boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public Object getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(Object phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Boolean getPhoneNumberConfirmed() {
        return phoneNumberConfirmed;
    }

    public void setPhoneNumberConfirmed(Boolean phoneNumberConfirmed) {
        this.phoneNumberConfirmed = phoneNumberConfirmed;
    }

    public Boolean getTwoFactorEnabled() {
        return twoFactorEnabled;
    }

    public void setTwoFactorEnabled(Boolean twoFactorEnabled) {
        this.twoFactorEnabled = twoFactorEnabled;
    }

    public Object getLockoutEnd() {
        return lockoutEnd;
    }

    public void setLockoutEnd(Object lockoutEnd) {
        this.lockoutEnd = lockoutEnd;
    }

    public Boolean getLockoutEnabled() {
        return lockoutEnabled;
    }

    public void setLockoutEnabled(Boolean lockoutEnabled) {
        this.lockoutEnabled = lockoutEnabled;
    }

    public Integer getAccessFailedCount() {
        return accessFailedCount;
    }

    public void setAccessFailedCount(Integer accessFailedCount) {
        this.accessFailedCount = accessFailedCount;
    }

}

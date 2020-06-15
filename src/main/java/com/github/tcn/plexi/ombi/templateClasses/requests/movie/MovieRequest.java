
package com.github.tcn.plexi.ombi.templateClasses.requests.movie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieRequest {

    @SerializedName("result")
    @Expose
    private Boolean result;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("isError")
    @Expose
    private Boolean isError;
    @SerializedName("errorMessage")
    @Expose
    private Object errorMessage;
    @SerializedName("requestId")
    @Expose
    private Integer requestId;

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getIsError() {
        return isError;
    }

    public void setIsError(Boolean isError) {
        this.isError = isError;
    }

    public Object getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(Object errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Integer getRequestId() {
        return requestId;
    }

    public void setRequestId(Integer requestId) {
        this.requestId = requestId;
    }

}

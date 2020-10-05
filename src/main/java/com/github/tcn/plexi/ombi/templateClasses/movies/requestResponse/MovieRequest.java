
package com.github.tcn.plexi.ombi.templateClasses.movies.requestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * A class representing data returned via Ombi's {@code /api/v1/request/movie} endpoint when something is requested.
 * This should not be confused with the data returned by the same endpoint via GET.
 * <br>
 * This class is used internally by {@link com.github.tcn.plexi.ombi.OmbiCallers#requestMovie(String)} to get a movie's request
 * status.
 * <br>
 * There is no way to get a completed version of this object.
 */
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

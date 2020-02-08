
package apis.ombi.templateClasses.movies.moreInfo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReleaseDate {

    @SerializedName("releaseDate")
    @Expose
    private String releaseDate;
    @SerializedName("type")
    @Expose
    private Integer type;

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

}

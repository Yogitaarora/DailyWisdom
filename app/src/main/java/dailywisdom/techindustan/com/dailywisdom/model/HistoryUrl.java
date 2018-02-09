
package dailywisdom.techindustan.com.dailywisdom.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class HistoryUrl {

    @SerializedName("response")
    private List<Response> mResponse;
    @SerializedName("status")
    private Long mStatus;

    public List<Response> getResponse() {
        return mResponse;
    }

    public void setResponse(List<Response> response) {
        mResponse = response;
    }

    public Long getStatus() {
        return mStatus;
    }

    public void setStatus(Long status) {
        mStatus = status;
    }

}

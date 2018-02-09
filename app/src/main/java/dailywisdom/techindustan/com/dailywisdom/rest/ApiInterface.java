package dailywisdom.techindustan.com.dailywisdom.rest;

import dailywisdom.techindustan.com.dailywisdom.model.HistoryUrl;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by android on 9/11/17.
 */

public interface ApiInterface {


    @FormUrlEncoded
    @POST("notification/history")
    Call<HistoryUrl> getHistoryUrls(@Field("access_token") String access_token);


}
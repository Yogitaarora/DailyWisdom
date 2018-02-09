package dailywisdom.techindustan.com.dailywisdom;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import dailywisdom.techindustan.com.dailywisdom.model.HistoryUrl;
import dailywisdom.techindustan.com.dailywisdom.model.Response;
import dailywisdom.techindustan.com.dailywisdom.rest.ApiClient;
import dailywisdom.techindustan.com.dailywisdom.rest.ApiInterface;
import dailywisdom.techindustan.com.dailywisdom.utils.SongsManager;
import retrofit2.Call;
import retrofit2.Callback;

/**
 * Created by android on 8/2/18.
 */

public class AudioFragment extends Fragment {
    ListAdapter mAdapter;
    RecyclerView recyclerView;
    SongsManager songsManager;
    ApiInterface apiService;
    ArrayList<HashMap<String, String>> songList = new ArrayList<HashMap<String, String>>();
    List<Response> audioDetails;
    ImageView ivBack;
    ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.audio_layout, container, false);
        songsManager = new SongsManager(getActivity());
        ivBack =view.findViewById(R.id.ivBack);
        progressBar = view.findViewById(R.id.progressBar_cyclic);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
            }
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        apiService = ApiClient.getClient().create(ApiInterface.class);
        mAdapter = new ListAdapter(AudioFragment.this, getActivity(), songList);
        recyclerView.setAdapter(mAdapter);
        getHistoryUrls();

        return view;
    }

    void getHistoryUrls() {
        String access_token = "29c2c3328c097fb8e5bbf4b8da16b059";
        Call<HistoryUrl> call = apiService.getHistoryUrls(access_token);
        progressBar.setVisibility(View.VISIBLE);
        call.enqueue(new Callback<HistoryUrl>() {
            @Override
            public void onResponse(Call<HistoryUrl> call, retrofit2.Response<HistoryUrl> response) {
                if (response.code() == 200) {
                    Log.e("res", "" + response.toString());
                    progressBar.setVisibility(View.GONE);
                    if (getActivity() != null) {
                        audioDetails = response.body().getResponse();
                        for (int i = 0; i < audioDetails.size(); i++) {
                            HashMap<String, String> map = new HashMap<String, String>();
                            int audioId = audioDetails.get(i).getId();
                            String title = audioDetails.get(i).getTitle();
                            String audioPath = audioDetails.get(i).getAudioPath();
                            String notificationTime = audioDetails.get(i).getNotificationTime();
                            String fileName = audioDetails.get(i).getFilename();
                            map.put("audio_id", String.valueOf(audioId));
                            map.put("audio_title", title);
                            map.put("audio_path", audioPath);
                            map.put("notification_time", notificationTime);
                                map.put("file_name", fileName);
                            songList.add(map);

                        }
                        // Collections.sort(songList, asending);
                        mAdapter.notifyDataSetChanged();

                    }


                } else {
                    try {
                        JSONObject object = new JSONObject(response.errorBody().string());
                        Toast.makeText(getActivity(), object.getString("response"), Toast.LENGTH_SHORT).show();

                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<HistoryUrl> call, Throwable t) {
                progressBar.setVisibility(View.GONE);
                if (getActivity() != null) {

                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    Log.e("response", t + "");
                }
            }
        });

    }

    Comparator<HashMap<String, String>> asending = new Comparator<HashMap<String, String>>() {
        @Override
        public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
            String date1 = o1.get("");
            String date2 = o2.get("");
            return date1.compareTo(date2);

        }
    };


}

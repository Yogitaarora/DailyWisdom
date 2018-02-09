package dailywisdom.techindustan.com.dailywisdom;

import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by android on 8/2/18.
 */

public class ListAdapter extends RecyclerView.Adapter {

    Context context;
    ArrayList<HashMap<String, String>> songList = new ArrayList<HashMap<String, String>>();
    public static onSongSelected onSongSelected;

    public ListAdapter(AudioFragment audioFragment, FragmentActivity activity, ArrayList<HashMap<String, String>> songList) {
        this.songList = songList;
        this.context = activity;
        onSongSelected = (dailywisdom.techindustan.com.dailywisdom.ListAdapter.onSongSelected) activity;

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final MyViewHolder holder1 = (MyViewHolder) holder;
        HashMap<String, String> hash = songList.get(position);
        String title = hash.get("audio_title");
        String fileName = hash.get("file_name");
        Log.e("filename", "" + fileName);
        holder1.tvFileName.setText(title);
        holder1.tvFileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSongSelected.playSelectedSong(songList, position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return songList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tvFileName;


        public MyViewHolder(View view) {
            super(view);
            tvFileName = (TextView) view.findViewById(R.id.tvFileName);


        }
    }

    public interface onSongSelected {
        void playSelectedSong(ArrayList<HashMap<String, String>> songList, int position);
    }
}

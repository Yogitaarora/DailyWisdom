package dailywisdom.techindustan.com.dailywisdom.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by android on 8/2/18.
 */

public class MusicBoundService extends Service {
    private IBinder mBinder = new MyBinder();
    MediaPlayer player = new MediaPlayer();
    ArrayList<HashMap<String, String>> songList = new ArrayList<HashMap<String, String>>();
    int songIndex;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "service bounded succesfully", Toast.LENGTH_SHORT).show();
        return mBinder;
    }

    public void playSong(int songIndex) {
        try {
            player.setDataSource(songList.get(songIndex).get("audio_path"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        player.start();


    }

    public void setSongList(ArrayList<HashMap<String, String>> songList) {
        this.songList = songList;

    }



    public class MyBinder extends Binder {
        public MusicBoundService getService() {
            return MusicBoundService.this;
        }
    }
}

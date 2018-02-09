package dailywisdom.techindustan.com.dailywisdom;

import android.app.DownloadManager;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import dailywisdom.techindustan.com.dailywisdom.service.MusicBoundService;
import dailywisdom.techindustan.com.dailywisdom.utils.SongsManager;

public class MainActivity extends AppCompatActivity implements ListAdapter.onSongSelected, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener {
    DownloadManager downloadManager;
    Uri Download_Uri;
    ArrayList<Long> list = new ArrayList<>();
    DownloadManager.Request request;
    ImageView ivPlay;
    long refid;
    boolean isPlaying = false;
    MusicBoundService musicBoundService;
    boolean isBound;
    SongsManager songsManager;
    ImageView ivPreviousAudioMessages;
    ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> localSongList = new ArrayList<HashMap<String, String>>();
    int songIndex = 0;
    MediaPlayer mp = new MediaPlayer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivPlay = findViewById(R.id.ivPlay);
        songsManager = new SongsManager(getApplicationContext());
        ivPreviousAudioMessages = (ImageView) findViewById(R.id.ivPreviousAudioMessages);
        Intent intent = new Intent(this, MusicBoundService.class);
        startService(intent);
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
        registerReceiver(onComplete,
                new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
        downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        String url = "http://dailywisdoms.com/backend/uploads/audios/Lesson4.mp3";

        ivPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // if we want to stream online
                //audioStream();
                HashMap<String, String> hash = new HashMap<>();
                localSongList = songsManager.getPlayList();
                for (int i = 0; i < localSongList.size(); i++) {
                    hash = localSongList.get(i);
                    if (hash.get("audio_title").equals(songsList.get(songIndex).get("file_name"))) {
                        playOrPauseSong();
                        break;
                    } else {
                        DownloadFile(songsList.get(songIndex).get("audio_path"));
                    }


                }


            }
        });
        ivPreviousAudioMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment, new AudioFragment()).addToBackStack(null).commit();
            }
        });

    }


    public long DownloadFile(String url) {
        Download_Uri = Uri.parse(url);
        request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle("Downloading " + "Sample" + ".mp3");
        request.setDescription("Downloading " + "Sample" + ".mp3");
        request.setVisibleInDownloadsUi(true);
        refid = downloadManager.enqueue(request);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/" + "Sample" + ".mp3");
        list.add(refid);
        return refid;
    }

    BroadcastReceiver onComplete = new BroadcastReceiver() {
        public void onReceive(Context ctxt, Intent intent) {
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            list.remove(referenceId);
            if (list.isEmpty()) {
                Log.e("INSIDE", "" + referenceId);
                NotificationCompat.Builder mBuilder =
                        new NotificationCompat.Builder(MainActivity.this)
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle("sample")
                                .setContentText("All Download completed");
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(455, mBuilder.build());


            }

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(onComplete);
        if (isBound) {
            unbindService(serviceConnection);
            isBound = false;
        }
        Intent intent = new Intent(MainActivity.this, MusicBoundService.class);
        stopService(intent);

    }


    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBoundService.MyBinder binder = (MusicBoundService.MyBinder) service;
            musicBoundService = binder.getService();
            isBound = true;

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBoundService = null;
            isBound = false;
        }
    };


    @Override
    public void playSelectedSong(ArrayList<HashMap<String, String>> musicList, int position) {
        getSupportFragmentManager().popBackStack();
        songsList.clear();
        songsList.addAll(musicList);
        songIndex = position;
        ivPlay.performClick();

    }

    public void playOrPauseSong() {
        if (musicBoundService != null) {
            musicBoundService.setSongList(songsList);
            if (isPlaying) {
                ivPlay.setImageResource(R.drawable.pause_icon);
            } else {
                isPlaying = true;
                musicBoundService.playSong(songIndex);
                ivPlay.setImageResource(R.drawable.play_icon);
            }
        }
    }

    public void audioStream() {
        if (isPlaying) {
            ivPlay.setImageResource(R.drawable.play_icon);
            mp.reset();
            mp.pause();
            isPlaying = false;
        } else {
            isPlaying = true;
            ivPlay.setImageResource(R.drawable.pause_icon);
            mp.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mp.setOnPreparedListener(this);
            mp.setOnErrorListener(this);
            try {
                mp.setDataSource("http://www.robtowns.com/music/blind_willie.mp3");
            } catch (IOException e) {
                e.printStackTrace();
            }
            mp.prepareAsync();
        }

    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }
}

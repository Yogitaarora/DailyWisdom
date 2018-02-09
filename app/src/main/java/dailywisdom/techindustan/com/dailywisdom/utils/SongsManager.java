package dailywisdom.techindustan.com.dailywisdom.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by android on 16/1/18.
 */

public class SongsManager {// SDCard Path
    final String MEDIA_PATH = new String(Environment.getExternalStorageDirectory() + "/newVoices/");
    private ArrayList<HashMap<String, String>> songsList = new ArrayList<HashMap<String, String>>();
    static Context context;


    // Constructor
    public SongsManager(Context context) {
        this.context = context;


    }

    public ArrayList<HashMap<String, String>> getPlayList() {
        File home = new File(MEDIA_PATH);
        if (home.listFiles(new FileExtensionFilter()).length > 0) {
            for (File file : home.listFiles(new FileExtensionFilter())) {
                HashMap<String, String> song = new HashMap<String, String>();
                String[] s = file.getName().split(".");
                song.put("audio_title", file.getName());
                song.put("songPath", file.getPath());
                Date lastModDate = new Date(file.lastModified());
                songsList.add(song);


            }

        }

        return songsList;
    }

    class FileExtensionFilter implements FilenameFilter {
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }
    }

}
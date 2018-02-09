
package dailywisdom.techindustan.com.dailywisdom.model;

import com.google.gson.annotations.SerializedName;


@SuppressWarnings("unused")
public class Response {

    @SerializedName("audio_path")
    private String mAudioPath;
    @SerializedName("filename")
    private String mFilename;
    @SerializedName("id")
    private int mId;
    @SerializedName("notification_time")
    private String mNotificationTime;
    @SerializedName("title")
    private String mTitle;

    public String getAudioPath() {
        return mAudioPath;
    }

    public void setAudioPath(String audioPath) {
        mAudioPath = audioPath;
    }

    public String getFilename() {
        return mFilename;
    }

    public void setFilename(String filename) {
        mFilename = filename;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getNotificationTime() {
        return mNotificationTime;
    }

    public void setNotificationTime(String notificationTime) {
        mNotificationTime = notificationTime;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

}

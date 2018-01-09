package partha.qbchatdemo.chat.utils;

import android.text.TextUtils;

/**
 * TODO: Created by Partha Chatterjee
 */
public class Extra {

    public static String getFileType(String extension) {
        if (TextUtils.isEmpty(extension)) {
            return "message";
        }

        if (extension.equalsIgnoreCase("jpg")
                || extension.equalsIgnoreCase("jpeg")
                || extension.equalsIgnoreCase("png")) {
            return "photo";
        } else if (extension.equalsIgnoreCase("mp4")
                || extension.equalsIgnoreCase("avi")
                || extension.equalsIgnoreCase("mkv")) {
            return "video";
        } else {
            return "other";
        }
    }
}

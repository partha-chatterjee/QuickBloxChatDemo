package partha.qbchatdemo.chat.interfaces;

import com.quickblox.chat.model.QBChatMessage;

/**
 * Created by Partha Chatterjee on 18-08-2017.
 */

public interface OnImageDownloadedListener {
    void inImageDownloaded(Boolean isMe, int position, QBChatMessage qbChatMessage);
}

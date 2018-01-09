package partha.qbchatdemo.chat;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBIncomingMessagesManager;
import com.quickblox.chat.QBPrivateChatManager;
import com.quickblox.chat.QBRestChatService;
import com.quickblox.chat.QBSignaling;
import com.quickblox.chat.QBWebRTCSignaling;
import com.quickblox.chat.exception.QBChatException;
import com.quickblox.chat.listeners.QBChatDialogMessageListener;
import com.quickblox.chat.listeners.QBVideoChatSignalingManagerListener;
import com.quickblox.chat.model.QBAttachment;
import com.quickblox.chat.model.QBChatDialog;
import com.quickblox.chat.model.QBChatMessage;
import com.quickblox.chat.request.QBMessageGetBuilder;
import com.quickblox.content.QBContent;
import com.quickblox.content.model.QBFile;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.QBProgressCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.core.request.QBRequestGetBuilder;
import com.quickblox.videochat.webrtc.QBRTCClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;
import java.util.ArrayList;

import partha.qbchatdemo.R;
import partha.qbchatdemo.chat.adapter.ChatMessageAdapter;
import partha.qbchatdemo.chat.interfaces.OnImageDownloadedListener;
import partha.qbchatdemo.chat.utils.CameraManagerActivity;
import partha.qbchatdemo.chat.utils.L;
import partha.qbchatdemo.chat.utils.Utils;

public class ChatActivity extends CameraManagerActivity implements View.OnClickListener, OnImageDownloadedListener {

    private RecyclerView mRecyclerView;
    private EditText et_msg;
    private Button btn_send, btn_img;

    private int OPPONENT_ID;
    private int MY_ID;
    private String DIALOG_ID = "5a09b53aa28f9a443a2793cf";
    private ArrayList<QBChatMessage> arrayList = new ArrayList<>();
    private static final int user1 = 36662845;
    private static final int user2 = 36663462;
    private ChatMessageAdapter chatMessageAdapter;
    private QBPrivateChatManager privateChatManager;

    Utils utils;
    private ProgressBar progressBar;
    private Uri fileUri = null;
    QBChatService chatService;

    // dialog : 5a008088a28f9a2dca87b137

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        utils = new Utils(this);

//        MY_ID = getIntent().getExtras().getInt("MY_ID");
        OPPONENT_ID = user1;
        MY_ID = user2;
        chatService = QBChatService.getInstance();

        initFields();
    }

    private void initFields() {
        progressBar = new ProgressBar(this);

        et_msg = (EditText) findViewById(R.id.et_msg);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);
        btn_img = (Button) findViewById(R.id.btn_img);
        btn_img.setOnClickListener(this);

        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatMessageAdapter = new ChatMessageAdapter(arrayList, this, MY_ID);
        chatMessageAdapter.setOnImageDownloadedListener(this);
        mRecyclerView.setAdapter(chatMessageAdapter);
        chatMessageAdapter.notifyDataSetChanged();


//        getChatDialogs();
//        getUsersList();
        initCall();
        getPreviousChat();
    }

    private void initCall() {
        QBChatService.getInstance().getVideoChatWebRTCSignalingManager()
                .addSignalingManagerListener(new QBVideoChatSignalingManagerListener() {
                    @Override
                    public void signalingCreated(QBSignaling qbSignaling, boolean createdLocally) {
                        if (!createdLocally) {
                            QBRTCClient.getInstance(ChatActivity.this).addSignaling((QBWebRTCSignaling) qbSignaling);
                        }
                    }
                });
    }

    private void receiveIncomingMessage() {

    }

    // contains last message
    //QBDialog{id=5a008088a28f9a2dca87b137, created_at=2017-06-11 21:02:24, updated_at=2017-10-11 16:13:14, last_msg_user_id=36662845, occupants_ids=[36662845, 36663462], last_message=aptech.digital@gmail.com, last_message_date_sent=1510310594, type=PRIVATE, name=User 2, room_jid=null, user_id=36662845, photo=null, unread_message_count=0, customData=null}
    private void getChatDialogs() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(1000);

        QBRestChatService.getChatDialogs(null, requestBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBChatDialog> result, Bundle params) {
                int totalEntries = params.getInt("total_entries");
            }

            @Override
            public void onError(QBResponseException responseException) {
                L.d(responseException.getMessage());
            }
        });
    }

    private void getPreviousChat() {

        QBChatDialog chatDialog = new QBChatDialog(DIALOG_ID);

        QBMessageGetBuilder messageGetBuilder = new QBMessageGetBuilder();
        messageGetBuilder.setLimit(100);

        QBRestChatService.getDialogMessages(chatDialog, messageGetBuilder).performAsync(new QBEntityCallback<ArrayList<QBChatMessage>>() {
            @Override
            public void onSuccess(ArrayList<QBChatMessage> qbChatMessages, Bundle bundle) {
                arrayList.addAll(qbChatMessages);
                chatMessageAdapter.notifyDataSetChanged();
                scrollToBottom();
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("PARTHA : ", e.getMessage());
            }
        });

        QBIncomingMessagesManager incomingMessagesManager = chatService.getIncomingMessagesManager();
        incomingMessagesManager.addDialogMessageListener(new QBChatDialogMessageListener() {
            @Override
            public void processMessage(String s, QBChatMessage qbChatMessage, Integer integer) {
                arrayList.add(qbChatMessage);
                chatMessageAdapter.notifyDataSetChanged();
                scrollToBottom();
            }

            @Override
            public void processError(String s, QBChatException e, QBChatMessage qbChatMessage, Integer integer) {
                Log.d("PARTHA : ", e.getMessage());
            }
        });

        /*incomingMessagesManager.addMessageListener(
                new QBChatDialogMessageListener() {
                    @Override
                    public void processMessage(String dialogId, QBChatMessage message, Integer senderId) {

                    }

                    @Override
                    public void processError(String dialogId, QBChatException exception, QBChatMessage message, Integer senderId) {

                    }
                });*/

        /*QBChatDialogMessageListener messagesListener;
        messagesListener = new QBChatDialogMessageListener() {
            @Override
            public void processMessage(String dialogId, QBChatMessage qbChatMessage, Integer senderId) {
                //process new incoming message there
                arrayList.add(qbChatMessage);
                chatMessageAdapter.notifyDataSetChanged();
            }

            @Override
            public void processError(String dialogId, QBChatException e, QBChatMessage qbChatMessage, Integer senderId) {
                //process error there
                L.d(e.getMessage());
            }
        };
        incomingMessagesManager.addDialogMessageListener(messagesListener);*/


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send:
                if (et_msg.getText().toString().trim().length() > 0) {
                    sendMessage();
                }
                break;
            case R.id.btn_img:
//                uploadImageDialog();
                onCallCameraButton();
                break;
        }
    }

    private void sendMessage() {
        QBChatMessage chatMessage = new QBChatMessage();
        chatMessage.setBody(et_msg.getText().toString().trim());
//        chatMessage.setDateSent(System.currentTimeMillis());
        chatMessage.setSaveToHistory(true);
        chatMessage.setSenderId(MY_ID);
        chatMessage.setRecipientId(OPPONENT_ID);

        boolean sendToDialog = true; //set true to send this message to the chat or false to create it.

        QBRestChatService.createMessage(chatMessage, sendToDialog).performAsync(new QBEntityCallback<QBChatMessage>() {
            @Override
            public void onSuccess(QBChatMessage message, Bundle bundle) {
                arrayList.add(message);
                chatMessageAdapter.notifyDataSetChanged();
                scrollToBottom();
                et_msg.setText("");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("PARTHA", e.getMessage());
            }
        });
    }

    private void uploadImage(final String filePath, Boolean isCamera) {
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setProgress(0);
        Boolean fileIsPublic = true;    // To get direct access to file you have to upload it with public=true
        String[] tags = new String[]{String.valueOf(MY_ID), String.valueOf(OPPONENT_ID), String.valueOf(System.currentTimeMillis())};
        File file = new File(filePath);


        QBContent.uploadFileTask(file, fileIsPublic, String.valueOf(tags), new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int i) {
                // i - progress in percentages
                progressBar.setProgress(i);
            }
        }).performAsync(new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                // create a message
                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name) + File.pathSeparator + "sent");
                dir.mkdirs();
                File fileSent = new File(dir, qbFile.getId() + ".jpg");
                try {
                    copyFile(new File(filePath), fileSent);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                sendImageMessage(qbFile.getId(), qbFile.getPublicUrl());
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onError(QBResponseException e) {
                // error
                Log.d("PARTHA", e.getMessage());
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void sendImageMessage(Integer id, String publicUrl) {
        QBChatMessage chatMessage = new QBChatMessage();
//        chatMessage.setBody(et_msg.getText().toString().trim());
        chatMessage.setDateSent(System.currentTimeMillis());
        chatMessage.setSaveToHistory(true);
        chatMessage.setSenderId(MY_ID);
        chatMessage.setRecipientId(OPPONENT_ID);

        // attach a photo
        QBAttachment attachment = new QBAttachment(QBAttachment.IMAGE_TYPE);
        attachment.setId(id.toString());
        attachment.setUrl(publicUrl);
//        attachment.setUrl(fileUri.toString());
        chatMessage.addAttachment(attachment);

        boolean sendToDialog = true; //set true to send this message to the chat or false to create it.
        QBRestChatService.createMessage(chatMessage, sendToDialog).performAsync(new QBEntityCallback<QBChatMessage>() {
            @Override
            public void onSuccess(QBChatMessage message, Bundle bundle) {
                arrayList.add(message);
                chatMessageAdapter.notifyDataSetChanged();
                scrollToBottom();
                et_msg.setText("");
            }

            @Override
            public void onError(QBResponseException e) {
                Log.d("PARTHA", e.getMessage());
            }
        });
    }

    private void scrollToBottom() {
        int messageCount = chatMessageAdapter.getItemCount();
//        int lastVisiblePosition = mLinearLayoutManager.findLastCompletelyVisibleItemPosition();
        // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
        // to the bottom of the list to show the newly added message.
        /*if (lastVisiblePosition == -1 || (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
            mMessageRecyclerView.scrollToPosition(positionStart);
        }*/
        mRecyclerView.scrollToPosition(messageCount - 1);
    }

    @Override
    protected void onBitmapReceivedFromCamera(Bitmap bitmap, String path) {
        uploadImage(path, true);
    }

    @Override
    protected void onBitmapReceivedFromGallery(Bitmap bitmap, String path) {
        uploadImage(path, false);
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!sourceFile.exists()) {
            return;
        }

        FileChannel source = null;
        FileChannel destination = null;
        source = new FileInputStream(sourceFile).getChannel();
        destination = new FileOutputStream(destFile).getChannel();
        if (destination != null && source != null) {
            destination.transferFrom(source, 0, source.size());
        }
        if (source != null) {
            source.close();
        }
        if (destination != null) {
            destination.close();
        }
    }

    @Override
    public void inImageDownloaded(Boolean isMe, int position, QBChatMessage qbChatMessage) {//        new ImageDownloadTask(isMe, position, qbChatMessage).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        downloadImage(isMe, position, qbChatMessage);
    }

    private void downloadImage(final Boolean isMe, final int position, final QBChatMessage qbChatMessage) {
        final ArrayList<QBAttachment> files = new ArrayList<>(qbChatMessage.getAttachments());
        QBContent.getFile(Integer.parseInt(files.get(0).getId())).performAsync(new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                String fileUrl = qbFile.getPublicUrl();
                String fileUrl1 = qbFile.getPrivateUrl();
            }

            @Override
            public void onError(QBResponseException e) {
                L.d(e.getMessage());
            }
        });
        QBContent.downloadFile(files.get(0).getId()).performAsync(new QBEntityCallback<InputStream>() {
            @Override
            public void onSuccess(InputStream inputStream, Bundle params) {
                // process file
                File dir;
                if (isMe) {
                    dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name) +  File.pathSeparator + "sent");
                } else {
                    dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name) + File.pathSeparator + "received");
                }
                dir.mkdirs();
                File file = new File(dir, files.get(0).getId() + ".jpg");
//                copyInputStreamToFile(isMe, inputStream, file, qbChatMessage, position);
                new AsyncImage(isMe, inputStream, file, qbChatMessage, position).execute();

            }

            @Override
            public void onError(QBResponseException errors) {
                // errors
                L.d(errors.getMessage());
            }
        });
    }

    private class AsyncImage extends AsyncTask<Void, Void, Void> {

        AlertDialog.Builder alertDialog ;
        ImageView img ;
        private Boolean isMe;
        private InputStream inputStream;
        private QBChatMessage qbChatMessage;
        private File file;
        private int position;
        Bitmap bmp;

        AsyncImage(Boolean isMe, InputStream inputStream, File file, QBChatMessage qbChatMessage, int position){
            this.isMe=isMe;
            this.inputStream = inputStream;
            this.file = file;
            this.qbChatMessage = qbChatMessage;
            this.position = position;
//            alertDialog = new AlertDialog.Builder(ChatActivity.this);
//            img = new ImageView(ChatActivity.this);
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                OutputStream out = new FileOutputStream(file);
                byte[] buf = new byte[4*1024];
                int len;
                while ((len = inputStream.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }
                out.close();
                inputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            L.d("File Size : " + file.length());

//            bmp = BitmapFactory.decodeStream(inputStream);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            chatMessageAdapter.notifyDataSetChanged();


            /*img.setImageBitmap(bmp);
            alertDialog.setView(img);
            alertDialog.show();*/

        }
    }
}

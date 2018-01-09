package partha.qbchatdemo.chat;


import android.support.v7.app.AppCompatActivity;

public class CreateChatActivity extends AppCompatActivity {

    /*private static final int MY_VIEW = 0;
    private static final int OPPONENT_VIEW = 1;

    private static final int PICK_FILE_RESULT_CODE = 3;
    private static final int STORAGE_PERMISSION = 4;
    private static final String HAS_FILE = "has_file";

    private static final int user1 = 36662845;
    private static final int user2 = 36663462;

    private String mPath = "";
    private Uri fileUri = null;

    QBPrivateChatManagerListener privateChatManagerListener = new QBPrivateChatManagerListener() {
        @Override
        public void chatCreated(final QBPrivateChat privateChat, final boolean createdLocally) {
            //if (!createdLocally) {
            //L.d("chatCreated  createdLocally true");
            //} else {
            L.d("chatCreated  created");


            //}
        }
    };
    QBMessageSentListener<QBPrivateChat> messageSentListener = new QBMessageSentListener<QBPrivateChat>() {
        @Override
        public void processMessageSent(QBPrivateChat qbPrivateChat, QBChatMessage qbChatMessage) {
            L.d("Message send successfully");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

*//*                    ChatData chatData = new ChatData();
                    chatData.message = editText.getText().toString();
                    chatData.isMe = true;
                    arrayList.add(chatData);

                    adapter.notifyDataSetChanged();*//*
                }
            });
        }

        @Override
        public void processMessageFailed(QBPrivateChat qbPrivateChat, QBChatMessage qbChatMessage) {
            L.d("Message sending failed");
        }
    };
    private String fileType;
    private ListView listView;
    private Toast toast;
    QBIsTypingListener<QBPrivateChat> isTypingListener = new QBIsTypingListener<QBPrivateChat>() {
        @Override
        public void processUserIsTyping(QBPrivateChat qbPrivateChat, Integer integer) {
            L.d(integer + " typing");
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    toast = Toast.makeText(getApplicationContext(), "Typing", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });

        }

        @Override
        public void processUserStopTyping(QBPrivateChat qbPrivateChat, Integer integer) {
            L.d(integer + "stop typing");
            toast.cancel();
        }
    };
    private int OPPONENT_ID;
    private int MY_ID;
    private ArrayList<ChatData> arrayList = new ArrayList<>();
    private EditText editText;
    private MyAdapter adapter;
    QBMessageListener<QBPrivateChat> privateChatMessageListener = new QBMessageListener<QBPrivateChat>() {
        @Override
        public void processMessage(QBPrivateChat privateChat, final QBChatMessage chatMessage) {

            Log.d("PARTHA", "history check");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    String hasFile = (String) chatMessage.getProperty(HAS_FILE);

                    if (hasFile.equalsIgnoreCase("yes")) {

                        final ArrayList<QBAttachment> files = new ArrayList<>(chatMessage.getAttachments());

                        L.d("Attachment size: " + files.size());
                        //for (QBAttachment attachment : files) { // for now only one picture
                        fileType = files.get(0).getType();
                        //}

                        QBContent.downloadFile(files.get(0).getId(), new QBEntityCallback<InputStream>() {
                            @Override
                            public void onSuccess(InputStream inputStream, Bundle bundle) {

                                if (inputStream != null){

                                    *//*BitmapFactory.Options options = new BitmapFactory.Options();
                                    options.inJustDecodeBounds = true;

                                    Bitmap bmp = BitmapFactory.decodeStream(inputStream, null, options);*//*

                                    DisplayMetrics displayMetrics = new DisplayMetrics();
                                    getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
                                    int reqHeight = displayMetrics.heightPixels / 2;
                                    int reqwidth = displayMetrics.widthPixels / 2;

//                                    Bitmap bmp = decodeSampledBitmapFromStream(inputStream, reqwidth, reqHeight);

//                                    String imgPath = saveToInternalStorage(bmp, files.get(0).getId());


//                                    Bitmap bmp = BitmapFactory.decodeStream(inputStream);

                                    *//*ChatData chatData = new ChatData();
                                    chatData.message = chatMessage.getBody();
                                    chatData.isMe = false;
                                    chatData.chatType = ChatType.PICTURE;

                                    chatData.imageUrl = imgPath;
                                    arrayList.add(chatData);
                                    adapter.notifyDataSetChanged();*//*

                                    ChatData chatData = new ChatData();
                                    chatData.message = "This is an image. will show it later.";
                                    chatData.isMe = false;
                                    chatData.chatType = ChatType.MESSAGE;
                                    arrayList.add(chatData);
                                    adapter.notifyDataSetChanged();

                                }

                                *//*if (inputStream == null) {
                                    L.d(" inputStreamNULL");
                                } else {
                                    L.d(" inputStream NOT null");
                                }

                                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name));
                                dir.mkdirs();
                                File file = new File(dir, "pic.jpg");

                                ChatData chatData = new ChatData();
                                chatData.message = chatMessage.getBody();
                                chatData.isMe = false;
                                chatData.chatType = ChatType.PICTURE;

                                new MyTask(inputStream, file, chatData).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);*//*


                                //copyInputStreamToFile(inputStream, file, chatData);
*//*
                                final File file;
                                if (fileType.equalsIgnoreCase("photo")) {


                                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

                                    if (bitmap == null) {
                                        L.d("Bitmap is NULL");
                                    } else {
                                        L.d("Bitmap is NOT null");
                                    }
                                    //L.d("Bitmap Size " + String.valueOf(bitmap.getByteCount()));

                                    File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), getResources().getString(R.string.app_name));
                                    dir.mkdirs();
                                    file = new File(dir, "pic.jpg");

                                    FileOutputStream out = null;
                                    try {
                                        out = new FileOutputStream(file);
                                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    } finally {
                                        try {
                                            if (out != null) {
                                                out.close();
                                            }
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }

                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            ChatData chatData = new ChatData();
                                            chatData.message = chatMessage.getBody();
                                            chatData.isMe = false;
                                            chatData.chatType = ChatType.PICTURE;
                                            chatData.imageUrl = file.getAbsolutePath();
                                            arrayList.add(chatData);
                                            adapter.notifyDataSetChanged();
                                        }
                                    });
                                }*//*
                                // TODO: later i have to handle video download
                            }

                            @Override
                            public void onError(QBResponseException e) {

                            }
                        });
                    } else {
                        ChatData chatData = new ChatData();
                        chatData.message = chatMessage.getBody();
                        chatData.isMe = false;
                        chatData.chatType = ChatType.MESSAGE;
                        arrayList.add(chatData);
                        adapter.notifyDataSetChanged();
                    }


                }
            });

        }

        @Override
        public void processError(QBPrivateChat privateChat, QBChatException error, QBChatMessage originMessage) {
            L.d("sendMessage processError" + error.getMessage());
        }
    };
    private QBPrivateChatManager privateChatManager;
    private QBPrivateChat privateChat;
    private boolean isTypingShowing = false;
    public static final int CAMERA_REQUEST_CODE = 101;
    public static final int REQUEST_IMAGE = 111;
    public static final int MEDIA_TYPE_IMAGE = 10;
    public static final int CAPTURE_INTENT_CALLED = 102;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_chat);

        MY_ID = getIntent().getExtras().getInt("MY_ID");
        OPPONENT_ID = user2;

        listView = (ListView) findViewById(R.id.listView);

        adapter = new MyAdapter(arrayList);

        //recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        listView.setAdapter(adapter);

        editText = (EditText) findViewById(R.id.editText);

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
*//*                if (privateChat != null && !isTypingShowing) {
                    try {
                        privateChat.sendIsTypingNotification();
                        isTypingShowing = true;

                        final int size = editText.getText().toString().trim().length();

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                if (editText.getText().toString().trim().length() > size) {
                                    try {
                                        privateChat.sendIsTypingNotification();
                                        isTypingShowing = false;
                                    } catch (XMPPException | SmackException.NotConnectedException e) {
                                        e.printStackTrace();
                                    }
                                } else if (editText.getText().toString().trim().length() == size) {
                                    try {
                                        privateChat.sendStopTypingNotification();
                                        isTypingShowing = false;
                                    } catch (XMPPException | SmackException.NotConnectedException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }, 1000);


                    } catch (XMPPException | SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }
                }*//*

                try {
                    privateChat.sendIsTypingNotification();
                } catch (XMPPException | SmackException.NotConnectedException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
*//*                if (privateChat != null && !isTypingShowing) {
                    try {
                        privateChat.sendIsTypingNotification();
                        isTypingShowing = true;
                    } catch (XMPPException | SmackException.NotConnectedException e) {
                        e.printStackTrace();
                    }
                }*//*
            }
        });

        //getChatDialogs();
        //initPrivateChat(OPPONENT_ID);

        initPrivateChat();


        final Button sendMessage = (Button) findViewById(R.id.send_message);
        sendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (editText.getText().toString().trim().length() > 0) {
                    sendMessage(null, null);
                } else {
                    L.d("Please enter something");
                }
            }
        });

        QBChatService.getTotalUnreadMessagesCount(null, new QBEntityCallback<Integer>() {
            @Override
            public void onSuccess(Integer total, Bundle params) {
//                Log.i(TAG, "total unread messages: " + total);
                Toast.makeText(CreateChatActivity.this, "Total Unread Messages : "+total, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(QBResponseException responseException) {
            }
        });
    }

    private void copyInputStreamToFile(InputStream in, final File file, final ChatData chatData) {
        try {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        L.d("File Size : " + file.length());

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                chatData.imageUrl = file.getAbsolutePath();
                arrayList.add(chatData);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_chat, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_send_file:

                int hasWriteStoragePermission = ContextCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE);

                if (hasWriteStoragePermission != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(CreateChatActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            STORAGE_PERMISSION);
                } else {
//                    openFileManager();
                    uploadImageDialog();
                }

                break;
        }
        return true;
    }

    private void openFileManager() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//        intent.setType("file*//*");
        intent.setType("file*//*");
        startActivityForResult(intent, PICK_FILE_RESULT_CODE);
    }

    *//*@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case STORAGE_PERMISSION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFileManager();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Sorry, not enough permission!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        }
    }*//*

    *//*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_FILE_RESULT_CODE:

                if (resultCode == RESULT_OK) {
                    if (data.getData() != null) {
                        L.d(getRealPathFromURI(data.getData()));

                        uploadFile(data.getData());
                    }
                }
                break;
        }
    }*//*

    private void uploadFile(Uri fileUri) {

        final File file = new File(getRealPathFromURI(fileUri));

        QBContent.uploadFileTask(file, false, null, new QBEntityCallback<QBFile>() {
            @Override
            public void onSuccess(QBFile qbFile, Bundle bundle) {
                L.d("uploadFileTask onSuccess " + qbFile.getSize());
                sendMessage(qbFile.getId().toString(), file);
            }

            @Override
            public void onError(QBResponseException e) {
                L.d("uploadFileTask onError");
            }
        }, new QBProgressCallback() {
            @Override
            public void onProgressUpdate(int i) {
                L.d("Progress: " + i);
            }
        });
    }

    private String getRealPathFromURI(Uri uri) {

        String filePath = "";
        if (uri.getScheme().compareTo("content") == 0) {

            L.d("content");

            String[] projection = {"_data"};  // "_data" is the string to look for message. It's equivalent to MediaStore.Images.Media.DATA
            CursorLoader loader = new CursorLoader(CreateChatActivity.this, uri, projection, null, null, null);
            Cursor cursor = loader.loadInBackground();
            int columnIndex = cursor.getColumnIndexOrThrow("_data");
            cursor.moveToFirst();

            filePath = cursor.getString(columnIndex);

            cursor.close();
        } else if (uri.getScheme().compareTo("file") == 0) {

            L.d("file");

            filePath = uri.getPath();
        } else {
            L.d("else file path");
            filePath = uri.getPath();
        }
        return filePath;
    }

    private void initPrivateChat() {
        privateChatManager = QBChatService.getInstance().getPrivateChatManager();
        privateChat = privateChatManager.getChat(OPPONENT_ID);

        if (privateChat == null) {
            privateChat = privateChatManager.createChat(OPPONENT_ID, privateChatMessageListener);
        }

        privateChat.addMessageListener(privateChatMessageListener);
        privateChat.addMessageSentListener(messageSentListener);
        privateChat.addIsTypingListener(isTypingListener);

        privateChatManager.createDialog(OPPONENT_ID, new QBEntityCallback<QBDialog>() {
            @Override
            public void onSuccess(QBDialog dialog, Bundle args) {
                L.d("50 " + "success");
            }

            @Override
            public void onError(QBResponseException errors) {
                L.d("51 " + "failed");
            }
        });
    }

    // get all chat dialogs
    // TODO: this method need to be called from message list screen
    private void getChatDialogs() {
        QBChatService.getChatDialogs(QBDialogType.PRIVATE, new QBRequestGetBuilder(), new QBEntityCallback<ArrayList<QBDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBDialog> qbDialogs, Bundle bundle) {

                if (qbDialogs.size()>0) {
                    for (Integer integer : qbDialogs.get(0).getOccupants()) {
                        if (integer != MY_ID) {
                            OPPONENT_ID = integer;
                            initPrivateChat();
                            break;
                        }
                    }
                }

                L.d(MY_ID + " " + OPPONENT_ID);
            }

            @Override
            public void onError(QBResponseException e) {
                L.d("getChatDialog() error : "+e.getMessage());
            }
        });
    }

    private void sendMessage(String uploadedFileId, File file) {
        L.d("uploadedFileId " + uploadedFileId);

        QBChatService.getInstance().getPrivateChatManager().addPrivateChatManagerListener(privateChatManagerListener);

        String fileExt;
        if (file != null) {
            fileExt = MimeTypeMap.getFileExtensionFromUrl(file.getName());
        } else {
            fileExt = null;
        }

        String fileType = Extra.getFileType(fileExt);


        try {
            QBChatMessage chatMessage = new QBChatMessage();
            chatMessage.setBody(editText.getText().toString());
//            chatMessage.setProperty("save_to_history", "1");        // save msg to history
            chatMessage.setSaveToHistory(true);

            if (uploadedFileId != null && fileExt != null) {
                chatMessage.setProperty(HAS_FILE, "yes");
                QBAttachment attachment = new QBAttachment(fileType);
                attachment.setId(uploadedFileId);
                chatMessage.addAttachment(attachment);
            } else {
                chatMessage.setProperty(HAS_FILE, "no");
            }

            privateChat.sendMessage(chatMessage);

            ChatData chatData = new ChatData();
            chatData.message = editText.getText().toString();
            chatData.isMe = true;

            if (fileType.equalsIgnoreCase("photo")) {
                chatData.imageUrl = file.getAbsolutePath();
                chatData.chatType = ChatType.PICTURE;
            } else if (fileType.equalsIgnoreCase("video")) {
                chatData.imageUrl = file.getAbsolutePath();
                chatData.chatType = ChatType.VIDEO;
            } else if (fileType.equalsIgnoreCase("message")) {
                chatData.chatType = ChatType.MESSAGE;
            } else {
                chatData.chatType = ChatType.OTHER;
            }

            arrayList.add(chatData);

            adapter.notifyDataSetChanged();

            editText.setText("");

        } catch (SmackException.NotConnectedException e) {
            L.d("SmackException.NotConnectedException " + e.getMessage());
        }
    }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        private InputStream mInputStream;
        private File mFile;
        private ChatData mChatData;


        MyTask(InputStream stream, File file, ChatData chatData) {
            mInputStream = stream;
            mFile = file;
            mChatData = chatData;
        }


        @Override
        protected Void doInBackground(Void... voids) {

            copyInputStreamToFile(mInputStream, mFile, mChatData);

            return null;
        }
    }

    private class MyAdapter extends BaseAdapter {

        private ArrayList<ChatData> mArrayList;

        MyAdapter(ArrayList<ChatData> data) {
            this.mArrayList = data;
        }


        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int i) {
            return mArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public int getViewTypeCount() {
            return 2;
        }

        @Override
        public int getItemViewType(int position) {
            return mArrayList.get(position).isMe ? MY_VIEW : OPPONENT_VIEW;
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {

            switch (this.getItemViewType(position)) {
                case MY_VIEW:

                    MyHolder1 holder;

                    if (view == null) {
                        view = getLayoutInflater().inflate(R.layout.message_my_view, null);
                        holder = new MyHolder1(view);
                        view.setTag(holder);
                    } else {
                        holder = (MyHolder1) view.getTag();
                    }

                    if (arrayList.get(position).chatType.equals(ChatType.MESSAGE)) {
                        holder.imageView.setVisibility(View.GONE);

                    } else if (arrayList.get(position).chatType.equals(ChatType.PICTURE)) {
                        L.d("Image in List: " + arrayList.get(position).imageUrl);
                        holder.imageView.setVisibility(View.VISIBLE);
                        Glide.with(CreateChatActivity.this).load(arrayList.get(position).imageUrl).into(holder.imageView);
                    } else if (arrayList.get(position).chatType.equals(ChatType.VIDEO)) {
                        holder.imageView.setVisibility(View.VISIBLE);
                        Glide.with(CreateChatActivity.this).load(arrayList.get(position).imageUrl).into(holder.imageView);
                    } else if (arrayList.get(position).chatType.equals(ChatType.OTHER)) {
                        holder.imageView.setVisibility(View.GONE);
                    }

                    holder.textView.setText(mArrayList.get(position).message);

                    break;
                case OPPONENT_VIEW:

                    MyHolder2 holder2;

                    if (view == null) {
                        view = getLayoutInflater().inflate(R.layout.message_opponent_view, null);
                        holder2 = new MyHolder2(view);
                        view.setTag(holder2);
                    } else {
                        holder2 = (MyHolder2) view.getTag();
                    }

                    if (arrayList.get(position).chatType.equals(ChatType.MESSAGE)) {
                        holder2.imageView.setVisibility(View.GONE);

                    } else if (arrayList.get(position).chatType.equals(ChatType.PICTURE)) {
                        holder2.imageView.setVisibility(View.VISIBLE);
                        Glide.with(CreateChatActivity.this).load(*//*Uri.parse(*//*arrayList.get(position).imageUrl*//*)*//*).into(holder2.imageView);
                    } else if (arrayList.get(position).chatType.equals(ChatType.VIDEO)) {
                        holder2.imageView.setVisibility(View.VISIBLE);
                        Glide.with(CreateChatActivity.this).load(arrayList.get(position).imageUrl).into(holder2.imageView);
                    } else if (arrayList.get(position).chatType.equals(ChatType.OTHER)) {
                        holder2.imageView.setVisibility(View.GONE);
                    }

                    holder2.textView.setText(mArrayList.get(position).message);

                    break;
            }

            return view;
        }

        private class MyHolder1 {
            private TextView textView;
            private ImageView imageView;

            MyHolder1(View view) {
                textView = (TextView) view.findViewById(R.id.textView);
                imageView = (ImageView) view.findViewById(R.id.imageView);
            }
        }

        private class MyHolder2 {
            private TextView textView;
            private ImageView imageView;

            MyHolder2(View view) {
                textView = (TextView) view.findViewById(R.id.textView);
                imageView = (ImageView) view.findViewById(R.id.imageView);
            }
        }
    }


    public void uploadImageDialog() {
        final CharSequence[] options = {"Take Photo", "Choose from Gallery", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateChatActivity.this);
        builder.setTitle("Add Photo!");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Take Photo")) {

                    if (PermissionUtils.checkCamerapermission(CreateChatActivity.this)) {
                        takePhoto();
//                        takePhotoIntent();
//                        dispatchTakePictureIntent();
                    } else {
                        askCameraPermission();
                    }
                } else if (options[item].equals("Choose from Gallery")) {
                    shareFromGalleryIntent();

                } else if (options[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private void askCameraPermission() {
//        ActivityCompat.requestPermissions(this, permissions, CAMERA_REQUEST_CODE);
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE}, CAMERA_REQUEST_CODE);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        fileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE, getFileName());
        intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
        startActivityForResult(intent, CAPTURE_INTENT_CALLED);
    }

    private void shareFromGalleryIntent() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image*//*");
        startActivityForResult(intent, REQUEST_IMAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case CAMERA_REQUEST_CODE:

                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    uploadImageDialog();
                } else {

                    if (!ActivityCompat.shouldShowRequestPermissionRationale(CreateChatActivity.this,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE) && !ActivityCompat.shouldShowRequestPermissionRationale(CreateChatActivity.this,
                            android.Manifest.permission.CAMERA)) {

                        // Show an explanation to the user *asynchronously* -- don't block
                        // this thread waiting for the user's response! After the user
                        // sees the explanation, try again to request the permission.
                        Toast.makeText(this, "Go to app settings to enable storage and camera permission.", Toast.LENGTH_LONG).show();
                    } else {
                        askCameraPermission();
                    }
                }
                break;
        }
    }

    public String getFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyy_hhmmss");
        return simpleDateFormat.format(new Date());
    }

    *//**
     * Create a file Uri for saving an image or video
     *//*
    public Uri getOutputMediaFileUri(int type, String name) {
        return Uri.fromFile(getOutputMediaFile(type, name));
    }

    *//**
     * Create a File for saving an image or video
     *//*
    public File getOutputMediaFile(int type, String fName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        *//*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Stock_Up");*//*
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), getResources().getString(R.string.app_name));
        // This location works best if you want the created images to be shared
        // between applications and persist after your app has been uninstalled.

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                Log.e("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_" + fName + ".jpg");
        } *//*else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        }*//* else {
            return null;
        }

        return mediaFile;
    }

    *//**
     * Check & Returns the correct oriented Bitmap Image
     *//*
    private Bitmap getBitmapExifInterface(Uri selectedImageUri, Bitmap bmp) {
        /////for rotation of image
        ExifInterface exif;
        int rotation;
        try {
            mPath = ImageFilePath.getPath(this, selectedImageUri);
//            mPath = selectedImageUri.getPath();// "file:///mnt/sdcard/FileName.mp3"
            Log.e("PARENT- ", " Path: " + mPath);
            exif = new ExifInterface(mPath);
            rotation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

            bmp = getRotatedBitmap(rotation, bmp);

            Log.e("PARENT- ", " Exif Rotation: " + rotation);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    *//**
     * For checking bitmap image orientation and returns the rotated bitmap.
     *//*
    public Bitmap getRotatedBitmap(int rotation, Bitmap bmp) {

        Matrix matrix = new Matrix();

        switch (rotation) {
            case ExifInterface.ORIENTATION_NORMAL:
                return bmp;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.setScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.setRotate(180);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.setRotate(180);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.setRotate(90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.setRotate(90);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.setRotate(-90);
                matrix.postScale(-1, 1);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.setRotate(270);
                break;
            case ExifInterface.ORIENTATION_UNDEFINED:
                return bmp;
            default:
                return bmp;
        }

        try {
            Bitmap bmRotated = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);
            bmp.recycle();
            return bmRotated;
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
            return null;
        }

    }

    *//**
     * Returns the scaled bitmap (by calculating the sample size)
     *//*
    private Bitmap decodeBitmap(InputStream inputStream) {

        Bitmap bitmap = null;
        InputStream is1 = null, is2 = null;

        try {
            Log.e("PARENT- ", "BITMAP: InputStream- " + inputStream.toString());

            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            // Fake code simulating the copy
            // You can generally do better with nio if you need...
            // And please, unlike me, do something about the Exceptions :D
            byte[] buffer = new byte[1024];
            int len;
            while ((len = inputStream.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            // Open new InputStreams using the recorded bytes
            // Can be repeated as many times as you wish
            is1 = new ByteArrayInputStream(baos.toByteArray());
            is2 = new ByteArrayInputStream(baos.toByteArray());

                *//*DecodeBitmap before sampling*//*
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            BitmapFactory.decodeStream(is1, null, options);
            int imageHeight = options.outHeight;
            int imageWidth = options.outWidth;


                *//*Calculate screen height & width*//*
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int reqHeight = displayMetrics.heightPixels / 2;
            int reqwidth = displayMetrics.widthPixels / 2;

            Log.e("PURCHASED_ACTIVITY ", "BITMAP: width- " + imageWidth + " height- " + imageHeight +
                    " reqheight- " + reqHeight + " reqWidth- " + reqwidth);

            options.inSampleSize = calculateInSampleSize(options, reqwidth, reqHeight);

            Log.e("PURCHASED_ACTIVITY ", "BITMAP: SampleSize- " + options.inSampleSize + " InputStream- " + inputStream.toString());
            // Decode bitmap with inSampleSize set (DecodeSampledBitmap)
            options.inJustDecodeBounds = false;
            options.inPreferredConfig = Bitmap.Config.RGB_565;
            options.inDither = true;
            bitmap = BitmapFactory.decodeStream(is2, null, options);

            if (bitmap != null) {
                Log.e("PURCHASED_ACTIVITY ", "BITMAP: height- " + bitmap.getHeight() + " width- " + bitmap.getWidth());
            } else {
                Log.e("PURCHASED_ACTIVITY ", "BITMAP: is Null.");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (is1 != null) {
                    is1.close();
                }
                if (is2 != null) {
                    is2.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bitmap;
    }

    *//*
    * Calculating sample size of the bitmap
    * *//*
    public int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    *//**
     * - Class is used to get Absolute Path from file -<br>
     * Irrespective of any android build version.
     *//*
    private static class ImageFilePath {

        *//**
         * Method for return file path of Gallery image
         *
         * @param context
         * @param uri
         * @return path of the selected image file from gallery
         *//*
        static String nopath = "Select Video Only";

        @SuppressLint("NewApi")
        static String getPath(final Context context, final Uri uri) {

            // check here to KITKAT or new version
            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

            // DocumentProvider
            if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {

                // ExternalStorageProvider
                if (isExternalStorageDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    if ("primary".equalsIgnoreCase(type)) {
                        return Environment.getExternalStorageDirectory() + "/"
                                + split[1];
                    }
                }
                // DownloadsProvider
                else if (isDownloadsDocument(uri)) {

                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(
                            Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));

                    return getDataColumn(context, contentUri, null, null);
                }
                // MediaProvider
                else if (isMediaDocument(uri)) {
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];

                    Uri contentUri = null;
                    if ("image".equals(type)) {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    } else if ("video".equals(type)) {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                    } else if ("audio".equals(type)) {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                    }

                    final String selection = "_id=?";
                    final String[] selectionArgs = new String[]{split[1]};

                    return getDataColumn(context, contentUri, selection,
                            selectionArgs);
                }
            }
            // MediaStore (and general)
            else if ("content".equalsIgnoreCase(uri.getScheme())) {

                // Return the remote address
                if (isGooglePhotosUri(uri))
                    return uri.getLastPathSegment();

                return getDataColumn(context, uri, null, null);
            }
            // File
            else if ("file".equalsIgnoreCase(uri.getScheme())) {
                return uri.getPath();
            }

            return nopath;
        }

        *//**
         * Get the value of the data column for this Uri. This is <span id="IL_AD2"
         * class="IL_AD">useful</span> for MediaStore Uris, and other file-based
         * ContentProviders.
         *
         * @param context       The context.
         * @param uri           The Uri to query.
         * @param selection     (Optional) Filter used in the query.
         * @param selectionArgs (Optional) Selection arguments used in the query.
         * @return The value of the _data column, which is typically a file path.
         *//*
        static String getDataColumn(Context context, Uri uri,
                                    String selection, String[] selectionArgs) {

            Cursor cursor = null;
            final String column = "_data";
            final String[] projection = {column};

            try {
                cursor = context.getContentResolver().query(uri, projection,
                        selection, selectionArgs, null);
                if (cursor != null && cursor.moveToFirst()) {
                    final int index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(index);
                }
            } finally {
                if (cursor != null)
                    cursor.close();
            }
            return nopath;
        }

        *//**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is ExternalStorageProvider.
         *//*
        static boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri
                    .getAuthority());
        }

        *//**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is DownloadsProvider.
         *//*
        static boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri
                    .getAuthority());
        }

        *//**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is MediaProvider.
         *//*
        static boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri
                    .getAuthority());
        }

        *//**
         * @param uri The Uri to check.
         * @return Whether the Uri authority is Google Photos.
         *//*
        static boolean isGooglePhotosUri(Uri uri) {
            return "com.google.android.apps.photos.content".equals(uri
                    .getAuthority());
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("onActivityResult: ", "requestCode=" + requestCode + ", resultCode=" + resultCode);

        if (requestCode == REQUEST_IMAGE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    final Uri uri = data.getData();
                    Log.d("onActivityResult: ", "Uri: " + uri.toString());

//                    sendImageMessage(uri);

                    uploadFile(uri);
                }
            }
        } else if (requestCode == CAPTURE_INTENT_CALLED) {

            if (resultCode == RESULT_OK) {

                Uri selectedImageUri = fileUri;

                Bitmap mBitmap;
                try {
                    InputStream input = getContentResolver().openInputStream(selectedImageUri);
                    mBitmap = getBitmapExifInterface(selectedImageUri, decodeBitmap(input));
                    Log.e("GALLERY_INTENT_CALLED", "" + mPath);
//                    onBitmapReceivedFromCamera(mBitmap, mPath);
                    if (input != null) {
                        input.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                sendImageMessage(selectedImageUri);

                uploadFile(selectedImageUri);
            }
        }
    }

    private String saveToInternalStorage(Bitmap bitmapImage, String name){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/QuickBloxChatImg
        File directory = cw.getDir("QuickBloxChatImg", Context.MODE_PRIVATE);
        // Create imageDir
        File mypath=new File(directory, name+".jpg");

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);
            // Use the compress method on the BitMap object to write image to the OutputStream
            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath();
    }

    public Bitmap decodeSampledBitmapFromStream(InputStream inputStream, int reqWidth, int reqHeight) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            int n;
            byte[] buffer = new byte[1024];
            while ((n = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, n);
            }
            return decodeSampledBitmapFromByteArray(outputStream.toByteArray(), reqWidth, reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public Bitmap decodeSampledBitmapFromByteArray(byte[] data, int reqWidth, int reqHeight) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeByteArray(data, 0, data.length, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeByteArray(data, 0, data.length, options);
    }*/

}

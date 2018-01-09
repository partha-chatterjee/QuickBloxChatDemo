package partha.qbchatdemo.chat;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.quickblox.auth.session.QBSession;
import com.quickblox.auth.session.QBSessionManager;
import com.quickblox.auth.session.QBSessionParameters;
import com.quickblox.chat.QBChatService;
import com.quickblox.chat.QBSignaling;
import com.quickblox.chat.QBWebRTCSignaling;
import com.quickblox.chat.listeners.QBVideoChatSignalingManagerListener;
import com.quickblox.core.QBEntityCallback;
import com.quickblox.core.exception.QBResponseException;
import com.quickblox.users.QBUsers;
import com.quickblox.users.model.QBUser;
import com.quickblox.videochat.webrtc.QBRTCClient;

import partha.qbchatdemo.R;
import partha.qbchatdemo.chat.utils.L;

@SuppressLint("SetTextI18n")
public class CreateSessionActivity extends AppCompatActivity {

    private EditText lUser, lPass;
    private QBUser qbUser;
    private int myUserID;
    private Button btn_logout;

    private static final int user1 = 36662845;
    private static final int user2 = 36663462;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_session);

        initViews();
    }


    private void initViews() {
        lUser = (EditText) findViewById(R.id.username);
        lPass = (EditText) findViewById(R.id.password);
        btn_logout = (Button) findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logoutChatSession();
            }
        });

        /*ImageView img = (ImageView) findViewById(R.id.img);
        Picasso.with(this)
                .load("https://s3.amazonaws.com/qbprod/81bd504381da4be5ae98afba1a0538f200")
                .placeholder(R.drawable.no_image_placeholder_gray)
                .resize(250,250)
                .into(img);*/

        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(lUser.getText().toString()) && TextUtils.isEmpty(lPass.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Enter all details", Toast.LENGTH_SHORT).show();
                } else {
                    qbUser = new QBUser(lUser.getText().toString(), lPass.getText().toString());
                    qbUser.setId(user2);
//                    createSessionForRegistration();
//                    qbSignUp();
//                    createSessionWithUSer(qbUser);
                    loginUser(qbUser);
                }
            }
        });

        Button user1 = (Button) findViewById(R.id.user_1);
        Button user2 = (Button) findViewById(R.id.user_2);

        user1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lUser.setText("user1");
                lPass.setText("12345");
            }
        });

        user2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lUser.setText("user2");
                lPass.setText("12345");
            }
        });
    }

    // creating session with QbUser
    private void createSessionWithUSer(final QBUser qbUser) {
        /*QBAuth.createSession(qbUser, new QBEntityCallback<QBSession>() {
            @Override
            public void onSuccess(QBSession qbSession, Bundle bundle) {
                L.d("createSession With QbUser onSuccess " + qbSession.getUserId());
                myUserID = qbSession.getUserId();
                qbUser.setId(qbSession.getUserId());
                // now logging in him
                loginUser(qbUser);
            }

            @Override
            public void onError(QBResponseException e) {
                L.d("createSession With QbUser onError " + e.getMessage() + " " + e.getHttpStatusCode());
            }
        });*/


        QBSessionManager.getInstance().addListener(new QBSessionManager.QBSessionListener() {

            @Override
            public void onSessionCreated(QBSession qbSession) {
                L.d("createSession With QbUser onSuccess " + qbSession.getUserId());
                myUserID = qbSession.getUserId();
                qbUser.setId(qbSession.getUserId());
                // now logging in him
                loginUser(qbUser);
            }

            @Override
            public void onSessionUpdated(QBSessionParameters sessionParameters) {
                //calls when user signed in or signed up
                //QBSessionParameters stores information about signed in user.
                L.d("Updated");
            }

            @Override
            public void onSessionDeleted() {
                //calls when user signed Out or session was deleted
                L.d("Deleted");
            }

            @Override
            public void onSessionRestored(QBSession session) {
                //calls when session was restored from local storage
                L.d("createSession With QbUser onSuccess " + session.getUserId());
                myUserID = session.getUserId();
                qbUser.setId(session.getUserId());
                // now logging in him
                loginUser(qbUser);
            }

            @Override
            public void onSessionExpired() {
                //calls when session is expired
                L.d("Expired");
            }

            @Override
            public void onProviderSessionExpired(String s) {
                L.d("Expired " + s);

            }
        });
    }

    private void qbSignUp() {
        // user sign up
/*
        QBUsers.signUp(qbUser, new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser qbUser, Bundle bundle) {
                L.d("createSession signUp onSuccess");
                // user successfully registered, now log him in
                createSessionWithUSer(qbUser);
            }

            @Override
            public void onError(QBResponseException e) {
                // might be user is already registered
                L.d("createSession signUp onError " + e.getMessage() + " " + e.getHttpStatusCode());
                createSessionWithUSer(qbUser);
            }
        });
*/

        QBUsers.signUp(qbUser).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success
                createSessionWithUSer(qbUser);
            }

            @Override
            public void onError(QBResponseException error) {
                // error
                createSessionWithUSer(qbUser);
            }
        });
    }

/*    private void getDialog() {
        QBRequestGetBuilder requestBuilder = new QBRequestGetBuilder();
        requestBuilder.setLimit(100);

        QBChatService.getChatDialogs(null, requestBuilder, new QBEntityCallback<ArrayList<QBDialog>>() {
            @Override
            public void onSuccess(ArrayList<QBDialog> dialogs, Bundle args) {
                int totalEntries = args.getInt("total_entries");
                L.d("12 " + totalEntries + " " + "dialog success");
            }

            @Override
            public void onError(QBResponseException errors) {
                L.d("13 " + "dialog failed" + " " + errors.getMessage());
            }
        });
    }*/

/*    private void initPrivateChat(int opponentId) {
        QBPrivateChatManager privateChatManager = QBChatService.getInstance().getPrivateChatManager();
        privateChatManager.createDialog(opponentId, new QBEntityCallback<QBDialog>() {
            @Override
            public void onSuccess(QBDialog dialog, Bundle args) {
                L.d("50 " + "success");
            }

            @Override
            public void onError(QBResponseException errors) {
                L.d("51 " + "failed");
            }
        });
    }*/

    private void loginUser(QBUser user) {
        /*QBChatService.getInstance().login(user, new QBEntityCallback() {
            @Override
            public void onSuccess(Object o, Bundle bundle) {
                L.d("QBChatService login onSuccess " + myUserID);

                QBSessionParameters sessionParameters = QBSessionManager.getInstance().getSessionParameters();

                // user logged in
//                Intent intent = new Intent(CreateSessionActivity.this, CreateChatActivity.class);
                *//*Intent intent = new Intent(CreateSessionActivity.this, ChatActivity.class);
                intent.putExtra("MY_ID", myUserID);
                startActivity(intent);*//*
            }

            @Override
            public void onError(QBResponseException e) {
                L.d("QBChatService login onError " + e.getMessage() + " " + e.getHttpStatusCode());
            }
        });*/

        QBUsers.signIn(user).performAsync(new QBEntityCallback<QBUser>() {
            @Override
            public void onSuccess(QBUser user, Bundle args) {
                // success
                L.d("QBChatService login onSuccess " + myUserID);

                QBSessionParameters sessionParameters = QBSessionManager.getInstance().getSessionParameters();

                QBChatService.getInstance().login(qbUser, new QBEntityCallback() {
                    @Override
                    public void onSuccess(Object o, Bundle bundle) {
                        // user logged in
//                Intent intent = new Intent(CreateSessionActivity.this, CreateChatActivity.class);
                        /*Intent intent = new Intent(CreateSessionActivity.this, ChatActivity.class);
                        intent.putExtra("MY_ID", myUserID);
                        startActivity(intent);*/
                        initCall();
                    }

                    @Override
                    public void onError(QBResponseException e) {
                        L.d(e.getMessage());
                    }
                });

            }

            @Override
            public void onError(QBResponseException error) {
                // error
                L.d("QBChatService login onError " + error.getMessage() + " : " + error.getHttpStatusCode());
            }
        });
    }

    private void initCall() {
        QBChatService.getInstance().getVideoChatWebRTCSignalingManager()
                .addSignalingManagerListener(new QBVideoChatSignalingManagerListener() {
                    @Override
                    public void signalingCreated(QBSignaling qbSignaling, boolean createdLocally) {
                        if (!createdLocally) {
                            QBRTCClient.getInstance(CreateSessionActivity.this).addSignaling((QBWebRTCSignaling) qbSignaling);
                            Intent intent = new Intent(CreateSessionActivity.this, ChatActivity.class);
                            intent.putExtra("MY_ID", myUserID);
                            startActivity(intent);
                        }
                    }
                });
    }

    private void logoutChatSession() {
        QBUsers.signOut().performAsync(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void result, Bundle args) {
                QBChatService.getInstance().destroy();
            }

            @Override
            public void onError(QBResponseException errors) {
                L.d("error : " + errors.getMessage());
            }
        });
        /*QBUsers.signOut(new QBEntityCallback<Void>() {
            @Override
            public void onSuccess(Void aVoid, Bundle bundle) {
                QBAuth.deleteSession(new QBEntityCallback<Void>() {
                    @Override
                    public void onSuccess(Void aVoid, Bundle bundle) {
                        QBChatService.getInstance().destroy();
                    }

                    @Override
                    public void onError(QBResponseException e) {

                    }
                });
            }

            @Override
            public void onError(QBResponseException e) {
                L.d("error : "+e.getMessage());
            }
        });*/
    }

    protected boolean checkSignIn() {
//        return QBSessionManager.getInstance().getSessionParameters() != null;
        return false;
    }
}

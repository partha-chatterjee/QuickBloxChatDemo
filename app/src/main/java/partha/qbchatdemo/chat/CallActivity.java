package partha.qbchatdemo.chat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.quickblox.videochat.webrtc.QBRTCClient;
import com.quickblox.videochat.webrtc.view.QBRTCSurfaceView;

import org.webrtc.EglBase;

import partha.qbchatdemo.R;

public class CallActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        initFields();
    }

    private void initFields() {
        QBRTCSurfaceView surfaceView = (QBRTCSurfaceView) findViewById(R.id.remote_video_view);
        EglBase eglContext = QBRTCClient.getInstance(this).getEglContext();
        surfaceView.init(eglContext.getEglBaseContext(), null);


        /*QBRTCSurfaceView.init(EglBase.Context, RendererCommon.RendererEvents);//Initialize this view using webrtc Egl context, It is allowed to call init() to reinitialize the view after a previous init()/release() cycle.
        QBRTCSurfaceView.release(); // releases all related GL resources
        QBRTCSurfaceView.setScalingType(scalingType); //Set how the video will fill the allowed layout area
        QBRTCSurfaceView.setMirror(mirror); //Set if the video stream should be mirrored or not.
        QBRTCSurfaceView.requestLayout();*/ // Request to invalidate view when something has changed
    }
}

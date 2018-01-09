package partha.qbchatdemo.chat.utils;

import android.graphics.Bitmap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class ImageDownloaderQueue /*implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener*/ {

    private static ImageDownloaderQueue imageDownloaderQueue = null;
//    private GoogleApiClient googleApiClient = null;
    private ArrayList<String> arrayList = new ArrayList<>();
    private OnImageDownloadSuccessListener onImageDownloadSuccessListener = null;

    private ImageDownloaderQueue() {
    }

    // getInstanjce().setArrayList()
    public static ImageDownloaderQueue getInstance() {
        if (imageDownloaderQueue == null) {
            imageDownloaderQueue = new ImageDownloaderQueue();
        }
        return imageDownloaderQueue;
    }

    // Call this
    private void saveBitmap(String driveId, Bitmap bitmap) {
        boolean isExpCalled = false;
        File file = new File(TrackApplication.getAppContext().getFilesDir(), driveId);
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            bitmap = scaleBitmap(bitmap);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);

        } catch (Exception e) {
            isExpCalled = true;
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (!isExpCalled) {
                if (onImageDownloadSuccessListener != null) {
                    onImageDownloadSuccessListener.onImageDownloadSuccess(arrayList.get(0));
                }
                arrayList.remove(0);
                if (arrayList.size() > 0) {
                    downloadFile();
                }
            }
        }
    }

    private Bitmap scaleBitmap(Bitmap mBitmap) {
        int ScaleSize = 480;
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();
        float excessSizeRatio = width > height ? width / ScaleSize : height / ScaleSize;
        return Bitmap.createBitmap(mBitmap, 0, 0, (int) (width / excessSizeRatio), (int) (height / excessSizeRatio));
    }

    public ImageDownloaderQueue setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
        return imageDownloaderQueue;
    }

    public void startDownloading() {
        if (arrayList.size() > 0) {
            File file = new File(TrackApplication.getAppContext().getFilesDir(), arrayList.get(0));
            if (!file.exists()) {
                downloadFile();
            } else {
                arrayList.remove(0);
                if (arrayList.size() > 0) {
                    startDownloading();
                }
            }
        }
    }

    @SuppressWarnings("CloneDoesntCallSuperClone")
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Don't fuck with me, B'coz I can do everything.");
    }

    // TODO: 10-11-2017 Download cod eof QB
    private void downloadFile() {
        /*DriveFile.DownloadProgressListener listener = new DriveFile.DownloadProgressListener() {
            @Override
            public void onProgress(long bytesDownloaded, long bytesExpected) {
                //noinspection unused
                int progress = (int) (bytesDownloaded * 100 / bytesExpected);
            }
        };
        DriveFile driveFile = DriveId.decodeFromString(arrayList.get(0)).asDriveFile();
        driveFile.open(googleApiClient, DriveFile.MODE_READ_ONLY, listener).setResultCallback(driveContentsCallback);*/
    }

    public interface OnImageDownloadSuccessListener {
        void onImageDownloadSuccess(String driveId);
    }

    public ImageDownloaderQueue setOnImageDownloadSuccessListener(OnImageDownloadSuccessListener onImageDownloadSuccessListener) {
        this.onImageDownloadSuccessListener = onImageDownloadSuccessListener;
        return imageDownloaderQueue;
    }
}
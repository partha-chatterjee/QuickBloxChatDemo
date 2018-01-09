package partha.qbchatdemo.chat.utils;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.io.File;

import partha.qbchatdemo.R;

/**
 * Created by Partha Chatterjee on 10-11-2017.
 */

public class Utils {
    Context context;
    private int MEDIA_TYPE_IMAGE = 10;

    public Utils(Context context){
        this.context = context;
    }

    public void setSquareImage(ImageView image, String image_name) {
        if (image_name != null && image_name.length() > 0) {
            String url = /*Constants.image_url+*/image_name;
            Picasso.with(context)
//                    .setLoggingEnabled(true)
                    .load(url)
                    .centerCrop()
                    .fit()
                    .placeholder(R.drawable.no_image_placeholder_gray)
                    .into(image);
        } else {
            Picasso.with(context)
                    .load(R.drawable.no_image_placeholder_gray)
                    .fit()
                    .centerInside()
                    .into(image);
        }
    }

    public void setSquareImage(ImageView image, File path) {
//        if (image_name != null && image_name.length() > 0) {
            Picasso.with(context)
                    .load(path)
                    .centerCrop()
                    .fit()
//                    .placeholder(R.drawable.no_image)
                    .into(image);
        /*} else {
            Picasso.with(context)
                    .load(R.drawable.no_image_placeholder_gray)
                    .fit()
                    .centerInside()
                    .into(image);
        }*/
    }

    public String getRealPathFromURI(Uri uri) {

        String filePath = "";
        if (uri.getScheme().compareTo("content") == 0) {

            L.d("content");

            String[] projection = {"_data"};  // "_data" is the string to look for message. It's equivalent to MediaStore.Images.Media.DATA
            CursorLoader loader = new CursorLoader(context, uri, projection, null, null, null);
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

    public String getFileName() {
        return String.valueOf(System.currentTimeMillis());
    }

    /**
     * Create a file Uri for saving an image or video
     */
    public Uri getOutputMediaFileUri(int type, String name) {
        return Uri.fromFile(getOutputMediaFile(type, name));
    }

    /**
     * Create a File for saving an image or video
     */
    public File getOutputMediaFile(int type, String fName) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        /*File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "Stock_Up");*/
        File mediaStorageDir = new File(Environment.getExternalStorageDirectory(), context.getResources().getString(R.string.app_name));
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
        } /*else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        }*/ else {
            return null;
        }

        return mediaFile;
    }

    /**
     * Check & Returns the correct oriented Bitmap Image
     */
    /*private Bitmap getBitmapExifInterface(Uri selectedImageUri, Bitmap bmp) {
        /////for rotation of image
        ExifInterface exif;
        int rotation;
        try {
            mPath = ImageFilePath.getPath(context, selectedImageUri);
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
    }*/

    /**
     * For checking bitmap image orientation and returns the rotated bitmap.
     */
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
}

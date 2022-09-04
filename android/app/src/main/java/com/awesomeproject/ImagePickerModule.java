package com.awesomeproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.webkit.MimeTypeMap;

import androidx.core.content.FileProvider;

import com.facebook.react.bridge.ActivityEventListener;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.ReadableMap;
import com.facebook.react.bridge.WritableArray;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ImagePickerModule extends ReactContextBaseJavaModule implements ActivityEventListener {
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    public static final int RESULT_LOAD_IMAGE = 3;
    final ReactApplicationContext reactContext;
    Callback callback;

    public ImagePickerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
        this.reactContext.addActivityEventListener(this);
    }

    @Override
    public String getName() {
        return "ImagePicker";
    }

    @ReactMethod
    public void launchCamera(final Callback callback) {

        this.callback = callback;

        if (reactContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            Intent takeImageIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            this.reactContext.startActivityForResult(takeImageIntent, MEDIA_TYPE_IMAGE, null);
        }
    }

    @ReactMethod
    public  void launchLibrary(Callback callback) {
        this.callback = callback;

        Intent i = new Intent(
                Intent.ACTION_PICK, MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY));


        this.reactContext.startActivityForResult(i, RESULT_LOAD_IMAGE, null);
    }

    @Override
    public void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data) {
        if (resultCode != activity.RESULT_CANCELED && data != null ) {

            switch (requestCode) {
                case MEDIA_TYPE_IMAGE:
                    Bitmap bmp = (Bitmap)data.getExtras().get("data");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bmp.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    byte[] byteArray = stream.toByteArray();

                    Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
                    String encImage = Base64.encodeToString(byteArray, Base64.DEFAULT);

                    getOutputMediaFileUri(1 , bitmap);
                    callback.invoke(encImage);

                    break;

                case RESULT_LOAD_IMAGE:
                    Uri image = data.getData();
                    InputStream imageStream = null;

                    try {
                        imageStream = reactContext.getContentResolver().openInputStream(image);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }

                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    String encodedImage = encodeImage(selectedImage);
                    callback.invoke(encodedImage);
                    break;
            }

        }
    }

    private String encodeImage(Bitmap bm)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG,100,baos);
        byte[] b = baos.toByteArray();
        String encImage = Base64.encodeToString(b, Base64.DEFAULT);
        return encImage;
    }

    private static Uri getOutputMediaFileUri(int type ,Bitmap bitmap){
        return Uri.fromFile(getOutputMediaFile(type ,bitmap ));
    }

    private static File getOutputMediaFile(int type ,Bitmap bitmap){
        File mediaStorageDir = new File(Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES), "MyCameraApp");
        if (! mediaStorageDir.exists()){
            if (! mediaStorageDir.mkdirs()){
                Log.d("MyCameraApp", "failed to create directory");
                return null;
            }
        }

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE){
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "IMG_"+ timeStamp + ".jpg");
        } else if(type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(mediaStorageDir.getPath() + File.separator +
                    "VID_"+ timeStamp + ".mp4");
        } else {
            return null;
        }

        try {
            FileOutputStream out = new FileOutputStream(mediaFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG,100,out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mediaFile;
    }

    @Override
    public void onNewIntent(Intent intent) {}
}
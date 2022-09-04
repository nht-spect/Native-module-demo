package com.awesomeproject;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.provider.MediaStore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.text.DecimalFormat;



public class DemoModule extends ReactContextBaseJavaModule {
    static final int REQUEST_IMAGE_CAPTURE = 1;


    public DemoModule(@Nullable ReactApplicationContext reactContext) {
        super(reactContext);
    }

    @NonNull
    @Override
    public String getName() {
        return "DemoHello";
    }


    @ReactMethod
    public  void sayHello(String name, Callback callback) {

        try{
            String message ="hello" + name;
            callback.invoke(null, message);

        }catch (Exception e){
            callback.invoke(e, null);
        }
    }

    @ReactMethod
    public  void formatCurrency(Integer number, Callback callback) {
        try {
            DecimalFormat df = new DecimalFormat("###,###");
            String currency = df.format(number);
            callback.invoke(null, currency);

        }catch (Exception e) {
            callback.invoke(e, null);
        }
    }
}


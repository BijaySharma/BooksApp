package com.bijay.books;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.IOException;
import java.net.URL;

public class InternetService extends IntentService {
    private static final String TAG = ApiUtils.class.getSimpleName();

    public InternetService() {
        super("InternetServiceThread");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.i("BOOK", "Intent service started");
        String result = "";
        ResultReceiver receiver = intent.getParcelableExtra("reciver");
        String searchKeyword = intent.getStringExtra("searchKeyword");
        URL requestUrl = ApiUtils.buildUrl(searchKeyword);
        try {
            result = ApiUtils.getJson(requestUrl);
        } catch (IOException e) {
            Log.e(TAG, "Problems retriving data or reciving the requested url",e);
        }

        Bundle resultBundle = new Bundle();
        resultBundle.putString("result",result);
        receiver.send(101,resultBundle);
    }
}

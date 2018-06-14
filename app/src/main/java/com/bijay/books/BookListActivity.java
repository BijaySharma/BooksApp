package com.bijay.books;

import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BookListActivity extends AppCompatActivity {
    TextView tvResult;
    ProgressBar loadingIndicator;
    Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_list);

        loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);

        tvResult = findViewById(R.id.tvReciver);

        String searchKeyword ="half girlfriend";

        Intent internetCall = new Intent(this,InternetService.class);
        InternetCallResultHandler reciver = new InternetCallResultHandler(null);
        internetCall.putExtra("searchKeyword",searchKeyword);
        internetCall.putExtra("reciver", reciver);

        startService(internetCall);



    }

    private class InternetCallResultHandler extends ResultReceiver{

         InternetCallResultHandler(Handler handler) {
            super(null);
        }

        @Override
        protected void onReceiveResult(int resultCode, Bundle resultData) {
            super.onReceiveResult(resultCode, resultData);
            Log.i("MyResult Reciver", "Thread Name : " + Thread.currentThread().getName());
            if(resultCode == 101 && resultData != null){
                final String result = resultData.getString("result");
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        tvResult.setText(result);
                        loadingIndicator.setVisibility(View.GONE);
                    }
                });

            }

        }
    }
}

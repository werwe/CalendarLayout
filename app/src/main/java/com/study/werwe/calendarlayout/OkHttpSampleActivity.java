package com.study.werwe.calendarlayout;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;

import java.io.IOException;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


public class OkHttpSampleActivity extends ActionBarActivity {

    final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    public static final String TAG = OkHttpSampleActivity.class.getSimpleName();
    @InjectView(R.id.hello)
    Button mHello;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ok_http_sample);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.hello)
    public void hello() {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    OkHttpClient client = new OkHttpClient();
                    return client.newCall(sampleRequest()).execute().body().string();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String response) {
                Log.d(TAG,response);
                AlertDialog dialog = new AlertDialog.Builder(OkHttpSampleActivity.this)
                        .setMessage(response)
                        .setPositiveButton("ok", null)
                        .create();
                dialog.show();
            }
        }.execute();
    }
    
    public Request sampleRequest() throws IOException {
        Request request = new Request.Builder()
                .url("  https://api.parse.com/1/functions/hello")
                .header("X-Parse-Application-Id", "Jod6VsCN4t7aP20iJpiZ0XzLxUFXuFDuOEW3e3gs")
                .addHeader("X-Parse-REST-API-Key", "CmVRtWdbY7othD3kXazgttPAjaz0Mo000sng70Wc")
                .addHeader("Content-Type", "application/json")
                .post(RequestBody.create(JSON, "{}"))
                .build();
        return request;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ok_http_sample, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

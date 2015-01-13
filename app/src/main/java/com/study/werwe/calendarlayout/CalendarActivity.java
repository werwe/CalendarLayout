package com.study.werwe.calendarlayout;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.googleapis.services.AbstractGoogleClientRequest;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.study.werwe.cal.backend.hiApi.HiApi;
import com.study.werwe.cal.backend.hiApi.model.Hi;

import java.io.IOException;


public class CalendarActivity extends ActionBarActivity {
    enum CalendarType {Default, JodaTime}

    LinearLayout mRoot;
    Button[][] mCells;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Button hi = (Button) findViewById(R.id.hi);
        hi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HiApi.Builder builder = new HiApi.Builder(
                    AndroidHttp.newCompatibleTransport(),
                    new AndroidJsonFactory(),
                    null);

                builder.setRootUrl("https://calendar-sample-0001.appspot.com/_ah/api");
                builder.setGoogleClientRequestInitializer( new GoogleClientRequestInitializer() {
                    @Override
                    public void initialize(AbstractGoogleClientRequest<?> abstractGoogleClientRequest) throws IOException {
                        abstractGoogleClientRequest.setDisableGZipContent(true);
                    }
                });
                final HiApi api = builder.build();

                new AsyncTask<Void, Void, Void>() {
                    @Override
                    protected Void doInBackground(Void... params) {
                        try {
                            Hi hi = api.hi("성철").execute().clone();

                            for (String key : hi.keySet()) {
                                String name = (String) hi.get(key);
                                System.out.println("hi " + name);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                }.execute();
            }
        });

        mRoot = (LinearLayout) findViewById(R.id.root_layout);
        int rowCount = mRoot.getChildCount();

        mCells = new Button[rowCount][];
        for (int row = 0; row < rowCount; row++) {
            LinearLayout linear = (LinearLayout) mRoot.getChildAt(row);
            int columnCount = linear.getChildCount();
            mCells[row] = new Button[columnCount];
            for (int col = 0; col < columnCount; col++) {
                mCells[row][col] = (Button) linear.getChildAt(col);
                mCells[row][col].setText(row + "," + col);
                Log.d("TAG", row + "," + col);
            }
        }

        initCalendar();

        //visitAllChild(mRoot);
        //int buutonCount = visitAllChild(mRoot, 0);
        //Log.d("tag", "button count:" + buutonCount);
    }

    private void initCalendar() {
        CalendarStrategy cal = createCalendar(CalendarType.Default);
        cal.moveToFirstDayOfFirstWeek();
        int row = mCells.length;
        int col = mCells[0].length;

        for (int r = 0; r < row; r++) {
            for (int c = 0; c < col; c++) {
                mCells[r][c].setText(cal.getDay() + "");
                cal.add(1);
            }
        }
    }

    private CalendarStrategy createCalendar(CalendarType aDefault) {
        switch (aDefault) {
            case Default:
                return DefaultCalendar.getInstance();
            case JodaTime:
                return JodaCalendar.getInstance();
        }
        return DefaultCalendar.getInstance();
    }

    //recursion method
    private void visitAllChild(ViewGroup root) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof ViewGroup)
                visitAllChild((ViewGroup) view);
            else if (view instanceof Button)
                view.setBackgroundColor(Color.argb(255, 255, 255, 0));
        }
    }

    //recursion method v2 -- counting feature
    private int visitAllChild(ViewGroup root, int count) {
        for (int i = 0; i < root.getChildCount(); i++) {
            View view = root.getChildAt(i);
            if (view instanceof ViewGroup)
                count = visitAllChild((ViewGroup) view, count);
            else if (view instanceof Button) {
                view.setBackgroundColor(Color.argb(255, 255, 255, 0));
                ((Button) view).setText(count + "");
                count++;
            }
        }
        return count;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
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

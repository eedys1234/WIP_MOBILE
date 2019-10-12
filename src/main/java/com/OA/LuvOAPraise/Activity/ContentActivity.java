package com.OA.LuvOAPraise.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.TextView;

import com.OA.LuvOAPraise.R;

/**
 * Created by lee on 2019-04-25.
 */
public class ContentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_content);

        //SQLITE 사용
//        DBHelper dbHelper = new DBHelper(getApplicationContext(), "tblWFPRAISE.db", null, 1);

        Intent intent = getIntent();
        String strTitle = intent.getExtras().getString("title");
        getSupportActionBar().setTitle(strTitle);

        String strContent = intent.getExtras().getString("lyics");

        TextView txt_Content = (TextView)findViewById(R.id.txt_Content);
        txt_Content.setText(strContent);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_settings).getActionView();
        return true;
    }

}

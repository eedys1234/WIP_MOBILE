package com.OA.LuvOAPraise.Activity;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
//import android.widget.SearchView;
import android.support.v7.widget.SearchView;
import com.OA.LuvOAPraise.R;
import java.util.ArrayList;

import com.OA.LuvOAPraise.Bean.SongItem;
import com.OA.LuvOAPraise.Utils.DBHelper;
//import Utils.FloatingActionButton;
import com.OA.LuvOAPraise.Utils.RequestHttpURLConnection;
import com.OA.LuvOAPraise.Utils.SoundSearcher;
import com.OA.LuvOAPraise.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static DBHelper dbHelper;

    ArrayList<String> items;
    ArrayList<SongItem> lstSong;
    ListView listview;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("서로사랑하는 공동체");

        items = new ArrayList<String>();
        lstSong = new ArrayList<>();

        try{
            LoadSongTask SongTask = new LoadSongTask(getString(R.string.strURL)+"/SongList.jsp", null);
            String result = SongTask.execute().get();

            JSONObject resData = new JSONObject(Utils.GetDecodeString(result));
            JSONArray arySong = resData.getJSONArray("SongList");

            for(int i=0;i<arySong .length();i++)
            {
                JSONObject Song = (JSONObject)arySong .get(i);
                SongItem item = new SongItem();
                item.setTitle((String)Song.get("Title"));
                item.setLyics((String)Song.get("Lyics"));
                item.setPages((String)Song.get("Pages"));
                item.setSongKey((String)Song.get("SongKey"));
                lstSong.add(item);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        for (int i = 0; i < lstSong.size(); i++) {
            items.add(lstSong.get(i).getTitle());
        }

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, items);

        listview = (ListView) findViewById(R.id.lstView);
        listview.setAdapter(adapter);
        listview.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);

                Intent intent = new Intent(getApplicationContext(), ContentMainActivity.class);
//                Intent intent = new Intent(getApplicationContext(), ContentActivity.class);
                intent.putExtra("title", strText);
                for(int i=0;i<lstSong.size();i++)
                {
                    if(lstSong.get(i).getTitle().equals(strText))
                    {
                        intent.putExtra("pages", lstSong.get(i).getPages());
                        intent.putExtra("lyics", lstSong.get(i).getLyics());
                        intent.putExtra("songkey", lstSong.get(i).getSongKey());
                    }
                }
                startActivity(intent);
            }
        });

        listview.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                String strText = (String) parent.getItemAtPosition(position);
                return true;
            }
        });

        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);

        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setMaxWidth(Integer.MAX_VALUE);

        searchView.setQueryHint("제목을 검색하세요.");

        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                items.clear();

                if (newText.equals("")) {
                    for (int i = 0; i < lstSong.size(); i++) {
                        items.add(lstSong.get(i).getTitle());
                    }
                } else {
                    for (int i = 0; i < lstSong.size(); i++) {
                        if (SoundSearcher.matchString((lstSong.get(i).getTitle()).replace(" ", ""), newText.replace(" ","")) || SoundSearcher.matchString((lstSong.get(i).getLyics()).replace(" ", ""), newText.replace(" ",""))) {
                            items.add(lstSong.get(i).getTitle());
                        }
                    }
                }

                adapter.notifyDataSetChanged();

                return false;
            }
        };

        searchView.setOnQueryTextListener(queryTextListener);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }

        searchView.setIconifiedByDefault(true);
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

    public class LoadSongTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public LoadSongTask(String url, ContentValues values) {

            this.url = url;
            this.values = values;
        }

        @Override
        protected String doInBackground(Void... params) {

            String result; // 요청 결과를 저장할 변수.
            RequestHttpURLConnection requestHttpURLConnection = new RequestHttpURLConnection();
            result = requestHttpURLConnection.request(url, values); // 해당 URL로 부터 결과물을 얻어온다.

            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //doInBackground()로 부터 리턴된 값이 onPostExecute()의 매개변수로 넘어오므로 s를 출력한다.
        }
    }
}

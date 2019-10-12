package com.OA.LuvOAPraise.Fragment;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.OA.LuvOAPraise.Activity.MainActivity;
import com.OA.LuvOAPraise.Adapter.SheetMusicAdapter;
import com.OA.LuvOAPraise.Bean.SheetMusicItem;
import com.OA.LuvOAPraise.Bean.SongItem;
import com.OA.LuvOAPraise.R;
import com.OA.LuvOAPraise.Utils.RequestHttpURLConnection;
import com.OA.LuvOAPraise.Utils.Utils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SheetMusicFragment extends Fragment {

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    String strSongKey;
    String strPages;
    ArrayList<SheetMusicItem> SheetMusicList= new ArrayList<>();

    public static SheetMusicFragment newInstance(String strSongKey, String strPages) {
        SheetMusicFragment fragment = new SheetMusicFragment();
        Bundle args = new Bundle();
        args.putString("strSongKey", strSongKey);
        args.putString("strPages", strPages);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strSongKey = getArguments().getString("strSongKey");
        strPages = getArguments().getString("strPages");

        try
        {
            LoadSheetMusicTask SongTask = new LoadSheetMusicTask(getString(R.string.strURL)+"/Song_SheetMusicList.jsp?SongKey="+strSongKey, null);
            String result = SongTask.execute().get();

            JSONObject resData = new JSONObject(Utils.GetDecodeString(result));

            String strResultCode = resData.getString("resultCode");

            if(strResultCode.equals("200"))
            {
                JSONArray SheetMusicJSONAry = resData.getJSONArray("resultData");

                for(int i=0;i<SheetMusicJSONAry.length();i++)
                {
                    JSONObject jsonObject = (JSONObject)SheetMusicJSONAry.get(i);

                    SheetMusicItem sheetMusicItem = new SheetMusicItem();
                    sheetMusicItem.setSongKey(jsonObject.getString("SongKey"));
                    sheetMusicItem.setSheetMusicKey(jsonObject.getString("SheetMusicKey"));
                    SheetMusicList.add(sheetMusicItem);
                }
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_song_sheetmusic, container, false);
        recyclerView = view.findViewById(R.id.recycler);

        String strURL = getString(R.string.strURL);
        int intPages = 0;
        intPages = Integer.parseInt(strPages);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
//        ArrayList<SongItem> SongList = new ArrayList<SongItem>();
//
//        for(int i=0; i<intPages; i++)
//        {
//            SongItem songItem = new SongItem();
//            songItem.setSongKey(strSongKey);
//            songItem.setPages(i+"");
//
//            SongList.add(songItem);
//        }

        SheetMusicAdapter sheetMusicAdapter = new SheetMusicAdapter(getActivity(), SheetMusicList, strURL);

        recyclerView.setAdapter(sheetMusicAdapter);

        return view;
    }

    public class LoadSheetMusicTask extends AsyncTask<Void, Void, String> {

        private String url;
        private ContentValues values;

        public LoadSheetMusicTask(String url, ContentValues values) {

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

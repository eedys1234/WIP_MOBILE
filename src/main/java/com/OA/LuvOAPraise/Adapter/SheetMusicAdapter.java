package com.OA.LuvOAPraise.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;

import com.OA.LuvOAPraise.Bean.SheetMusicItem;
import com.OA.LuvOAPraise.Bean.SongItem;
import com.OA.LuvOAPraise.R;
import com.OA.LuvOAPraise.Utils.PicassoTransformations;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SheetMusicAdapter extends RecyclerView.Adapter<SheetMusicAdapter.ViewHolder> {

    Context context;
    ArrayList<SheetMusicItem> SheetMusicList;
    public String strURL;
    public String strSongKey;
    public String strSheetMusicKey;
    int intPosition;
    Bitmap bitmap = null;

    public SheetMusicAdapter(Context context, ArrayList<SheetMusicItem> SheetMusicList, String strURL)
    {
        this.context = context;
        this.SheetMusicList = SheetMusicList;
        this.strURL = strURL;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sheetmusic_item, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        SheetMusicItem item = SheetMusicList.get(position);
        strSongKey = item.getSongKey();
        strSheetMusicKey = item.getSheetMusicKey();
        intPosition = position;
        Picasso.with(context)
        .load(strURL + "/Images/" + strSongKey + "/" + strSheetMusicKey + ".png")
//        .load(strURL + "/Images/" + strSongKey + "/" + strSongKey + "_" + intPosition + ".png")
        .transform(PicassoTransformations.resizeTransformation)
        .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return SheetMusicList.size();
    }

    public Bitmap GetSheetMusic() {

        Thread mThread = new Thread() {

            public void run() {

                try {


                    Log.i("strSongKey", strURL + "/Images/" + strSongKey + "/" + strSongKey + "_" + intPosition + ".png");
                    URL url = new URL(strURL + "/Images/" + strSongKey + "/" + strSongKey + "_" + intPosition + ".png");
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                    conn.setDoInput(true);
                    conn.connect();

                    InputStream is = conn.getInputStream();
                    bitmap = BitmapFactory.decodeStream(is);

                    Log.i("SheetMusic", "success");

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };

        mThread.start();

        try
        {
            mThread.join();

        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
        return bitmap;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);

            this.imageView = view.findViewById(R.id.imageView);
        }
    }
}

package com.OA.LuvOAPraise.Utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.OA.LuvOAPraise.Bean.SongItem;
import com.OA.LuvOAPraise.R;

import java.util.ArrayList;

public class SongAdapter extends BaseAdapter {

    private ArrayList<SongItem> lstSong = new ArrayList<SongItem>() ;

    @Override
    public int getCount() {
        return lstSong.size();
    }

    @Override
    public Object getItem(int position) {
        return lstSong.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.song_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.txt_lst_Title) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        SongItem item = lstSong.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(item.getTitle());

        return convertView;
    }

    public void addItem(String title) {
        SongItem item = new SongItem();

        item.setTitle(title);

        lstSong.add(item);
    }

    public void clearItem() {

        lstSong.clear();
    }
}

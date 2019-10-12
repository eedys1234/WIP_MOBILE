package com.OA.LuvOAPraise.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.OA.LuvOAPraise.R;

public class LyicsFragment extends Fragment {
    String strLyics;

    public static LyicsFragment newInstance(String strLyics) {
        LyicsFragment fragment = new LyicsFragment();
        Bundle args = new Bundle();
        args.putString("strLyics", strLyics);
        fragment.setArguments(args);
        return fragment;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        strLyics = getArguments().getString("strLyics");

    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_lycis, container, false);
        TextView textView= (TextView) view.findViewById(R.id.txt_Content);
        textView.setText(strLyics);
        return view;
    }
}

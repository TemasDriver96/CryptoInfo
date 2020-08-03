package com.example.user.cryptoinfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 05/04/2018.
 */

public class TopNews extends BaseAdapter {

    private Activity act;
    private ArrayList<HashMap<String, String>> tpndata;

    public TopNews(Activity a, ArrayList<HashMap<String, String>> topnewsd) {
        act = a;
        tpndata = topnewsd;
    }

    public int getCount() {
        return tpndata.size();
    }

    public Object getItem(int tpposition) {
        return tpposition;
    }

    public long getItemId(int tpposition) {
        return tpposition;
    }

    public View getView(int tpposition, View tpchangeView, ViewGroup pat) {
        topnewsListHold tphold = null;
        if (tpchangeView == null) {
            tphold = new topnewsListHold();

            tpchangeView = LayoutInflater.from(act).inflate(R.layout.tplistrow, pat, false);
            tphold.title = (TextView) tpchangeView.findViewById(R.id.topnewsTitle);
            tphold.description = (TextView) tpchangeView.findViewById(R.id.topnewsDetails);
            tphold.publisheddate = (TextView) tpchangeView.findViewById(R.id.topnewsTime);
            tphold.galImg = (ImageView) tpchangeView.findViewById(R.id.topnewsImg);
            tpchangeView.setTag(tphold);

        } else {
            tphold = (topnewsListHold) tpchangeView.getTag();
        }

        tphold.title.setId(tpposition);
        tphold.description.setId(tpposition);
        tphold.publisheddate.setId(tpposition);
        tphold.galImg.setId(tpposition);

        HashMap<String, String>  s = new HashMap<String, String>();
        s = tpndata.get(tpposition);

        try{
            tphold.title.setText(s.get(topnewsActivity.TITLE));
            tphold.description.setText(s.get(topnewsActivity.DESCRIPTION));
            tphold.publisheddate.setText(s.get(topnewsActivity.PUBLISHEDDATE));

            if(s.get(topnewsActivity.URLTOIMAGE).toString().length() < 5) {
                tphold.galImg.setVisibility(View.GONE);
            } else {
                Picasso.with(act).load(s.get(topnewsActivity.URLTOIMAGE).toString()).resize(300, 200).into(tphold.galImg);
            }

        } catch (Exception e) {}
        return tpchangeView;
    }

    class topnewsListHold {
        TextView title;
        TextView description;
        TextView publisheddate;
        ImageView galImg;
    }
}
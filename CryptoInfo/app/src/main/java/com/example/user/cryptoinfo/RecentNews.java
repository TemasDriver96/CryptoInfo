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
 * Created by User on 06/04/2018.
 */

public class RecentNews extends BaseAdapter {

    private Activity newact;
    private ArrayList<HashMap<String, String>> rcndata;

    public RecentNews(Activity newa, ArrayList<HashMap<String, String>> recentnewsdata) {
        newact = newa;
        rcndata = recentnewsdata;
    }

    public int getCount() {
        return rcndata.size();
    }

    public Object getItem(int rcposition) {
        return rcposition;
    }

    public long getItemId(int rcposition) {
        return rcposition;
    }

    public View getView(int rcposition, View rcchangeView, ViewGroup newpat) {
        recentnewsListHold rchold = null;
        if(rcchangeView == null) {
            rchold = new recentnewsListHold();

            rcchangeView = LayoutInflater.from(newact).inflate(R.layout.rclistrow, newpat, false);
            rchold.newgalImg = (ImageView) rcchangeView.findViewById(R.id.recentnewsImg);
            rchold.newtitle = (TextView) rcchangeView.findViewById(R.id.recentnewsTitle);
            rchold.newauthor = (TextView) rcchangeView.findViewById(R.id.recentnewsAuthor);
            rchold.newdescription = (TextView) rcchangeView.findViewById(R.id.recentnewsDetails);
            rchold.newpublisheddate = (TextView) rcchangeView.findViewById(R.id.recentnewsTime);
            rcchangeView.setTag(rchold);
        } else {
            rchold = (recentnewsListHold) rcchangeView.getTag();
        }

        rchold.newgalImg.setId(rcposition);
        rchold.newtitle.setId(rcposition);
        rchold.newauthor.setId(rcposition);
        rchold.newdescription.setId(rcposition);
        rchold.newpublisheddate.setId(rcposition);

        HashMap<String, String>  t = new HashMap<String, String>();
        t = rcndata.get(rcposition);

        try{
            rchold.newtitle.setText(t.get(recentnewsActivity.TITLE));
            rchold.newauthor.setText(t.get(recentnewsActivity.AUTHOR));
            rchold.newdescription.setText(t.get(recentnewsActivity.DESCRIPTION));
            rchold.newpublisheddate.setText(t.get(recentnewsActivity.PUBLISHEDDATE));

            if(t.get(recentnewsActivity.URLTOIMAGE).toString().length() < 5) {
                rchold.newgalImg.setVisibility(View.GONE);
            } else {
                Picasso.with(newact).load(t.get(recentnewsActivity.URLTOIMAGE).toString()).resize(300, 200).into(rchold.newgalImg);
            }

        } catch (Exception e) {}
        return rcchangeView;
    }

    class recentnewsListHold {
        ImageView newgalImg;
        TextView newtitle;
        TextView newauthor;
        TextView newdescription;
        TextView newpublisheddate;
    }
}

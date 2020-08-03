package com.example.user.cryptoinfo;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by User on 13/04/2018.
 */

public class ExchangeRate extends BaseAdapter {

    Activity exAct;
    private ArrayList<HashMap<String, String>> exdata;

    public ExchangeRate(Activity newexA, ArrayList<HashMap<String, String>> exchangeratedata) {
        exAct = newexA;
        exdata = exchangeratedata;

    }

    //tells the list view how many items to display
    public int getCount() {
        return exdata.size();
    }

    //returns the item that has to be placed at the given position from url
    public Object getItem(int exposition) {
        return exposition;
    }

    //uses the position as its unique row id
    public long getItemId(int exposition) {
        return exposition;
    }

    //here you can place the information you want in a listview
    //it calls its own custom view from XML "exlistrow"
    public View getView (final int exposition, View exchangeView, ViewGroup expat){
        exchangerateListHold exhold = null;
        if(exchangeView == null) {
            exhold = new ExchangeRate.exchangerateListHold();

            exchangeView = LayoutInflater.from(exAct).inflate(R.layout.exlistrow, expat, false);
            exhold.exchangerateCC = (TextView) exchangeView.findViewById(R.id.exchangerateCC);
            exhold.exchangerateRate = (TextView) exchangeView.findViewById(R.id.exchangerateRate);
            exchangeView.setTag(exhold);
        } else {
            exhold = (ExchangeRate.exchangerateListHold) exchangeView.getTag();
        }

        exhold.exchangerateCC.setId(exposition);
        exhold.exchangerateRate.setId(exposition);

        HashMap<String, String>  ex = new HashMap<String, String>();
        ex = exdata.get(exposition);

        try{
            exhold.exchangerateCC.setText(ex.get(exchangerateActivity.COIN));
            exhold.exchangerateRate.setText(ex.get(exchangerateActivity.RATE));

        } catch (Exception e) {}

        return exchangeView;
    }

    class exchangerateListHold {
        TextView exchangerateCC;
        TextView exchangerateRate;
    }

}

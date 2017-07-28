package com.example.sukesh.webmobitask;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Sukesh on 7/27/2017.
 */
public class LAdapter extends BaseAdapter{
    Activity activity;
    ArrayList<GetSetter> arrraylist;
    static  int k=0;
    private static LayoutInflater inflater=null;
    public LAdapter(Activity activity, ArrayList<GetSetter> arrayList) {
        this.activity = activity;
        this.arrraylist = arrayList;
        inflater = ( LayoutInflater )activity.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return arrraylist.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView=convertView;
        ViewHolder v;
        final GetSetter getSetter=arrraylist.get(position);
        if (rowView==null) {
            rowView = inflater.inflate(R.layout.list_item, null);
            v=new ViewHolder();
            v.word = (TextView) rowView.findViewById(R.id.word);
            v.count = (TextView) rowView.findViewById(R.id.count);
            v.header = (TextView) rowView.findViewById(R.id.header);
            v.rl = (RelativeLayout)rowView.findViewById(R.id.rl);

            rowView.setTag(v);
        }else {
            v = (ViewHolder)rowView.getTag();
        }
        if (getSetter.getCount() == 0){
            v.word.setTextSize(20);
            v.word.setText(getSetter.getWord());
            v.count.setText("");
            v.word.setTextColor(Color.parseColor("#ffffff"));
            v.rl.setBackgroundResource(R.color.colorPrimary);

            v.rl.setGravity(Gravity.CENTER );
            //v.word.setBackgroundResource(R.color.colorAccent);
        }else {
            v.word.setTextSize(15);
            v.count.setTextSize(15);
            v.rl.setGravity(Gravity.LEFT );
            v.word.setTextColor(R.color.black);
            v.word.setText(getSetter.getWord() + ": ");
            v.count.setText(getSetter.getCount() + "");
            v.rl.setBackgroundResource(R.color.white);
        }

        return rowView;
    }

    private static class ViewHolder{
        TextView word,count,header;
        RelativeLayout rl;
    }
}

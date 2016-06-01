package com.example.zhuhongwei.joke;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;



/**
 * Created by sunzhengchun on 16/6/1.
 */
public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private JSONArray jsonArray;

    public ListViewAdapter(Context context, JSONArray jsonArray) {
        this.context = context;
        this.jsonArray = jsonArray;
    }

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
       // return jsonArray.size();
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return jsonArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = View.inflate(context,R.layout.listview_item,null);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView)convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.textView.setText("Hello World!");
        return convertView;
    }

    class ViewHolder{
        private TextView textView;
    }
}

package com.example.zhuhongwei.joke;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

/**
 * Created by sunzhengchun on 16/6/14.
 */
public class TwoFragmentOneListViewAdapter extends BaseAdapter {

    private JSONArray categoryArray;
    private Context context;
    private FinalBitmap finalBitmap;
    private Handler handler;

    public TwoFragmentOneListViewAdapter(JSONArray categoryArray, Context context) {
        this.categoryArray = categoryArray;
        this.context = context;
        finalBitmap = FinalBitmap.create(context);
    }

    public TwoFragmentOneListViewAdapter(Handler handler, Context context, JSONArray categoryArray) {
        this.handler = handler;
        this.context = context;
        finalBitmap = FinalBitmap.create(context);
        this.categoryArray = categoryArray;
    }

    @Override
    public int getCount() {
        return categoryArray.size();
    }

    @Override
    public Object getItem(int position) {
        return categoryArray.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            convertView = View.inflate(context,R.layout.twofragment_list_item,null);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView)convertView.findViewById(R.id.iv_twofragment_listImageview);
            viewHolder.title = (TextView)convertView.findViewById(R.id.tv_twofragment_listTitle);
            viewHolder.subTitle = (TextView)convertView.findViewById(R.id.tv_twofragment_listSubtitle);
            viewHolder.subscribe_count = (TextView)convertView.findViewById(R.id.tv_twofragment_subscribe_count);
            viewHolder.total_updates = (TextView)convertView.findViewById(R.id.tv_twofragment_total_updates);
            viewHolder.dingyue = (TextView)convertView.findViewById(R.id.tv_twofragment_dingyue);
            viewHolder.dingyue.setTag(position);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder)convertView.getTag();
        }
        JSONObject object = categoryArray.getJSONObject(position);
        finalBitmap.display(viewHolder.imageView, object.get("small_icon_url").toString());
        viewHolder.title.setText(object.get("name").toString());
        viewHolder.subTitle.setText(object.getString("intro"));
        viewHolder.subscribe_count.setText(object.getString("subscribe_count") + " 订阅 |");
        viewHolder.total_updates.setText(object.getString("total_updates"));
        viewHolder.dingyue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getrequest((int)v.getTag());
            }
        });
        return convertView;
    }

    private void getrequest(final int position){
        String url = "http://60.29.248.126/2/essay/zone/subscribe/?iid=4491235424&device_id=17210915723&ac=wifi&channel=tengxun&aid=7&app_name=joke_essay&version_code=522&version_name=5.2.2&device_platform=android&ssmix=a&device_type=NX403A&device_brand=nubia&os_api=17&os_version=4.2.2&uuid=863396020214159&openudid=fa14a38585eabe25&manifest_version_code=522&resolution=720*1280&dpi=320&update_version_code=5220";
        FinalHttp finalHttp = new FinalHttp();
        AjaxParams parms = new AjaxParams("category_id",categoryArray.getJSONObject(position).getString("id"));
        finalHttp.post(url, parms, new AjaxCallBack() {
            @Override
            public void onSuccess(Object response) {
                Message message = handler.obtainMessage();
                message.what = Constent.SUBCRIBE_NET_SUCCESS;
                message.obj = position;
                handler.sendMessage(message);
                Log.i("zhuhongwei","POST请求成功");
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Message message = handler.obtainMessage();
                message.what = Constent.SUBCRIBE_NET_ERROR;
                message.obj = "订阅失败";
                handler.sendMessage(message);
            }
        });
    }

    class ViewHolder{
        private ImageView imageView;
        private TextView title;
        private TextView subTitle;
        private TextView subscribe_count;
        private TextView total_updates;
        private TextView dingyue;

    }
}

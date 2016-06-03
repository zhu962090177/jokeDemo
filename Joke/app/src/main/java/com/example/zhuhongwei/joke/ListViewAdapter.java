package com.example.zhuhongwei.joke;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.tsz.afinal.FinalBitmap;


/**
 * Created by sunzhengchun on 16/6/1.
 */
public class ListViewAdapter extends BaseAdapter{
    private Context context;
    private JSONArray jsonArray;
    private FinalBitmap fb;
    private View.OnClickListener onClickListener;
    public ListViewAdapter(Context context, JSONArray jsonArray,View.OnClickListener onClickListener) {
        this.context = context;
        this.jsonArray = jsonArray;
        fb = FinalBitmap.create(context);
        this.onClickListener = onClickListener;
    }

    public ListViewAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        Log.i("hongwei--->count",""+jsonArray.size());
        return jsonArray.size();
        //return 10;
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
            viewHolder.is_hot_imageView = (ImageView)convertView.findViewById(R.id.iv_is_hot);
            viewHolder.headerImageView = (ImageView)convertView.findViewById(R.id.iv_heade);
            viewHolder.userName = (TextView)convertView.findViewById(R.id.tv_heade_username);
            viewHolder.dislikeTextView = (TextView)convertView.findViewById(R.id.tv_dislike);
            viewHolder.contentTextView = (TextView)convertView.findViewById(R.id.tv_content);
            viewHolder.categoryNameTextView = (TextView)convertView.findViewById(R.id.tv_category_name);
            viewHolder.mediumLayout = (LinearLayout)convertView.findViewById(R.id.layout_medio_content);
            viewHolder.commentLayout = (LinearLayout)convertView.findViewById(R.id.layout_comment);
            viewHolder.commentHeaderImageView = (ImageView)convertView.findViewById(R.id.iv_comment_header);
            viewHolder.commentUserName = (TextView)convertView.findViewById(R.id.tv_comment_username);
            viewHolder.commentUpperImageView = (ImageView)convertView.findViewById(R.id.iv_comment_upper);
            viewHolder.commentUpperNum = (TextView)convertView.findViewById(R.id.tv_comment_person);
            viewHolder.commentTextView = (TextView)convertView.findViewById(R.id.tv_comment_content);
            viewHolder.upperImageView = (ImageView)convertView.findViewById(R.id.iv_upper);
            viewHolder.upperNumTextView = (TextView)convertView.findViewById(R.id.tv_upper_num);
            viewHolder.downImageView = (ImageView)convertView.findViewById(R.id.iv_down);
            viewHolder.downNumTextView = (TextView)convertView.findViewById(R.id.tv_down_num);
            viewHolder.newsImageView = (ImageView)convertView.findViewById(R.id.iv_news);
            viewHolder.newsNumTextView = (TextView)convertView.findViewById(R.id.tv_news_num);
            viewHolder.moreImageView = (ImageView)convertView.findViewById(R.id.iv_more);
            viewHolder.moreNumTextView = (TextView)convertView.findViewById(R.id.tv_more_num);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.mediumLayout.setVisibility(View.GONE);
        JSONObject groupObject = jsonArray.getJSONObject(position).getJSONObject("group");
        JSONArray commentsArray = jsonArray.getJSONObject(position).getJSONArray("comments");
        if (groupObject.getString("status_desc").equals("热门投稿")){
            viewHolder.is_hot_imageView.setVisibility(View.VISIBLE);
        }else {
            viewHolder.is_hot_imageView.setVisibility(View.GONE);
        }
        fb.display(viewHolder.headerImageView, groupObject.getJSONObject("user").getString("avatar_url"));
        viewHolder.userName.setText(groupObject.getJSONObject("user").getString("name"));
        viewHolder.contentTextView.setText(groupObject.getString("content"));
        viewHolder.categoryNameTextView.setText(groupObject.getString("category_name"));
        if (commentsArray.size() != 0){
            viewHolder.commentLayout.setVisibility(View.VISIBLE);
            JSONObject commentObject = commentsArray.getJSONObject(0);
            fb.display(viewHolder.commentHeaderImageView,commentObject.getString("avatar_url"));
            viewHolder.commentUserName.setText(commentObject.getString("user_name"));
            viewHolder.commentUpperNum.setText(commentObject.getString("digg_count"));
            viewHolder.commentTextView.setText(commentObject.getString("text"));
        }else {
            viewHolder.commentLayout.setVisibility(View.GONE);
        }
        viewHolder.upperNumTextView.setText(groupObject.getString("digg_count"));
        viewHolder.downNumTextView.setText(groupObject.getString("bury_count"));
        viewHolder.newsNumTextView.setText(groupObject.getString("comment_count"));
        viewHolder.moreNumTextView.setText(groupObject.getString("share_count"));

        viewHolder.dislikeTextView.setOnClickListener(onClickListener);
        viewHolder.dislikeTextView.setTag(position);
        return convertView;
    }

    static class ViewHolder{
        private ImageView is_hot_imageView;  //是否是热门
        private ImageView headerImageView;   //用户头像
        private TextView userName;  //用户名称
        private TextView dislikeTextView;  //右上角的不喜欢按钮
        private TextView contentTextView;  //正文
        private TextView categoryNameTextView;  //类别名称
        private LinearLayout mediumLayout;   //中间的多媒体的布局
        private LinearLayout commentLayout;  //神评论的布局
        private ImageView commentHeaderImageView;  //评论头像图片
        private TextView commentUserName;   //评论用户名
        private TextView commentTextView;   //评论正文
        private ImageView commentUpperImageView;  //评论点赞图片
        private TextView commentUpperNum;   //神评论的点赞数
        private ImageView upperImageView;   //总共的点赞图片
        private TextView upperNumTextView;  //总共的点赞数
        private ImageView downImageView;     //鄙视的图片
        private TextView downNumTextView;    //鄙视的中人数
        private ImageView newsImageView;    //评论的总图片
        private TextView newsNumTextView;   //评论的总数量
        private ImageView moreImageView;    //更多的图片
        private TextView  moreNumTextView;   //更多的人数
    }
}

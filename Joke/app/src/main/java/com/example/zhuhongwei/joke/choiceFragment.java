package com.example.zhuhongwei.joke;

import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunzhengchun on 16/5/31.
 */
public class choiceFragment extends Fragment implements View.OnClickListener{

    private ViewPager viewPager;
    private MyAdapter adapter;
    private List<View> list = null;
    private List<TextView> titleList = null;
    private LayoutInflater layoutInflater = null;
    private LinearLayout layoutTitle = null;
    private TextView viewpagerTitle1;
    private HorizontalScrollView scrollView;
    private PullToRefreshListView pullToRefreshListView;
    private ListViewAdapter listViewAdapter;
    private List<JSONArray> arrayList = new ArrayList<JSONArray>();
    private JSONArray jsonArray = null;
    private int index;   //标志是第几页
    private static final int PULL_LIST_X = 0x10;
    private List<PullToRefreshListView>  listViewList = new ArrayList<PullToRefreshListView>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_choice,null);
        index = 0;
        viewPager = (ViewPager)view.findViewById(R.id.viewPager);
        layoutInflater = LayoutInflater.from(getActivity());
        layoutTitle = (LinearLayout)view.findViewById(R.id.layout_title);
        viewpagerTitle1 = (TextView)view.findViewById(R.id.tv_viewpager_title1);
        scrollView = (HorizontalScrollView)view.findViewById(R.id.title_scrollview);
        list = new ArrayList<>();
        String[] temp = new String[]{"推荐","视频","图片","段子","精华","同城"};
        titleList = new ArrayList<>();


        for (int i = 0;i<6;i++){
            View tab = inflater.inflate(R.layout.viewpager_item,null);
            pullToRefreshListView = (PullToRefreshListView)tab.findViewById(R.id.pull_to_refresh);
            listViewList.add(pullToRefreshListView);
            pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
            pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
                @Override
                public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                    String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME |
                            DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                    requestData(index);
                }

                @Override
                public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                    String label = DateUtils.formatDateTime(getActivity(), System.currentTimeMillis(), DateUtils.FORMAT_SHOW_TIME |
                            DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_ABBREV_ALL);
                    refreshView.getLoadingLayoutProxy().setLastUpdatedLabel(label);
                }
            });
            JSONArray jsonArr = new JSONArray();
            arrayList.add(jsonArr);
            listViewAdapter = new ListViewAdapter(getActivity(),(JSONArray)arrayList.get(i),this);
            pullToRefreshListView.setAdapter(listViewAdapter);
            //pullToRefreshListView.setRefreshing(true,-Constant.dip2px(this, 60));
            list.add(tab);

            TextView textView = null;
            if (i == 0){
                textView = viewpagerTitle1;
            }else {
                textView = new TextView(getActivity());
                textView.setLayoutParams(viewpagerTitle1.getLayoutParams());
                textView.setGravity(Gravity.CENTER);
                textView.setText(temp[i]);
                layoutTitle.addView(textView);
            }
            textView.setOnClickListener(this);
            textView.setTag(i);
            titleList.add(textView);

        }
        adapter = new MyAdapter();
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(changeListener);
        return view;
    }

    private void requestData(int i){
        String url = null;
            switch (i){
                case 0:
                    url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-101";
                    break;
                case 1:
                    url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-104";
                    break;
                case 2:
                    url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-103";
                    break;
                case 3:
                    url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-102";
                    break;
                case 4:
                    url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-201";
                    break;
                case 5:
                    url = "http://ic.snssdk.com/neihan/stream/mix/v1/?content_type=-101";
                    break;
            }
        final int index1 = i;
        FinalHttp http = new FinalHttp();
        Log.i("zhuhongwei----->url",url);
        http.get(url, new AjaxCallBack() {
            @Override
            public void onSuccess(Object response) {
                //listViewList.get(index1).onRefreshComplete();
                //pullToRefreshListView.onRefreshComplete();
                Log.i("zhuhongwei------>", response.toString());
                Message message = handler.obtainMessage();
                message.obj = response;
                message.what = PULL_LIST_X;
                message.arg1 = index1;
                handler.sendMessage(message);
            }


            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                //listViewList.get(index1).onRefreshComplete();
                //pullToRefreshListView.onRefreshComplete();
                Message message = handler.obtainMessage();
                message.obj = "网络异常";
                message.what = Constent.NETWORKERROR;
                handler.sendMessage(message);
            }
        });

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            JSONObject object = null;
            switch (msg.what){
                case PULL_LIST_X:
                    //jsonArray.clear();
                    object = JSONObject.parseObject(msg.obj.toString());
                    JSONObject dataObject = object.getJSONObject("data");
                    int i = msg.arg1;
                    arrayList.get(i).addAll(JSONArray.parseArray(dataObject.getString("data")));
                    //jsonArray.addAll(JSONArray.parseArray(dataObject.getString("data")));
                    Log.i("zhuhongwei--->jsonArray", arrayList.get(i).toJSONString());
                    listViewList.get(i).onRefreshComplete();
                    //pullToRefreshListView.onRefreshComplete();
                    listViewAdapter.notifyDataSetChanged();
                    break;
                case Constent.NETWORKERROR:
                    pullToRefreshListView.onRefreshComplete();
                    listViewAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };
    public ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                index = position;
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0;i<6;i++) {
                TextView textView = titleList.get(i);
                if (position == i) {
                    textView.setTextColor(getResources().getColor(R.color.titleSelected));
                }else {
                    textView.setTextColor(getResources().getColor(R.color.brown));
                }
               // textView.setKeepScreenOn(true);
            }

            listViewAdapter.notifyDataSetChanged();
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_dislike:
                int i = Integer.valueOf(v.getTag().toString());

                break;
            default:
                if (v.getTag() != null){
                    int position = (int)v.getTag();
                    viewPager.setCurrentItem(position);
                }
        }
    }

    public class MyAdapter extends PagerAdapter{

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager)container).addView(list.get(position));
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(list.get(position));
        }

    }
}


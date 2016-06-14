package com.example.zhuhongwei.joke;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by sunzhengchun on 16/6/6.
 */
public class TwoFragmentOne extends Fragment {

    private ViewPager viewPager;
    private TextView viewPagerTitle;
    private LinearLayout dotsLayout;
    private View dot0;
    private ListView listView;
    private FinalBitmap finalBitmap;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<View> dots = new ArrayList<View>();   //图片的点
    private JSONObject result;
    private JSONArray categoryArray;
    private ScheduledExecutorService scheduledExecutorService;
    private MyAdapter1 adapter;
    private TwoFragmentOneListViewAdapter listViewAdapter;
    private int currentpageIndex = 0;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.towfragment_one,null);
        viewPager = (ViewPager)view.findViewById(R.id.twoFragmentOne_viewPager);
        viewPagerTitle = (TextView)view.findViewById(R.id.tv_viewpagerTitle);
        dotsLayout = (LinearLayout)view.findViewById(R.id.layout_dots);
        dot0 = view.findViewById(R.id.v_dot0);
        finalBitmap = FinalBitmap.create(getActivity());
        listView = (ListView)view.findViewById(R.id.twoFragmentOne_listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(),dingyueDetailActivity.class);
                getActivity().startActivity(intent);
            }
        });
        adapter = new MyAdapter1();
        getrequestData();


        return view;
    }

    @Override
    public void onStart() {
        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new ScrollTask(),1,4, TimeUnit.SECONDS);
        super.onStart();
    }

    @Override
    public void onStop() {
        scheduledExecutorService.shutdown();
        super.onStop();
    }

    public class ScrollTask implements Runnable{

        @Override
        public void run() {
            synchronized (viewPager){
                currentpageIndex = (currentpageIndex+1)%imageViews.size();
                Message message = handler.obtainMessage();
                message.obj = "图片轮换消息";
                message.what = Constent.PIC_ROTATE_NOTICE;
                handler.sendMessage(message);

            }
        }
    }

    private void getrequestData(){
        String url = "http://lf.snssdk.com/2/essay/discovery/v3/?iid=4487395750&os_version=9.2&os_api=18&app_name=joke_essay&channel=App%20Store&device_platform=iphone&idfa=15A913ED-4B65-4461-8B46-2FDDA3DF58BE&live_sdk_version=120&vid=B3788343-EDCF-4BAF-A135-FF496A052539&openudid=c885093617f2e6d0c26caf72a82b9610d25f4870&device_type=iPhone8,1&version_code=5.2.0&ac=WIFI&screen_width=750&device_id=17344965222&aid=7";
        FinalHttp http = new FinalHttp();
        http.get(url, new AjaxCallBack() {
            @Override
            public void onSuccess(Object response) {
                Message message = handler.obtainMessage();
                message.obj = response;
                message.what = Constent.RESPONSE_NET_SUCCESS;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Throwable t, int errorNo, String strMsg) {
                Message message = handler.obtainMessage();
                message.obj = "请求失败";
                message.what = Constent.NETWORKERROR;
                handler.sendMessage(message);
            }
        });

    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case Constent.RESPONSE_NET_SUCCESS:
                    result = JSON.parseObject(msg.obj.toString());
                    Log.i("result", result.toJSONString());
                    initAdComponent();
                    JSONObject dataObj = result.getJSONObject("data");
                    JSONObject categories = dataObj.getJSONObject("categories");
                    categoryArray = categories.getJSONArray("category_list");
                    listViewAdapter = new TwoFragmentOneListViewAdapter(this,getActivity(),categoryArray);
                    listView.setAdapter(listViewAdapter);
                    break;
                case Constent.NETWORKERROR:
                    break;
                case Constent.PIC_ROTATE_NOTICE:
                    viewPager.setCurrentItem(currentpageIndex);
                    break;
                case Constent.SUBCRIBE_NET_SUCCESS:
                    //订阅成功
                    Log.i("zhuhongwei","订阅成功");
                    categoryArray.remove(msg.obj);
                    listViewAdapter.notifyDataSetChanged();
                    break;
                case Constent.SUBCRIBE_NET_ERROR:
                    //订阅失败
                    break;
                default:
                    break;
            }
        }
    };

    private void initAdComponent(){
        JSONObject dataObj = result.getJSONObject("data");
        JSONObject rotate_bannerObj = dataObj.getJSONObject("rotate_banner");
        JSONArray bannerArray = rotate_bannerObj.getJSONArray("banners");
        for (int i = 0;i < bannerArray.size();i++){
            ImageView imageView = new ImageView(getActivity());
            String schema_url = JSONObject.parseObject(bannerArray.getJSONObject(i).toString()).getString("schema_url");
            JSONObject banner_url = JSONObject.parseObject(bannerArray.get(i).toString()).getJSONObject("banner_url");
            viewPagerTitle.setText(banner_url.getString("title"));
            JSONObject url_listObject = banner_url.getJSONArray("url_list").getJSONObject(0);
            Log.i("url_listObject",url_listObject.toJSONString());
            String imgUrl = url_listObject.getString("url");
            imageView.setTag(schema_url);
            finalBitmap.display(imageView, imgUrl);
            imageViews.add(imageView);

            View view = null;
            if (i == 0){
                view = dot0;
            }else {
                view = new View(getActivity());
                view.setLayoutParams(dot0.getLayoutParams());
                view.setBackgroundResource(R.drawable.dotnormal);
                dotsLayout.addView(view);
            }
            dots.add(view);
        }
        viewPager.setAdapter(adapter);
        viewPager.setOnPageChangeListener(changeListener);
    }

    private ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        private int oldPosition = 0;
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            currentpageIndex = position;
            dots.get(position).setBackgroundResource(R.drawable.dotfouces);
            dots.get(oldPosition).setBackgroundResource(R.drawable.dotnormal);
            oldPosition = position;
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private class MyAdapter1 extends PagerAdapter{

        @Override
        public int getCount() {
            return imageViews.size();
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imageViews.get(position));
            return imageViews.get(position);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(imageViews.get(position));

        }
    }
}

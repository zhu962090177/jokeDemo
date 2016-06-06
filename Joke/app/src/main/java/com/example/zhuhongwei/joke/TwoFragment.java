package com.example.zhuhongwei.joke;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by sunzhengchun on 16/5/31.
 */
public class TwoFragment extends Fragment implements View.OnClickListener{

    TwoFragmentOne fragmentOne;
    TwoFragmentTwo fragmentTwo;
    private TextView leftTitleTextView;
    private TextView rightTitleTextView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_two,null);
        leftTitleTextView = (TextView)view.findViewById(R.id.tv_twoFragment_titleleft);
        rightTitleTextView = (TextView)view.findViewById(R.id.tv_twoFragment_titleright);
        fragmentOne = (TwoFragmentOne)getFragmentManager().findFragmentById(R.id.fragment_twoFragment_one);
        fragmentTwo = (TwoFragmentTwo)getFragmentManager().findFragmentById(R.id.fragment_twoFragment_two);
        leftTitleTextView.setOnClickListener(this);
        rightTitleTextView.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_twoFragment_titleleft:
                getFragmentManager().beginTransaction().show(fragmentOne).hide(fragmentTwo).commit();
                leftTitleTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionleft_press));
                leftTitleTextView.setTextColor(getResources().getColor(R.color.white));
                rightTitleTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionright));
                rightTitleTextView.setTextColor(getResources().getColor(R.color.brown));
                break;
            case R.id.tv_twoFragment_titleright:
                getFragmentManager().beginTransaction().show(fragmentTwo).hide(fragmentOne).commit();
                rightTitleTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionleft_press));
                rightTitleTextView.setTextColor(getResources().getColor(R.color.white));
                leftTitleTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionright));
                leftTitleTextView.setTextColor(getResources().getColor(R.color.brown));
                break;
            default:
                getFragmentManager().beginTransaction().show(fragmentOne).hide(fragmentTwo).commit();
                leftTitleTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionleft_press));
                leftTitleTextView.setTextColor(getResources().getColor(R.color.white));
                rightTitleTextView.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionright));
                rightTitleTextView.setTextColor(getResources().getColor(R.color.brown));
                break;
        }
    }
}

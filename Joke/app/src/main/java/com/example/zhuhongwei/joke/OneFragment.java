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
public class OneFragment extends Fragment implements View.OnClickListener{

    careFragment fragmentcare;
    choiceFragment fragmentChoice;
    private TextView tv_title_left;
    private TextView tv_title_right;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.content_main,null);
        tv_title_left = (TextView)view.findViewById(R.id.tv_title_left);
        tv_title_right = (TextView)view.findViewById(R.id.tv_title_right);
        tv_title_left.setOnClickListener(this);
        tv_title_right.setOnClickListener(this);
        fragmentcare = (careFragment)getFragmentManager().findFragmentById(R.id.fragment_care);
        fragmentChoice = (choiceFragment)getFragmentManager().findFragmentById(R.id.fragment_choice);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_title_left:
                getFragmentManager().beginTransaction().hide(fragmentcare).show(fragmentChoice).commit();
                tv_title_left.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionleft_press));
                tv_title_left.setTextColor(getResources().getColor(R.color.white));
                tv_title_right.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionright));
                tv_title_right.setTextColor(getResources().getColor(R.color.brown));
                break;
            case R.id.tv_title_right:
                getFragmentManager().beginTransaction().hide(fragmentChoice).show(fragmentcare).commit();
                tv_title_left.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionleft));
                tv_title_left.setTextColor(getResources().getColor(R.color.brown));
                tv_title_right.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionright_press));
                tv_title_right.setTextColor(getResources().getColor(R.color.white));
                break;
            default:
                getFragmentManager().beginTransaction().hide(fragmentcare).show(fragmentChoice).commit();
                tv_title_left.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionleft_press));
                tv_title_left.setTextColor(getResources().getColor(R.color.white));
                tv_title_right.setBackgroundDrawable(getResources().getDrawable(R.drawable.optionright));
                tv_title_right.setTextColor(getResources().getColor(R.color.brown));
                break;
        }
    }
}

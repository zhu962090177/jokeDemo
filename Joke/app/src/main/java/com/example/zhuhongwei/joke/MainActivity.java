package com.example.zhuhongwei.joke;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    OneFragment oneFragment;
    TwoFragment twoFragment;
    ThreeFragment threeFragment;
    FourFragment fourFragment;
    private LinearLayout lOne,lTwo,lThree,lFour;
    private ImageView imageView1,imageView2,imageView3,imageView4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lOne = (LinearLayout)findViewById(R.id.layout_one);
        lTwo = (LinearLayout)findViewById(R.id.layout_two);
        lThree = (LinearLayout)findViewById(R.id.layout_three);
        lFour = (LinearLayout)findViewById(R.id.layout_four);
        lOne.setOnClickListener(this);
        lTwo.setOnClickListener(this);
        lThree.setOnClickListener(this);
        lFour.setOnClickListener(this);
        imageView1 = (ImageView)findViewById(R.id.iv_imageView1);
        imageView2 = (ImageView)findViewById(R.id.iv_imageView2);
        imageView3 = (ImageView)findViewById(R.id.iv_imageView3);
        imageView4 = (ImageView)findViewById(R.id.iv_imageView4);
        oneFragment = (OneFragment)getFragmentManager().findFragmentById(R.id.fragment_one);
        twoFragment = (TwoFragment)getFragmentManager().findFragmentById(R.id.fragment_tow);
        threeFragment = (ThreeFragment)getFragmentManager().findFragmentById(R.id.fragment_three);
        fourFragment = (FourFragment)getFragmentManager().findFragmentById(R.id.fragment_four);

        Intent intent = getIntent();
        int i = intent.getIntExtra("fragment",0);
        switch (i){
            case 4:
                break;
            default:
                lOne.performClick();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_two:
                getFragmentManager().beginTransaction().hide(oneFragment).hide(threeFragment).hide(fourFragment).show(twoFragment).commit();
                imageView1.setImageDrawable(getResources().getDrawable(R.mipmap.home));
                imageView2.setImageDrawable(getResources().getDrawable(R.mipmap.found_press));
                imageView3.setImageDrawable(getResources().getDrawable(R.mipmap.audit_night));
                imageView4.setImageDrawable(getResources().getDrawable(R.mipmap.newstab));
                break;
            case R.id.layout_three:
                getFragmentManager().beginTransaction().hide(oneFragment).hide(twoFragment).hide(fourFragment).show(threeFragment).commit();
                imageView1.setImageDrawable(getResources().getDrawable(R.mipmap.home));
                imageView2.setImageDrawable(getResources().getDrawable(R.mipmap.found));
                imageView3.setImageDrawable(getResources().getDrawable(R.mipmap.audit_press));
                imageView4.setImageDrawable(getResources().getDrawable(R.mipmap.newstab));
                break;
            case R.id.layout_four:
                getFragmentManager().beginTransaction().hide(oneFragment).hide(threeFragment).hide(twoFragment).show(fourFragment).commit();
                imageView1.setImageDrawable(getResources().getDrawable(R.mipmap.home));
                imageView2.setImageDrawable(getResources().getDrawable(R.mipmap.found));
                imageView3.setImageDrawable(getResources().getDrawable(R.mipmap.audit_night));
                imageView4.setImageDrawable(getResources().getDrawable(R.mipmap.newstab_press));
                break;
            default:
                getFragmentManager().beginTransaction().hide(twoFragment).hide(threeFragment).hide(fourFragment).show(oneFragment).commit();
                imageView1.setImageDrawable(getResources().getDrawable(R.mipmap.home_press));
                imageView2.setImageDrawable(getResources().getDrawable(R.mipmap.found));
                imageView3.setImageDrawable(getResources().getDrawable(R.mipmap.audit_night));
                imageView4.setImageDrawable(getResources().getDrawable(R.mipmap.newstab));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}

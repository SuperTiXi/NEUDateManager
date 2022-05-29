package com.example.neudatemanager.ui.curriculum;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSON;
import com.example.neudatemanager.R;
import com.example.neudatemanager.entity.SimpleNEUClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class CurriculumActivity extends Activity {
    private TimeTableView mTimaTableView;
    private static List<TimeTableModel> mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_course);
        mList = new ArrayList<TimeTableModel>();
        mTimaTableView = (TimeTableView) findViewById(R.id.main_timetable_ly);
        addList();
        mTimaTableView.setTimeTable(mList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void addList() {
        SharedPreferences userInfo = getSharedPreferences("msg_settings", MODE_PRIVATE);
        String tem = userInfo.getString("course","");
        ArrayList<SimpleNEUClass> classArray = (ArrayList<SimpleNEUClass>) JSON.parseArray(tem, SimpleNEUClass.class);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        int week = 0;
        try {
            Date targerDay = sdf.parse("2022-02-28");
            long targetTime = targerDay.getTime();
            //        4.获取当前日期，转换为毫秒值getTime();方法
            long todaytime = new Date().getTime();
            long time =Math.abs(targetTime-todaytime);
//        用format()方法将一个 Date 格式化为日期/时间字符串。 返回一个字符串。
            week = (int) (time / 1000 / 60 / 60 / 24 / 7);
        } catch (ParseException e) {
            e.printStackTrace();
            return;
        }
        if(classArray == null) {
            return;
        }
        for(SimpleNEUClass simpleNEUClass: classArray) {
            if(simpleNEUClass.getWeeks().contains(week + 1)) {
                mList.add(new TimeTableModel(0,simpleNEUClass.getSections().get(0),
                        simpleNEUClass.getSections().get(1), simpleNEUClass.getDay(), "8:20", "10:10",
                        simpleNEUClass.getName(), simpleNEUClass.getTeacher(), simpleNEUClass.getPosition(),"1"));

            }
        }
//        mList.add(new TimeTableModel(0, 3, 5, 5, "8:20", "10:10", "税务筹划",
//                "老师6", "10", "2-13"));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_screenshot:
                ScreenshotUtil.getBitmapByView(this, (ScrollView) findViewById(R.id.main_scrollview));
                break;
        }
        return true;
    }
}

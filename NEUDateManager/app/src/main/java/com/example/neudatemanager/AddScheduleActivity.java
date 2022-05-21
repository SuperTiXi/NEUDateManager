package com.example.neudatemanager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TimePicker;

import com.example.neudatemanager.entity.Schedule;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

public class AddScheduleActivity extends AppCompatActivity {

    ImageView imageView;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_schedule);

        //寻找组件
        imageView =findViewById(R.id.imageView_Commit);
        listView = findViewById(R.id.ListView_AddSchedule);

        //获取creator
        SharedPreferences sharedPreferences = getSharedPreferences(null,MODE_PRIVATE);
        String creator = sharedPreferences.getString("name",null);

        Schedule schedule = new Schedule(creator);

        //初步设置list
        setListView(schedule);

        //编辑日程
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                switch(i){
                    case 0://设置标题
                        final EditText inputServer = new EditText(AddScheduleActivity.this);
                        AlertDialog.Builder builder = new AlertDialog.Builder(AddScheduleActivity.this);
                        builder.setTitle("输入标题")
                                .setIcon(android.R.drawable.ic_dialog_info)
                                .setView(inputServer)
                                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                });
                        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                schedule.setName(inputServer.getText().toString());
                                dialogInterface.dismiss();
                                setListView(schedule);
                            }
                        });
                        break;
                    case 1://设置日期
                        Calendar calendar = Calendar.getInstance();
                        new DatePickerDialog(AddScheduleActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                Date date = new Date(year,month,day);
                                schedule.setDay(schedule.formatDate(date));
                                setListView(schedule);
                            }
                        },calendar.get(Calendar.YEAR)
                        ,calendar.get(Calendar.MONTH)
                        ,calendar.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                    case 2://设置开始时间
                        calendar = Calendar.getInstance();
                        new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                Date date = new Date();
                                date.setHours(hourOfDay);
                                date.setMinutes(minute);
                                schedule.setStartTime(schedule.formatTime(date));
                                setListView(schedule);
                            }
                        },calendar.get(Calendar.HOUR_OF_DAY)
                        ,calendar.get(Calendar.MINUTE)
                        ,true).show();
                        break;
                    case 3://设置结束时间
                        calendar = Calendar.getInstance();
                        new TimePickerDialog(AddScheduleActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                                Date date = new Date();
                                date.setHours(hourOfDay);
                                date.setMinutes(minute);
                                schedule.setEndTime(schedule.formatTime(date));
                                setListView(schedule);
                            }
                        },calendar.get(Calendar.HOUR_OF_DAY)
                                ,calendar.get(Calendar.MINUTE)
                                ,true).show();
                        break;
                }
            }
        });

    }

    //list设置函数
    public void setListView(Schedule schedule){
        ArrayList list = new ArrayList();
        HashMap map  = new HashMap();

        map.put("head","事件标题");
        map.put("body",schedule.getName());
        list.add(map);
        map = new HashMap();

        map.put("head","事件日期");
        map.put("body",schedule.getDay());
        list.add(map);
        map = new HashMap();

        map.put("head","开始时间");
        map.put("body",schedule.getStartTime());
        list.add(map);
        map = new HashMap();

        map.put("head","结束时间");
        map.put("body",schedule.getEndTime());
        list.add(map);

        String[] from = {"head","body"};
        int[] to = {R.id.textView_Head,R.id.textView_Body};
        SimpleAdapter simpleAdapter = new SimpleAdapter(this,list,R.layout.list_add_schedule,from,to);
        listView.setAdapter(simpleAdapter);
    }
}
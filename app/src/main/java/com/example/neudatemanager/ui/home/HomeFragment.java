package com.example.neudatemanager.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.neudatemanager.databinding.FragmentHomeBinding;
import com.example.neudatemanager.ui.scheduleManage.AddScheduleActivity;
import com.example.neudatemanager.R;
import com.example.neudatemanager.entity.Schedule;
import com.example.neudatemanager.ui.scheduleManage.ModifyScheduleActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private Cursor cursor;
    private String formatDate;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView listView = binding.ListViewScheduleOfToday;
        final FloatingActionButton button = binding.ButtonAddSchedule;
        final CalendarView calendarView = binding.calendarView;

        //获取参数
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(null, Context.MODE_PRIVATE);
        String creator = sharedPreferences.getString("creator",null);
        Schedule schedule = new Schedule(creator);


        //点击日历时切换，今日日程
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Date date = new Date(year-1900,month,day);
                formatDate = schedule.formatDate(date);
                cursor = schedule.getCursorByDay(null,getActivity(),formatDate);
                int[] to = {R.id.textView_nameGet,R.id.textView_startTimeGet,R.id.textView_endTimeGet};
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.list,cursor,cursor.getColumnNames(),to);
                listView.setAdapter(simpleCursorAdapter);

            }
        });

        //跳转到修改界面
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                cursor.moveToPosition(position);
                SharedPreferences sharedPreferences1 = getActivity().getSharedPreferences("scheduleInfo",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences1.edit();
                editor.putString("name",cursor.getString(0));
                editor.putString("startTime",cursor.getString(1));
                editor.putString("endTime",cursor.getString(2));
                editor.putString("date",formatDate);
                editor.commit();
                Intent intent = new Intent(getActivity(), ModifyScheduleActivity.class);
                startActivity(intent);
            }
        });

        //创建日程按钮
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AddScheduleActivity.class);
                startActivity(intent);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
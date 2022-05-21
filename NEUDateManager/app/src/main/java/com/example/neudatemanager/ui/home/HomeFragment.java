package com.example.neudatemanager.ui.home;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.CalendarView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.neudatemanager.AddScheduleActivity;
import com.example.neudatemanager.R;
import com.example.neudatemanager.databinding.FragmentHomeBinding;
import com.example.neudatemanager.entity.Schedule;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.util.Date;


public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        final ListView listView = binding.ListViewScheduleOfToday;
        final FloatingActionButton button = binding.ButtonAddSchedule;
        final CalendarView calendarView = binding.calendarView;
        


        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                Schedule schedule = new Schedule();
                Date date = new Date(year,month,day);
                Cursor cursor = schedule.getCursorByDay(null,getActivity(),schedule.formatDate(date));
                int[] to = {R.id.textView_nameGet,R.id.textView_startTimeGet,R.id.textView_endTimeGet};
                SimpleCursorAdapter simpleCursorAdapter = new SimpleCursorAdapter(getActivity(),R.layout.list,cursor,new String[]{"name","startTime","endTime"},to);
                listView.setAdapter(simpleCursorAdapter);

            }
        });

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
package com.example.neudatemanager.ui.mine;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;


import com.example.neudatemanager.entity.User;


public class MineFragment extends Fragment {

    private FragmentMineBinding binding;
    TextView textView_welcome;
    TextView textView_modifyPassword;
    ImageView imageView;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        //组件赋值
        textView_modifyPassword = binding.textViewModifyPassword;
        textView_welcome = binding.textViewWelcome;
        imageView = binding.imageViewModifyPassword;

        //获取用户名
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(null, Context.MODE_PRIVATE);
        String userName = sharedPreferences.getString("creator",null);
        textView_welcome.setText("欢迎！"+" "+userName);

        textView_modifyPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final EditText inputServer = new EditText(getActivity());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("输入新密码")
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
                        User newUser = new User(userName,inputServer.getText().toString());
                        int a = newUser.modifyPassword(null,getActivity());
                        if(a==0){
                            Toast.makeText(getActivity(),"修改失败",Toast.LENGTH_LONG).show();
                        }else{
                            Toast.makeText(getActivity(),"修改成功",Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.show();
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
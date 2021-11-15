package com.example.homework;

/*
    本程序中对数据库的插入操作和查询，使用了MyDAO类的相关方法
    首次运行时，增加2条记录并使用ListView控件显示出来
*/

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyFragment extends Fragment implements View.OnClickListener {

    private MyDAO myDAO;  //数据库访问对象
    private ListView listView;
    private List<Map<String, Object>> listData;
    private Map<String, Object> listItem;
    private SimpleAdapter listAdapter;
    private View view;

    private EditText et_name;  //数据表包含3个字段，第1字段为自增长类型
    private EditText et_password;

    private String selId = null;  //选择项id


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.my_fragment, container, false);

        Button bt_add = (Button) view.findViewById(R.id.bt_login);
        bt_add.setOnClickListener(this);
        Button bt_modify = (Button) view.findViewById(R.id.bt_freeRigster);
        bt_modify.setOnClickListener(this);
        Button bt_del = (Button) view.findViewById(R.id.bt_delete);
        bt_del.setOnClickListener(this);

        et_name = (EditText) view.findViewById(R.id.et_name);
        et_password = (EditText) view.findViewById(R.id.et_password);

        myDAO = new MyDAO(view.getContext());  //创建数据库访问对象
        if (myDAO.getRecordsNumber() == 0) {  //防止重复运行时重复插入记录
            myDAO.insertInfo("胡涛", "123");   //插入记录
            myDAO.insertInfo("何世洋", "456"); //插入记录
        }

        displayRecords();   //显示记录

        return view;
    }

    public void displayRecords() {  //显示记录方法定义
        listView = (ListView) view.findViewById(R.id.listView);
        listData = new ArrayList<Map<String, Object>>();
        Cursor cursor = myDAO.allQuery();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);  //获取字段值
            String name = cursor.getString(1);
            //int password=cursor.getInt(2);
            @SuppressLint("Range") int password = cursor.getInt(cursor.getColumnIndex("password"));//推荐此种方式
            listItem = new HashMap<String, Object>(); //必须在循环体里新建
            listItem.put("_id", id);  //第1参数为键名，第2参数为键值
            listItem.put("name", name);
            listItem.put("password", password);
            listData.add(listItem);   //添加一条记录
        }
        listAdapter = new SimpleAdapter(view.getContext(),
                listData,
                R.layout.list_item, //自行创建的列表项布局
                new String[]{"_id", "name", "password"},
                new int[]{R.id.tv_id, R.id.tv_name, R.id.tv_password});
        listView.setAdapter(listAdapter);  //应用适配器
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {  //列表项监听
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> rec = (Map<String, Object>) listAdapter.getItem(position);  //从适配器取记录
                et_name.setText(rec.get("name").toString());  //刷新文本框
                et_password.setText(rec.get("password").toString());
                Log.i("ly", rec.get("_id").toString());
                selId = rec.get("_id").toString();  //供修改和删除时使用
            }
        });
    }

    @Override
    public void onClick(View v) {  //实现的接口方法
        if (selId != null) {  //选择了列表项后，可以增加/删除/修改
            String p1 = et_name.getText().toString().trim();
            String p2 = et_password.getText().toString().trim();
            switch (v.getId()) {
                case R.id.bt_login:
                    myDAO.insertInfo(p1, p2);
                    break;
                case R.id.bt_freeRigster:
                    myDAO.updateInfo(p1, p2, selId);
                    Toast.makeText(view.getContext(), "更新成功！", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.bt_delete:
                    myDAO.deleteInfo(selId);
                    Toast.makeText(view.getContext(), "删除成功！", Toast.LENGTH_SHORT).show();
                    et_name.setText(null);
                    et_password.setText(null);
                    selId = null; //提示
            }
        } else {  //未选择列表项
            if (v.getId() == R.id.bt_login) {  //单击登陆
                String p1 = et_name.getText().toString();
                String p2 = et_password.getText().toString();
                if (p1.equals("") || p2.equals("")) {  //要求输入了信息
                    Toast.makeText(view.getContext(), "用户名和密码都不能空！", Toast.LENGTH_SHORT).show();
                } else {
                    myDAO.insertInfo(p1, p2);
                }
            } else {   //单击了注册或其他登录方式按钮
                Toast.makeText(view.getContext(), "请先选择记录！", Toast.LENGTH_SHORT).show();
            }
        }
        displayRecords();//刷新ListView对象
    }


}

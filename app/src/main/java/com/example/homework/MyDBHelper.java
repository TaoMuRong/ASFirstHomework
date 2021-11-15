package com.example.homework;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    public static final String TB_NAME = "users";  //表名

    //构造方法：第1参数为上下文，第2参数库库名，第3参数为游标工厂，第4参数为版本
    public MyDBHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);//创建或打开数据库
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //第一次创建数据库时，方法onCreate()被调用
        //通常将创建表的操作放在这里
        //当表不存在时，创建表；第一字段为自增长类型
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS " +
                TB_NAME + "( _id integer primary key autoincrement," +
                "name varchar," + "password varchar" + ")");

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // 执行SQL命令
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TB_NAME);
        onCreate(sqLiteDatabase);
    }
}

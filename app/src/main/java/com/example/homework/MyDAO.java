package com.example.homework;

/*
    本类MyDAO调用了打开数据库的助手类DbHelper
    本类MyDAO提供的CRUD针对数据库test.db的表users
    查询数据库表所有记录的方法：allQuery()
    插入记录的方法：insertInfo(String name,int password)
    删除记录的方法：deleteInfo(String selId)
    修改记录方法：updateInfo(String name,int password,String selId)
*/

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class MyDAO {
    private SQLiteDatabase myDb;  //类的成员
    private MyDBHelper myDBHelper;  //类的成员

    public MyDAO(Context context) {  //构造方法，参数为上下文对象
        //第1参数为上下文，第2参数为数据库名
        myDBHelper = new MyDBHelper(context,"test.db",null,1);
    }

    public Cursor allQuery(){    //查询所有记录
        myDb = myDBHelper.getReadableDatabase();
        return myDb.rawQuery("select * from users",null);
    }
    public  int getRecordsNumber(){  //返回数据表记录数
        myDb = myDBHelper.getReadableDatabase();
        Cursor cursor= myDb.rawQuery("select * from users",null);
        return cursor.getCount();
    }

    public void insertInfo(String name,String password){  //登陆
        myDb = myDBHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        long rowid=myDb.insert(MyDBHelper.TB_NAME, null, values);
        if(rowid==-1)
            Log.i("myDbDemo", "登陆失败！");
        else
            Log.i("myDbDemo", "登陆成功！"+rowid);
    }
    public void deleteInfo(String selId){  //删除记录
        String where = "_id=" + selId;
        int i = myDb.delete(myDBHelper.TB_NAME, where, null);
        if (i > 0)
            Log.i("myDbDemo", "数据删除成功！");
        else
            Log.i("myDbDemo", "数据未删除！");
    }
    public void updateInfo(String name,String password,String selId){  //修改记录
        //方法中的第三参数用于修改选定的记录
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        String where="_id="+selId;
        int i=myDb.update(myDBHelper.TB_NAME, values, where, null);

        //上面几行代码的功能可以用下面的一行代码实现
        //myDb.execSQL("update users set name = ? ,password = ? where _id = ?",new Object[]{name,password,selId});

        if(i>0)
            Log.i("myDbDemo","数据更新成功！");
        else
            Log.i("myDbDemo","数据未更新！");
    }
}

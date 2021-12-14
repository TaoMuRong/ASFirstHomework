package com.example.homework;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface Api {
    /**
     * 上传
     * Multipart 这个注解代表多表单上传
     * @param partList 表单信息
     * @return .
     */
    @Multipart
    @POST("服务器地址(就创建retrofit设置的基站地址后面的具体地址)")
    Call<BaseBean> upLoading(@Part List<MultipartBody.Part> partList);
}

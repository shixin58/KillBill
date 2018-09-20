package com.max.thirdparty.protocal;

import com.max.thirdparty.bean.PhoneNumberModel;
import com.max.thirdparty.bean.WrapperModel;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public interface IService {
    @GET("mobile/get")
    Call<WrapperModel<PhoneNumberModel>> getPhoneInfo(@Query("phone") String phone,
                                                      @Query("key") String key);
    @GET("mobile/get")
    Observable<WrapperModel<PhoneNumberModel>> getPhoneInfo2(@Query("phone") String phone,
                                                             @Query("key") String key);
}

package com.wlan.familymembers.model.personal;

import com.rwq.framworkapp.net.HttpUtils;
import com.rwq.framworkapp.net.ThreadTransformer;
import com.wlan.familymembers.contract.Contract;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import rx.Observable;

/**
 * 类作用：
 * Created by Administrator on 2018/9/21.
 */

public class BaseInformationModel implements Contract.BaseInformationContract.Model{
    @Override
    public Observable<ResponseBody> BaseInformation(String userId) {
        Map<String,String> map=new HashMap<>();
        map.put("userid",userId);
        return HttpUtils.initRetrofit().BaseInformation(map).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> uploadPicture(File imgFile, String userId) {
      final RequestBody requestBody=RequestBody.create(MediaType.parse("image/png"),imgFile);
        MultipartBody.Part part = MultipartBody.Part.createFormData("pic", imgFile.getName(), requestBody);
        MultipartBody.Part userPart = MultipartBody.Part.createFormData("userid", userId);
        return HttpUtils.initRetrofit().uploadPicture(part,userPart).compose(new ThreadTransformer());
    }

    @Override
    public Observable<ResponseBody> keep(String userId,String i,String name) {
        Map<String,String> map=new HashMap<>();
        map.put("userid",userId);
        map.put("sex",i);
        map.put("nickName",name);
        return HttpUtils.initRetrofit().keep(map).compose(new ThreadTransformer());
    }
}

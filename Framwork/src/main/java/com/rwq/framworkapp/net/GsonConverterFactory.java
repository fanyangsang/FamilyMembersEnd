package com.rwq.framworkapp.net;

import com.google.gson.Gson;

import retrofit2.Converter;

/**
 * 类作用：
 * Created by rwq_Administrator on 2018/4/24.
 */

public class GsonConverterFactory extends Converter.Factory {
    public static GsonConverterFactory create() {
        return create(new Gson());
    }

    public static GsonConverterFactory create(Gson gson) {
        return new GsonConverterFactory(gson);
    }

    private final Gson gson;

    private GsonConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }
}

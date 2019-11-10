package com.nutcunofficial.okHttp;

import okhttp3.Request;

public class ChromeHeaderBuilder extends Request.Builder {

    public final String User_Agent = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/76.0.3809.132 Safari/537.36";
    public final String Accept = "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3";
    public final String Accept_Lang = "zh-TW,zh;q=0.9,ja-JP;q=0.8,ja;q=0.7,en-US;q=0.6,en;q=0.5";

    public ChromeHeaderBuilder(String url){
        url(url);
        addHeader("User-Agent", User_Agent);
        addHeader("Accept", Accept);
        addHeader("Accept-Language", Accept_Lang);
    }

}

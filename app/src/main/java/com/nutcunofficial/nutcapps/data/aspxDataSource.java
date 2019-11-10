package com.nutcunofficial.nutcapps.data;

import java.util.HashMap;

public interface aspxDataSource {

    interface UpdateAspNetHiddenValueCallBack{
        void onLoadedHTML(HashMap<String,String> update);
        void onLoadedFailure();
    }

    void UpdateAspNetHiddenValue(String url,final aspxDataSource.UpdateAspNetHiddenValueCallBack callBack);
}

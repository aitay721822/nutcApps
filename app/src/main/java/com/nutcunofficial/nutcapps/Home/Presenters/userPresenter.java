package com.nutcunofficial.nutcapps.Home.Presenters;

import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.UserContract;
import com.nutcunofficial.nutcapps.Home.Modules.StudentModule;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class userPresenter implements UserContract.Presenter, memberDataSource.GetUserDataCallback,memberDataSource.fetchUserImageCallBack {

    private UserContract.View mView;

    public userPresenter(UserContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestStuData() {
        mView.showProgress(true);
        memberRepository.getInstance().GetUserData(this);
    }

    @Override
    public void requestUserImage() {
        mView.showProgress(true);
        memberRepository.getInstance().fetchUserImage(this);

    }

    @Override
    public void cancelRequest() {
        memberRepository.getInstance().cancelRequest();
    }

    @Override
    public void onGettingSuccess(String html) {
        Document doc = Jsoup.parse(html);

        // select student type
        List<String> typeSet = new ArrayList<>();
        Elements type = doc.select("div.alert.alert-notice");
        if(type.hasText()){
            String[] splited = type.text().split(" ");
            typeSet.add(splited[0].substring(0,splited[0].indexOf("(")));
            typeSet.add(splited[0].substring(splited[0].indexOf("(") + 1 ,splited[0].indexOf(")")));
            typeSet.add(splited[1]);
        }
        else
            typeSet = null;

        HashMap<String,String> CSS_QUERY = new HashMap<>();
        CSS_QUERY.put("姓名","em[id=name]");
        CSS_QUERY.put("性別","span.label.label-primary");
        CSS_QUERY.put("連絡電話","input[name=tel]");
        CSS_QUERY.put("行動電話","input[name=phone]");
        CSS_QUERY.put("英文姓名","input[name=ename]");

        HashMap<String,String> result = new HashMap<>();

        Elements headers = doc.select("table>tbody>tr>th");
        Elements datas = doc.select("table>tbody>tr>td");

        if (headers.size() != datas.size() || typeSet == null || typeSet.size() < 3) {
            mView.showError(nutcStatusCode.FETCH_ERROR);
            mView.showProgress(false);
            return;
        }

        for(int i = 0 ; i < datas.size(); i++){

            Element header = headers.get(i);
            Element data = datas.get(i);

            boolean scanText = true;

            for(Map.Entry<String,String> keyValue : CSS_QUERY.entrySet()){
                Elements selected = data.select(keyValue.getValue());
                if(selected.size() > 0 ){
                    // is input field
                    if((selected.select("input")).size() > 0 ){
                        String value = selected.select("input").attr("value");

                        result.put(keyValue.getKey(),value);
                    }
                    // is normal field
                    else if (selected.hasText()){
                        result.put(keyValue.getKey(),selected.text());
                    }
                    // THEN DO NOT SCAN TEXT IN THIS ELEMENTS.
                    scanText = false;
                }
            }
            // IF CSS QUERY NOT SELECT ITEM
            if(scanText && data.hasText() && header.hasText()){
                result.put(header.text().substring(0,header.text().indexOf("：")),data.text());
            }

        }
        mView.setStuData(new StudentModule(typeSet.get(0),typeSet.get(1),typeSet.get(2),result));
        mView.showProgress(false);
    }

    @Override
    public void onGettingFailure(int StatusCode) {
        mView.showError(StatusCode);
        mView.showProgress(false);
    }

    @Override
    public void onFetchSuccess(String url) {
        mView.setUserImage(url);
        mView.showProgress(false);
    }

    @Override
    public void onFetchError(int StatusCode) {
        mView.showError(StatusCode);
        mView.showProgress(false);
    }
}

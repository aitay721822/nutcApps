package com.nutcunofficial.nutcapps.Home.Presenters;

import android.util.Log;

import com.nutcunofficial.nutcapps.Home.Modules.AbsentModule;
import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.AbsentContract;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class AbsentPresenter implements AbsentContract.Presenter, memberDataSource.GetAbsentDataCallback,memberDataSource.fetchUserImageCallBack,memberDataSource.GetOptionsCallback {
    private AbsentContract.View mView;

    public AbsentPresenter(AbsentContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestOptions() {
        mView.showProgress(true);
        memberRepository.getInstance().GetOptions(this);
    }

    @Override
    public void requestAbsentData(String sem) {
        mView.showProgress(true);
        memberRepository.getInstance().GetAbsentData(sem,this);
    }

    @Override
    public void requestUserImage() {
        memberRepository.getInstance().fetchUserImage(this);
    }

    @Override
    public void cancelRequest() {
        memberRepository.getInstance().cancelRequest();
    }

    @Override
    public void GettingSuccess(String html) {
        Document doc = Jsoup.parse(html);
        Elements total = doc.select("table>tbody>tr.tr_total");
        Elements data = doc.select("table>tbody>tr.tr_data");
        if(total == null || total.size() == 0 || data == null || data.size() == 0){ mView.showError(nutcStatusCode.FETCH_ERROR);return;}

        Elements total_value = total.select("td");
        if(total_value.size() < 12 ) {mView.showError(nutcStatusCode.FETCH_ERROR);return;}
        AbsentModule result = new AbsentModule();
        List<String> value = new ArrayList<>();
        for (Element e : total_value){
            if(e.hasText()){
                value.add( e.text());
            }
        }
        result.setTotalModule(new AbsentModule.AbsentTotalModule(value));

        for (Element e : data) {
            if(e.hasAttr("data-yysem")){

                Elements class_data = e.select("td");
                String[] parse = new String[class_data.size()];

                int idx = 0;
                for(Element d : class_data){
                    parse[idx++] = d.text();
                }

                //String classRoom_Name,String class_Name,String group,String type,String credit,String teacher,String[] status,String[] detailStat

                String[] status = (!parse[7].isEmpty()) ? parse[7].split("</br>") : new String[0];
                String[] detail = (!class_data.get(class_data.size()-1).attr("title").isEmpty()) ?
                        class_data.get(class_data.size()-1).attr("title").split("</br>") :
                        new String[0];

                result.addClass(new AbsentModule.AbsentClassModule(
                    parse[1],parse[2],parse[3],parse[4],parse[5],parse[6],status,detail
                ));

            }
        }

        mView.showAbsentList(result);
        mView.showProgress(false);
    }

    @Override
    public void GettingFailure(int StautsCode) {
        mView.showProgress(false);
        mView.showError(StautsCode);
    }

    @Override
    public void onFetchSuccess(String url) {
        mView.showProgress(false);
        mView.setUserImage(url);
    }

    @Override
    public void onFetchError(int StatusCode) {
        mView.showProgress(false);
        mView.showError(StatusCode);
    }

    @Override
    public void GetOptionsData(HashMap<String, String> Options) {
        mView.showAbsentOptions(Options);
        mView.showProgress(false);
    }

    @Override
    public void GetOptionsFail(int StatusCode) {
        mView.showProgress(false);
        mView.showError(StatusCode);
    }
}

package com.nutcunofficial.nutcapps.Home.Presenters;

import androidx.annotation.NonNull;

import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.GradeContract;
import com.nutcunofficial.nutcapps.Home.Modules.GradeModule;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.HashMap;

public class GradePresenter implements GradeContract.Presenter , memberDataSource.fetchUserImageCallBack,memberDataSource.GetGradeDataCallback{
    @NonNull
    private GradeContract.View mView;

    public GradePresenter(GradeContract.View mView){
        this.mView =mView;
    }

    @Override
    public void requestGradeList() {
        mView.showProgress(true);
        memberRepository.getInstance().GetGradeData(this);
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
    public void GetSuccess(String html) {
        Document doc = Jsoup.parse(html);
        Elements options = doc.select("option");
        Elements total = doc.select("table>tbody>tr.tr_total");
        Elements data = doc.select("table>tbody>tr.tr_data");
        if(total == null || total.size() == 0 || data == null || data.size() == 0) mView.showError(nutcStatusCode.FETCH_ERROR);

        GradeModule result = new GradeModule();

        HashMap<String,String> Opt = new HashMap<>();
        for(Element e : options){
            if(e.hasText() && e.hasAttr("value")){
                Opt.put(e.attr("value"),e.text());
            }
        }

        for (Element e : total){
            if(e.hasAttr("data-yysem")){

                String data_yysem = e.attr("data-yysem");

                Elements total_data = e.select("td");
                String[] parse = new String[total_data.size()];

                int idx = 0;
                for(Element d :total_data){
                    parse[idx++] = d.text();
                }

                result.setGradeModule(data_yysem,
                        new GradeModule.GradeTotalModule(
                                parse[0],parse[1],parse[2],parse[3],parse[4]
                        ),
                        Opt.get(data_yysem)
                );

            }
        }

        for (Element e : data){
            if(e.hasAttr("data-yysem")){

                String data_yysem = e.attr("data-yysem");

                Elements grade_data = e.select("td");
                String[] parse = new String[grade_data.size()];

                int idx = 0;
                for(Element d : grade_data){
                    parse[idx++] = d.text();
                }

                result.addClassModule(data_yysem,
                        new GradeModule.GradeClassModule(
                                Integer.valueOf(parse[0]),parse[1],parse[2],parse[3],parse[4],parse[5],parse[6]
                        )
                );

            }
        }

        mView.showGradeList(result);
        mView.showProgress(false);
    }

    @Override
    public void GetError(int StatusCode) {
        mView.showProgress(false);
        mView.showError(StatusCode);
    }
}

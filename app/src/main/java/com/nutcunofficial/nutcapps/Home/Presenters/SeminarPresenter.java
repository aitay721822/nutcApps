package com.nutcunofficial.nutcapps.Home.Presenters;

import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.SeminarContract;
import com.nutcunofficial.nutcapps.Home.Modules.SeminarModules;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class SeminarPresenter implements SeminarContract.Presenter, memberDataSource.GetSeminarListCallBack,memberDataSource.fetchUserImageCallBack {

    private SeminarContract.View mView;

    public SeminarPresenter(SeminarContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void cancelRequest() {
        memberRepository.getInstance().cancelRequest();
        mView.showProgress(false);
    }

    @Override
    public void requestSeminarList() {
        mView.showProgress(true);
        memberRepository.getInstance().GetSeminarList(this);
    }

    @Override
    public void onRequestSuccessful(String responseHTML) {
        List<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(responseHTML);
        Elements elements = doc.select("table>tbody>tr>td");
        for(Element e : elements){
            if(e.hasText()){
                list.add(e.text());
            }
        }

        List<SeminarModules> result = new ArrayList<>();
        if(list.size() % 3 == 0 ){
            for(int idx = 0; idx<list.size();idx+=3){
                SeminarModules seminarData = new SeminarModules(
                        list.get(idx),list.get(idx+1),list.get(idx+2)
                );
                result.add(seminarData);
            }
            mView.showSeminarList(result);
        }
        else
            mView.showError(nutcStatusCode.FETCH_ERROR);
        mView.showProgress(false);
    }

    @Override
    public void onRequestFailure(int StatusCode) {
        mView.showError(StatusCode);
        mView.showProgress(false);
    }

    @Override
    public void requestUserImage() {
        mView.showProgress(true);
        memberRepository.getInstance().fetchUserImage(this);
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

package com.nutcunofficial.nutcapps.Home.Presenters;

import androidx.annotation.NonNull;

import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.HomeContract;
import com.nutcunofficial.nutcapps.Home.Modules.noticeModule;
import com.nutcunofficial.nutcapps.util.RegexParser;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.Presenter,
        memberDataSource.getNoticesCallBack,
        memberDataSource.fetchUserImageCallBack{

    @NonNull
    private final HomeContract.View mView;

    public HomePresenter(@NonNull HomeContract.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestNotice() {
        mView.showProgress(true);
        memberRepository.getInstance().getNotices(this);
    }

    @Override
    public void requestUserImage() {
        memberRepository.getInstance().fetchUserImage(this);
    }

    @Override
    public void cancelRequest() {
        memberRepository.getInstance().cancelRequest();
        mView.showProgress(false);
    }

    @Override
    public void onNoticeContent(String response) {
        List<noticeModule> moduleList = new ArrayList<>();
        Document doc = Jsoup.parse(response);
        // Select Content
        Elements elements = doc.select("table>tbody>tr>td");
        for(int i =0;i<elements.size();i+=5){
            try{
                int Hash = Integer.valueOf(elements.get(i).text());
                String entities = elements.get(i+1).text();
                String type = elements.get(i+2).text();
                String title = elements.get(i+3).text();
                Element ViewIdElem = elements.get(i+4).select("td>a").get(0);
                int ViewId = RegexParser.NoticeMatcher(ViewIdElem.attr("onclick"));
                moduleList.add(new noticeModule(
                        Hash,entities,type,title,ViewId
                ));
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
        mView.showNotice(moduleList);
        mView.showProgress(false);
    }

    @Override
    public void onNoticeGetterFail(int StatusCode) {
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

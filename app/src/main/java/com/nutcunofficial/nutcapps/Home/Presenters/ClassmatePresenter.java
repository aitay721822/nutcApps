package com.nutcunofficial.nutcapps.Home.Presenters;

import androidx.annotation.NonNull;

import com.nutcunofficial.nutcapps.data.memberDataSource;
import com.nutcunofficial.nutcapps.data.memberRepository;
import com.nutcunofficial.nutcapps.Home.Contracts.ClassmateContracts;
import com.nutcunofficial.nutcapps.Home.Modules.ClassmateModule;
import com.nutcunofficial.nutcapps.util.nutcStatusCode;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class ClassmatePresenter implements ClassmateContracts.Presenter, memberDataSource.GetClassmateCallback,memberDataSource.fetchUserImageCallBack {

    @NonNull
    private ClassmateContracts.View mView;

    public ClassmatePresenter(@NonNull ClassmateContracts.View mView) {
        this.mView = mView;
    }

    @Override
    public void requestUserImage() {
        mView.showProgress(true);
        memberRepository.getInstance().fetchUserImage(this);
    }

    @Override
    public void requestClassmateList() {
        mView.showProgress(true);
        memberRepository.getInstance().GetClassmateList(this);
    }

    @Override
    public void cancelRequest() {
        memberRepository.getInstance().cancelRequest();
        mView.showProgress(false);
    }

    @Override
    public void onRequestClassmateSuccessful(String html) {
        List<String> list = new ArrayList<>();
        Document doc = Jsoup.parse(html);
        Elements elements = doc.select("table>tbody>tr>td");
        for(int i =0;i<elements.size();i++){
            Element e = elements.get(i);
            if(e.hasText()){
                list.add(e.text());
            }
        }

        List<ClassmateModule> result = new ArrayList<>();

        if(list.size()%5==0){
            for(int idx=0;idx<list.size();idx+=5){
                result.add(new ClassmateModule(
                   Integer.valueOf(list.get(idx)),
                   list.get(idx+1),
                   list.get(idx+2),
                   list.get(idx+3)
                ));
            }
            mView.showClassmateList(result);
        }
        else
            mView.showError(nutcStatusCode.FETCH_ERROR);

        mView.showProgress(false);
    }

    @Override
    public void onRequestFailure(int StautsCode) {
        mView.showProgress(false);
        mView.showError(StautsCode);
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

package com.nutcunofficial.nutcapps.Home.Modules;

import java.util.Comparator;

public class noticeModule {
    private int hash;
    private String Entites;
    private String Type;
    private String title;
    private int viewId;

    public noticeModule(int hash, String entites, String type, String title, int viewId) {
        this.hash = hash;
        Entites = entites;
        Type = type;
        this.title = title;
        this.viewId = viewId;
    }

    public void setHash(int hash) {
        this.hash = hash;
    }

    public void setEntites(String entites) {
        Entites = entites;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getHash() {
        return hash;
    }

    public String getEntites() {
        return Entites;
    }

    public String getType() {
        return Type;
    }

    public String getTitle() {
        return title;
    }

    public int getViewId() {
        return viewId;
    }

    public static Comparator<noticeModule> sort = new Comparator<noticeModule>() {
        @Override
        public int compare(noticeModule o1, noticeModule o2) {
            if(o1.getHash()>o2.getHash()) return 1;
            else if (o1.getHash()<o2.getHash()) return  -1;
            else return 0;
        }
    };
}

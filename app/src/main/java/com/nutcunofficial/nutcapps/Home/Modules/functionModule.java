package com.nutcunofficial.nutcapps.Home.Modules;

public class functionModule {

    private String desc;
    private int drawbleId;

    public functionModule(int drawbleId,String desc){
        this.desc = desc;
        this.drawbleId=drawbleId;
    }

    public String getDescription() {
        return desc;
    }

    public int getDrawbleId() {
        return drawbleId;
    }

    public void setDescription(String desc) {
        this.desc = desc;
    }

    public void setDrawbleId(int drawbleId) {
        this.drawbleId = drawbleId;
    }
}

package com.atguigu.mybatis.bean;

public class Subject {
    private Integer sId;
    private String sName;
    private String sDirector;
    private String sActor;
    private Integer picId;
    private Float sRate;
    private String sDate;
    private Integer typeId;
    private String sContent;
    private Integer sHit;

    public Integer getsId() {
        return sId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsDirector() {
        return sDirector;
    }

    public void setsDirector(String sDirector) {
        this.sDirector = sDirector;
    }

    public String getsActor() {
        return sActor;
    }

    public void setsActor(String sActor) {
        this.sActor = sActor;
    }

    public Integer getPicId() {
        return picId;
    }

    public void setPicId(Integer picId) {
        this.picId = picId;
    }

    public Float getsRate() {
        return sRate;
    }

    public void setsRate(Float sRate) {
        this.sRate = sRate;
    }

    public String getsDate() {
        return sDate;
    }

    public void setsDate(String sDate) {
        this.sDate = sDate;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getsContent() {
        return sContent;
    }

    public void setsContent(String sContent) {
        this.sContent = sContent;
    }

    public Integer getsHit() {
        return sHit;
    }

    public void setsHit(Integer sHit) {
        this.sHit = sHit;
    }
}

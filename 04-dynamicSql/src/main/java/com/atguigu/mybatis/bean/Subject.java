package com.atguigu.mybatis.bean;

public class Subject {
    private Integer sId;
    private String sName;
    private String sDirector;
    private String sActor;
    private String picSrc;
    private Float sRate;
    private String sDate;
    private Type type;
    private Integer typeId;
    private String sContent;
    private Integer sHit;

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public void setsId(Integer sId) {
        this.sId = sId;
    }

    public Integer getsId() {
        return sId;
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

    public String getPicSrc() {
        return picSrc;
    }

    public void setPicSrc(String picSrc) {
        this.picSrc = picSrc;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
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

    @Override
    public String toString() {
        return "Subject{" +
                "sId=" + sId +
                ", sName='" + sName + '\'' +
                ", sDirector='" + sDirector + '\'' +
                ", sActor='" + sActor + '\'' +
                ", picSrc='" + picSrc + '\'' +
                ", sRate=" + sRate +
                ", sDate='" + sDate + '\'' +
                ", type=" + type +
                ", sContent='" + sContent + '\'' +
                ", sHit=" + sHit +
                '}';
    }
}

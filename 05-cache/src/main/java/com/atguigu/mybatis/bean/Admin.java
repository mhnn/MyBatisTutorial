package com.atguigu.mybatis.bean;

import java.io.Serializable;

public class Admin implements Serializable {
    private Integer aId;
    private String aName;
    private String aPassword;

    public Admin() {
        super();
    }

    public Admin(String aName, String aPassword) {
        this.aName = aName;
        this.aPassword = aPassword;
    }

    public Admin(Integer aId, String aName, String aPassword) {
        this.aId = aId;
        this.aName = aName;
        this.aPassword = aPassword;
    }

    public Integer getaId() {
        return aId;
    }

    public void setaId(Integer aId) {
        this.aId = aId;
    }

    public String getaName() {
        return aName;
    }

    public void setaName(String aName) {
        this.aName = aName;
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "aId=" + aId +
                ", aName='" + aName + '\'' +
                ", aPassword='" + aPassword + '\'' +
                '}';
    }
}

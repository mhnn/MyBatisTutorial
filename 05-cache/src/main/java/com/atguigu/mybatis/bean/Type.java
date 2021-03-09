package com.atguigu.mybatis.bean;

import java.io.Serializable;
import java.util.List;

public class Type implements Serializable {
    private Integer typeId;
    private String typeName;
    private List<Subject> subs;


    public List<Subject> getSubs() {
        return subs;
    }

    public void setSubs(List<Subject> subs) {
        this.subs = subs;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    @Override
    public String toString() {
        return "Type{" +
                "typeId=" + typeId +
                ", typeName='" + typeName + '\'' +
                '}';
    }
}

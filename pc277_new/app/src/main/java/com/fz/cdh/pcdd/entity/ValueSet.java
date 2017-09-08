package com.fz.cdh.pcdd.entity;

import java.io.Serializable;

/**
 * description 值集
 * Created by xiaoge
 */
public class ValueSet implements Serializable {

    private String typeName;
    private String sysType;
    private String typeCode;
    private String typeId;
    private boolean isChoosed;//是否选中

    public ValueSet() {

    }

    public ValueSet(String typeName) {
        this.typeName = typeName;
    }

    public ValueSet(String typeCode, String typeName) {
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public ValueSet(String typeId, String typeCode, String typeName) {
        this.typeId = typeId;
        this.typeCode = typeCode;
        this.typeName = typeName;
    }

    public String getTypeName() {
        return typeName;
    }

    public String getSysType() {
        return sysType;
    }

    public String getTypeCode() {
        return typeCode;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public void setSysType(String sysType) {
        this.sysType = sysType;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public boolean isChoosed() {
        return isChoosed;
    }

    public void setChoosed(boolean choosed) {
        isChoosed = choosed;
    }
}

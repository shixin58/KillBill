package com.bride.baselib.bean;

/**
 * <p>Created by shixin on 2018/9/20.
 */
public class PhoneNumberModel {
    public String province;
    public String city;
    public String areacode;
    public String zip;
    public String company;

    @Override
    public String toString() {
        return getClass().getSimpleName()+"[province="+this.province+", city="+this.city
                +", areacode="+this.areacode+", zip="+this.zip+", company="+this.company+"]";
    }
}

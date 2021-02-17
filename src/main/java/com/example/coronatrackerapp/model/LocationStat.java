package com.example.coronatrackerapp.model;

public class LocationStat {
    //field for storing name of province
    private String province;
    // field for storing name of country
    private String country;
    //field for storing total number of cases
    private int totalNum;
    //field for storing difference between current number of cases an the one of yesterday
    private int diffWithPrevDay;

    public LocationStat() {
    }

    public int getDiffWithPrevDay() {
        return diffWithPrevDay;
    }

    public void setDiffWithPrevDay(int diffWithPrevDay) {
        this.diffWithPrevDay = diffWithPrevDay;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    @Override
    public String toString() {
        return "LocationStat{" +
                "province='" + province + '\'' +
                ", country='" + country + '\'' +
                ", totalNum=" + totalNum +
                '}';
    }
}

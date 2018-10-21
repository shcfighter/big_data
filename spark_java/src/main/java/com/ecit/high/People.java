package com.ecit.high;

import java.io.Serializable;

public class People implements Serializable {
    String sex;
    int high;

    public People(String sex, int high) {
        this.sex = sex;
        this.high = high;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getHigh() {
        return high;
    }

    public void setHigh(int high) {
        this.high = high;
    }

    @Override
    public String toString() {
        return "People{" +
                "sex='" + sex + '\'' +
                ", high=" + high +
                '}';
    }
}

package com.demo3.fruit.domain;

import javax.annotation.processing.Generated;
import java.util.Objects;

public class Fruit {

    private int fid;
    private String fname;
    private double price;
    private int fcount;
    private String remark;

    public Fruit() {
    }

    public Fruit(int fid, String fname, double price, int fcount, String remark) {
        this.fid = fid;
        this.fname = fname;
        this.price = price;
        this.fcount = fcount;
        this.remark = remark;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getFcount() {
        return fcount;
    }

    public void setFcount(int fcount) {
        this.fcount = fcount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Fruit fruit = (Fruit) o;
        return fid == fruit.fid && Double.compare(fruit.price, price) == 0 && fcount == fruit.fcount && Objects.equals(fname, fruit.fname) && Objects.equals(remark, fruit.remark);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fid, fname, price, fcount, remark);
    }

    @Override
    public String toString() {
        return "Fruit{" +
                "fid=" + fid +
                ", fname='" + fname + '\'' +
                ", price=" + price +
                ", fcount=" + fcount +
                ", remark='" + remark + '\'' +
                '}';
    }
}

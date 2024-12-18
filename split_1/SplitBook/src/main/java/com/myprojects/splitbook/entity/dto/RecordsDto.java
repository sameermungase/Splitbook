package com.myprojects.splitbook.entity.dto;

import java.time.LocalDate;

public class RecordsDto {

    private LocalDate date;
    private String description;
    private String info;
    private int cid;        //Contribution id
    private int tid;        //Trip id

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    @Override
    public String toString() {
        return getDate().toString()+" - "+getDescription()+" - "+getInfo();
    }
}

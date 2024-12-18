package com.myprojects.splitbook.entity.dto;

public class SettlementsDto {

    private String info;
    private float amount;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public SettlementsDto()
    {
        super();
    }

    public SettlementsDto(String info, float amount) {
        this.info = info;
        this.amount = amount;
    }

    @Override
    public String toString() {
        return info + " : " + amount;
    }
}

package com.myprojects.splitbook.entity.dto;

public class Amount implements Comparable<Amount>{

    private int id;     //memberId
    private float intAmount;      //credit-debit

    public Amount(int id, float intAmount) {
        this.id = id;
        this.intAmount = intAmount;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public float getIntAmount() {
        return intAmount;
    }
    public void setIntAmount(float intAmount) {
        this.intAmount = intAmount;
    }

    @Override
    public int compareTo(Amount o) {
        if(this.getIntAmount()==o.getIntAmount())
        {
            return Integer.compare(this.getId(),o.getId());
        }
        return Float.compare(this.getIntAmount(),o.getIntAmount());
    }
}

package com.example.source.factory;

public class DeliveryInfoModel {
    private String brandName;
    private Integer amountOrder;
    private Integer commissionPercent;
    private Double totalCommission;
    private String phone;

    public DeliveryInfoModel(String brandName,Integer amountOrder, Integer commissionPercent, Double totalCommission, String phone) {
        this.brandName = brandName;
        this.amountOrder = amountOrder;
        this.commissionPercent = commissionPercent;
        this.totalCommission = totalCommission;
        this.phone = phone;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public Integer getAmountOrder() {
        return amountOrder;
    }

    public void setAmountOrder(Integer amountOrder) {
        this.amountOrder = amountOrder;
    }

    public Integer getCommissionPercent() {
        return commissionPercent;
    }

    public void setCommissionPercent(Integer commissionPercent) {
        this.commissionPercent = commissionPercent;
    }

    public Double getTotalCommission() {
        return totalCommission;
    }

    public void setTotalCommission(Double totalCommission) {
        this.totalCommission = totalCommission;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

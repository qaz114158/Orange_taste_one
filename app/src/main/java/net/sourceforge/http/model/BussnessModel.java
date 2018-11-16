package net.sourceforge.http.model;

public class BussnessModel {

    public int coinType;

    public String orderNum;

    public String dealNum;

    public String tranTime;

    public String count;

    public String min;

    public String balance;

    public String totalCount;

    public BussnessModel() {

    }

    public BussnessModel(int coinType, String orderNum, String dealNum, String tranTime, String count, String min, String balance, String totalCount) {
        this.coinType = coinType;
        this.orderNum = orderNum;
        this.dealNum = dealNum;
        this.tranTime = tranTime;
        this.count = count;
        this.min = min;
        this.balance = balance;
        this.totalCount = totalCount;
    }
}

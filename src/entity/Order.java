package entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "order")
@Table(name = "order")
public class Order {
    @Id
    @Column(name = "oid")
    private String orderId;
    @Column(name = "cid")
    //@OneToMany(mappedBy = "order")
    private String CId;
    @Column(name = "orderDate")
    private LocalDate orderDate;
    @Column(name = "orderTime")
    private String orderTime;
    @Column(name = "cost")
    private double cost;

    public Order() {
    }

    public Order(String orderId, String CId, LocalDate orderDate, String orderTime, double cost) {
        this.orderId = orderId;
        this.CId = CId;
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.cost = cost;
    }

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> orderList = new ArrayList<>();

    public Order(String orderId, String CId, LocalDate orderDate, String orderTime, double cost, List<OrderDetail> orderList) {
        this.setOrderId(orderId);
        this.setCId(CId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCost(cost);
        this.setOrderList(orderList);
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCId() {
        return CId;
    }

    public void setCId(String CId) {
        this.CId = CId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public List<OrderDetail> getOrderList() {
        return orderList;
    }

    public void setOrderList(List<OrderDetail> orderList) {
        this.orderList = orderList;
    }
}

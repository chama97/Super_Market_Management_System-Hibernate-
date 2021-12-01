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

    @OneToMany(mappedBy = "order")
    private List<OrderDetail> petList = new ArrayList<>();


    public Order() {
    }
    public Order(String orderId, String CId, LocalDate orderDate, String orderTime, double cost, List<OrderDetail> petList) {
        this.setOrderId(orderId);
        this.setCId(CId);
        this.setOrderDate(orderDate);
        this.setOrderTime(orderTime);
        this.setCost(cost);
        this.setPetList(petList);
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

    public List<OrderDetail> getPetList() {
        return petList;
    }

    public void setPetList(List<OrderDetail> petList) {
        this.petList = petList;
    }
}

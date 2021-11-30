package entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity(name = "order")
@Table(name = "user")
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
        this.setOrderDate(orderDate);
        this.orderTime = orderTime;
        this.cost = cost;
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

    public void setCId(String cId) {
        this.CId = cId;
    }

    public String getOrderTime() { return orderTime; }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public String toString() {
        return "order{" +
                "orderId='" + orderId + '\'' +
                ", cId='" + CId + '\'' +
                ", orderDate=" + getOrderDate() +
                ", orderTime='" + orderTime + '\'' +
                ", cost=" + cost +
                '}';
    }
}

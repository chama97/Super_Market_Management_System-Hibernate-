package entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.security.acl.Owner;

@Entity
@Table(name = "order")
public class OrderDetail {
    @Id
    private String itemCode;
    private String orderId;
    private int qty;
    private double price;

    @ManyToOne
    private Order order;

    public OrderDetail() {
    }

    public OrderDetail(String itemCode, String orderId, int qty, double price) {
        this.setItemCode(itemCode);
        this.setOrderId(orderId);
        this.setQty(qty);
        this.setPrice(price);
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "orderDetail{" +
                "itemCode='" + itemCode + '\'' +
                ", orderId='" + orderId + '\'' +
                ", qty=" + qty +
                ", price=" + price +
                '}';
    }
}

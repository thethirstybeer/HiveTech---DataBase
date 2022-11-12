package JDBC;

import java.sql.Date;

public class Orders {
    private int orderNumber;
    private java.sql.Date orderDate;
    private java.sql.Date requiredDate;
    private java.sql.Date shippedDate;
    private String status;
    private String comments;
    private int customerNumber;


    public Orders(int orderNumber, Date orderDate, Date requiredDate, Date shippedDate, String status, String comments, int customerNumber) {
        this.orderNumber = orderNumber;
        this.orderDate = orderDate;
        this.requiredDate = requiredDate;
        this.shippedDate = shippedDate;
        this.status = status;
        this.comments = comments;
        this.customerNumber = customerNumber;
    }

    public String toString() {
        return String.format("Orders [OrderNumber: %d, OrderDate: %tc, RequiredDate: %tc, ShippedDate: %tc, Status: %s, Comments: %s, CustomerNumber: %d]", orderNumber, orderDate, requiredDate, shippedDate, status, comments, customerNumber);
    }
}

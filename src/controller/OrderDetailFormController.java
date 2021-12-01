package controller;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import entity.OrderDetail;
import util.factory.OrderDetailDAO;
import view.tm.OrderDetailTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailFormController {
    public AnchorPane orderDetail;
    public TableView<OrderDetailTM> tblOrderDetail;
    public TableColumn colItemCod;
    public TableColumn colOrderId;
    public TableColumn colQty;
    public TableColumn colPrice;
   // private final OrderDetailsDAO orderDAO = (OrderDetailsDAO) DAOFactory.getDAOFactory().getDAO(DAOFactory.DAOTypes.ORDERDETAILS);

    public void initialize() {
        colItemCod.setCellValueFactory(new PropertyValueFactory<>("itemCode"));
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        loadAllOrderDetails();
    }

    private void loadAllOrderDetails() {
        tblOrderDetail.getItems().clear();
            List<OrderDetail> allOrders = OrderDetailDAO.getOrderDetail();
            for (OrderDetail order : allOrders) {
                tblOrderDetail.getItems().add(new OrderDetailTM(order.getItemCode(),order.getOrderId(),  order.getQty(), order.getPrice()));
            }
    }

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)orderDetail.getScene().getWindow();
        window.setScene(new Scene(load));
    }
}

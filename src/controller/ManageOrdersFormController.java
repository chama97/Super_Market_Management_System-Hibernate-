package controller;

import com.jfoenix.controls.JFXButton;
import entity.Order;
import javafx.event.ActionEvent;
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
import util.factory.OrderDAO;
import view.tm.OrderTM;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageOrdersFormController {
    public AnchorPane manageOrder;
    public TableView<OrderTM> tblOrders;
    public TableColumn colOrderId;
    public TableColumn colCstId;
    public TableColumn colDate;
    public TableColumn colTime;
    public TableColumn colCost;

    public JFXButton btnUpdate;
    public JFXButton btnDelete;

    public void initialize() {
        colOrderId.setCellValueFactory(new PropertyValueFactory<>("orderId"));
        colCstId.setCellValueFactory(new PropertyValueFactory<>("CId"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTime.setCellValueFactory(new PropertyValueFactory<>("orderTime"));
        colCost.setCellValueFactory(new PropertyValueFactory<>("cost"));

        initUI();
        tblOrders.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            btnDelete.setDisable(newValue == null);
            btnUpdate.setDisable(newValue == null);
        });
        loadAllOrders();
    }

    private void loadAllOrders() {
        tblOrders.getItems().clear();
            List<Order> allOrders = OrderDAO.getOrder();
            for (Order order : allOrders) {
                tblOrders.getItems().add(new OrderTM(order.getOrderId(), order.getCId(), order.getOrderDate(), order.getOrderTime(), order.getCost()));
            }
    }

    private void initUI() {
        btnDelete.setDisable(true);
        btnUpdate.setDisable(true);
    }

    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)manageOrder.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void UpdateOrderOnAction(ActionEvent actionEvent) {
        initUI();
    }

    public void deleteOrderOnAction(ActionEvent actionEvent) {
        String id = tblOrders.getSelectionModel().getSelectedItem().getOrderId();
            OrderDAO.DeleteOrder(id);
            tblOrders.getItems().remove(tblOrders.getSelectionModel().getSelectedItem());
            tblOrders.getSelectionModel().clearSelection();
            new Alert(Alert.AlertType.CONFIRMATION, "Deleted Successfully " + id).show();
            initUI();
    }

   /* boolean existOrder(String id) throws SQLException, ClassNotFoundException {
        return orderDAO.ifOrderExist(id);
    }*/

}

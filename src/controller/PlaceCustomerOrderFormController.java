package controller;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entity.Customer;
import entity.Item;
import entity.Order;
import entity.OrderDetail;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.FactoryConfiguration;
import util.factory.CustomerDAO;
import util.factory.ItemDAO;
import util.factory.OrderDAO;
import view.tm.CartTM;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PlaceCustomerOrderFormController {
    public AnchorPane placeOrder;
    public TableView<CartTM> tblCart;
    public TableColumn colCode;
    public TableColumn colTotal;
    public TableColumn colUnitPrice;
    public ComboBox<String> cmbCustomerIds;
    public ComboBox<String> cmbItemIds;
    public TextField txtName;
    public TextField txtDescription;
    public TextField txtAddress;
    public TextField txtQtyOnHand;
    public TextField txtUnitPrice;
    public TextField txtQty;
    public Label lblOrderId;
    public Label lblDate;
    public Label lblTime;
    public Label lblTotalIncome;
    public TextField txtPostalCode;
    public TextField txtProvince;
    public JFXButton btnAddCart;
    public JFXButton btnClear;
    public JFXButton btnPlace;
    public TableColumn colDescription;
    public TableColumn colQty;
    public TableColumn colDelete;
    private String orderId;
   // private final PurchaseOrderBO purchaseOrderBO = (PurchaseOrderBO) BoFactory.getBOFactory().getBO(BoFactory.BoTypes.PURCHASE_ORDER);

    public void initialize() {

        colCode.setCellValueFactory(new PropertyValueFactory<>("code"));
        colDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        colUnitPrice.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("total"));
        TableColumn<CartTM, FontAwesomeIconView> lastCol = (TableColumn<CartTM, FontAwesomeIconView>) tblCart.getColumns().get(5);

        lastCol.setCellValueFactory(param -> {
            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);

            deleteIcon.setStyle(
                    " -fx-cursor: hand ;"
                            + "-glyph-size:27px;"
                            + "-fx-fill:#ff1744;"
            );
            deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                tblCart.getItems().remove(param.getValue());
                tblCart.getSelectionModel().clearSelection();
                calculateTotal();
                enableOrDisablePlaceOrderButton();
            });
            return new ReadOnlyObjectWrapper(deleteIcon);
        });

        loadDateAndTime();
        loadAllCustomerIds();
        loadAllItemCodes();
        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);

        cmbCustomerIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            enableOrDisablePlaceOrderButton();
            if (newValue != null) {
                /*if (!existCustomer(newValue + "")) {
                    new Alert(Alert.AlertType.ERROR, "There is no such customer associated with the id " + newValue + "").show();
                }*/
                Customer customerDTO = CustomerDAO.searchCustomer(newValue + "");
                txtName.setText(customerDTO.getName());
                txtAddress.setText(customerDTO.getAddress());
                txtProvince.setText(customerDTO.getProvince());
                txtPostalCode.setText(customerDTO.getPostalCode());

            } else {
                txtName.clear();
                txtAddress.clear();
                txtProvince.clear();
                txtPostalCode.clear();
            }
        });

        cmbItemIds.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newItemCode) -> {
            txtQty.setEditable(newItemCode != null);
            btnAddCart.setDisable(newItemCode == null);
            if (newItemCode != null) {
                /*try {
                    if (!existItem(newItemCode + "")) {
                    }*/
                    Item item = ItemDAO.searchItem(newItemCode + "");
                    txtDescription.setText(item.getDescription());
                    txtUnitPrice.setText(String.valueOf(item.getUnitPrice()));
                    Optional<CartTM> optOrderDetail = tblCart.getItems().stream().filter(detail -> detail.getCode().equals(newItemCode)).findFirst();
                    txtQtyOnHand.setText((optOrderDetail.isPresent() ? item.getQtyOnHand() - optOrderDetail.get().getQty() : item.getQtyOnHand()) + "");

                /*} catch (SQLException throwables) {
                    throwables.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }*/
            } else {
                txtDescription.clear();
                txtQty.clear();
                txtQtyOnHand.clear();
                txtUnitPrice.clear();
            }
        });
    }

   /* private boolean existItem(String code) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifItemExist(code);
    }

    boolean existCustomer(String id) throws SQLException, ClassNotFoundException {
        return purchaseOrderBO.ifCustomerExist(id);
    }*/

    public String generateNewOrderId() {

      //  try {
            System.out.println("O-002");
       /*     return OrderDA.generateNewOrderId();
        } catch (SQLException e) {
            new Alert(Alert.AlertType.ERROR, "Failed to generate a new order id").show();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    private void enableOrDisablePlaceOrderButton() {
        btnPlace.setDisable(!(cmbCustomerIds.getSelectionModel().getSelectedItem() != null && !tblCart.getItems().isEmpty()));
    }

    private void loadAllCustomerIds() {
            List<Customer> all = CustomerDAO.getCustomers();
            for (Customer customerDTO : all) {
                cmbCustomerIds.getItems().add(customerDTO.getId());
            }
    }

    private void loadAllItemCodes() {
            List<Item> all = ItemDAO.getItems();
            for (Item dto : all) {
                cmbItemIds.getItems().add(dto.getCode());
            }
    }

    private void loadDateAndTime() {

        Date date = new Date();
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(f.format(date));

        Timeline time = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            LocalTime currentTime = LocalTime.now();
            lblTime.setText(
                    currentTime.getHour() + " : " + currentTime.getMinute() +
                            " : " + currentTime.getSecond()
            );
        }),
                new KeyFrame(Duration.seconds(1))
        );
        time.setCycleCount(Animation.INDEFINITE);
        time.play();
    }


    public void closeWindowOnAction(MouseEvent mouseEvent) throws IOException {
        URL resource  = (getClass().getResource("../view/DashBoardForm.fxml"));
        Parent load = FXMLLoader.load(resource);
        Stage window = (Stage)placeOrder.getScene().getWindow();
        window.setScene(new Scene(load));
    }

    public void addToCartOnAction(ActionEvent actionEvent) {
        if (!txtQty.getText().matches("\\d+") || Integer.parseInt(txtQty.getText()) <= 0 ||
                Integer.parseInt(txtQty.getText()) > Integer.parseInt(txtQtyOnHand.getText())) {
            new Alert(Alert.AlertType.ERROR, "Invalid qty").show();
            txtQty.requestFocus();
            txtQty.selectAll();
            return;
        }

        String itemCode = cmbItemIds.getSelectionModel().getSelectedItem();
        String description = txtDescription.getText();
        double unitPrice = Double.parseDouble(txtUnitPrice.getText());
        int qty = Integer.parseInt(txtQty.getText());
        double total = qty * unitPrice;

        boolean exists = tblCart.getItems().stream().anyMatch(detail -> detail.getCode().equals(itemCode));

        if (exists) {
            CartTM orderDetailTM = tblCart.getItems().stream().filter(detail -> detail.getCode().equals(itemCode)).findFirst().get();

            if (btnAddCart.getText().equalsIgnoreCase("Update")) {
                orderDetailTM.setQty(qty);
                orderDetailTM.setTotal(total);
                tblCart.getSelectionModel().clearSelection();
            } else {
                orderDetailTM.setQty(orderDetailTM.getQty() + qty);
                total = orderDetailTM.getQty() * unitPrice;
                orderDetailTM.setTotal(total);
            }
            tblCart.refresh();
        } else {
            tblCart.getItems().add(new CartTM(itemCode, description, unitPrice, qty, total));
        }
        cmbItemIds.getSelectionModel().clearSelection();
        cmbItemIds.requestFocus();
        calculateTotal();
        enableOrDisablePlaceOrderButton();
    }

    private void calculateTotal() {
        BigDecimal total = new BigDecimal(0);

        for (CartTM detail : tblCart.getItems()) {
            total = total.add(BigDecimal.valueOf(detail.getTotal()));
        }
        lblTotalIncome.setText(String.valueOf(total));
    }


    public void clearOnAction(ActionEvent actionEvent) {
    }

    public void placeOrderOnAction(ActionEvent actionEvent) {
        boolean b = saveOrder(orderId, cmbCustomerIds.getValue(), LocalDate.parse(lblDate.getText()), lblTime.getText(), Double.parseDouble(lblTotalIncome.getText()),
                tblCart.getItems().stream().map(tm -> new OrderDetail(tm.getCode(), orderId , tm.getQty(), tm.getUnitPrice())).collect(Collectors.toList()));
        if (b) {
            new Alert(Alert.AlertType.INFORMATION, "Order has been placed successfully").show();
        } else {
            new Alert(Alert.AlertType.ERROR, "Order has not been placed successfully").show();
        }

        orderId = generateNewOrderId();
        lblOrderId.setText(orderId);
        cmbCustomerIds.getSelectionModel().clearSelection();
        cmbItemIds.getSelectionModel().clearSelection();
        tblCart.getItems().clear();
        txtQty.clear();
        calculateTotal();
    }

    public boolean saveOrder(String orderId, String customerId, LocalDate orderDate,  String time, double cost, List<OrderDetail> orderDetails) {
        try {
            Order orderDTO = new Order(orderId, customerId, orderDate, time, cost, orderDetails);
            return OrderDAO.purchaseOrder(orderDTO);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Item findItem(String code) {
        return ItemDAO.searchItem(code);

    }

    public void printOnAction(MouseEvent mouseEvent) {
    }
}

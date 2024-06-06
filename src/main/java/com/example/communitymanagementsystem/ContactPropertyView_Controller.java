package com.example.communitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.ArrayList;

public class ContactPropertyView_Controller {
    public TableView<Property_Request> tableView_myRecord;
    public TableColumn<Property_Request, Integer> tableColumn_requestID;
    public TableColumn<Property_Request, String> tableColumn_description;
    public TableColumn<Property_Request, String> tableColumn_phone;
    public TableColumn<Property_Request, Date> endTime;
    public TableColumn<Property_Request, Date> startTime;
    public TableColumn<Property_Request, String> status;
    public TextField textField_description;
    public TextField textField_phone;
    public Button button_commitRequest;

    public void initialize() {
        ArrayList<Property_Request> propertyRequestArrayList = MySQL_ConnUtils.getInstance().getPropertyRequestByCurrentUser();
        tableColumn_requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        tableColumn_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumn_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        tableView_myRecord.setItems(FXCollections.observableArrayList(propertyRequestArrayList));
    }

    public void buttonAction_commitRequest(ActionEvent actionEvent) {
        String description = textField_description.getText();
        String phone = textField_phone.getText();
        if (description.isEmpty() || phone.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("提交失败");
            alert.setHeaderText("请填写完整信息");
            alert.showAndWait();
            return;
        }
        if(MySQL_ConnUtils.getInstance().addPropertyRequest(description, phone)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("提交成功");
            alert.setHeaderText("您的请求已提交，等待管理员审核");
            alert.showAndWait();
            refreshTable();
        }
    }
    public void refreshTable() {
        ArrayList<Property_Request> propertyRequestArrayList = MySQL_ConnUtils.getInstance().getPropertyRequestByCurrentUser();
        tableView_myRecord.getItems().clear();
        tableView_myRecord.setItems(FXCollections.observableArrayList(propertyRequestArrayList));
    }
}

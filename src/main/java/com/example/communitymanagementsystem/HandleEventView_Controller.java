package com.example.communitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.sql.Date;
import java.util.ArrayList;

/**
 * @author uncle_yumo
 * @CreateDate 2024/6/6
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class HandleEventView_Controller {
    public TableView<Property_Request> tableView_allUserRecord;
    public TableColumn<Property_Request, Integer> tableColumn_requestID;
    public TableColumn<Property_Request, String> tableColumn_username;
    public TableColumn<Property_Request, String> tableColumn_description;
    public TableColumn<Property_Request, String> tableColumn_phone;
    public TableColumn<Property_Request, Date> tableColumn_endTime;
    public TableColumn<Property_Request, Date> tableColumn_startTime;
    public TableColumn<Property_Request, String> tableColumn_status;
    public Button button_confirmEvent;

    public void initialize() {
        ArrayList<Property_Request> propertyRequestArrayList = MySQL_ConnUtils.getInstance().getAllPropertyRequest();
        tableView_allUserRecord.getItems().clear();
        tableView_allUserRecord.getItems().addAll(propertyRequestArrayList);
        tableColumn_requestID.setCellValueFactory(new PropertyValueFactory<>("requestID"));
        tableColumn_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        tableColumn_description.setCellValueFactory(new PropertyValueFactory<>("description"));
        tableColumn_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        tableColumn_endTime.setCellValueFactory(new PropertyValueFactory<>("endTime"));
        tableColumn_startTime.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        tableColumn_status.setCellValueFactory(new PropertyValueFactory<>("status"));

        tableView_allUserRecord.getItems().clear();
        tableView_allUserRecord.getItems().addAll(propertyRequestArrayList);
    }

    public void buttonAction_confirmEvent(ActionEvent actionEvent) {
        // 获取选中的行
        Property_Request propertyRequest = tableView_allUserRecord.getSelectionModel().getSelectedItem();
        if (propertyRequest == null) {
            return;
        }
        // 确认事件
        MySQL_ConnUtils.getInstance().confirmEventByRequestID(propertyRequest.getRequestID());
        // 刷新表格
        refreshTable();
    }

    public void refreshTable() {
        ArrayList<Property_Request> propertyRequestArrayList = MySQL_ConnUtils.getInstance().getAllPropertyRequest();
        tableView_allUserRecord.getItems().clear();
        tableView_allUserRecord.getItems().addAll(propertyRequestArrayList);
    }
}

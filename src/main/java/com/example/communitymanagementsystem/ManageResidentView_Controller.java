package com.example.communitymanagementsystem;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.ArrayList;

/**
 * @author uncle_yumo
 * @CreateDate 2024/6/6
 * @School 无锡学院
 * @StudentID 22344131
 * @Description
 */
public class ManageResidentView_Controller {
    public TableView<User> tableView_user;
    public TableColumn<User, Integer> tableColum_id;
    public TableColumn<User, String> tableColum_username;
    public TableColumn<User, String> tableColum_password;
    public TableColumn<User, String> tableColum_role;
    public TextField textField_username;
    public ComboBox<String> comboBox_role;
    public TextField textField_password;
    public Button button_addUser;
    public Button button_delUser;
    public Button button_modifyUser;
    public Button button_queryUser;

    public User user;

    public void initialize() {
        user = MySQL_ConnUtils.getInstance().getCurrentUser();
        // 刷新表格，获取所有用户信息
        ArrayList<User> userArrayList = MySQL_ConnUtils.getInstance().getAllUser();
        tableView_user.getItems().clear();
        tableColum_id.setCellValueFactory(new PropertyValueFactory<>("userId"));
        tableColum_username.setCellValueFactory(new PropertyValueFactory<>("userName"));
        tableColum_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        tableColum_role.setCellValueFactory(new PropertyValueFactory<>("userRole"));

        tableView_user.getItems().clear();
        tableView_user.setItems(FXCollections.observableArrayList(userArrayList));

        // 设置ComboBox选项
        comboBox_role.getItems().addAll("管理员", "居民");
    }

    public void buttonAction_addUser(ActionEvent actionEvent) {
        String username = textField_username.getText();
        String password = textField_password.getText();
        String role = comboBox_role.getSelectionModel().getSelectedItem();
        if(username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请输入完整信息！");
            alert.showAndWait();
            return;
        }
        if(role.equals("管理员")) {
            role = "admin";
        }else if(role.equals("居民")) {
            role = "resident";
        }
        User newUser = new User(username, password, role);
        if(MySQL_ConnUtils.getInstance().addUser(newUser)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("成功");
            alert.setHeaderText("添加用户成功！");
            alert.showAndWait();
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("添加用户失败！");
            alert.showAndWait();
        }
        textField_username.clear();
        textField_password.clear();
        comboBox_role.getSelectionModel().clearSelection();
        refreshTable();
    }

    public void buttonAction_delUser(ActionEvent actionEvent) {
        // 获取选中的行
        User selectedUser = tableView_user.getSelectionModel().getSelectedItem();
        if(selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请先选择用户！");
            alert.showAndWait();
            return;
        }
        if(MySQL_ConnUtils.getInstance().delUserByUserName(selectedUser.getUserName())) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("成功");
            alert.setHeaderText("删除用户成功！");
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("删除用户失败！");
        }
        refreshTable();
    }

    public void buttonAction_modifyUser(ActionEvent actionEvent) {
        // 获取选中的行
        User selectedUser = tableView_user.getSelectionModel().getSelectedItem();
        if(selectedUser == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请先选择用户！");
            alert.showAndWait();
            return;
        }
        String username = textField_username.getText();
        String password = textField_password.getText();
        String role = comboBox_role.getSelectionModel().getSelectedItem();
        if(username.isEmpty() || password.isEmpty() || role.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请输入完整信息！");
            alert.showAndWait();
            return;
        }
        if(role.equals("管理员")) {
            role = "admin";
        }else if(role.equals("居民")) {
            role = "resident";
        }
        User newUser = new User(selectedUser.getUserId(), username, password, role);
        if(MySQL_ConnUtils.getInstance().modifyUser(newUser)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("成功");
            alert.setHeaderText("修改用户成功！");
        }else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("修改用户失败！");
        }
        textField_username.clear();
        textField_password.clear();
        comboBox_role.getSelectionModel().clearSelection();
        refreshTable();
    }

    public void buttonAction_queryUser(ActionEvent actionEvent) {
        // 根据用户姓名模糊查询
        String username = textField_username.getText();
        if(username.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("错误");
            alert.setHeaderText("请输入用户名！");
            alert.showAndWait();
            return;
        }
        ArrayList<User> userArrayList = MySQL_ConnUtils.getInstance().queryUserByUserName(username);
        tableView_user.getItems().clear();
        tableView_user.setItems(FXCollections.observableArrayList(userArrayList));
        textField_username.clear();
    }

    public void refreshTable() {
        ArrayList<User> userArrayList = MySQL_ConnUtils.getInstance().getAllUser();
        tableView_user.getItems().clear();
        tableView_user.setItems(FXCollections.observableArrayList(userArrayList));
    }
}

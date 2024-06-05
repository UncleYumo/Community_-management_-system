package com.example.communitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

import java.io.IOException;

public class MainView_Controller {

    public Button button_login;
    public Button button_contactToProperty;
    public Button button_mangeResident;
    public Button button_handleEvent;
    public Label label_title;
    public Pane mainPane;

    public static MainView_Controller instance;

    public void initialize() {
        instance = this;
        // 设置标题
        label_title.setText("社区管理系统（请登录）");
        // 设置mainPane的背景颜色，淡黄色
        mainPane.setStyle("-fx-background-color: #ffffea;");
        // 登录前禁用除登录按钮以外的其他按钮
        button_contactToProperty.setDisable(true);
        button_mangeResident.setDisable(true);
        button_handleEvent.setDisable(true);
    }

    public void buttonAction_login(ActionEvent actionEvent) throws IOException {
        label_title.setText("欢迎业主回家");
        // 正确地创建FXMLLoader并加载登录界面，注意变量名一致性
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        // 替换mainPane中的内容为加载的FXML文件所指定的界面
        mainPane.getChildren().setAll((Pane) fxmlLoader.load());
    }

    public void buttonAction_contactToProperty(ActionEvent actionEvent) throws IOException {
        label_title.setText("物业联系界面");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("contact-property-view.fxml"));
        mainPane.getChildren().setAll((Pane) fxmlLoader.load());
    }

    public void buttonAction_mangeResident(ActionEvent actionEvent) throws IOException {
        label_title.setText("居民管理界面（管理员）");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("manage-resident-view.fxml"));
        mainPane.getChildren().setAll((Pane) fxmlLoader.load());
    }
    public void buttonAction_handEvent(ActionEvent actionEvent) throws IOException {
        label_title.setText("物业处理界面（管理员）");
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("handle-event-view.fxml"));
        mainPane.getChildren().setAll((Pane) fxmlLoader.load());
    }

    public void login_success(User user) {
        if (user.getUserRole().equals("resident")) {
            // 居民权限
            button_contactToProperty.setDisable(false);
            // 限制重复登录
            button_login.setDisable(true);
        } else if (user.getUserRole().equals("admin")) {
            // 开放管理员权限
            button_contactToProperty.setDisable(false);
            button_mangeResident.setDisable(false);
            button_handleEvent.setDisable(false);
            // 限制重复登录
            button_login.setDisable(true);
        }
    }
}

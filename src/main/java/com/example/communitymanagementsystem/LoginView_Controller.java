package com.example.communitymanagementsystem;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginView_Controller {

    public Button button_login;
    public TextField textField_username;
    public TextField textField_password;

    public static boolean isLogin = false;

    public User user = new User();

    public void buttonAction_login(ActionEvent actionEvent) {
        String username = textField_username.getText();
        String password = textField_password.getText();
        user = MySQL_ConnUtils.getInstance().Login(username, password);
        if (user != null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("登录成功！");
            alert.setHeaderText("欢迎回来，" + user.getUserName() + "！您的权限是：" + user.userRoleToString());
            alert.showAndWait();
            isLogin = true;
            button_login.setDisable(true);
            MainView_Controller.instance.mainPane.setStyle("-fx-background-color: #e8fbdd;");
            MainView_Controller.instance.login_success(user);
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("登录失败！");
            alert.setHeaderText("用户名或密码错误！");
            alert.showAndWait();
        }
    }
}

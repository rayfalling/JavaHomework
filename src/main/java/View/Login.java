package View;

import com.jfoenix.controls.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class Login implements Initializable {

    @FXML
    private JFXButton login;

    @FXML
    private JFXTextField username;

    @FXML
    private JFXTextField password;

    @FXML
    private void doLogin() {
        try {
            JFXAlert alert = new JFXAlert((Stage) login.getScene().getWindow());
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setOverlayClose(false);
            JFXDialogLayout layout = new JFXDialogLayout();
            layout.setHeading(new Label("操作提示"));
            layout.setBody(new Label("登陆成功"));
            JFXButton closeButton = new JFXButton("确定");
            closeButton.getStyleClass().add("dialog-accept");
            closeButton.setOnAction(event -> {
                alert.hideWithAnimation();
                try {
                    login.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            layout.setActions(closeButton);
            alert.setContent(layout);
            alert.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

}

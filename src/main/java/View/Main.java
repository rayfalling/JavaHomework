package View;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialogLayout;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Main implements Initializable {

    @FXML
    private JFXButton personManager;

    @FXML
    private JFXButton medicineInfoManager;

    /**
     * 导航至病人信息管理页面
     */
    @FXML
    private void NavToPersonPage() throws Exception {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Person.fxml")));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(NavBack());
        personManager.getScene().getWindow().hide();
    }

    /**
     * 导航至药品信息管理页面
     */
    @FXML
    private void NavToMedicinePage() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Medicine.fxml")));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(NavBack());
        personManager.getScene().getWindow().hide();
    }

    /**
     * 导航至就诊信息管理页面
     */
    public void NavToPersonnelVisit() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/PersonnelVisit.fxml")));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(NavBack());
        personManager.getScene().getWindow().hide();
    }

    /**
     * 导航至处方明细维护页面
     */
    public void NavToPrescription() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Prescription.fxml")));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(NavBack());
        personManager.getScene().getWindow().hide();
    }


    /**
     * 为次级页面注册窗口关闭事件
     * 当窗口关闭时打开主页面
     */
    private EventHandler<WindowEvent> NavBack() {
        return new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                Stage stage = new Stage();
                Scene scene = null;
                try {
                    scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                stage.setScene(scene);
                stage.show();
            }
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    /**
     * 数据录入失败模态弹窗
     */
    static void showDialog(Stage stage) {
        JFXAlert alert = new JFXAlert(stage);
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("操作提示"));
        layout.setBody(new Label("数据录入失败！"));
        JFXButton closeButton = new JFXButton("确定");
        closeButton.getStyleClass().add("dialog-accept");
        closeButton.setOnAction(event -> {
            alert.hideWithAnimation();
        });
        layout.setActions(closeButton);
        alert.setContent(layout);
        alert.show();
    }

    /**
     * 返回主页面
     */
    void backMain(Stage primaryStage) throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")));
        stage.setScene(scene);
        stage.show();
        primaryStage.hide();
    }
}

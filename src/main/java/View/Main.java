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

    /**
     * 报销结算
     * 由于窗口大小问题
     * 暂时弃用
     */
//    public void Reimburese() {
//        JFXComboBox<Label> jfxCombo = new JFXComboBox<>();
//        new PersonManager().getFromFile().forEach((item) -> jfxCombo.getItems().add(new Label(item.getId())));
//        jfxCombo.setPromptText("选择结算Id");
//        JFXAlert alert = new JFXAlert((Stage) personManager.getScene().getWindow());
//        alert.initModality(Modality.APPLICATION_MODAL);
//        alert.setOverlayClose(false);
//        JFXDialogLayout layout = new JFXDialogLayout();
//        layout.setHeading(new Label("预结算"));
//        layout.setBody(jfxCombo);
//        JFXButton confirmButton = new JFXButton("确定");
//        confirmButton.getStyleClass().add("dialog-accept");
//        confirmButton.setOnAction(event -> {
//            alert.hideWithAnimation();
//            if (jfxCombo.getValue().getText() == null || jfxCombo.getValue().getText().equals("") || jfxCombo.getValue().getText().equals("选择结算Id"))
//                return;
//            PreSettleResult preSettleResult = ReimburseService.preSettle(jfxCombo.getValue().getText());
//            List<PreSettleResult> preSettleResults = new PreSettleResultManager().getFromFile();
//            if (preSettleResult == null) return;
//            preSettleResults.remove(preSettleResult);
//            new PreSettleResultManager().writeToFile((ArrayList<PreSettleResult>) preSettleResults);
//            String show = "用户Id:" + Objects.requireNonNull(preSettleResult).getId() + "\n"
//                    + "门诊号" + preSettleResult.getClinicNumber() + "\n"
//                    + "费用总额" + preSettleResult.getTotalExpenses() + "\n"
//                    + "报销金额" + preSettleResult.getReimbursementAmount() + "\n"
//                    + "自费金额" + preSettleResult.getSelfFundedAmount() + "\n"
//                    + "起付标准" + preSettleResult.getStartStandardAmount() + "\n"
//                    + "第一段自费" + preSettleResult.getFirstRangeAmount() + "\n"
//                    + "第二段自费" + preSettleResult.getSecondRangeAmount() + "\n"
//                    + "第三段自费" + preSettleResult.getThirdRangeAmount() + "\n"
//                    + "年度总计报销" + preSettleResult.getYearTotalReimbursementAmount();
//            JFXAlert alert1 = new JFXAlert((Stage) personManager.getScene().getWindow());
//            alert1.initModality(Modality.APPLICATION_MODAL);
//            alert1.setOverlayClose(false);
//            JFXDialogLayout layout1 = new JFXDialogLayout();
//            layout1.setHeading(new Label("预结算结果"));
//            layout1.setBody(new Label(show));
//            JFXButton confirmButton1 = new JFXButton("确定");
//            confirmButton1.getStyleClass().add("dialog-accept");
//            confirmButton1.setOnAction(event1 -> {
//                alert1.hideWithAnimation();
//                try {
//                    SettleResult settleResult = ReimburseService.Settle(jfxCombo.getValue().getText());
//                    ArrayList<SettleResult> settleResultArrayList = (ArrayList<SettleResult>) new SettleResultManager().getFromFile();
//                    settleResultArrayList.add(settleResult);
//                    new SettleResultManager().writeToFile(settleResultArrayList);
//                    new Tools().printSettleResultToExcel(jfxCombo.getValue().getText());
//                    JFXAlert alert2 = new JFXAlert((Stage) personManager.getScene().getWindow());
//                    alert2.initModality(Modality.APPLICATION_MODAL);
//                    alert2.setOverlayClose(false);
//                    JFXDialogLayout layout2 = new JFXDialogLayout();
//                    layout2.setHeading(new Label("结算结果"));
//                    layout2.setBody(new Label("文件地址" + Class.class.getClass().getResource("/").toURI().getPath().replaceFirst("/", "") + "/报销结算打印单" + jfxCombo.getValue().getText()+ ".xls"));
//                    JFXButton confirmButton2 = new JFXButton("确定");
//                    confirmButton2.getStyleClass().add("dialog-accept");
//                    confirmButton2.setOnAction(event2 -> alert2.hideWithAnimation());
//                    layout2.setActions(confirmButton2);
//                    alert2.setContent(layout2);
//                    alert2.show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            });
//            JFXButton closeButton = new JFXButton("取消");
//            closeButton.getStyleClass().add("dialog-cancel");
//            closeButton.setOnAction(event1 -> alert1.hideWithAnimation());
//            layout1.setActions(confirmButton1, closeButton);
//            alert1.setContent(layout1);
//            alert1.show();
//        });
//        JFXButton closeButton = new JFXButton("取消");
//        closeButton.getStyleClass().add("dialog-cancel");
//        closeButton.setOnAction(event -> alert.hideWithAnimation());
//        layout.setActions(confirmButton, closeButton);
//        alert.setContent(layout);
//        alert.show();
//    }
    public void NavToHospital() throws IOException {
        Stage stage = new Stage();
        Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Hospital.fxml")));
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(NavBack());
        personManager.getScene().getWindow().hide();
    }

    public void Exit() {
        personManager.getScene().getWindow().hide();
    }
}

package View;

import Controller.PrescriptionManager;
import Model.Prescription;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.DoubleTextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;

import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static View.Main.showDialog;

public class PrescriptionPage implements Initializable {
    @FXML
    public Label editableTreeTableViewCount;
    @FXML
    public JFXTextField searchField;
    @FXML
    public JFXTreeTableView<PrescriptionData> editableTreeTableView;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, String> Id;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, String> clinicNumber;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, String> code;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, String> hospitalCode;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, Number> itemPrice;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, Number> count;
    @FXML
    public JFXTreeTableColumn<PrescriptionData, Number> totalPrice;
    @FXML
    public JFXRippler back;
    @FXML
    public JFXRippler save;
    @FXML
    public JFXRippler add;
    private ObservableList<PrescriptionData> mapped;
    private PrescriptionData selected;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private boolean backFlag = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapped = FXCollections.observableArrayList();
        ArrayList<Prescription> prescriptionArrayList = (ArrayList<Prescription>) new PrescriptionManager().getFromFile();
        for (Prescription p : prescriptionArrayList) {
            mapped.add(new PrescriptionData(p.getId(), p.getClinicNumber(), p.getCode(), p.getHospitalCode(),
                    p.getItemPrice(), p.getCount(), p.getTotalPrice()));
        }
        setupEditableTableView();
        editableTreeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selected = newValue == null ? null : newValue.getValue()
        );
    }

    private void setupEditableTableView() {
        Id.setCellValueFactory(param -> param.getValue().getValue().Id);
        Id.setCellFactory((TreeTableColumn<PrescriptionData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        Id.setOnEditCommit((TreeTableColumn.CellEditEvent<PrescriptionData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().Id.setValue(t.getNewValue()));

        clinicNumber.setCellValueFactory(param -> param.getValue().getValue().clinicNumber);
        clinicNumber.setCellFactory((TreeTableColumn<PrescriptionData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        clinicNumber.setOnEditCommit((TreeTableColumn.CellEditEvent<PrescriptionData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().clinicNumber.setValue(t.getNewValue()));

        code.setCellValueFactory(param -> param.getValue().getValue().code);
        code.setCellFactory((TreeTableColumn<PrescriptionData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        code.setOnEditCommit((TreeTableColumn.CellEditEvent<PrescriptionData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().code.setValue(t.getNewValue()));

        hospitalCode.setCellValueFactory(param -> param.getValue().getValue().hospitalCode);
        hospitalCode.setCellFactory((TreeTableColumn<PrescriptionData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        hospitalCode.setOnEditCommit((TreeTableColumn.CellEditEvent<PrescriptionData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().hospitalCode.setValue(t.getNewValue()));

        itemPrice.setCellValueFactory(param -> param.getValue().getValue().itemPrice);
        itemPrice.setCellFactory((TreeTableColumn<PrescriptionData, Number> param) -> new GenericEditableTreeTableCell<>(
                new DoubleTextFieldEditorBuilder()));
        itemPrice.setOnEditCommit((TreeTableColumn.CellEditEvent<PrescriptionData, Number> t) -> {
            PrescriptionData prescriptionData = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue();
            prescriptionData.itemPrice.setValue(t.getNewValue());
            prescriptionData.totalPrice.setValue(new DoubleStringConverter().fromString(new DecimalFormat("#.00").format(t.getNewValue().doubleValue() * prescriptionData.count.get())));
        });

        count.setCellValueFactory(param -> param.getValue().getValue().count);
        count.setCellFactory((TreeTableColumn<PrescriptionData, Number> param) -> new GenericEditableTreeTableCell<>(
                new DoubleTextFieldEditorBuilder()));
        count.setOnEditCommit((TreeTableColumn.CellEditEvent<PrescriptionData, Number> t) -> {
            PrescriptionData prescriptionData = t.getTreeTableView().getTreeItem(t.getTreeTablePosition().getRow()).getValue();
            prescriptionData.count.setValue(t.getNewValue());
            prescriptionData.totalPrice.setValue(new DoubleStringConverter().fromString(new DecimalFormat("#.00").format(t.getNewValue().doubleValue() * prescriptionData.itemPrice.get())));
        });

        totalPrice.setCellValueFactory(param -> param.getValue().getValue().totalPrice);
        totalPrice.setCellFactory((TreeTableColumn<PrescriptionData, Number> param) -> new GenericEditableTreeTableCell<>(
                new DoubleTextFieldEditorBuilder()));

        editableTreeTableView.setRoot(new RecursiveTreeItem<>(mapped, RecursiveTreeObject::getChildren));
        editableTreeTableView.setShowRoot(false);
        editableTreeTableView.setEditable(true);
        editableTreeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + editableTreeTableView.getCurrentItemsCount() + POSTFIX,
                        editableTreeTableView.currentItemsCountProperty()));
        searchField.textProperty()
                .addListener(setupSearchField(editableTreeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<PrescriptionData> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final PrescriptionData PrescriptionData = personProp.getValue();
                    return PrescriptionData.Id.getValue().contains(newVal)
                            || PrescriptionData.clinicNumber.getValue().contains(newVal)
                            || PrescriptionData.code.getValue().contains(newVal)
                            || PrescriptionData.itemPrice.getValue().toString().contains(newVal)
                            || PrescriptionData.count.getValue().toString().contains(newVal)
                            || PrescriptionData.totalPrice.getValue().toString().contains(newVal);
                });
    }

    @FXML
    public void BackToMain() throws IOException {
        Save();
        if (backFlag) {
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/PersonnelVisit.fxml")));
            stage.setScene(scene);
            stage.show();
            editableTreeTableView.getScene().getWindow().hide();
        } else
            new Main().backMain((Stage) editableTreeTableView.getScene().getWindow());
    }

    public void Save() {
        try {
            ArrayList<Prescription> prescriptionArrayList = new ArrayList<>();
            mapped.forEach((item) -> prescriptionArrayList.add(
                    new Prescription(item.Id.get(), item.clinicNumber.get(), item.code.get(), item.hospitalCode.get(),
                            item.itemPrice.get(), item.count.get(), item.totalPrice.get())
            ));
            new PrescriptionManager().writeToFile(prescriptionArrayList);
        } catch (Exception e) {
            showDialog((Stage) editableTreeTableView.getScene().getWindow());
        }

    }

    void DoFilter(String filter) {
        backFlag = true;
        searchField.setText(filter);
        searchField.setDisable(true);
    }

    public void AddNew() {
        mapped.add(new PrescriptionData("Id", "门诊号", "药品编号", "医院编号", 0.0, 0.0, 0.0));
        editableTreeTableView.setCurrentItemsCount(editableTreeTableView.getCurrentItemsCount() + 1);
    }

    public void Delete() {
        this.mapped.remove(selected);
        editableTreeTableView.setCurrentItemsCount(editableTreeTableView.getCurrentItemsCount() - 1);
    }

    /**
     * 用于表单展示的数据绑定类
     * 与{@link Model.Person}同步
     */
    static class PrescriptionData extends RecursiveTreeObject<PrescriptionData> {
        StringProperty Id;
        StringProperty clinicNumber;
        StringProperty code;
        StringProperty hospitalCode;
        DoubleProperty itemPrice;
        DoubleProperty count;
        DoubleProperty totalPrice;

        PrescriptionData(String Id, String clinicNumber, String code, String hospitalCode, double itemPrice, double count, double totalPrice) {
            this.Id = new SimpleStringProperty(Id);
            this.clinicNumber = new SimpleStringProperty(clinicNumber);
            this.code = new SimpleStringProperty(code);
            this.hospitalCode = new SimpleStringProperty(hospitalCode);
            this.itemPrice = new SimpleDoubleProperty(itemPrice);
            this.count = new SimpleDoubleProperty(count);
            this.totalPrice = new SimpleDoubleProperty(totalPrice);
        }
    }
}

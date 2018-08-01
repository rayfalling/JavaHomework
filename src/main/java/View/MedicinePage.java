package View;

import Controller.MedicineInfoManager;
import Model.ChargeItemLevel;
import Model.HospitalLevel;
import Model.MedicineInfo;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static View.Main.showDialog;
import static View.MedicinePage.MedicineInfoData.*;

@SuppressWarnings("ALL")
public class MedicinePage implements Initializable {
    @FXML
    public Label editableTreeTableViewCount;
    @FXML
    public JFXTextField searchField;
    @FXML
    public JFXTreeTableView<MedicineInfoData> editableTreeTableView;
    @FXML
    public JFXTreeTableColumn<MedicineInfoData, String> drugCode;
    @FXML
    public JFXTreeTableColumn<MedicineInfoData, String> Name;
    @FXML
    public JFXTreeTableColumn<MedicineInfoData, Number> priceLimit;
    @FXML
    public JFXTreeTableColumn<MedicineInfoData, String> drugDosageUnit;
    @FXML
    public JFXTreeTableColumn<MedicineInfoData, String> chargeItemLevel;
    @FXML
    public JFXTreeTableColumn<MedicineInfoData, String> hospitalLevel;
    @FXML
    public JFXRippler back;
    @FXML
    public JFXRippler save;
    @FXML
    public JFXRippler add;
    private ObservableList<MedicineInfoData> mapped;
    private MedicineInfoData selected;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapped = FXCollections.observableArrayList();
        ArrayList<MedicineInfo> medicineInfoArrayList = (ArrayList<MedicineInfo>) new MedicineInfoManager().getFromFile();
        for (MedicineInfo medicineInfo : medicineInfoArrayList) {
            mapped.add(new MedicineInfoData(medicineInfo.getDrugCode(), medicineInfo.getName(), medicineInfo.getPriceLimit(),
                    medicineInfo.getDrugDosageUnit(), ConvertEnumChargeItemLevelToString(medicineInfo.getChargeItemLevel()),
                    ConvertEnumHospitalLevelToString(medicineInfo.getHospitalLevel())));
        }
        setupEditableTableView();
        editableTreeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selected = newValue == null ? null : newValue.getValue()
        );
    }

    private void setupEditableTableView() {
        drugCode.setCellValueFactory(param -> param.getValue().getValue().drugCode);
        drugCode.setCellFactory((TreeTableColumn<MedicineInfoData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        drugCode.setOnEditCommit((TreeTableColumn.CellEditEvent<MedicineInfoData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().drugCode.setValue(t.getNewValue()));

        Name.setCellValueFactory(param -> param.getValue().getValue().name);
        Name.setCellFactory((TreeTableColumn<MedicineInfoData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        Name.setOnEditCommit((TreeTableColumn.CellEditEvent<MedicineInfoData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().name.setValue(t.getNewValue()));

        priceLimit.setCellValueFactory(param -> param.getValue().getValue().priceLimit);
        priceLimit.setCellFactory((TreeTableColumn<MedicineInfoData, Number> param) -> new GenericEditableTreeTableCell<>(
                new DoubleTextFieldEditorBuilder()));
        priceLimit.setOnEditCommit((TreeTableColumn.CellEditEvent<MedicineInfoData, Number> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().priceLimit.setValue(t.getNewValue()));

        drugDosageUnit.setCellValueFactory(param -> param.getValue().getValue().drugDosageUnit);
        drugDosageUnit.setCellFactory((TreeTableColumn<MedicineInfoData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        drugDosageUnit.setOnEditCommit((TreeTableColumn.CellEditEvent<MedicineInfoData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().drugDosageUnit.setValue(t.getNewValue()));

        chargeItemLevel.setCellValueFactory(param -> param.getValue().getValue().chargeItemLevel);
        chargeItemLevel.setCellFactory((TreeTableColumn<MedicineInfoData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        chargeItemLevel.setOnEditCommit((TreeTableColumn.CellEditEvent<MedicineInfoData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().chargeItemLevel.setValue(t.getNewValue()));

        hospitalLevel.setCellValueFactory(param -> param.getValue().getValue().hospitalLevel);
        hospitalLevel.setCellFactory((TreeTableColumn<MedicineInfoData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        hospitalLevel.setOnEditCommit((TreeTableColumn.CellEditEvent<MedicineInfoData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().hospitalLevel.setValue(t.getNewValue()));

        editableTreeTableView.setRoot(new RecursiveTreeItem<>(mapped, RecursiveTreeObject::getChildren));
        editableTreeTableView.setShowRoot(false);
        editableTreeTableView.setEditable(true);
        editableTreeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + editableTreeTableView.getCurrentItemsCount() + POSTFIX,
                        editableTreeTableView.currentItemsCountProperty()));
        searchField.textProperty()
                .addListener(setupSearchField(editableTreeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<MedicineInfoData> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final MedicineInfoData medicineInfoData = personProp.getValue();
                    return medicineInfoData.name.getValue().contains(newVal)
                            || medicineInfoData.drugCode.getValue().contains(newVal)
                            || medicineInfoData.drugDosageUnit.getValue().contains(newVal)
                            || medicineInfoData.priceLimit.getValue().toString().contains(newVal)
                            || medicineInfoData.hospitalLevel.toString().contains(newVal)
                            || medicineInfoData.chargeItemLevel.toString().contains(newVal);
                });
    }

    @FXML
    public void BackToMain() throws IOException {
        Save();
        new Main().backMain((Stage) editableTreeTableView.getScene().getWindow());
    }

    public void Save() {
        try {
            ArrayList<MedicineInfo> medicineInfoArrayList = new ArrayList<>();
            mapped.forEach((item) -> {
                medicineInfoArrayList.add(
                        new MedicineInfo(item.drugCode.get(), item.name.get(), item.priceLimit.get(),
                                item.drugDosageUnit.get(), ConvertStringToEnumChargeItemLevel(item.chargeItemLevel.get()),
                                ConvertStringToEnumHospitalLevel(item.hospitalLevel.get()))
                );
            });
            new MedicineInfoManager().writeToFile(medicineInfoArrayList);
        } catch (Exception e) {
            showDialog((Stage) editableTreeTableView.getScene().getWindow());
        }

    }

    public void AddNew() {
        mapped.add(new MedicineInfoData("Id", "药品名称", 10.0, "ug/支", ChargeItemLevel.ClassA, HospitalLevel.OneClass));
    }

    public void Delete() {
        this.mapped.remove(selected);
    }

    /**
     * 用于表单展示的数据绑定类
     * 与{@link Model.Person}同步
     */
    static class MedicineInfoData extends RecursiveTreeObject<MedicineInfoData> {
        StringProperty drugCode;
        StringProperty name;
        DoubleProperty priceLimit;
        StringProperty drugDosageUnit;
        StringProperty chargeItemLevel;
        StringProperty hospitalLevel;

        MedicineInfoData(String drugCode, String name, double priceLimit, String drugDosageUnit, ChargeItemLevel chargeItemLevel, HospitalLevel hospitalLevel) {
            this.drugCode = new SimpleStringProperty(drugCode);
            this.name = new SimpleStringProperty(name);
            this.priceLimit = new SimpleDoubleProperty(priceLimit);
            this.drugDosageUnit = new SimpleStringProperty(drugDosageUnit);
            this.chargeItemLevel = new SimpleStringProperty(ConvertEnumChargeItemLevelToString(chargeItemLevel));
            this.hospitalLevel = new SimpleStringProperty(ConvertEnumHospitalLevelToString(hospitalLevel));
        }

        MedicineInfoData(String drugCode, String name, double priceLimit, String drugDosageUnit, String s, String s1) {
            this.drugCode = new SimpleStringProperty(drugCode);
            this.name = new SimpleStringProperty(name);
            this.priceLimit = new SimpleDoubleProperty(priceLimit);
            this.drugDosageUnit = new SimpleStringProperty(drugDosageUnit);
            this.chargeItemLevel = new SimpleStringProperty(s);
            this.hospitalLevel = new SimpleStringProperty(s1);
        }

        /**
         * 枚举类{@link HospitalLevel}转字符串
         * 无匹配默认返回""
         */
        static String ConvertEnumHospitalLevelToString(HospitalLevel hospitalLevel) {
            switch (hospitalLevel) {
                case OneClass:
                    return "一类";
                case SecondClass:
                    return "二类";
                case ThirdClass:
                    return "三类";
                case Community:
                    return "社区";
            }
            return "";
        }

        /**
         * 字符串转枚举类{@link HospitalLevel}
         * 无匹配默认返回社区级别
         */
        static HospitalLevel ConvertStringToEnumHospitalLevel(String hospitalLevel) {
            switch (hospitalLevel) {
                case "一类":
                    return HospitalLevel.OneClass;
                case "二类":
                    return HospitalLevel.SecondClass;
                case "三类":
                    return HospitalLevel.ThirdClass;
                case "社区":
                    return HospitalLevel.Community;
            }
            return HospitalLevel.Community;
        }

        /**
         * 枚举类{@link ChargeItemLevel}转字符串
         * 无匹配默认返回""
         */
        static String ConvertEnumChargeItemLevelToString(ChargeItemLevel chargeItemLevel) {
            switch (chargeItemLevel) {
                case ClassA:
                    return "甲类";
                case ClassB:
                    return "乙类";
                case ClassC:
                    return "丙类";
            }
            return "";
        }

        /**
         * 字符串转枚举类{@link HospitalLevel}
         * 无匹配默认返回社丙类
         */
        static ChargeItemLevel ConvertStringToEnumChargeItemLevel(String hospitalLevel) {
            switch (hospitalLevel) {
                case "甲类":
                    return ChargeItemLevel.ClassA;
                case "乙类":
                    return ChargeItemLevel.ClassB;
                case "丙类":
                    return ChargeItemLevel.ClassC;
            }
            return ChargeItemLevel.ClassC;
        }
    }
}

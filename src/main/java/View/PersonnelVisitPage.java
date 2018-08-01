package View;

import Controller.PersonnelVisitManager;
import Model.HospitalLevel;
import Model.PersonnelVisit;
import com.jfoenix.controls.*;
import com.jfoenix.controls.cells.editors.TextFieldEditorBuilder;
import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.beans.binding.Bindings;
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

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static View.Main.showDialog;
import static View.PersonnelVisitPage.PersonnelVisitData.ConvertStringToEnumHospitalLevel;

@SuppressWarnings("ALL")
public class PersonnelVisitPage implements Initializable {
    @FXML
    public Label editableTreeTableViewCount;
    @FXML
    public JFXTextField searchField;
    @FXML
    public JFXTreeTableView<PersonnelVisitData> editableTreeTableView;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> Id;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> clinicNumber;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> category;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> admissionDate;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> leaveDate;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> code;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> hospitalLevel;
    @FXML
    public JFXTreeTableColumn<PersonnelVisitData, String> leaveReason;
    @FXML
    public JFXRippler back;
    @FXML
    public JFXRippler save;
    @FXML
    public JFXRippler add;
    private ObservableList<PersonnelVisitData> mapped;
    private PersonnelVisitData selected;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private boolean backFlag = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapped = FXCollections.observableArrayList();
        ArrayList<PersonnelVisit> personnelVisitArrayList = (ArrayList<PersonnelVisit>) new PersonnelVisitManager().getFromFile();
        for (PersonnelVisit p : personnelVisitArrayList) {
            mapped.add(new PersonnelVisitData(p.getId(), p.getClinicNumber(), p.getCategory(), p.getAdmissionDate(), p.getLeaveDate(), p.getCode(), p.getHospitalLevel(), p.getLeaveReason()));
        }
        setupEditableTableView();
        editableTreeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selected = newValue == null ? null : newValue.getValue()
        );
    }

    private void setupEditableTableView() {
        Id.setCellValueFactory(param -> param.getValue().getValue().Id);
        Id.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        Id.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().Id.setValue(t.getNewValue()));

        clinicNumber.setCellValueFactory(param -> param.getValue().getValue().clinicNumber);
        clinicNumber.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        clinicNumber.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().clinicNumber.setValue(t.getNewValue()));

        category.setCellValueFactory(param -> param.getValue().getValue().category);
        category.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        category.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().category.setValue(t.getNewValue()));

        admissionDate.setCellValueFactory(param -> param.getValue().getValue().admissionDate);
        admissionDate.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        admissionDate.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().admissionDate.setValue(t.getNewValue()));

        leaveDate.setCellValueFactory(param -> param.getValue().getValue().leaveDate);
        leaveDate.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        leaveDate.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().leaveDate.setValue(t.getNewValue()));

        code.setCellValueFactory(param -> param.getValue().getValue().code);
        code.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        code.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().code.setValue(t.getNewValue()));

        hospitalLevel.setCellValueFactory(param -> param.getValue().getValue().hospitalLevel);
        hospitalLevel.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        hospitalLevel.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().hospitalLevel.setValue(t.getNewValue()));

        leaveReason.setCellValueFactory(param -> param.getValue().getValue().leaveReason);
        leaveReason.setCellFactory((TreeTableColumn<PersonnelVisitData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        leaveReason.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonnelVisitData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().leaveReason.setValue(t.getNewValue()));

        editableTreeTableView.setRoot(new RecursiveTreeItem<>(mapped, RecursiveTreeObject::getChildren));
        editableTreeTableView.setShowRoot(false);
        editableTreeTableView.setEditable(true);
        editableTreeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + editableTreeTableView.getCurrentItemsCount() + POSTFIX,
                        editableTreeTableView.currentItemsCountProperty()));
        searchField.textProperty()
                .addListener(setupSearchField(editableTreeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<PersonnelVisitData> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final PersonnelVisitData p = personProp.getValue();
                    return p.Id.getValue().contains(newVal)
                            || p.clinicNumber.getValue().contains(newVal)
                            || p.category.getValue().contains(newVal)
                            || p.admissionDate.getValue().contains(newVal)
                            || p.leaveDate.getValue().contains(newVal)
                            || p.code.getValue().contains(newVal)
                            || p.hospitalLevel.getValue().contains(newVal)
                            || p.leaveReason.getValue().contains(newVal);
                });
    }

    @FXML
    public void BackToMain() throws IOException {
        Save();
        if (backFlag) {
            Stage stage = new Stage();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/fxml/Person.fxml")));
            stage.setScene(scene);
            stage.show();
            editableTreeTableView.getScene().getWindow().hide();
        } else
            new Main().backMain((Stage) editableTreeTableView.getScene().getWindow());
    }

    public void Save() {
        try {
            ArrayList<PersonnelVisit> personArrayList = new ArrayList<>();
            mapped.forEach((item) -> {
                personArrayList.add(
                        new PersonnelVisit(item.Id.get(), item.clinicNumber.get(), item.category.get(),
                                item.admissionDate.get() == null || item.admissionDate.get().equals("")
                                        ? null : LocalDate.parse(item.admissionDate.get()),
                                item.leaveDate.get() == null || item.leaveDate.get().equals("")
                                        ? null : LocalDate.parse(item.admissionDate.get()),
                                item.code.get(), ConvertStringToEnumHospitalLevel(item.hospitalLevel.get()), item.leaveReason.get()));
            });
            new PersonnelVisitManager().writeToFile(personArrayList);
        } catch (Exception e) {
            showDialog((Stage) editableTreeTableView.getScene().getWindow());
        }
    }

    public void AddNew() {
        mapped.add(new PersonnelVisitData("Id", "门诊号", "医疗类别", LocalDate.now(), LocalDate.now(), "医院代码", HospitalLevel.OneClass, "出院原因"));
    }

    public void Delete() {
        this.mapped.remove(selected);
    }

    public void DoFilter(String filter) {
        backFlag = true;
        searchField.setText(filter);
        searchField.setDisable(true);
    }

    public void ShowPersonnelVisits() throws IOException {
        if (selected != null) {
            Save();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Prescription.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            editableTreeTableView.getScene().getWindow().hide();
            PrescriptionPage control = loader.getController();
            control.DoFilter(selected.clinicNumber.getValue());
        }
    }

    /**
     * 用于表单展示的数据绑定类
     * 与{@link Model.Person}同步
     */
    static class PersonnelVisitData extends RecursiveTreeObject<PersonnelVisitData> {
        StringProperty Id;
        StringProperty clinicNumber;
        StringProperty category;
        StringProperty admissionDate;
        StringProperty leaveDate;
        StringProperty code;//医院代码
        StringProperty hospitalLevel;
        StringProperty leaveReason;

        public PersonnelVisitData(String id, String clinicNumber, String category, LocalDate admissionDate, LocalDate leaveDate, String code, HospitalLevel hospitalLevel, String leaveReason) {
            Id = new SimpleStringProperty(id);
            this.clinicNumber = new SimpleStringProperty(clinicNumber);
            this.category = new SimpleStringProperty(category);
            this.admissionDate = new SimpleStringProperty(admissionDate.toString());
            this.leaveDate = new SimpleStringProperty(leaveDate.toString());
            this.code = new SimpleStringProperty(code);
            this.hospitalLevel = new SimpleStringProperty(ConvertEnumHospitalLevelToString(hospitalLevel));
            this.leaveReason = new SimpleStringProperty(leaveReason);
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
    }

}

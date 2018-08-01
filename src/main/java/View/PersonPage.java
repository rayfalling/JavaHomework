package View;

import Controller.PersonManager;
import Controller.PreSettleResultManager;
import Controller.ReimburseService;
import Model.Person;
import Model.PreSettleResult;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static View.Main.showDialog;

@SuppressWarnings("ALL")
public class PersonPage implements Initializable {
    @FXML
    public Label editableTreeTableViewCount;
    @FXML
    public JFXTextField searchField;
    @FXML
    public JFXTreeTableView<PersonData> editableTreeTableView;
    @FXML
    public JFXTreeTableColumn<PersonData, String> Id;
    @FXML
    public JFXTreeTableColumn<PersonData, String> Name;
    @FXML
    public JFXTreeTableColumn<PersonData, String> certificateId;
    @FXML
    public JFXTreeTableColumn<PersonData, String> typeOfCertificate;
    @FXML
    public JFXTreeTableColumn<PersonData, String> sex;
    @FXML
    public JFXTreeTableColumn<PersonData, String> nationality;
    @FXML
    public JFXTreeTableColumn<PersonData, String> Birthday;
    @FXML
    public JFXRippler back;
    @FXML
    public JFXRippler save;
    @FXML
    public JFXRippler add;
    private ObservableList<PersonData> mapped;
    private PersonData selected;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapped = FXCollections.observableArrayList();
        ArrayList<Person> personArrayList = (ArrayList<Person>) new PersonManager().getFromFile();
        for (Person p : personArrayList) {
            mapped.add(new PersonData(p.getId(), p.getName(), p.getTypeOfCertificate(), p.getCertificateId(), p.getSex().toString(), p.getNationality(), p.getBirthday()));
        }
        setupEditableTableView();
        editableTreeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selected = newValue == null ? null : newValue.getValue()
        );
    }

    private void setupEditableTableView() {
        Id.setCellValueFactory(param -> param.getValue().getValue().Id);
        Id.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        Id.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().Id.setValue(t.getNewValue()));

        Name.setCellValueFactory(param -> param.getValue().getValue().name);
        Name.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        Name.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().name.setValue(t.getNewValue()));

        typeOfCertificate.setCellValueFactory(param -> param.getValue().getValue().typeOfCertificate);
        typeOfCertificate.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        typeOfCertificate.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().typeOfCertificate.setValue(t.getNewValue()));

        certificateId.setCellValueFactory(param -> param.getValue().getValue().certificateId);
        certificateId.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        certificateId.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().certificateId.setValue(t.getNewValue()));

        sex.setCellValueFactory(param -> param.getValue().getValue().sex);
        sex.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        sex.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().sex.setValue(t.getNewValue()));

        nationality.setCellValueFactory(param -> param.getValue().getValue().nationality);
        nationality.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        nationality.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().nationality.setValue(t.getNewValue()));

        Birthday.setCellValueFactory(param -> param.getValue().getValue().Birthday);
        Birthday.setCellFactory((TreeTableColumn<PersonData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        Birthday.setOnEditCommit((TreeTableColumn.CellEditEvent<PersonData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().Birthday.setValue(t.getNewValue()));

        editableTreeTableView.setRoot(new RecursiveTreeItem<>(mapped, RecursiveTreeObject::getChildren));
        editableTreeTableView.setShowRoot(false);
        editableTreeTableView.setEditable(true);
        editableTreeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + editableTreeTableView.getCurrentItemsCount() + POSTFIX,
                        editableTreeTableView.currentItemsCountProperty()));
        searchField.textProperty()
                .addListener(setupSearchField(editableTreeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<PersonData> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(personProp -> {
                    final PersonData person = personProp.getValue();
                    return person.name.getValue().contains(newVal)
                            || person.typeOfCertificate.getValue().contains(newVal)
                            || person.Id.getValue().contains(newVal)
                            || person.certificateId.getValue().contains(newVal)
                            || person.sex.getValue().contains(newVal)
                            || person.Birthday.toString().contains(newVal)
                            || person.nationality.getValue().contains(newVal);
                });
    }

    @FXML
    public void BackToMain() throws IOException {
        Save();
        new Main().backMain((Stage) editableTreeTableView.getScene().getWindow());
    }

    public void Save() {
        try {
            ArrayList<Person> personArrayList = new ArrayList<>();
            mapped.forEach((item) -> {
                personArrayList.add(
                        new Person(item.Id.get(), item.typeOfCertificate.get(), item.certificateId.get(), item.name.get(),
                                item.sex.get().equals("男") ? Model.sex.male : Model.sex.female, item.nationality.get(),
                                item.Birthday.get() == null || item.Birthday.get().equals("")
                                        ? null : LocalDate.parse(item.Birthday.get())));
            });
            new PersonManager().writeToFile(personArrayList);
        } catch (Exception e) {
            showDialog((Stage) editableTreeTableView.getScene().getWindow());
        }
    }

    public void AddNew() {
        mapped.add(new PersonData("Id", "姓名", "身份证", "证件编号", "男", null, (String) null));
    }

    public void Delete() {
        this.mapped.remove(selected);
    }

    public void ShowPersonnelVisits() throws IOException {
        if (selected != null) {
            Save();
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/PersonnelVisit.fxml"));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.show();
            editableTreeTableView.getScene().getWindow().hide();
            PersonnelVisitPage control = loader.getController();
            control.DoFilter(selected.Id.getValue());
        }
    }

    public void DoSettle(ActionEvent actionEvent) {
        if (selected == null) return;
        PreSettleResult preSettleResult = ReimburseService.preSettle(selected.Id.get());
        List<PreSettleResult> preSettleResults = new PreSettleResultManager().getFromFile();
        preSettleResults.add(preSettleResult);
        new PreSettleResultManager().writeToFile((ArrayList<PreSettleResult>) preSettleResults);
        String show = "用户Id:" + preSettleResult.getId() + "\n"
                + "门诊号" + preSettleResult.getClinicNumber() + "\n"
                + "费用总额" + preSettleResult.getTotalExpenses() + "\n"
                + "报销金额" + preSettleResult.getReimbursementAmount() + "\n"
                + "自费金额" + preSettleResult.getSelfFundedAmount() + "\n"
                + "起付标准" + preSettleResult.getStartStandardAmount() + "\n"
                + "第一段自费" + preSettleResult.getFirstRangeAmount() + "\n"
                + "第二段自费" + preSettleResult.getSecondRangeAmount() + "\n"
                + "第三段自费" + preSettleResult.getThirdRangeAmount() + "\n"
                + "年度总计报销" + preSettleResult.getYearTotalReimbursementAmount();
        JFXAlert alert = new JFXAlert((Stage) editableTreeTableView.getScene().getWindow());
        alert.initModality(Modality.APPLICATION_MODAL);
        alert.setOverlayClose(false);
        JFXDialogLayout layout = new JFXDialogLayout();
        layout.setHeading(new Label("预结算结果"));
        layout.setBody(new Label(show));
        JFXButton confirmButton = new JFXButton("确定");
        confirmButton.getStyleClass().add("dialog-accept");
        confirmButton.setOnAction(event -> {
            alert.hideWithAnimation();
//            try {
//                editableTreeTableView.getScene().setRoot(FXMLLoader.load(getClass().getResource("/fxml/main.fxml")));
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        });
        JFXButton closeButton = new JFXButton("取消");
        closeButton.getStyleClass().add("dialog-cancel");
        closeButton.setOnAction(event -> alert.hideWithAnimation());
        layout.setActions(confirmButton,closeButton);
        alert.setContent(layout);
        alert.show();
    }

    /**
     * 用于表单展示的数据绑定类
     * 与{@link Model.Person}同步
     */
    static class PersonData extends RecursiveTreeObject<PersonData> {
        StringProperty Id;
        StringProperty name;
        StringProperty typeOfCertificate;
        StringProperty certificateId;
        StringProperty sex;
        StringProperty nationality;
        StringProperty Birthday;

        PersonData(String id, String name, String typeOfCertificate, String certificateId, String sex, String nationality, LocalDate birthday) {
            this.Id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.typeOfCertificate = new SimpleStringProperty(typeOfCertificate);
            this.certificateId = new SimpleStringProperty(certificateId);
            this.sex = new SimpleStringProperty(sex.equals("male") ? "男" : "女");
            this.nationality = new SimpleStringProperty(nationality);
            this.Birthday = new SimpleStringProperty(birthday == null ? null : birthday.toString());
        }

        PersonData(String id, String name, String typeOfCertificate, String certificateId, String sex, String nationality, String birthday) {
            this.Id = new SimpleStringProperty(id);
            this.name = new SimpleStringProperty(name);
            this.typeOfCertificate = new SimpleStringProperty(typeOfCertificate);
            this.certificateId = new SimpleStringProperty(certificateId);
            this.sex = new SimpleStringProperty(sex);
            this.nationality = new SimpleStringProperty(nationality);
            this.Birthday = new SimpleStringProperty(birthday);
        }
    }
}
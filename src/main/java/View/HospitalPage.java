package View;

import Controller.HospitalManager;
import Model.Hospital;
import Model.HospitalLevel;
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
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TreeTableColumn;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static Controller.Tools.ConvertEnumHospitalLevelToString;
import static Controller.Tools.ConvertStringToEnumHospitalLevel;
import static View.Main.showDialog;

@SuppressWarnings("ALL")
public class HospitalPage implements Initializable {
    @FXML
    public Label editableTreeTableViewCount;
    @FXML
    public JFXTextField searchField;
    @FXML
    public JFXTreeTableView<HospitalData> editableTreeTableView;
    @FXML
    public JFXTreeTableColumn<HospitalData, String> code;
    @FXML
    public JFXTreeTableColumn<HospitalData, String> name;
    @FXML
    public JFXTreeTableColumn<HospitalData, String> level;
    @FXML
    public JFXRippler back;
    @FXML
    public JFXRippler save;
    @FXML
    public JFXRippler add;
    private ObservableList<HospitalData> mapped;
    private HospitalData selected;
    private static final String PREFIX = "( ";
    private static final String POSTFIX = " )";
    private boolean backFlag = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapped = FXCollections.observableArrayList();
        ArrayList<Hospital> hospitalArrayList = (ArrayList<Hospital>) new HospitalManager().getFromFile();
        for (Hospital h : hospitalArrayList) {
            mapped.add(new HospitalData(h.getCode(), h.getName(), h.getLevel()));
        }
        setupEditableTableView();
        editableTreeTableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> selected = newValue == null ? null : newValue.getValue()
        );
    }

    private void setupEditableTableView() {
        code.setCellValueFactory(param -> param.getValue().getValue().code);
        code.setCellFactory((TreeTableColumn<HospitalData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        code.setOnEditCommit((TreeTableColumn.CellEditEvent<HospitalData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().code.setValue(t.getNewValue()));

        name.setCellValueFactory(param -> param.getValue().getValue().name);
        name.setCellFactory((TreeTableColumn<HospitalData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        name.setOnEditCommit((TreeTableColumn.CellEditEvent<HospitalData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().name.setValue(t.getNewValue()));

        level.setCellValueFactory(param -> param.getValue().getValue().level);
        level.setCellFactory((TreeTableColumn<HospitalData, String> param) -> new GenericEditableTreeTableCell<>(
                new TextFieldEditorBuilder()));
        level.setOnEditCommit((TreeTableColumn.CellEditEvent<HospitalData, String> t) -> t.getTreeTableView()
                .getTreeItem(t.getTreeTablePosition().getRow())
                .getValue().level.setValue(t.getNewValue()));

        editableTreeTableView.setRoot(new RecursiveTreeItem<>(mapped, RecursiveTreeObject::getChildren));
        editableTreeTableView.setShowRoot(false);
        editableTreeTableView.setEditable(true);
        editableTreeTableViewCount.textProperty()
                .bind(Bindings.createStringBinding(() -> PREFIX + editableTreeTableView.getCurrentItemsCount() + POSTFIX,
                        editableTreeTableView.currentItemsCountProperty()));
        searchField.textProperty()
                .addListener(setupSearchField(editableTreeTableView));
    }

    private ChangeListener<String> setupSearchField(final JFXTreeTableView<HospitalData> tableView) {
        return (o, oldVal, newVal) ->
                tableView.setPredicate(hospitalPorp -> {
                    final HospitalData hospitalData = hospitalPorp.getValue();
                    return hospitalData.code.getValue().contains(newVal)
                            || hospitalData.name.getValue().contains(newVal)
                            || hospitalData.level.getValue().contains(newVal);
                });
    }

    @FXML
    public void BackToMain() throws IOException {
        new Main().backMain((Stage) editableTreeTableView.getScene().getWindow());
    }

    public void Save() {
        try {
            ArrayList<Hospital> hospitalDataArrayList = new ArrayList<>();
            mapped.forEach((item) -> hospitalDataArrayList.add(
                    new Hospital(item.code.get(), item.name.get(), ConvertStringToEnumHospitalLevel(item.level.get()))
            ));
            new HospitalManager().writeToFile(hospitalDataArrayList);
        } catch (Exception e) {
            showDialog((Stage) editableTreeTableView.getScene().getWindow());
        }
    }

    public void AddNew() {
        mapped.add(new HospitalData("医院编号", "医院名称", HospitalLevel.Community));
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
    static class HospitalData extends RecursiveTreeObject<HospitalData> {
        StringProperty code;
        StringProperty name;
        StringProperty level;

        HospitalData(String code, String name, HospitalLevel level) {
            this.code = new SimpleStringProperty(code);
            this.name = new SimpleStringProperty(name);
            this.level = new SimpleStringProperty(ConvertEnumHospitalLevelToString(level));
        }
    }
}

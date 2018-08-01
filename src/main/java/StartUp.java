import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class StartUp extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("HIS");
        Scene scene = new Scene(root);
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();

    }

    public static void main(String[] args) {
//        ArrayList<Person> p1 = new ArrayList<>();
//        p1.add(new Person("C001", "身份证", "3202002022962016X", "王水生", sex.male, "han", LocalDate.now()));
//        p1.add(new Person("C002", "身份证", "32020020229620163", "王水", sex.female));
//        new PersonManager().writeToFile(p1);
        launch(args);
    }
}
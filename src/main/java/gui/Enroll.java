package gui;

import auth.Face;
import auth.Fingerprint;
import auth.Voice;
import auth.User;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Enroll extends Application {
    private User user = new User();
    protected Face face = new Face(user);
    protected Fingerprint fingerprint = new Fingerprint(user);
    protected Voice voice = new Voice(user);

    @Override
    public void start(Stage primaryStage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Enroll.fxml"));
        primaryStage.setTitle("BioLogin");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }

}

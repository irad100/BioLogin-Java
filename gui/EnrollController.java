package gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class EnrollController extends Enroll implements Initializable
{
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
    }
    @FXML
    private void enroll(ActionEvent event)
    {
        Button b = (Button)event.getSource();
        switch (b.getId()) {
            case "faceBtn" :
                if(face.enroll()) { face.setEnabled(true); }
                break;
            case "fingerprintBtn" :
                if(fingerprint.enroll()) { fingerprint.setEnabled(true); }
                break;
            case "voiceBtn" :
                if(voice.enroll()) { voice.setEnabled(true); }
        }
    }
}
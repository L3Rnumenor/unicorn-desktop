package controller;

import annotation.Method;
import annotation.Modification;
import annotation.Parameter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import model.Main;
import model.User;
import model.Verifications;
import sun.misc.BASE64Encoder;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * Created by Micka on 07/03/2017.
 */
public class Connection implements Initializable {
    public static User account;
    private Main main;
    private boolean logs;

    @FXML
    private Label title;

    @FXML
    private TextField login;

    @FXML
    private TextField pass;

    @FXML
    private Button admins;

    @FXML
    private BorderPane border;

    @Override
    public void initialize(URL url, ResourceBundle re){
    }


    @Method(
            auteur = "MA",
            date = "07/03/2017",
            nomDeLaMethode = "buttonConn",
            portee = "private",
            typeRetour = "Void",
            description = "Boutton de connexion - Permet à l'utilisateur de se connecter",
            parametres =
                    {
                            @Parameter(
                                    type = "ActionEvent",
                                    nom = "event",
                                    description = "Evenement"
                            )
                    },
            modifications =
                    {
                            @Modification(
                                    auteur = "AED",
                                    dateModification = "27/05/2017",
                                    descriptionModification = "Ajout de l'annotation"
                            )
                    }
    )
    @FXML
    private void buttonConn(ActionEvent event) throws IOException {
        Verifications verif = new Verifications();
        if(verif.isNotEmpty(login.getText()) && verif.isNotEmpty(pass.getText())){
            if (verif.isValidEmail(login.getText())) {
                BASE64Encoder encoder = new BASE64Encoder();
                String passEncode = encoder.encode(pass.getText().getBytes()).toString();
                User user = new User().findUser(login.getText());
                if (login.getText().equals(user.getMailAdress()) && passEncode.equals(user.getPassword())) {

                    if (user.getStatus().getId() == 2 || user.getStatus().getId() == 3) {
                        account = user;
                        main.sample();
                    } else {
                        error("L'utilisateur n'a pas les droits pour accéder à l'application");
                    }
                    logs = true;
                }
                if (!logs) {
                    error("Les identifiants ne sont pas valides");
                }
            }
            else{
                error("L'identifiant ne correspond pas à un mail valide");
            }
        }
        else{
            error("Un des champs est vide");
        }
    }

    public void error(String message){
        new Message(message);
        login.setText("");
        pass.setText("");
        login.requestFocus();
    }

    public void setMain(Main main){
        this.main = main;
    }
}

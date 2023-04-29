package jbr.javafx.expjavafx.control;

import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class CustomControl extends VBox {
    @FXML
    private LetterStatus ls1;
    @FXML
    private LetterStatus ls2;

    public CustomControl() {
        super(2);
        FXMLLoader fxmlLoader = new FXMLLoader(CustomControl.class.getResource("/fxml/custom-control.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    @FXML
    protected void doSomething() {
        ls1.setLetter(1, "A");
        System.out.println("The Button was clicked");
    }
}

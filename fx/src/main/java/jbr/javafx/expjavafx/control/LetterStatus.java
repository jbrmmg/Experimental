package jbr.javafx.expjavafx.control;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class LetterStatus extends HBox {
    private static final String RIGHT_PLACE = "22C55E";
    private static final String WRONG_PLACE = "EAB308";

    private final Letter[] status = new Letter[5];
    private boolean rightPlace;


    private void setInternalLetter(String letter) {
        for(Letter next : status) {
            next.setLetter(letter.substring(0,1));
        }
    }

    private void setStatus(boolean rightPlace) {
        for(Letter next : status) {
            next.setGood(rightPlace);
        }
    }

    public LetterStatus() {
        super(2);
        this.rightPlace = true;
        for(int i = 0; i < status.length; i++) {
            status[i] = new Letter();
            this.getChildren().add(status[i]);
        }
    }

    public void setLetter(int index, String letter) {
        if(letter.length() < 1) {
            setInternalLetter(" ");
        }

        setInternalLetter(letter);
    }

    public String getLetter() {
        return status[1].getLetter();
    }

    public void setRightPlace(boolean rightPlace) {
        this.rightPlace = rightPlace;
        setStatus(rightPlace);
    }

    public boolean getRightPlace() {
        return this.rightPlace;
    }
}

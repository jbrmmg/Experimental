package jbr.javafx.expjavafx.control;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Letter extends StackPane {
    private final Rectangle background;
    private final Text letter;
    private boolean good;

    private void internalSetGood(boolean good) {
        this.good = good;
        if(good) {
            this.background.setFill(Color.rgb(0x22, 0xc5, 0x5e));
        } else {
            this.background.setFill(Color.rgb(0xea, 0xb3, 0x08));
        }
    }

    public Letter() {
        this.background = new Rectangle(60, 60);
        this.background.setStroke(Color.BLACK);
        setGood(true);

        this.letter = new Text("A");
        this.letter.setFont(Font.font("Arial", 55));
        this.letter.setFill(Color.BLACK);

        getChildren().addAll(this.background,letter);
    }

    public void setLetter(String letter) {
        this.letter.setText(letter);
    }

    public String getLetter() {
        return this.letter.getText();
    }

    public void setGood(boolean good) {
        internalSetGood(good);
    }

    public Boolean getGood() {
        return this.good;
    }
}

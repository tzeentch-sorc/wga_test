import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class Main extends Application {
    private Field field;
    private List<Button> buttonArea;
    private final String redClass = "-fx-background-color: red;";
    private final String redHoverClass = "-fx-background-color: crimson;";
    private final String redClickedClass = "-fx-background-color: maroon;";
    private final String yellowClass = "-fx-background-color: gold;";
    private final String yellowHoverClass = "-fx-background-color: goldenRod;";
    private final String yellowClickedClass = "-fx-background-color: peru;";
    private final String greenClass = "-fx-background-color: MediumSeaGreen ;";
    private final String greenHoverClass = "-fx-background-color: forestgreen;";
    private final String greenClickedClass = "-fx-background-color: darkgreen;";
    private final String blockClass = "-fx-background-color: darkgrey;";
    private final String emptyClass = "-fx-background-color: white;";
    private final String emptyHoverClass = "-fx-background-color: lightblue;";

    private int clickedIndex = -1;

    private GridPane mainPane;

    public Main(){
        field = new Field();
        buttonArea = new ArrayList<>();
        field.getPlayArea().forEach(elem -> {
            Button b = new Button(elem.toString());
            customizeButton(b, elem);
            buttonArea.add(b);
        });
    }

    public static void main(String args[]){
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("WGA_test");

        mainPane = new GridPane();
        GridPane typePane = new GridPane();
        VBox vBox = new VBox();
        AnchorPane ap = new AnchorPane();
        vBox.setSpacing(10);
        final int numCols = 5 ;
        final int numRows = 5 ;

        for (int i = 0; i < numCols; i++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPercentWidth(100.0 / numCols);
            mainPane.getColumnConstraints().add(colConst);
            typePane.getColumnConstraints().add(colConst);
        }
        for (int i = 0; i < numRows; i++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPercentHeight(100.0 / numRows);
            mainPane.getRowConstraints().add(rowConst);
        }

        refillGrid();

        Button red = new Button("Red");
        red.setStyle(redClass);
        red.setMinSize(100, 100);
        red.setMaxSize(100, 100);
        red.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
        Button yellow = new Button("Yellow");
        yellow.setStyle(yellowClass);
        yellow.setMinSize(100, 100);
        yellow.setMaxSize(100, 100);
        yellow.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
        Button green = new Button("Green");
        green.setStyle(greenClass);
        green.setMinSize(100, 100);
        green.setMaxSize(100, 100);
        green.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));



        typePane.add(red, 0, 0);
        typePane.add(yellow, 2 , 0);
        typePane.add(green, 4 , 0);

        typePane.setMinSize(500, 100);
        typePane.setMaxSize(500, 100);


        vBox.getChildren().addAll(typePane, mainPane);
        vBox.setAlignment(Pos.CENTER);

        ap.getChildren().addAll(vBox);
        AnchorPane.setLeftAnchor(vBox, 10d);
        AnchorPane.setTopAnchor(vBox, 10d);

        Scene sc = new Scene(ap, 520, 630);
        stage.setMinWidth(520);
        stage.setMinHeight(630);
        stage.setResizable(false);
        stage.setScene(sc);
        stage.show();
        showRules();
    }

    private void showRules(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("РљР°Рє РёРіСЂР°С‚СЊ?");
        alert.setHeaderText(null);
        alert.getDialogPane().setMinHeight(200);
        alert.setContentText("РўСЂРµР±СѓРµС‚СЃСЏ РІС‹СЃС‚Р°РІРёС‚СЊ С„РёС€РєРё РІ С‚СЂРё СЃС‚РѕР»Р±С†Р° РїРѕ С†РІРµС‚Р°Рј, СѓРєР°Р·Р°РЅРЅС‹Рј РЅР°Рґ РїРѕР»РµРј.\n" +
                "Р§С‚РѕР±С‹ РІС‹Р±СЂР°С‚СЊ С„РёС€РєСѓ - РЅР°Р¶Р°С‚СЊ Р›РљРњ, Р·Р°С‚РµРј РІС‹Р±СЂР°С‚СЊ СЃРІРѕР±РѕРґРЅРѕРµ РїРѕР»Рµ СЃ РїРѕРјРѕС‰СЊСЋ Р›РљРњ\n" +
                "РЎРІРѕР±РѕРґРЅС‹С… РїРѕР»СЏ РІСЃРµРіРѕ 4. РћСЃС‚Р°РІС€РёРµСЃСЏ 6\nР·Р°РєСЂС‹С‚С‹ Р±Р»РѕРєР°РјРё. \n\nРЈРґР°С‡РЅРѕР№ РёРіСЂС‹!");
        alert.showAndWait();
    }

    private void customizeButton(Button b, BlockType elem){
        b.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(0), new BorderWidths(1))));
        b.setMaxSize(100,100);
        b.setMinSize(100,100);
        switch (elem){
            case RED:
                b.setOnMouseEntered(e -> {
                    if(buttonArea.indexOf(b)!=clickedIndex)
                    b.setStyle(redHoverClass);
                });
                b.setOnMouseExited(e->{
                    if(buttonArea.indexOf(b)!=clickedIndex)
                        b.setStyle(redClass);
                });
                break;
            case YELLOW:
                b.setOnMouseEntered(e -> {
                    if(buttonArea.indexOf(b)!=clickedIndex)
                        b.setStyle(yellowHoverClass);
                });
                b.setOnMouseExited(e->{
                    if(buttonArea.indexOf(b)!=clickedIndex)
                        b.setStyle(yellowClass);
                });
                break;
            case GREEN:
                b.setOnMouseEntered(e -> {
                    if(buttonArea.indexOf(b)!=clickedIndex)
                        b.setStyle(greenHoverClass);
                });
                b.setOnMouseExited(e->{
                    if(buttonArea.indexOf(b)!=clickedIndex)
                        b.setStyle(greenClass);
                });
                break;
            case EMPTY:
                b.setOnMouseEntered(e -> b.setStyle(emptyHoverClass));
                b.setOnMouseExited(e->b.setStyle(emptyClass));
                break;
        }

        switch (elem){
            case BLOCK: break;
            case EMPTY:
                b.setOnMouseClicked(e -> {
                    int emptyIndex = buttonArea.indexOf(b);
                    if(clickedIndex >= 0 && checkNeighborClicked(emptyIndex, clickedIndex)){
                        int code = field.moveBlock(clickedIndex, emptyIndex);
                        if(code >= 0){
                            Collections.swap(buttonArea,emptyIndex,clickedIndex);
                            refillGrid();
                            clickedIndex = -1;
                        }
                        if(code == 1) {
                            showWin();
                        }

                    }
                });
                break;
            default:
                b.setOnMouseClicked(e -> {
                    BlockType buttonType = BlockType.valueOf(b.getText());
                    int curIndex = buttonArea.indexOf(b);
                    if(clickedIndex >= 0){
                        setDefaultStyle(clickedIndex, buttonType);
                    }

                    if(clickedIndex != curIndex){
                        if(clickedIndex !=-1)
                            setDefaultStyle(clickedIndex, BlockType.valueOf(buttonArea.get(clickedIndex).getText()));
                        clickedIndex = curIndex;
                        b.setStyle(getClickedStyle(buttonType));
                    } else {
                        setDefaultStyle(clickedIndex, buttonType);
                        clickedIndex = -1;
                    }
                });
        }

        b.setStyle(getDefaultStyle(elem));
    }

    private void showWin(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("Congratulations! You won the game!\n Want to play again?");

        ButtonType restart = new ButtonType("Restart");
        ButtonType exit = ButtonType.CLOSE;

        alert.getButtonTypes().clear();
        alert.getButtonTypes().addAll(restart, exit);

        Optional<ButtonType> option = alert.showAndWait();

        if (option.get() == restart) {
           field.generate();
           buttonArea.clear();
           field.getPlayArea().forEach(elem -> {
                Button b = new Button(elem.toString());
                customizeButton(b, elem);
                buttonArea.add(b);
           });
           refillGrid();
        } else {
            Platform.exit();
        }
    }

    private void refillGrid(){
        mainPane.getChildren().clear();
        for (int i = 0; i < field.getSize(); i++) {
            for (int j = 0; j < field.getSize(); j++) {
                mainPane.add(buttonArea.get(i+j*5), i, j);
            }
        }
    }

    private void setDefaultStyle(int index, BlockType buttonType){
        if(index != -1)
            buttonArea.get(index).setStyle(getDefaultStyle(buttonType));
    }

    private boolean checkNeighborClicked(int emptyIndex, int clickedIndex){
        if(emptyIndex / 5 == clickedIndex / 5 && Math.abs(emptyIndex - clickedIndex) == 1) return true;
        else return emptyIndex % 5 == clickedIndex % 5 && Math.abs(emptyIndex - clickedIndex) == 5;
    }

    private String getDefaultStyle(BlockType bT){
        String style;
        switch (bT){
            case RED:
                style = redClass;
                break;
            case YELLOW:
                style = yellowClass;
                break;
            case GREEN:
                style = greenClass;
                break;
            case BLOCK:
                style = blockClass;
                break;
            default:
                style = emptyClass;
        }
        return style;
    }

    private String getClickedStyle(BlockType bT){
        String style;
        switch (bT){
            case RED:
                style = redClickedClass;
                break;
            case YELLOW:
                style = yellowClickedClass;
                break;
            case GREEN:
                style = greenClickedClass;
                break;
            default: style = emptyClass;

        }
        return style;
    }

}
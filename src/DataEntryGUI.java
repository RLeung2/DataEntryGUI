import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class DataEntryGUI extends Application {
    @Override
    public void start(Stage primaryStage) {
        VBox vbox = new VBox();
        StackPane stack = new StackPane();

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(5.5);
        grid.setVgap(5.5);

        TextField name1 = new TextField();
        TextField name2 = new TextField();
        TextField name3 = new TextField();
        TextField phone1 = new TextField();
        TextField phone2 = new TextField();
        TextField phone3 = new TextField();

        makePrompt(name1, "Name");
        makePrompt(name2, "Name");
        makePrompt(name3, "Name");
        makePrompt(phone1, "(###) ###-####");
        makePrompt(phone2, "(###) ###-####");
        makePrompt(phone3, "(###) ###-####");

        // Place nodes in the grid pane at positions column,row
        grid.add(name1, 0, 0);
        grid.add(phone1, 1, 0);
        grid.add(name2, 0, 1);
        grid.add(phone2, 1, 1);
        grid.add(name3, 0, 2);
        grid.add(phone3, 1, 2);

        Button profileButton = createProfileButton(name1, name2, name3, phone1, phone2, phone3);

        stack.getChildren().add(profileButton);

        vbox.getChildren().add(grid);
        vbox.getChildren().add(stack);
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(5.5);

        name1.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                colorText(name1, validateName(name1));
            }
        });

        name2.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                colorText(name2, validateName(name2));
            }
        });

        name3.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                colorText(name3, validateName(name3));
            }
        });

        phone1.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                colorText(phone1, validatePhone(phone1));
            }
        });

        phone2.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                colorText(phone2, validatePhone(phone2));
            }
        });

        phone3.focusedProperty().addListener((arg0, oldValue, newValue) -> {
            if (!newValue) { // when focus lost
                colorText(phone3, validatePhone(phone3));
            }
        });

        Scene scene = new Scene(vbox, 500, 400);
        primaryStage.setTitle("DataEntryGUI");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void makePrompt(TextField text, String prompt){
        text.setPromptText(prompt);
    }

    public static boolean validateName(TextField name){
        return (name.getText().length() <= 20) && name.getText().matches("[A-Z]([a-z])* [A-Z]([a-z])*");
    }

    public static boolean validatePhone(TextField phone){
        return phone.getText().matches("\\(\\d{3}\\) \\d{3}-\\d{4}");
    }

    public static void colorText(TextField text, boolean valid){
        if(valid){
            text.setStyle("-fx-text-fill: black;");
        }
        else{
            text.setStyle("-fx-text-fill: red;");
        }
    }

    public static boolean validateAll(TextField... texts){
        for (TextField text : texts){
            if (text.getStyle() == "-fx-text-fill: red;"){
                return false;
            }
        }
        return true;
    }

    public static BooleanBinding isDisabled(TextField... texts){
        BooleanBinding bool = new BooleanBinding() {
            {
                for (TextField text : texts){
                    bind(text.textProperty());
                }
            }

            @Override
            protected boolean computeValue() {
                for (TextField text : texts){
                    if (text.getText().isEmpty()){
                        return true;
                    }
                }
                return false;
            }
        };
        return bool;
    }

    public static Button createProfileButton(TextField... texts){
        Button profileButton = new Button("Create Profiles");
        profileButton.setDisable(true);

        profileButton.disableProperty().bind(isDisabled(texts));

        profileButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if(validateAll(texts)){
                    lockTextFields(texts);
                    validPopUp();
                }
                else{
                    invalidPopUp();
                }
            }
        });
        return profileButton;
    }

    public static void lockTextFields(TextField... texts){
        for (TextField text : texts){
            text.setEditable(false);
        }
    }

    public static void validPopUp(){
        Label popUpLabel = new Label("The profiles have been saved and added to the database.");

        VBox popUpVBox = new VBox();
        popUpVBox.setSpacing(5.5);
        popUpVBox.setAlignment(Pos.CENTER);

        StackPane popUpStack = new StackPane();
        popUpStack.getChildren().add(popUpLabel);

        Button closeButton = new Button("Close");

        popUpVBox.getChildren().add(popUpStack);
        popUpVBox.getChildren().add(closeButton);

        Scene secondScene = new Scene(popUpVBox, 700, 150);

        // New window (Stage)
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle("Data Saved");
        popUpWindow.setScene(secondScene);

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popUpWindow.close();
            }
        });

        popUpWindow.show();
    }

    public static void invalidPopUp(){
        Label popUpLabel = new Label("INVALID INPUT: you have attempted to provide one or more invalid input(s). Please correct the information displayed in red and retry.");

        VBox popUpVBox = new VBox();
        popUpVBox.setSpacing(5.5);
        popUpVBox.setAlignment(Pos.CENTER);

        StackPane popUpStack = new StackPane();
        popUpStack.getChildren().add(popUpLabel);

        Button closeButton = new Button("Close");

        popUpVBox.getChildren().add(popUpStack);
        popUpVBox.getChildren().add(closeButton);

        Scene secondScene = new Scene(popUpVBox, 1000, 150);

        // New window (Stage)
        Stage popUpWindow = new Stage();
        popUpWindow.setTitle("Invalid input error");
        popUpWindow.setScene(secondScene);

        closeButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                popUpWindow.close();
            }
        });

        popUpWindow.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

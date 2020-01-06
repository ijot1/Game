import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class FxInputEventExample6 extends Application
{
    // Create the LoggingArea
    TextArea loggingArea = new TextArea("");

    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        // Create the Label
        Label label = new Label("Name:");
        // Create the TextField
        TextField textfield = new TextField();

        // Create the HBox
        HBox hbox = new HBox();
        // Set Padding and Spacing for the HBox
        hbox.setPadding(new Insets(20));
        hbox.setSpacing(20);
        // Add the children to the HBox
        hbox.getChildren().addAll(label, textfield);

        // Create the VBox
        VBox root = new VBox();
        // Set Padding and Spacing for the VBox
        root.setPadding(new Insets(20));
        root.setSpacing(20);
        // Add the children to the VBox
        root.getChildren().addAll(hbox, loggingArea);

        // Add key pressed and released events to the TextField
        textfield.setOnKeyPressed(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                handleEvent(keyEvent);
            }
        });

        textfield.setOnKeyReleased(new EventHandler<KeyEvent>()
        {
            public void handle(final KeyEvent keyEvent)
            {
                handleEvent(keyEvent);
            }
        });

        // Set the Padding and Border for the VBox
        root.setStyle("-fx-padding: 10;" +
                "-fx-border-style: solid inside;" +
                "-fx-border-width: 2;" +
                "-fx-border-insets: 5;" +
                "-fx-border-radius: 5;" +
                "-fx-border-color: blue;");

        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("A Key Pressed and Released Events Example");
        // Display the Stage
        stage.show();
    }

    // Helper Methods for Event Handling
    public void handleEvent(KeyEvent e)
    {
        // Get the Type of the Event
        String type = e.getEventType().getName();

        // Get the KeyCode of the Event
        KeyCode keyCode = e.getCode();

        // Log the Information
        loggingArea.appendText(type + ": Key Code=" + keyCode.getName() +
                ", Text=" + e.getText()+ "\n");
        System.out.println("Mam cie");
        // Show the help window when the F1 key is pressed
        if (e.getEventType() == KeyEvent.KEY_PRESSED && e.getCode() == KeyCode.F1)
        {
            displayHelp();
            e.consume();
        }
    }

    public void displayHelp()
    {
        // Create the Text
        Text text = new Text("Please enter a name.");

        // Create the HBox
        HBox root = new HBox();
        // Set the Style of the HBox
        root.setStyle("-fx-background-color: yellow;");
        // Add the Children to the HBox
        root.getChildren().add(text);
        // Create the Scene
        Scene scene = new Scene(root, 300, 200);

        // Create the Stage
        Stage helpStage = new Stage();
        // Add the Scene to the Stage
        helpStage.setScene(scene);
        // Set the Title of the Stage
        helpStage.setTitle("Help");
        // Display the Stage
        helpStage.show();
    }
}

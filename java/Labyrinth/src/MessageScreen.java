/*
 * Its a GUI class used to inform the user about something
 * It is called by the main class
 */

import java.io.IOException;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.Text;

public class MessageScreen extends Stage{
	@FXML private Button back; //Button to close message
	@FXML private Text msg;    //message
	@FXML private Pane root;
	private FXMLLoader loader;
	private Scene scene;
	
	public void ShowMsg(String p_msg, Stage primaryStage) {
		this.initModality(Modality.WINDOW_MODAL);
		this.initOwner(primaryStage);
		
		try {
			//Connect to the respective .fxml file that describes the appearance of the window
			loader = new FXMLLoader(getClass().getResource("FXMLs/MessageScreen.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        scene = new Scene(root);
	        this.setScene(scene);
	        
	        //Set the text that will be shown and the text of the button to close the window
	        back.setText("Back");
	        msg.setText(p_msg);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
		this.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent default handling
        });
		
		back.setOnAction(event -> { //if button is clicked show the previous window and close this one
			primaryStage.show();
			this.close();
		});
		
		primaryStage.hide();//hide previous window and wait for the user to be done with this one
		this.showAndWait();
	}
}

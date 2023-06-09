import java.io.IOException;
import javafx.beans.property.StringProperty;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.*;

public class QuestionScreen extends Stage{
	@FXML private Text msg;
	@FXML private TextField input;
	@FXML private Pane root;
	private FXMLLoader loader;
	private Scene scene;
	
	public void Ask(String p_msg, Stage primaryStage, StringProperty value) {
		this.initModality(Modality.WINDOW_MODAL);
		this.initOwner(primaryStage);
		
		try {
			loader = new FXMLLoader(getClass().getResource("FXMLs/QuestionScreen.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        scene = new Scene(root);
	        this.setScene(scene);
	        msg.setText(p_msg);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
		scene.setOnKeyPressed(event -> {
			value.set(input.getText());
			this.close();
		});
		
		this.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent default handling
        });
		
		this.showAndWait();
	}
}

import java.io.IOException;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import javafx.scene.text.Text;

public class MessageScreen extends Stage{
	@FXML private Button back;
	@FXML private Text msg;
	@FXML private Pane root;
	private FXMLLoader loader;
	private Scene scene;
	
	public void ShowMsg(String p_msg, Stage primaryStage) {
		this.initModality(Modality.WINDOW_MODAL);
		this.initOwner(primaryStage);
		
		try {
			loader = new FXMLLoader(getClass().getResource("FXMLs/MessageScreen.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        scene = new Scene(root);
	        this.setScene(scene);
	        back.setText("Back");
	        msg.setText(p_msg);
	        //new Color(0.85, 0.63, 0.40, 1)
	        msg.setStyle("text-area-background: "+ "rgb(217, 160, 102)" +";");
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
		this.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent default handling
        });
		
		back.setOnAction(event -> {
			primaryStage.show();
			this.close();
		});
		
		primaryStage.hide();
		this.showAndWait();
	}
}

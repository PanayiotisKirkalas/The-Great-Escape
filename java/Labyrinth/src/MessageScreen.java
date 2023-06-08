import java.util.*;
import java.io.IOException;
import java.util.function.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;
import javafx.stage.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class MessageScreen extends Stage{
	@FXML private Button back;
	@FXML private TextArea msg;
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
	        msg.setText(p_msg);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		
		back.setOnAction(event -> {
			primaryStage.show();
			this.close();
		});
		
		primaryStage.hide();
		this.showAndWait();
	}
}

import java.util.*;
import java.io.IOException;
import java.util.function.*;
import javafx.scene.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;

public class MainMenuScreen extends Scene{
	private ArrayList<Supplier<String>> options; //Supplier is used to store a function that takes no arguments and returns a value, in this case
												 //of type String. It is used to add functionalities to buttons dynamically
	public String Replay;
	@FXML Pane root;
	@FXML Button button1;
	@FXML Button button2;
	@FXML Button button3;
	FXMLLoader loader;
	
	public MainMenuScreen() {
		super(new Pane());
		
		
		Replay = new String();
		options = new ArrayList<Supplier<String>>();
		for (int i = 0; i < 3; ++i) {
			options.add(() -> {return "";});
		}
		
		try {
			loader = new FXMLLoader(getClass().getResource("FXMLs/MainMenuScr.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        this.setRoot(root);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void SetOption(int index, Supplier<String> function, String ButtonName) {
		options.set(index, function);
		switch (index) {
		case 0:
			button1.setText(ButtonName);
			break;
		case 1:
			button2.setText(ButtonName);
			break;
		case 2:
			button3.setText(ButtonName);
			break;
		}
	}
	
	@FXML
	public void option1(ActionEvent e) {
		e.consume();
		Replay = options.get(0).get();
	}
	@FXML
	public void option2(ActionEvent e) {
		e.consume();
		Replay = options.get(1).get();
	}
	@FXML
	public void option3(ActionEvent e) {
		e.consume();
		Replay = options.get(2).get();
	}
}

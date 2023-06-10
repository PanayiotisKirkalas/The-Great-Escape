/*
 * Its a GUI class. It represents the main menu screen to let the user choose the mode and to either play or see the rules
 * Its is used by the main class
 */

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
	@FXML Pane root;
	@FXML Button button1;
	@FXML Button button2;
	@FXML Button button3;
	FXMLLoader loader;
	
	public MainMenuScreen() {
		super(new Pane());//the pane that is going to be used, root, is still null at this point
		
		options = new ArrayList<Supplier<String>>();//initialize 3 Suppliers
		for (int i = 0; i < 3; ++i) {
			options.add(() -> {return "";});
		}
		
		try {
			//Connect to the respective .fxml file that describes the appearance of the window
			loader = new FXMLLoader(getClass().getResource("FXMLs/MainMenuScr.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        this.setRoot(root);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void SetOption(int index, Supplier<String> function, String ButtonName) { 
		//this method is used to dynamically set a functionality to one of the buttons
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
	
	//these are read by the respective .fxml file and describes that the buttons, when clicked will call the one of the functions
	//dynamically stored in the options ArrayList
	@FXML
	public void option1(ActionEvent e) {
		e.consume();
		options.get(0).get();
	}
	@FXML
	public void option2(ActionEvent e) {
		e.consume();
		options.get(1).get();
	}
	@FXML
	public void option3(ActionEvent e) {
		e.consume();
		options.get(2).get();
	}
}

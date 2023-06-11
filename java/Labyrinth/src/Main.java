/*
 * The .jar file doesn't run. We couldn't figure out why
 * 
 * To build the javafx library, in Run Configurations add the VM argument: 
 * --module-path "C:\Path\To\Where\Javafx\Is\Stored\lib" --add-modules javafx.controls,javafx.fxml
 * 
 * This is the main class of the project. It calls the MainMenuScreen class and sets the functionalities of it's buttons
 * It also provides 2 methods to allow the other classes inform and ask the user about something through the 
 * MessageScreen and QuestionScreen classes
 */

import javafx.scene.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Main extends Application{
	public static final boolean _1v1_ = false;
	public static final boolean _Battle_Royale_ = true;
	static Stage myStage; //main window of the application

	static void MainMenu2(Stage stage, Scene prev, boolean p_mode) { //it sets the second main menu, after a mode is chosen
		Mode m = (!p_mode) ? new Mode1() : new Mode2();
		
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		MainMenuScr.SetOption(0, () -> {
			m.Play();
			return "true";
		}, "Play");
		MainMenuScr.SetOption(1, () -> {
			ShowMessage(myStage, m.Explanation);
			return "true";
		}, "How to play");
		MainMenuScr.SetOption(2, () -> {
			stage.setScene(prev);
			return "true";
		}, "Back");
		stage.setScene(MainMenuScr);
	}

	static void MainMenu(boolean go_a) { //it sets the first main menu that lets the user to choose a mode
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		MainMenuScr.SetOption(0, () -> {
			MainMenu2(myStage, MainMenuScr, _1v1_);
			return "1v1";
		}, "1v1");
		MainMenuScr.SetOption(1, () -> {
			MainMenu2(myStage, MainMenuScr, _Battle_Royale_);
			return "Battle Royale";
		}, "Battle Royale");
		MainMenuScr.SetOption(2, () -> {
			Platform.exit();
			return "Quit";
		}, "Quit");
		myStage.setScene(MainMenuScr);
		myStage.show();
	}
	
	public static void ShowMessage(Stage currStage, String message) { 
		//it informs the user about something through the MessageScreen class. Can be called by every other class
		MessageScreen msg = new MessageScreen();
		msg.ShowMsg(message, currStage);
	}
	
	public static String AskUser(Stage currStage, String question) {
		//it asks the user about something and returns the answer as a string through the QuestionScreen class. 
		//Can be called by every other class
		StringProperty value = new SimpleStringProperty("[DEBUG] Error qs not working"); //here the answer will be stored to be returned
																						 //it has error message by default in case failure
		QuestionScreen qs = new QuestionScreen();
		if (currStage == null) //if no previous window is provided from the caller class set main window as previous
			qs.Ask(question, myStage, value);
		else
			qs.Ask(question, currStage, value);
		
		return value.get();
	}
	
	public void start(Stage primaryStage) {
		myStage = primaryStage;
		
		myStage.setOnCloseRequest(event -> {
            event.consume(); // Consume the event to prevent default handling
            System.exit(0);
        });
		
		primaryStage.setResizable(false);
		MainMenu(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

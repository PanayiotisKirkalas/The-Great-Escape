import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Main extends Application{
	static GameScreen GS;
	static Stage myStage;
	
	static boolean IntToBool(int x) {
		return x != 0;
	}

	static int BoolToInt(boolean b) {
		return (b) ? 1 : 0;
	}

	static void MainMenu2(Stage stage, Scene prev, boolean p_mode) {
		Mode m = (!p_mode) ? new Mode1() : new Mode2();
		
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		MainMenuScr.SetOption(0, () -> {
			//System.out.println("pressed by builder 1");
			m.Play();
			return "true";
		}, "Play");
		MainMenuScr.SetOption(1, () -> {
			//System.out.println("pressed by builder 2");
			ShowMessage(myStage, m.Explanation);
			return "true";
		}, "How to play");
		MainMenuScr.SetOption(2, () -> {
			System.out.println("pressed by builder 3");
			stage.setScene(prev);
			return "true";
		}, "Back");
//		if (Boolean.parseBoolean(MainMenuScr.Replay)) Play = Play + " again";
//		
//		if (!Mode) {
//			MainMenuScr.SetOption(0, () -> {
//				Mode1 m = new Mode1();
//				m.Play();
//				return "true";
//			}, Play);
//			
//			MainMenuScr.SetOption(1, () -> {
//				//Show explanation
//				Mode1 m = new Mode1();
//				System.out.println(m.Explanation);
//				return "false";
//			}, "How to play");
//		}
//		else {
//			MainMenuScr.SetOption(0, () -> {
//				Mode2 m = new Mode2();
//				m.Play();
//				return "true";
//			}, Play);
//			
//			MainMenuScr.SetOption(1, () -> {
//				//Show explanation
//				Mode2 m = new Mode2();
//				System.out.println(m.Explanation);
//				return "false";
//			}, "How to play");
//		}
//		MainMenuScr.SetOption(2, () -> {
//			return "";
//		}, "Back");
		stage.setScene(MainMenuScr);
	}

	static void MainMenu(boolean go_a) {
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		//MainMenuScr.setTitle("The Great Escape");
		MainMenuScr.SetOption(0, () -> {
			System.out.println("[DEBUG]1v1");
			MainMenu2(myStage, MainMenuScr, false);
			return "1v1";
		}, "1v1");
		MainMenuScr.SetOption(1, () -> {
			System.out.println("[DEBUG]Battle Royale");
			MainMenu2(myStage, MainMenuScr, true);
			return "Battle Royale";
		}, "Battle Royale");
		MainMenuScr.SetOption(2, () -> {
			//System.out.println("[DEBUG]Quit");
			Platform.exit();
			return "Quit";
		}, "Quit");
		myStage.setScene(MainMenuScr);
		myStage.show();
	}

	
	public static void setStage(Stage stage) {
		myStage.hide();
		stage.show();
	}
	
	public static void ShowMessage(Stage currStage, String message) {
		MessageScreen msg = new MessageScreen();
		msg.ShowMsg(message, currStage);
	}
	
	public static String AskUser(Stage currStage, String question) {
		StringProperty value = new SimpleStringProperty("[DEBUG] Error qs not working");
		QuestionScreen qs = new QuestionScreen();
		if (currStage == null)
			qs.Ask(question, myStage, value);
		else
			qs.Ask(question, currStage, value);
		
		return value.get();
	}
	
	public void start(Stage primaryStage) {
		myStage = primaryStage;
		primaryStage.setResizable(false);
		MainMenu(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

import java.awt.event.KeyEvent;
import java.io.*;
import java.util.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.application.*;

public class Main extends Application{
	private static boolean run = true;
	
	static boolean IntToBool(int x) {
		return x != 0;
	}

	static int BoolToInt(boolean b) {
		return (b) ? 1 : 0;
	}

	static void MainMenu2(boolean go_a, boolean Mode) {
		char ch;
		int index = 0, n = 0;
		String Play = "Play";
		
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		MainMenuScr.SetOption(0, () -> {
			System.out.println("pressed by builder 1");
			return "true";
		}, Play);
		MainMenuScr.SetOption(1, () -> {
			System.out.println("pressed by builder 2");
			return "true";
		}, Play);
		MainMenuScr.SetOption(2, () -> {
			System.out.println("pressed by builder 3");
			MainMenuScr.close();
			return "true";
		}, Play);
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
		MainMenuScr.show();
	}

	static void MainMenu(boolean go_a) {
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		MainMenuScr.setTitle("The Great Escape");
		MainMenuScr.SetOption(0, () -> {
			System.out.println("[DEBUG]1v1");
			MainMenuScr.hide();
			MainMenu2(false, false);
			run = false;
			System.out.println(run);
			if (run) {
				
			}
			else 
				MainMenuScr.show();
			return "1v1";
		}, "1v1");
		MainMenuScr.SetOption(1, () -> {
			System.out.println("[DEBUG]Battle Royale");
			MainMenuScr.hide();
			MainMenu2(false, true);
			System.out.println("[DEBUG]Battle Royale");
			MainMenuScr.show();
			return "Battle Royale";
		}, "Battle Royale");
		MainMenuScr.SetOption(2, () -> {
			//System.out.println("[DEBUG]Quit");
			Platform.exit();
			return "Quit";
		}, "Quit");
		MainMenuScr.show();
	}

	public void start(Stage primaryStage) {
		primaryStage.setResizable(false);
		MainMenu(false);
	}
	
	public static void main(String[] args) {
		launch(args);
	}

}

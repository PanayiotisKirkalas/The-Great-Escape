import javafx.scene.*;
import javafx.stage.*;
import javafx.application.*;
import javafx.stage.WindowEvent;
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

	static void MainMenu(boolean go_a) {
		MainMenuScreen MainMenuScr = new MainMenuScreen();
		MainMenuScr.SetOption(0, () -> {
			MainMenu2(myStage, MainMenuScr, false);
			return "1v1";
		}, "1v1");
		MainMenuScr.SetOption(1, () -> {
			MainMenu2(myStage, MainMenuScr, true);
			return "Battle Royale";
		}, "Battle Royale");
		MainMenuScr.SetOption(2, () -> {
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

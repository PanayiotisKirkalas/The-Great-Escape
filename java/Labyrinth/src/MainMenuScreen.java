import java.util.*;
import java.io.IOException;
import java.util.function.*;
import javafx.scene.*;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.event.*;

public class MainMenuScreen extends Stage{
	private ArrayList<Supplier<String>> options; //The Runnable is not meant to be used for Multithreading
	//private JPanel panel;
	//@FXML private ArrayList<Button> buttons;
	public String Replay;
	@FXML Pane root;
	@FXML Scene scene;
	
	public MainMenuScreen() {
		Replay = new String();
		options = new ArrayList<Supplier<String>>();
		//buttons = new ArrayList<Button>();
		for (int i = 0; i < 3; ++i) {
			options.add(() -> {return "";});
			//buttons.add(new Button());
//			buttons.get(i).setOnAction(e -> {
//				System.out.println("[DEBUG] pressed");
//			});
		}
		
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLs/MainMenuScr.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        scene = new Scene(root);
	        setScene(scene);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
	}
	
	public void SetOption(int index, Supplier<String> function, String ButtonName) {
//		buttons.get(index).setText("test");
//		buttons.get(index).setOnAction(e -> {
//			System.out.println("[DEBUG] pressed");
//			Replay = function.get();
//		});
		options.set(index, function);
	}
	
//	public void SetTitle(String title) {
//		SetTitle(title);
//	}
	
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
	
//	@Override
//	public void start(Stage primaryStage) {
//		primaryStage.setScene(scene);
////		ImageIcon background = new ImageIcon("resources/Labyrinth_Background_pixelart.png");
////		JLabel label = new JLabel("", background, JLabel.CENTER);
////		
////		label.setBounds(0, 0, getWidth(), getHeight());
////		setLayout(new OverlayLayout(panel));
////		panel = new JPanel();
////		panel.add(label);
////		for (int i = 0; i < 3; ++i)
////			panel.add(buttons.get(i));
////		setContentPane(panel);
//		
////		buttons.get(0).addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				setVisible(false);
////				Replay = options.get(0).get();
////			}
////		});
////		buttons.get(1).addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				setVisible(false);
////				Replay = options.get(1).get();
////			}
////		});
////		
////		buttons.get(2).addActionListener(new ActionListener() {
////			public void actionPerformed(ActionEvent e) {
////				setVisible(true);
////				Replay = options.get(2).get();
////			}
////		});
//		buttons.get(0).setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				primaryStage.hide();
//				Replay = options.get(0).get();
//			}
//		});
//		buttons.get(1).setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				primaryStage.hide();
//				Replay = options.get(1).get();
//			}
//		});
//		buttons.get(2).setOnAction(new EventHandler<ActionEvent>() {
//			public void handle(ActionEvent e) {
//				primaryStage.hide();
//				Replay = options.get(2).get();
//			}
//		});
//		
//		primaryStage.show();
//		
//	}
}

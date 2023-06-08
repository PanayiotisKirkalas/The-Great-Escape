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
import javafx.scene.text.Text;

public class GameScreen extends Stage {
	static public final char space = ' ';
	static public final char unseen = 117;
	static public final char player = 'O';
	static public final char h_wall = '=';
	static public final char v_wall = '|';
	static public final char corner_wall = '+';
	static public final char start = 'S';
	static public final char finish = 'f';
	static public final char[] top = {'1', '2', '3', '4', '5', '6'};
	static public final char[] side = {'A', 'B', 'C', 'D', 'E', 'F'};
	
	@FXML private SplitPane root;
	@FXML private GridPane map;
	@FXML private Text pName;
	private FXMLLoader loader;
	private Scene scene;
	private Player myPlayer;
	private Labyrinth myLabyrinth;
	private Mode mode;
	private boolean visibility = true;
	
	private coordinate translate(int y, int x) {
		coordinate temp = coordinate.make_pair(y, x);
		temp.y_axis = (temp.y_axis * 2) + 2;
		temp.x_axis = (temp.x_axis * 2) + 2;
		return temp;
	}
	
	public GameScreen(Player p_player, Mode p_mode) {
		try {
			loader = new FXMLLoader(getClass().getResource("FXMLs/GameScreen.fxml"));
	        loader.setController(this);
	        root = loader.load();
	        scene = new Scene(root);
	        this.setScene(scene);
		} catch (IOException e) {
			System.out.print(e.getMessage());
		}
		mode = p_mode;
		myLabyrinth = p_player.getLabyrinth();
		myPlayer = p_player;
		
		this.setResizable(false);
	}
	
	public void Alter(coordinate pos, char state) {
		Image image;
		switch (state) {
		case player:
			image = new Image(getClass().getResourceAsStream("resources/player.png"));
			break;
		case start:
			image = new Image(getClass().getResourceAsStream("resources/S.png"));
			break;
		case finish:
			image = new Image(getClass().getResourceAsStream("resources/Finish.png"));
			break;
		case space:
			image = new Image(getClass().getResourceAsStream("resources/space.png"));
			break;
		case unseen:
			image = new Image(getClass().getResourceAsStream("resources/unseen.png"));
			break;
		case h_wall:
			image = new Image(getClass().getResourceAsStream("resources/horizontal_wall.png"));
			break;
		case v_wall:
			image = new Image(getClass().getResourceAsStream("resources/vertical_wall.png"));
			break;
		case corner_wall:
			image = new Image(getClass().getResourceAsStream("resources/corner_wall.png"));
			break;
		default:
			if ((state >= top[0] && state <= top[5]) || (state >= side[0] && state <= side[5])) {
				image = new Image(getClass().getResourceAsStream("resources/" + state + ".png"));
			}
			else 
				image = new Image(getClass().getResourceAsStream("resources/unseen.png"));
			break;
		}
		ImageView imageView = new ImageView(image);
		
		coordinate temp = translate(pos.y_axis, pos.x_axis);
		map.add(imageView, temp.x_axis, temp.y_axis);
	}
	
	public void Update() {
		for (int i = 0; i < 14; ++i) {
			for (int j = 0; j < 14; ++j) {
				Alter(coordinate.make_pair(i, j), myLabyrinth.ingameAt(coordinate.make_pair(i, j)));
			}
		}
	}
	
	public void WallVisibility(boolean on) {
		visibility = on;
		for (int i = 2; i < 14; ++i) {
			for (int j = 2; j < 14; ++j) {
				switch(myLabyrinth.ingameAt(coordinate.make_pair(i, j))) {
				case h_wall: case v_wall:
					if (!on)
						Alter(coordinate.make_pair(i, j), space);
					else
						Alter(coordinate.make_pair(i, j), myLabyrinth.ingameAt(coordinate.make_pair(i, j)));
				}
				
			}
		}
	}
	
	public void SetPlayer(Player p_player) {
		//myLabyrinth = p_player.getLabyrinth();
		myPlayer = p_player;
		pName.setText(myPlayer.getName());
		SetLabyrinth(myPlayer.getLabyrinth());
	}
	
	public void SetLabyrinth(Labyrinth l) {
		myLabyrinth = l;
		
		Update();
		WallVisibility(visibility);
	}
	
	public void EnableMovement() {
		scene.setOnKeyPressed(event -> {
			
			switch (event.getCode()) {
			case UP:
				mode.PassToPlayer(Player.KEY_UP);
				break;
			case DOWN:
				mode.PassToPlayer(Player.KEY_DOWN);
				break;
			case LEFT:
				mode.PassToPlayer(Player.KEY_LEFT);
				break;
			case RIGHT:
				mode.PassToPlayer(Player.KEY_RIGHT);
				break;
			default:
				break;
			}
		});
	}
	
	public void DisableMovement() {
		scene.setOnKeyPressed(null);
	}
	
	public void Finish() {
		this.close();
	}
}

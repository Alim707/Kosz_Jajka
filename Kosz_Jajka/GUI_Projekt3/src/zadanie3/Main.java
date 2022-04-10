package zadanie3;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JOptionPane;
import javafx.animation.Animation;
import javafx.animation.ParallelTransition;
import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application {
	private Stage primaryStage;
	public ListView<String> lv;
	public int pozycja = 1;
	public int zycia = 4;
	public boolean gram;
	public int punkty;
	public double timer;
	public String[] s;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.gram = false;
		timer = 0;
		punkty = 0;
		this.primaryStage = primaryStage;
		lv = new ListView<>();
		try {

			FileInputStream fileIn = new FileInputStream("data/Score.txt");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			s = (String[]) objectIn.readObject();
			lv.getItems().addAll(s);
			objectIn.close();

		} catch (Exception e) {
			// e.printStackTrace();
		}
		menu();

	}

	public void menu() {
		this.gram = false;
		GridPane gp = new GridPane();
		Button b1 = new Button("New Game");
		b1.setPrefSize(250, 100);
		b1.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					gra();
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		Button b2 = new Button("High Scores");
		b2.setPrefSize(250, 100);
		b2.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				score();
			}
		});
		Button b3 = new Button("Exit");
		b3.setPrefSize(250, 100);
		b3.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				Platform.exit();
			}
		});
		gp.add(b1, 0, 0);
		gp.add(b2, 0, 1);
		gp.add(b3, 0, 2);
		Scene scene = new Scene(gp, 250, 300);
		scene.getAccelerators().put(KeyCombination.keyCombination("CTRL+SHIFT+Q"), new Back(this));
		primaryStage.setTitle("Menu");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void score() {
		StackPane root = new StackPane();
		root.getChildren().add(lv);
		Scene scene = new Scene(root, 250, 500);
		scene.getAccelerators().put(KeyCombination.keyCombination("CTRL+SHIFT+Q"), new Back(this));
		primaryStage.setTitle("High Score");
		primaryStage.setScene(scene);
		primaryStage.show();

	}

	public void gra() throws InterruptedException {
		pozycja = 1;
		this.zycia = 4;
		this.punkty = 0;
		timer = 0;
		Group root = new Group();
		Rectangle rect = new Rectangle(0, 5, 30, 300);
		rect.setRotate(-50);
		rect.setFill(javafx.scene.paint.Color.SANDYBROWN);
		Rectangle rect2 = new Rectangle(0, 200, 30, 300);
		rect2.setRotate(-50);
		rect2.setFill(javafx.scene.paint.Color.SANDYBROWN);
		Rectangle rect3 = new Rectangle(775, 5, 30, 300);
		rect3.setRotate(50);
		rect3.setFill(javafx.scene.paint.Color.SANDYBROWN);
		Rectangle rect4 = new Rectangle(775, 200, 30, 300);
		rect4.setRotate(50);
		rect4.setFill(javafx.scene.paint.Color.SANDYBROWN);
		root.getChildren().add(rect);
		root.getChildren().add(rect2);
		root.getChildren().add(rect3);
		root.getChildren().add(rect4);
		///////////////////////////////////////////////////////
		File file = new File("data/Wolf.png");
		Image image = new Image(file.toURI().toString());
		ImageView iv = new ImageView(image);
		iv.setFitWidth(300);
		iv.setPreserveRatio(true);
		iv.setX(200);
		iv.setY(300);
		root.getChildren().add(iv);
		file = new File("data/Egg.png");
		image = new Image(file.toURI().toString());
		ImageView iv2 = new ImageView(image);
		iv2.setFitWidth(50);
		iv2.setPreserveRatio(true);
		egg(iv2);
		root.getChildren().add(iv2);
		/////////////////////////////
		Scene scene = new Scene(root, 800, 600);
		scene.setFill(javafx.scene.paint.Color.GREENYELLOW);
		scene.getAccelerators().put(KeyCombination.keyCombination("CTRL+SHIFT+Q"), new Back(this));
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {

			@Override
			public void handle(KeyEvent e) {
				if (e.getCode() == KeyCode.UP) {
					iv.setX(200);
					iv.setY(300);
					iv.setRotate(0);
					iv.setScaleX(1);
					pozycja = 1;

				} else if (e.getCode() == KeyCode.DOWN) {
					iv.setX(350);
					iv.setY(350);
					iv.setRotate(40);
					iv.setScaleX(-1);
					pozycja = 4;

				} else if (e.getCode() == KeyCode.RIGHT) {
					iv.setX(350);
					iv.setY(300);
					iv.setRotate(0);
					iv.setScaleX(-1);
					pozycja = 3;

				} else if (e.getCode() == KeyCode.LEFT) {
					iv.setX(200);
					iv.setY(350);
					iv.setRotate(-40);
					iv.setScaleX(1);
					pozycja = 2;

				}

			}
		});
		primaryStage.setTitle("Game");
		primaryStage.setScene(scene);
		primaryStage.setX(0);
		primaryStage.setY(0);
		primaryStage.show();
		gram = true;
		new Thread(new Runnable() {

			@Override
			public void run() {
				int i = 1;
				while (zycia > 0 && gram) {
					try {
						Thread.sleep(1500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					timer += 1.5;
					if (pozycja != i) {
						zycia--;
					} else
						punkty++;
					i++;
					if (i > 4)
						i = 1;
				}
				if (zycia == 0) {
					String[] wynik = new String[s.length + 1];
					for (int n = 0; n < s.length; n++) {
						wynik[n]=s[n];
					}
					wynik[s.length] = (String) JOptionPane.showInputDialog("Podaj swoj nick") + " " + punkty * timer;
					try {
						FileOutputStream fileOut = new FileOutputStream("data/Score.txt");
						ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
						objectOut.writeObject(wynik);
						objectOut.close();

					} catch (Exception ex) {
						ex.printStackTrace();
					}
					Platform.exit();

				}

			}
		}).start();
	}

	public void egg(ImageView iv) {
		PathTransition pathTransition = new PathTransition(Duration.seconds(6), new Rectangle());
		Path path = new Path();
		path.getElements().add(new MoveTo(50, 120));
		path.getElements().add(new LineTo(200, 260));
		path.getElements().add(new MoveTo(0, 300));
		path.getElements().add(new LineTo(150, 440));
		path.getElements().add(new MoveTo(750, 130));
		path.getElements().add(new LineTo(600, 270));
		path.getElements().add(new MoveTo(750, 330));
		path.getElements().add(new LineTo(600, 470));
		pathTransition.setNode(iv);
		pathTransition.setPath(path);
		RotateTransition rt = new RotateTransition(Duration.seconds(1.5), iv);
		rt.setFromAngle(0);
		rt.setByAngle(360);
		rt.setCycleCount(2);
		RotateTransition rt2 = new RotateTransition(Duration.seconds(1.5), iv);
		rt2.setFromAngle(0);
		rt2.setByAngle(-360);
		rt2.setCycleCount(2);
		ParallelTransition pt = new ParallelTransition();
		SequentialTransition sq = new SequentialTransition();
		sq.getChildren().addAll(rt, rt2);
		pt.getChildren().addAll(pathTransition, sq);
		pt.setCycleCount(Animation.INDEFINITE);
		pt.play();

	}

	public static void main(String[] args) {
		launch();
	}

}

package lewon_therras.interfacegraphique;


import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.jeu.Jeu;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class JavaFXPuissance4 extends Application {

	private static GridPane fullGrid,jGrid;
	private static P4Grid p4Grid;
	private static PlayerGrid j1Grid,j2Grid;
	private static Button validB;
	private static TextArea logs;

	public static void updateStatus(String text)
	{
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				logs.appendText(text+"\n");
			}
		});
	}

	public static void updateGrille(Grille grille)
	{
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				JavaFXPuissance4.p4Grid.updateGrille(grille);
			}
		});
	}

	public static int getCoup() {
		JavaFXPuissance4.setEnableButtons(true);
		int coup=p4Grid.getCoup();
		JavaFXPuissance4.setEnableButtons(false);
		return coup;
	}

	public void initPane(GridPane gp, int gap, int pad, boolean border)
	{
		gp.setHgap(gap);
		gp.setVgap(gap);
		gp.setPadding(new Insets(pad, pad, pad, pad));
		if (border)
			gp.setStyle("-fx-border: 2; -fx-border-color: black;");
	}

	@Override
	public void start(Stage primaryStage) {

		fullGrid=new GridPane();
		j1Grid=new PlayerGrid(Constantes.JOUEUR_1);
		j2Grid=new PlayerGrid(Constantes.JOUEUR_2);
		p4Grid=new P4Grid();
		logs=new TextArea();
		logs.setEditable(false);

		GridPane validGrid=new GridPane();
		initPane(validGrid, 10, 10, false);
		validB=new Button("Lancer partie !");

		validB.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				Joueur j1=genererJoueur(Constantes.JOUEUR_1);
				Joueur j2=genererJoueur(Constantes.JOUEUR_2);
				logs.setText("");
				lancerPartie(j1, j2);
			}
		});


		validGrid.add(validB, 0, 0);

		j1Grid.setAlignment(Pos.TOP_LEFT);
		j2Grid.setAlignment(Pos.TOP_RIGHT);
		validGrid.setAlignment(Pos.CENTER);
		p4Grid.setAlignment(Pos.BOTTOM_CENTER);

		initPane(j1Grid,10,25, true);
		initPane(j2Grid,10,25, true);

		p4Grid.setEnableButtons(false);

		jGrid=new GridPane();
		initPane(jGrid, 10, 25, false);
		jGrid.add(j1Grid,0,0);
		jGrid.add(j2Grid, 1, 0);
		jGrid.setAlignment(Pos.CENTER);

		fullGrid.add(jGrid,0,0,2,1);
		fullGrid.add(validGrid, 0, 1, 2, 1);
		fullGrid.add(p4Grid, 0, 2, 2, 2);
		fullGrid.add(logs, 2, 0,1,5);

		Scene scene = new Scene(fullGrid);
		primaryStage.setScene(scene);
		primaryStage.setOnCloseRequest(e -> Platform.exit());
		primaryStage.setTitle("Puissance 4 : Projet IA Lewon - Therras");
		primaryStage.setResizable(false);
		primaryStage.show();
	}

	public static void startIG() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				Application.launch(JavaFXPuissance4.class);
			}
		}).start();
	}

	public Joueur genererJoueur(int joueur)
	{
		PlayerGrid grid;
		if (joueur==Constantes.JOUEUR_1)
		{
			grid=j1Grid;
		}
		else
		{
			grid=j2Grid;
		}
		if (grid.getType().equals("Humain"))
		{
			return new Humain(grid.getNom(), joueur);
		}
		else
		{
			String nomJoueur=Constantes.IA_NAMES[(int)Math.floor(Math.random()*Constantes.IA_NAMES.length)];
			int iaType;
			if (grid.getIA().equals(Constantes.IA_ALGOS[0]))
				iaType=Constantes.IA_MINIMAX;
			else
				iaType=Constantes.IA_ALPHABETA;
			return new IA(nomJoueur, joueur, iaType, grid.getDifficulty());
		}
	}

	public static void finirPartie()
	{
		jGrid.setDisable(false);
		validB.setDisable(false);
	}

	public void lancerPartie(Joueur j1, Joueur j2)
	{
		jGrid.setDisable(true);
		validB.setDisable(true);
		Console c=new ConsoleIG();
		Jeu j=new Jeu(j1, j2, c);
		j.start();
	}

	public static void setEnableButtons(boolean enable) {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				JavaFXPuissance4.p4Grid.setEnableButtons(enable);
			}
		});
	}

}

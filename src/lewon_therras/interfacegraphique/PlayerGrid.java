package lewon_therras.interfacegraphique;

import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class PlayerGrid extends GridPane{

	private GridPane humanGrid,iaGrid;
	private ComboBox<String> playerType,ia;
	private ComboBox<Integer> difficulty;
	private TextField name;

	public void initPane(GridPane gp, int gap, int pad)
	{
		gp.setHgap(gap);
		gp.setVgap(gap);
		gp.setPadding(new Insets(pad, pad, pad, pad));
	}

	public PlayerGrid(int joueur)
	{
		humanGrid=new GridPane();
		iaGrid=new GridPane();

		initPane(humanGrid,2,5);
		initPane(iaGrid,2,5);

		Text scenetitle; 
		if (joueur==Constantes.JOUEUR_1)
		{
			scenetitle= new Text("Joueur 1");
			scenetitle.setStroke(Color.BLACK);
			scenetitle.setFill(Color.YELLOW);
			scenetitle.setStrokeWidth(0.2);
		}
		else
		{
			scenetitle=new Text("Joueur 2");
			scenetitle.setStroke(Color.BLACK);
			scenetitle.setFill(Color.RED);
			scenetitle.setStrokeWidth(0.6);
		}
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 18));
		add(scenetitle, 0, 0);

		playerType=new ComboBox<>(FXCollections.observableArrayList("IA","Humain"));
		add(playerType,0,1);


		//Panel de l'humain 1
		Label userName = new Label("Nom du joueur:");
		humanGrid.add(userName, 0, 0);

		name=new TextField();
		humanGrid.add(name, 0, 1);


		//Panel de l'ia 1
		ObservableList<String> olS=FXCollections.observableArrayList();
		for (int i=0;i<Constantes.IA_ALGOS.length;i++)
		{
			olS.add(Constantes.IA_ALGOS[i]);
		}
		ia=new ComboBox<>(olS);
		ia.setValue(Constantes.IA_ALGOS[0]);
		iaGrid.add(ia, 0, 0);

		ObservableList<Integer> olI=FXCollections.observableArrayList();
		for (int i=1;i<=Constantes.NB_TOUR_MAX;i++)
		{
			olI.add(i);
		}
		difficulty=new ComboBox<>(olI);
		difficulty.setValue(1);
		iaGrid.add(difficulty, 0, 1);
		iaGrid.setVisible(false);



		playerType.setValue("Humain");
		add(humanGrid,0,2);
		add(iaGrid,0,2);

		playerType.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (playerType.getValue().equals("Humain"))
				{
					iaGrid.setVisible(false);
					humanGrid.setVisible(true);
				}
				else
				{
					iaGrid.setVisible(true);
					humanGrid.setVisible(false);
				}
			}
		});
	}

	public String getType() {
		return playerType.getValue();
	}

	public String getNom() {
		return name.getText();
	}

	public String getIA() {
		return ia.getValue();
	}

	public int getDifficulty() {
		return difficulty.getValue();
	}
}

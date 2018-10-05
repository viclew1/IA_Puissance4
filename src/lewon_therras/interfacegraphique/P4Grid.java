package lewon_therras.interfacegraphique;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.net.URL;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class P4Grid extends GridPane{

	private Button[][] cases =new Button[Constantes.NB_LIGNES][Constantes.NB_COLONNES];
	private Button[] colonnes=new Button[Constantes.NB_COLONNES];
	private Integer coup=-1;
	private Image X,O,V,CHOIX;

	public P4Grid()
	{
		try {
			X=new Image(new FileInputStream(new File(getClass().getResource("images/X.png").toURI())));
			O=new Image(new FileInputStream(new File(getClass().getResource("images/O.png").toURI())));
			V=new Image(new FileInputStream(new File(getClass().getResource("images/V.png").toURI())));
			CHOIX=new Image(new FileInputStream(new File(getClass().getResource("images/CHOIX.png").toURI())));
		}
		catch(FileNotFoundException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		setStyle("-fx-border: 2; -fx-border-color: black;");
		for (int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for (int j=0;j<Constantes.NB_COLONNES;j++)
			{
				final int colonne=j;
				cases[i][j]=new Button();
				cases[i][j].setPadding(new Insets(0,0,0,0));
				cases[i][j].setGraphic(new ImageView(V));
				cases[i][j].setStyle(" -fx-opacity: 1.0 ;");
				cases[i][j].setOnMouseEntered(new EventHandler<Event>() {

					@Override
					public void handle(Event event) {
						for (Button b : colonnes)
							b.setVisible(false);
						colonnes[colonne].setVisible(true);
					}
				});
				cases[i][j].setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						setCoup(colonne+1);
					}
				});
				add(cases[i][j],j,i,1,1);
			}
		}
		for (int i=0;i<colonnes.length;i++)
		{
			colonnes[i]=new Button();
			colonnes[i].setGraphic(new ImageView(CHOIX));
			colonnes[i].setPadding(new Insets(0,0,0,0));
			colonnes[i].setAlignment(Pos.BOTTOM_CENTER);
			colonnes[i].setVisible(false);
			final int colonne=i;
			colonnes[i].setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					setCoup(colonne+1);
				}
			});
			add(colonnes[i],i,Constantes.NB_LIGNES,1,1);
		}

	}

	public synchronized void setCoup(int i)
	{
		coup=i;
		notify();
	}

	public void updateGrille(Grille grille) {
		for (int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for (int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if (grille.getCase(i, j)==Case.X)
					cases[Constantes.NB_LIGNES-i-1][j].setGraphic(new ImageView(X));
				else if (grille.getCase(i, j)==Case.O)
					cases[Constantes.NB_LIGNES-i-1][j].setGraphic(new ImageView(O));
				else if (grille.getCase(i, j)==Case.V)
					cases[Constantes.NB_LIGNES-i-1][j].setGraphic(new ImageView(V));
			}
		}
	}

	public void setEnableButtons(boolean enable)
	{
		for (Button b : colonnes)
		{
			b.setDisable(!enable);
		}
		for (int i=0;i<cases.length;i++)
		{
			for (int j=0;j<cases[0].length;j++)
			{
				cases[i][j].setDisable(!enable);
			}
		}
	}

	public synchronized int getCoup() {
		while(coup == -1) {
			try {
				wait();
			} catch(InterruptedException ie) {
				ie.printStackTrace();
			}
		}

		final int c=coup;
		coup=-1;
		return c;
	}

}

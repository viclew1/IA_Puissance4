package lewon_therras.interfacegraphique;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.ui.Console;
import fr.uha.ensisa.puissance4.util.Constantes;

public class ConsoleIG extends Console{

	
	

	public int getHumanCoup(String nom) {
		JavaFXPuissance4.updateStatus("Coup de "+nom+" : ");
		return JavaFXPuissance4.getCoup();
	}

	public void reflexionIA(String nom)
	{
		JavaFXPuissance4.updateStatus(nom+" reflechit ...");
	}

	public void afficherCoup(Joueur joueurCourant, int coup, long t) {
		JavaFXPuissance4.updateStatus(joueurCourant.getNom() +" a choisi de mettre un jeton dans la colonne "+(coup+1)+" apres "+timeToString(t)+" de reflexion\n");
	}

	public void lancementPartie(Joueur joueur1, Joueur joueur2)
	{
		JavaFXPuissance4.updateStatus("************* Début de partie ************");
		JavaFXPuissance4.updateStatus("Joueur 1 : "+joueur1.getNom()+" ("+joueur1.getTypeNom()+")");
		JavaFXPuissance4.updateStatus("Joueur 2 : "+joueur2.getNom()+" ("+joueur2.getTypeNom()+")");		
	}
	
	public void lancementTour(int tour, Joueur joueurCourant, Grille grille)
	{
		JavaFXPuissance4.updateStatus("************* Tour "+tour+" ************");
		JavaFXPuissance4.updateStatus("C'est à "+joueurCourant.getNom()+" de jouer !");
		JavaFXPuissance4.updateGrille(grille);
	}



	public void afficherFinPartie(Partie partie) {
		String msg;
		switch(partie.getEtatPartie())
		{
			case Constantes.VICTOIRE_JOUEUR_1 : 
				msg="VICTOIRE "+partie.getJoueur1().getNom();
				break;
			case Constantes.VICTOIRE_JOUEUR_2 : 
				msg="VICTOIRE "+partie.getJoueur2().getNom();
				break;
			default : 
				msg="MATCH NUL";
				break;
		}
		JavaFXPuissance4.updateStatus("************ "+msg+" en "+(partie.getTour()-1)+" tours ***************");
		JavaFXPuissance4.updateGrille(partie.getGrille());
		JavaFXPuissance4.updateStatus(partie.getJoueur1().getNom()+" : "+timeToString(partie.getTempsReflexionJ1()));
		JavaFXPuissance4.updateStatus(partie.getJoueur2().getNom()+" : "+timeToString(partie.getTempsReflexionJ2()));
		JavaFXPuissance4.updateStatus("******************************************************************");
		JavaFXPuissance4.finirPartie();
		
	}
	
	public void afficheCoupInvalide() {
		JavaFXPuissance4.updateStatus("COUP INVALIDE : Recommencez !");
	}
	
}

package fr.uha.ensisa.puissance4.data;


import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class Grille {

	private Case[][] grille;

	public Grille()
	{
		grille= new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for(int i=0;i<Constantes.NB_COLONNES;i++)
			for(int j=0;j<Constantes.NB_LIGNES;j++)
			{
				grille[i][j] = Case.V;
			}		
	}

	/**
	 * Constructeur qui créé une copie de la grille donné en argument
	 * @param original
	 */
	private Grille(Grille original)
	{
		//À compléter
		grille= new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for(int i=0;i<Constantes.NB_COLONNES;i++)
			for(int j=0;j<Constantes.NB_LIGNES;j++)
			{
				grille[i][j] = original.getCase(j, i);
			}
	}

	/**
	 * Renvoie le contenu de la case aux coordonnées données en argument
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public Case getCase(int ligne, int colonne)
	{
		return grille[colonne][ligne];
	}

	/**
	 * Indique s'il y a encore de la place dans la colonne indiquée
	 * @param colonne
	 * @return
	 */
	public boolean isCoupPossible(int colonne) {
		if(colonne>=0&&colonne<Constantes.NB_COLONNES)
		{
			return grille[colonne][Constantes.NB_LIGNES-1]==Case.V;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Ajoute le symbole indiqué dans la colonne indiquée
	 * ce qui permet de jouer ce coup
	 * @param colonne
	 * @param symbole
	 */
	public void ajouterCoup(int colonne, Case symbole) {
		for(int j=0;j<Constantes.NB_LIGNES;j++)
		{
			if(grille[colonne][j] == Case.V)
			{
				grille[colonne][j]= symbole;
				break;
			}
		}

	}

	/**
	 * Renvoie l'état de la partie
	 * @param symboleJoueurCourant
	 * @param tour
	 * @return
	 */
	public int getEtatPartie(Case symboleJoueurCourant, int tour)
	{
		int victoire;
		if(symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			victoire=Constantes.VICTOIRE_JOUEUR_1;
		}
		else
		{
			victoire=Constantes.VICTOIRE_JOUEUR_2;
		}
		int nbAlignes=0;
		//Vérification alignement horizontaux
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==Constantes.TAILLE_LIGNE)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement verticaux
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			for(int i=0;i<Constantes.NB_LIGNES;i++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==Constantes.TAILLE_LIGNE)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=0;j<Constantes.NB_COLONNES-3;j++)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j+x<Constantes.NB_COLONNES;x++)
				{
					if(grille[j+x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==Constantes.TAILLE_LIGNE)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}

		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=Constantes.NB_COLONNES-1;j>=3;j--)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j-x>=0;x++)
				{
					if(grille[j-x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==Constantes.TAILLE_LIGNE)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}

		if(tour==Constantes.NB_TOUR_MAX)
		{
			return Constantes.MATCH_NUL;
		}

		return Constantes.PARTIE_EN_COURS;
	}

	/**
	 * Donne un score à la grille en fonction du joueur 
	 * @param symboleJoueurCourant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant, int tour)
	{
		double valeur=0;
		int valeurGrille=getEtatPartie(symboleJoueurCourant, tour);
		Case symboleAdversaire;
		if (symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			symboleAdversaire=Constantes.SYMBOLE_J2;
			if (getEtatPartie(symboleAdversaire, tour)==Constantes.VICTOIRE_JOUEUR_2)
			{
				valeur-=1000*Math.pow(10, Constantes.TAILLE_LIGNE);
			}
			else if (valeurGrille==Constantes.VICTOIRE_JOUEUR_1)
			{
				valeur+=1000*Math.pow(10, Constantes.TAILLE_LIGNE);
			}
		}
		else
		{
			symboleAdversaire=Constantes.SYMBOLE_J1;
			if (getEtatPartie(symboleAdversaire, tour)==Constantes.VICTOIRE_JOUEUR_1)
			{
				valeur-=1000*Math.pow(10, Constantes.TAILLE_LIGNE);
			}
			else if (valeurGrille==Constantes.VICTOIRE_JOUEUR_2)
			{
				valeur+=1000*Math.pow(10, Constantes.TAILLE_LIGNE);
			}
		}


		if (valeur==0)
		{
			for (int i=Constantes.TAILLE_LIGNE-1;i>1;i--)
			{
				valeur+=evaluerAlignements(symboleJoueurCourant, i);
			}
		}

		return valeur;
	}


	/**
	 * On analyse une serie de 4 cases et on regarde si, parmis ces cases, il n y a pas le symbole du joueur adversaire, et le nombre d'occurences du symbole du joueur.
	 * @param alignement
	 * @param symboleJoueurCourant
	 * @param tailleLigne
	 * @return
	 */
	public boolean traiterAlignement(Case[] alignement, Case symboleJoueurCourant, int tailleLigne)
	{
		int casesOk = 0;

		for (int i=0;i<alignement.length;i++)
		{
			if (alignement[i]==symboleJoueurCourant)
			{
				casesOk++;
			}
			else if (alignement[i]!=Case.V)
			{
				return false;
			}
		}

		if (casesOk==tailleLigne) 
		{
			return true;
		}

		return false;
	}

	/**
	 * On analyse les lignes, colonnes et diagonales pour compter le nombre de serie de 4 cases comprenant tailleLigne fois le meme symbole, et aucune occurence
	 * du symbole adversaire. 
	 * 
	 * @param symboleJoueurCourant
	 * @param tailleLigne
	 * @return
	 */
	public double evaluerAlignements(Case symboleJoueurCourant, int tailleLigne)
	{
		Case symboleAdversaire;
		if (symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			symboleAdversaire=Constantes.SYMBOLE_J2;
		}
		else
		{
			symboleAdversaire=Constantes.SYMBOLE_J1;
		}
		int totalJoueur=0,totalAdv=0;
		Case[] alignement;
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES-(Constantes.TAILLE_LIGNE-1);j++)
			{
				alignement=new Case[Constantes.TAILLE_LIGNE];
				for (int x=0;x<Constantes.TAILLE_LIGNE;x++)
				{
					alignement[x]=getCase(i, j+x);
				}
				if (traiterAlignement(alignement, symboleJoueurCourant, tailleLigne))
				{
					totalJoueur++;
				}
				else if (traiterAlignement(alignement, symboleAdversaire, tailleLigne))
				{
					totalAdv++;
				}
			}
		}

		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			for(int i=0;i<Constantes.NB_LIGNES-(Constantes.TAILLE_LIGNE-1);i++)
			{
				alignement=new Case[Constantes.TAILLE_LIGNE];
				for (int x=0;x<Constantes.TAILLE_LIGNE;x++)
				{
					alignement[x]=getCase(i+x, j);
				}
				if (traiterAlignement(alignement, symboleJoueurCourant, tailleLigne))
				{
					totalJoueur++;
				}
				else if (traiterAlignement(alignement, symboleAdversaire, tailleLigne))
				{
					totalAdv++;
				}
			}
		}

		for(int i=0;i<Constantes.NB_LIGNES-(Constantes.TAILLE_LIGNE-1);i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES-(Constantes.TAILLE_LIGNE-1);j++)
			{
				alignement=new Case[Constantes.TAILLE_LIGNE];
				for (int y=0;y<Constantes.TAILLE_LIGNE;y++)
				{
					alignement[y]=getCase(i+y,j+y);
				}
				if (traiterAlignement(alignement, symboleJoueurCourant, tailleLigne))
				{
					totalJoueur++;
				}
				else if (traiterAlignement(alignement, symboleAdversaire, tailleLigne))
				{
					totalAdv++;
				}
			}
		}
		for(int i=(Constantes.TAILLE_LIGNE-1);i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES-(Constantes.TAILLE_LIGNE-1);j++)
			{
				alignement=new Case[Constantes.TAILLE_LIGNE];
				for (int y=0;y<Constantes.TAILLE_LIGNE;y++)
				{
					alignement[y]=getCase(i-y,j+y);
				}
				if (traiterAlignement(alignement, symboleJoueurCourant, tailleLigne))
				{
					totalJoueur++;
				}
				else if (traiterAlignement(alignement, symboleAdversaire, tailleLigne))
				{
					totalAdv++;
				}
			}
		}

		//On donne un poids supérieur sur les alignements de 3 et 2 pièces du joueur adversaire pour se focaliser sur la défense.
		return totalJoueur*9*Math.pow(10, tailleLigne)-totalAdv*10*Math.pow(10, tailleLigne);

	}


	/**
	 * Clone la grille
	 */
	public Grille clone()
	{
		Grille copy = new Grille(this);
		return copy;
	}

}

package fr.uha.ensisa.puissance4.jeu.algosIA;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;



public class Minimax extends Algorithm {



	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}

	@Override
	public int choisirCoup() {
		return minmaxDecision();
	}

	private double best=Constantes.MOINS_INFINI;

	public int minmaxDecision()
	{
		List<Integer> c=new ArrayList<Integer>(Constantes.NB_COLONNES);
		ExecutorService es = Executors.newCachedThreadPool();
		for (int i=0;i<Constantes.NB_COLONNES;i++)
		{
			final int colonne=i;
			es.execute(new Runnable() {

				@Override
				public void run() {
					Grille tmp=grilleDepart.clone();
					if (tmp.isCoupPossible(colonne))
					{
						tmp.ajouterCoup(colonne, symboleMax);
						double value=minValue(tmp,tourDepart);
						if (value>best)
						{
							c.clear();
							c.add(colonne);
							best=value;
						}
						else if (value==best)
						{
							c.add(colonne);
						}
					}
				}});
		}

		es.shutdown();
		try {
			es.awaitTermination(15, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return c.get(new Random().nextInt(c.size()));
	}

	public double minValue(Grille g, int tour)
	{
		if (terminalTest(g, tour))
		{
			return g.evaluer(symboleMax, tour);
		}
		double v = 500000;
		for (int i=0;i<Constantes.NB_COLONNES;i++)
		{
			final int colonne=i;
			Grille g2=g.clone();
			if (g2.isCoupPossible(colonne))
			{
				g2.ajouterCoup(colonne, symboleMin);
				v=Math.min(v, maxValue(g2, tour+1));
			}
		}


		return v;
	}

	public double maxValue(Grille g, int tour)
	{
		if (terminalTest(g, tour))
		{
			return g.evaluer(symboleMax, tour);
		}
		double v = -500000;
		for (int i=0;i<Constantes.NB_COLONNES;i++)
		{
			final int colonne=i;
			Grille g2=g.clone();
			if (g2.isCoupPossible(colonne))
			{
				g2.ajouterCoup(colonne, symboleMax);
				v=Math.max(v, minValue(g2, tour+1));
			}
		}


		return v;
	}

}

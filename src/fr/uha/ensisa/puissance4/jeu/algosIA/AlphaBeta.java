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


public class AlphaBeta extends Algorithm {

	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);

	}

	@Override
	public int choisirCoup() {
		return alphabetaDecision(grilleDepart);
	}

	private double best=-0.5;

	public int alphabetaDecision(Grille grilleDepart) {
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
						double value=minValue(tmp,Constantes.MOINS_INFINI,Constantes.PLUS_INFINI,tourDepart);
						if (value>best || best==-0.5)
						{
							c.clear();
							c.add(colonne);
							best=value;
						}
						else if (value==best)
						{
							c.add(colonne);
							best=value;
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

	public double maxValue(Grille g, double alpha, double beta, int tour) {
		if (terminalTest(g, tour))
		{
			return g.evaluer(symboleMax, tour);
		}
		double v=Constantes.MOINS_INFINI;
		for (int i=0;i<Constantes.NB_COLONNES;i++)
		{
			Grille g2=g.clone();
			if (g2.isCoupPossible(i))
			{
				g2.ajouterCoup(i, symboleMax);
				v=Math.max(v,minValue(g2, alpha, beta, tour+1));
				if (v>=beta)
					return v;
				alpha=Math.max(alpha,v);
			}
		}
		return v;
	}

	public double minValue(Grille g, double alpha, double beta, int tour) {
		if (terminalTest(g, tour))
		{
			return g.evaluer(symboleMax, tour);
		}
		double v=Constantes.PLUS_INFINI;
		for (int i=0;i<Constantes.NB_COLONNES;i++)
		{
			Grille g2=g.clone();
			if (g2.isCoupPossible(i))
			{
				g2.ajouterCoup(i, symboleMin);
				v=Math.min(v,maxValue(g2, alpha, beta, tour+1));
				if (v<=alpha)
					return v;
				beta=Math.min(beta,v);
			}
		}
		return v;
	}


}

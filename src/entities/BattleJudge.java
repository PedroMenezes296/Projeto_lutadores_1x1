package entities;

public interface BattleJudge {
	 	Outcome decide(Guerreiro a, Guerreiro b);
	    double timeToKill(Guerreiro atacando, Guerreiro defendendo); // útil para depurar/exibir
}

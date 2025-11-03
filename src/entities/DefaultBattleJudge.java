package entities;

public class DefaultBattleJudge implements BattleJudge {

	// Parâmetros globais (ajuste conforme balanceamento desejado)
    private static final double VIDA_PADRAO = 100.0;
    private static final double LAMBDA_MITIGACAO = 0.6;     // quanto a defesa reduz a força do atacante
    private static final double DISTANCIA_INICIAL = 10.0;   // distância inicial entre lutadores

    @Override
    public Outcome decide(Guerreiro a, Guerreiro b) {
        double ttkA = timeToKill(a, b);
        double ttkB = timeToKill(b, a);

        if (Double.isInfinite(ttkA) && Double.isInfinite(ttkB)) {
            // Ninguém consegue causar dano (DPS zero para ambos) => empate
            return Outcome.TIE;
        }
        if (ttkA < ttkB) return Outcome.WIN;
        if (ttkB < ttkA) return Outcome.LOSE;
        return Outcome.TIE;
    }

    @Override
    public double timeToKill(Guerreiro atacante, Guerreiro defensor) {
        double dps = dpsContra(atacante, defensor);
        if (dps <= 0) {
            // Não consegue penetrar a defesa do oponente => nunca derruba
            return Double.POSITIVE_INFINITY;
        }

        double delay = atrasoInicial(atacante.getAlcance());
        // TTK = tempo para remover a vida + eventual atraso para alcançar o alvo
        return (VIDA_PADRAO / dps) + delay;
    }

    // --- Helpers privados ---

    private double dpsContra(Guerreiro atacante, Guerreiro defensor) {
        // DPS = max(0, forca_atacante - LAMBDA * defesa_defensor)
        double bruto = atacante.getForca() - (LAMBDA_MITIGACAO * defensor.getDefesa());
        return Math.max(0.0, bruto);
    }

    private double atrasoInicial(double alcanceDoAtacante) {
        // Δt = max(0, DISTANCIA_INICIAL - alcance)
        return Math.max(0.0, DISTANCIA_INICIAL - alcanceDoAtacante);
    }
}

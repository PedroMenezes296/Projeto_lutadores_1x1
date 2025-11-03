README — Projeto Lutadores 1x1 (CLI)

Um projetinho simples em Java para criar lutadores, administrar um roster (lista de personagens), e realizar duelos 1x1 no terminal. O foco é aprender organização de código, modelagem e uma lógica de combate clara.

✨ Visão geral

Interface de linha de comando (CLI) com menu.

Cadastro de lutadores (força, defesa, alcance, tipo).

Ranking por rating (usando Comparable).

Duelo 1x1 com regra de tempo-para-derrubar (TTK):

DPS = max(0, forca - λ * defesaDoOponente)

Atraso inicial = max(0, DISTANCIA_INICIAL - alcance)

TTK = (VIDA / DPS) + atraso

Opcional: vantagem de tipo para desempate/ajuste fino.

🧱 Estrutura do projeto
src/
├─ application/
│  └─ Program.java               # CLI (menu principal)
├─ entities/
│  ├─ Guerreiro.java             # Entidade (id, tipo, atributos, rating, compareTo)
│  ├─ Lutadores.java             # Enum: GUERREIRO, ESCUDEIRO, ARQUEIRO
│  └─ Lutadores_interface.java   # (opcional/legado) interface original do usuário
└─ services/
   └─ RosterService.java         # CRUD e consultas do roster


Se você já implementou o juiz de batalha:

src/
└─ battle/
   ├─ BattleJudge.java
   └─ DefaultBattleJudge.java  # Implementação do modelo TTK (+ desempates)

🧩 Principais classes
entities.Guerreiro

Campos (valores brutos recomendados): forca, defesa, alcance, tipoGuerreiro, UUID id.

rating() com pesos (ex.: 0.5/0.3/0.2).

compareTo para ordenar por rating (desc).

equals/hashCode por UUID.

toString() amigável para debug.

Observação importante: se você pondera atributos no construtor e também pondera no rating(), ocorre dupla ponderação. O ideal é guardar valores brutos e aplicar pesos apenas no cálculo do rating e da batalha.

entities.Lutadores

Enum: GUERREIRO, ESCUDEIRO, ARQUEIRO.

services.RosterService

Mantém uma List<Guerreiro> encapsulada.

Métodos úteis:

UUID adicionarLutador(Guerreiro g)

boolean update(UUID id, Lutadores novoTipo, Double novaForca, Double novaDefesa, Double novoAlcance)

boolean removerLutador(UUID id)

Optional<Guerreiro> findById(UUID id)

List<Guerreiro> listAll() (imutável)

List<Guerreiro> listSortedByRatingDesc() (ranking)

int size()

application.Program

Menu com as opções:

Adicionar um lutador

Escolher lutadores para 1x1 (seleção por índice da lista)

Listar ranking e ID

Função helper imprimirComIndices(List<Guerreiro>).

(Opcional) battle.BattleJudge / battle.DefaultBattleJudge

Outcome decide(Lutador a, Lutador b)

double timeToKill(Lutador atacante, Lutador defensor)

Parâmetros de combate (ajustáveis):

VIDA_PADRAO, LAMBDA_MITIGACAO, DISTANCIA_INICIAL

Desempates e/ou vantagens de tipo (ex.: Guerreiro>Arqueiro>Escudeiro>Guerreiro).

▶️ Como compilar e executar

Sem build tool, usando javac/java (ajuste o caminho conforme sua estrutura):

# dentro da pasta do projeto
javac -d out $(find ./src -name "*.java")
java -cp out application.Program


Com Maven/Gradle, configure o plugin/entries conforme seu padrão e rode mvn exec:java ou gradle run.

🕹️ Uso (fluxo do menu)

Adicionar lutador

Escolha o tipo (GUERREIRO/ESCUDEIRO/ARQUEIRO).

Informe força, defesa, alcance.

O sistema gera e mostra o UUID (ID).

Luta 1x1

O programa lista os lutadores numerados 1..N.

Digite o número do Lutador A e do Lutador B.

(Quando o juiz estiver plugado) mostra TTK de cada lado e o vencedor.

Ranking e ID

Lista os lutadores por rating (desc) com seus UUIDs.

🧠 Decisões de design

ID estável (UUID) para cada lutador — evita depender do índice da lista.

Service (RosterService) sem prints: UI (Program) decide como exibir.

Comparable vs julgamento de luta:

compareTo → ranking estático (independe do oponente).

decide(A,B) (juiz) → resultado do duelo (considera o oponente).

Empates:

Primeiro por igualdade de TTK (com ε).

Depois, vantagem de tipo (opcional).

Em seguida, rating maior, defesa maior… por fim, TIE.

🧪 Exemplos de saída (resumidos)
--- Ranking por Rating (desc) ---
1) ID=... | tipo=GUERREIRO | rating=3.40
2) ID=... | tipo=ARQUEIRO  | rating=3.10

--- Escolha os lutadores (por número) ---
1) ID=... | F=.. D=.. A=.. | rating=.. | tipo=..
2) ID=... | F=.. D=.. A=.. | rating=.. | tipo=..

Número do Lutador A: 1
Número do Lutador B: 2
Selecionados:
A -> ... | GUERREIRO
B -> ... | ARQUEIRO


(Com juiz plugado, exibiria TTKs e o vencedor.)

🛠️ Dicas e troubleshooting

Lista vazia / NullPointer
Nunca chame imprimirComIndices(null). Peça a lista ao service:
List<Guerreiro> lista = roster.listSortedByRatingDesc();
e verifique isEmpty() antes.

Scanner “pulando” entrada
Após nextInt(), consuma a quebra de linha com sc.nextLine() antes de ler String.

Instância única do roster
Crie RosterService roster = new RosterService(); uma vez, fora do switch, e mantenha um loop do menu. Se você reiniciar o programa, a lista recomeça vazia.

Dupla ponderação
Evite atribuir forca = forca*0.5 e depois calcular rating com pesos. Guarde valores brutos e aplique pesos só no cálculo.

🚀 Próximos passos

Adicionar nome ao Guerreiro e exibir no ranking/listas.

Implementar o BattleJudge (TTK + vantagem de tipo).

Registrar histórico dos combates (lista de MatchResult).

Modo liga (todos contra todos) e mata-mata (torneio).

Persistência simples em JSON (salvar/carregar roster e stats).

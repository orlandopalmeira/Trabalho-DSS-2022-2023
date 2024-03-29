package business.campeonatos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Circuito {
    private String nome;
    private int numVoltas;
    private boolean clima; // true-chove | false-seco
    private int comprimento;
    private int nSetores;
    private List<Setor> setores; // acho que faz sentido ter uma lista porque há uma noção de ordem nos setores.
    private List<Jogador> participantes; // terá a ordem dos jogadores na corrida
    private Map<Jogador,Integer> dnf;
    
    private Circuito(){}

    public Circuito(String nome, int numVoltas, boolean clima, int comprimento, int nSetores){
        this.nome = nome;
        this.numVoltas = numVoltas;
        this.clima = clima;
        this.comprimento = comprimento;
        this.nSetores = nSetores;
        List<Setor> setores = new ArrayList<>();
        for (int i=0; i<nSetores; i++){
            Setor s = new Setor();
            setores.add(s);
        }
        this.setores = setores;
        this.dnf = new HashMap<Jogador,Integer>();
    }



    public String getNome() {
        return nome;
    }

    public int getNumVoltas() {
        return numVoltas;
    }

    public boolean isClima() {
        return clima;
    }

    public int getComprimento() {
        return comprimento;
    }

    public int getNSetores(){
        return nSetores;
    }

    public List<Setor> getSetores() {
        return setores;
    }

    public Map<Jogador, Integer> getDnf() {
        return dnf;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("||||||||||||| Circuito: ").append(this.nome).append(" |||||||||||||\n");
        sb.append("| Clima:").append(this.clima ? "Chuva" : "Seco");
        sb.append(" | Voltas:").append(this.numVoltas);
        sb.append(" | Comprimento:").append(this.comprimento).append("km |\n");
        return sb.toString();
    }

    private String printJogadores(List<Jogador> jogadores, int volta){
        StringBuilder sb = new StringBuilder();
        sb.append("\n||||| Posições na volta ").append(volta).append(" |||||\n");

        int i=1;
        for (Jogador j:jogadores){
            sb.append(i).append("º -> ").append(j.toString()).append("\n");
            i++;
        }
        return sb.toString();
    }

    private String dnf(List<Jogador> jogadores, int volta){
        List<Jogador> aux = new ArrayList<>(jogadores);
        StringBuilder dnf = new StringBuilder();
        for (Jogador j:aux){
            if (j.dnf(volta)){
                dnf.append("O jogador ").append(j).append(" parou na volta ").append(volta).append("\n");
                this.dnf.put(j,volta);
                jogadores.remove(j);
            }
        }
        return dnf.toString();
    }

    /**
     * Método que simula uma corrida simulando todos os setores presentes na mesma.
     * O booleano halfDistance indica se a simulação irá decorrer na primeira metade ou na segunda metade da corrida.
     * False - Primeira Metade | True - Segunda Metade
     * @param jogadores Jogadores participantes na corrida
     * @return String com os resultados da corrida onde contém todas as posições em cada volta da mesma
     */
    public String SimularCorrida(List<Jogador> jogadores){
        StringBuilder result = new StringBuilder();
        StringBuilder ultrapassagens = new StringBuilder();

        this.participantes = new ArrayList<>(jogadores);
        result.append(printJogadores(participantes,0));

        boolean halfDistance = false;
        for(int i=1; i<=numVoltas; i++){
            ultrapassagens.append(dnf(jogadores,i));
            halfDistance = i>numVoltas/2;
            int nSetor = 1;
            for (Setor s: setores){
                ultrapassagens.append(s.SimularSetor(jogadores, this.clima, halfDistance,nSetor));
                nSetor++;
            }
            this.participantes = new ArrayList<>(jogadores);
            this.participantes.addAll(dnf.keySet());

            result.append(printJogadores(this.participantes,i)).append(ultrapassagens);
            ultrapassagens = new StringBuilder();
        }
        jogadores.addAll(dnf.keySet());
        return result.toString();
    }

    public void reset(){
        this.dnf = new HashMap<>();
        this.participantes = new ArrayList<>();
    }
}

package tanks;

public class Config {


    private int popSize ;
    private int iter ;
    private double percBest;
    private double percCros;
    private double percMuta;
    private double percNew;
    public static int numOfGenes;
    public static int numOfCommands;

    public void Config(int pop, int iter, double percBest, double percCros, double percMuta, double percNew, int numOfGenes, int numOfCommands){
        this.popSize = pop;
        this.iter = iter;
        this.numOfGenes = numOfGenes;

        if(percBest + percCros + percMuta + percNew == 1) {
            this.percBest = percBest;
            this.percCros = percCros;
            this.percMuta = percMuta;
            this.percNew = percNew;
        }else {
            this.percBest = 0.2;
            this.percCros = 0.3;
            this.percMuta = 0.3;
            this.percNew = 0.2;
        }
    }

    public int getIter() {
        return iter;
    }

    public double getPercBest() {
        return percBest;
    }

    public double getPercCros() {
        return percCros;
    }

    public double getPercMuta() {
        return percMuta;
    }

    public double getPercNew() {
        return percNew;
    }

    public int getNumOfGenes() {
        return numOfGenes;
    }

    public  int getNumOfCommands() {
        return numOfCommands;
    }
    public int getPopSize() {
        return popSize;
    }
}

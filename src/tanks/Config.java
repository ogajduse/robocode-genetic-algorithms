package tanks;

public class Config {


    private static int popSize  = 2;
    private static int iter  = 10;
    private static double percBest = 0.2;
    private static double percCros = 0.3;
    private static double percMuta = 0.3;
    private static double percNew = 0.2;
    public static int numOfGenes = 5;
    public static int numOfCommands = 5;

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

    public static int getIter() {
        return iter;
    }

    public static double getPercBest() {
        return percBest;
    }

    public static double getPercCros() {
        return percCros;
    }

    public static double getPercMuta() {
        return percMuta;
    }

    public static double getPercNew() {
        return percNew;
    }

    public static int getNumOfGenes() {
        return numOfGenes;
    }

    public static int getNumOfCommands() {
        return numOfCommands;
    }
    public static int getPopSize() {
        return popSize;
    }
}

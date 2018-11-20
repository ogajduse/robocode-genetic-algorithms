package tanks;

public class Config {


    private static int popSize  = 100;
    private static int iter  = 1000;
    private static double percBest = 0.2;
    private static double percCros = 0.3;
    private static double percMuta = 0.3;
    private static double percNew = 0.2;
    public static int numOfGenes = 8;
    public static int numOfCommands = 8;
    public static double percRun = 0.2;
    public static double percOnHit = 0.4;
    public static double percOnScanned = 0.4;


    public void Config(int pop, int iter, double percBest, double percCros, double percMuta, double percNew, int numOfGenes, int numOfCommands, double percRun, double percOnHit, double percOnScanned){
        this.popSize = pop;
        this.iter = iter;
        this.numOfGenes = numOfGenes;

        if(percBest + percCros + percMuta + percNew + percRun + percOnHit + percOnScanned == 2) {
            this.percBest = percBest;
            this.percCros = percCros;
            this.percMuta = percMuta;
            this.percNew = percNew;
            this.percRun = percRun;
            this.percOnHit = percOnHit;
            this.percOnScanned = percOnScanned;
        }else {
            this.percBest = 0.2;
            this.percCros = 0.3;
            this.percMuta = 0.3;
            this.percNew = 0.2;
            this.percRun = 0.2;
            this.percOnHit = 0.4;
            this.percOnScanned = 0.4;
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

    public static double getPercRun(){
        return percRun;
    }
    public static double getPercOnHit(){
        return percOnHit;
    }
    public static double getPercOnScanned(){
        return percOnScanned;
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

package tanks;

public class Config {

    private static int iter  = 100;

    private static int popSize  = 100;

    public static int numberOfCommands = 8;

    private static double percBest = 0.1;
    private static double percCros = 0.7;
    private static double percNew = 0.2;

    public static int numOfGenes = 10;
    public static double percRun = 0.4;
    public static double percOnHit = 0.3;
    public static double percOnScanned = 0.3;


    public static int getIter() {
        return iter;
    }

    public static double getPercBest() {
        return percBest;
    }

    public static double getPercCros() {
        return percCros;
    }

    public static double getPercNew() {
        return percNew;
    }

    public static double getPercRun() {
        return percRun;
    }

    public static double getPercOnHit() {
        return percOnHit;
    }

    public static double getPercOnScanned() {
        return percOnScanned;
    }

    public static int getNumOfGenes() {
        return numOfGenes;
    }

    public static int getPopSize() {

        return popSize;
    }
}

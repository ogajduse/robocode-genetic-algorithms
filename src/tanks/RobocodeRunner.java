package tanks;

import java.io.IOException;

public class RobocodeRunner {

    public static void main(String[] args) throws IOException {

        Config conf = new Config();

        Population pop = new Population();

        //op.loadBest();

        pop.evolve(Config.getIter());


        //pop.writeBest();
    }


}
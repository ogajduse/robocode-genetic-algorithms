package tanks;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.TreeSet;

public class RobocodeRunner {

    public static void main(String[] args) throws IOException {


        DataFactory dft = new DataFactory();
        TreeSet<Chromosome> tst = dft.getGeneration(1543770809);


        Config conf = new Config();

        //Population pop = new Population();
        Population pop = new Population(tst);

        //op.loadBest();

        pop.evolve(Config.getIter());


        //pop.writeBest();
    }


}
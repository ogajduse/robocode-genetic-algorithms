package tanks;

import java.io.IOException;

public class RobocodeRunner {

    public static void main(String[] args) throws IOException {
        DataFactory dft = new DataFactory();
        //TreeSet<Chromosome> tst = dft.getGeneration(1543770809);
        Config conf = new Config();

        Population pop = new Population();
        pop.evolve(Config.getIter());
    }


}
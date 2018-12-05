package tanks;

import java.io.IOException;

public class RobocodeRunner {

    public static void main(String[] args) throws IOException {
        long startTime;
        DataFactory dataFactory;

        dataFactory = new DataFactory();
        startTime = dataFactory.getUnixTimestamp();

        //TreeSet<Chromosome> tst = dft.getGeneration(1543770809);
        dataFactory.writeConfig();

        Population pop = new Population(startTime);
        pop.evolve(Config.getIter());
    }


}
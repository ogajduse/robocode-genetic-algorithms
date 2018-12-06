package tanks;

import java.io.IOException;

public class RobocodeRunner {

    public static void main(String[] args) throws IOException {
        long startTime;
        DataFactory dataFactory;

        dataFactory = new DataFactory();
        startTime = dataFactory.getUnixTimestamp();
//        // ziskani grafu pro konkretni timestamp
//        XYSeriesCollection dataSeries = dataFactory.createDataSeries();
//
//        Graph graph = new Graph("Results", "First and second best in iteration", dataSeries, startTime);

//        // ziskani posledni generace z daneho timestampu
//        TreeSet<Chromosome> tst = dataFactory.getGeneration();

        dataFactory.writeConfig();
        Population pop = new Population(startTime);
        pop.evolve(Config.getIter());
    }
}
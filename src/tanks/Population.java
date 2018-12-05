package tanks;

import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.*;


public class Population {
    private String myRobot = "TankCreator";
    private String enemyList = "Crazy";
    private long startTime = 0;
    private DataFactory dataFactory;
    private ArrayList<Chromosome> chromosomes;

    public Population(long startTime) {
        this();
        this.startTime = startTime;
        dataFactory = new DataFactory(this.startTime);
    }

    public Population() {
        chromosomes = new ArrayList<>();
        init();
    }

    public Population(ArrayList<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
        dataFactory = new DataFactory();
        startTime = dataFactory.getUnixTimestamp();
    }

    private void init() {
        for (int i = 0; i < Config.getPopSize(); i++) {
            chromosomes.add(new Chromosome());
        }
    }

    public void showChromosomes() {
        for (Chromosome ch : chromosomes) {
            ch.showGenesInChromosome();
            System.out.println();
        }
    }

    public void evolve(int iters) throws IOException {
        for (int i = 0; i < iters; i++) {
            Iterator<Chromosome> iter = chromosomes.iterator();
            for (int j = 0; j < chromosomes.size(); j++) {
                Chromosome chrom = iter.next();
                chrom.setFitness(this.runRobocode(chrom.getGenes(), enemyList));
            }

            TreeSet<Chromosome> newChromosomes = new TreeSet<>();
            for (Chromosome chromosome : chromosomes) {
                newChromosomes.add(chromosome);
            }

            dataFactory.writeGeneration(newChromosomes, i);

            chromosomes = new ArrayList<>();

            for (int j = 0; j < Config.getPercBest() * Config.getPopSize(); j++) {
                chromosomes.add(newChromosomes.pollFirst());
            }
            for (int j = 0; j < (Config.getPercCros() / 2) * Config.getPopSize(); j++) {
                Chromosome ch1 = newChromosomes.pollFirst();
                Chromosome ch2 = newChromosomes.pollFirst();
                crossover(ch1, ch2);
                mutate(ch1);
                mutate(ch2);
                chromosomes.add(ch1);
                chromosomes.add(ch2);
            }
            for (int j = 0; j < Config.getPercNew() * Config.getPopSize(); j++) {
                chromosomes.add(new Chromosome());
            }
        }
    }

    public void mutate(Chromosome chrom) {
        Random rnd = new Random();
        for (int i = 0; i < 0.1 * Config.getNumOfGenes(); i++) {
            int randomNumber = rnd.nextInt(Config.getNumOfGenes());
            Gene gene = chrom.getGene(randomNumber);
            Gene gene2 = new Gene();
            gene.type = gene2.type;
            gene.value = gene2.value;
        }
    }

    public void crossover(Chromosome chrom1, Chromosome chrom2) {
        Random rnd = new Random();
        for (int i = 0; i < 0.1 * Config.getNumOfGenes(); i++) {
            int randomNumber = rnd.nextInt(Config.getNumOfGenes());
            Gene gen1 = chrom1.getGene(randomNumber);
            Gene gen2 = chrom2.getGene(randomNumber);
            int gen1Type = gen1.type;
            double gen1Value = gen1.value;
            gen1.type = gen2.type;
            gen1.value = gen2.value;
            gen2.type = gen1Type;
            gen2.value = gen1Value;
        }
    }

    public double runRobocode(Vector<Gene> genes, String seznamProtivniku) throws IOException {

        String mujRobot = "MyRobot";

        String dst = "robots/sample/TankDst.java";

        File dest = new File(dst);

        //Files.copy(source.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);

        createTank(genes, dest);
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        compiler.run(null, System.out, System.out, dst);

        seznamProtivniku = seznamProtivniku.replaceAll("\\s", "");

        String tanks[] = seznamProtivniku.split(",");
        String finalListOfTanks = "";
        for (String string : tanks) {
            string = "sample." + string + ",";
            finalListOfTanks += string;
        }

        finalListOfTanks += "sample.TankDst";

        BattleObserver battleListener = new BattleObserver();

        // Create the RobocodeEngine
        RobocodeEngine engine = new RobocodeEngine();// Run from current
        // working directory

        // Add battle listener to our RobocodeEngine
        engine.addBattleListener(battleListener);

        // Show the battles
        //engine.setVisible(true);

        // Setup the battle specification

        int numberOfRounds = 3;
        BattlefieldSpecification battlefield = new BattlefieldSpecification(800, 800); // 800x600
        // RobotSpecification[] selectedRobots =
        // engine.getLocalRepository("sample.Corners, sample.MujRobot");
        RobotSpecification[] selectedRobots = engine.getLocalRepository(finalListOfTanks);

        BattleSpecification battleSpec = new BattleSpecification(numberOfRounds, battlefield, selectedRobots);
        // Run our specified battle and let it run till it's over
        engine.runBattle(battleSpec, true/* wait till the battle is over */);

        double fitness;
        if (battleListener.getResults()[0].getTeamLeaderName().equals("sample.TankDst")) {
            fitness = battleListener.getResults()[0].getScore();
        } else {
            fitness = battleListener.getResults()[1].getScore();
        }
        // Cleanup our RobocodeEngine
        engine.close();

        return fitness;
    }

    public void createTank(Vector<Gene> genes, File dst) {
        Vector<Gene> genesRun;
        Vector<Gene> genesOnScanned;
        Vector<Gene> genesOnHit;

        genesRun = new Vector<>();
        int counter = 0;
        double condition = Config.getPercRun() * Config.getNumOfGenes();
        for (; counter < condition; counter++) {
            genesRun.add(genes.get(counter));
        }
        genesOnScanned = new Vector<>();
        condition += Config.getPercOnScanned() * Config.getNumOfGenes();
        for (; counter < condition; counter++) {
            genesOnScanned.add(genes.get(counter));
        }
        genesOnHit = new Vector<>();
        condition += Config.getPercOnHit() * Config.getNumOfGenes();
        for (; counter < condition; counter++) {
            genesOnHit.add(genes.get(counter));
        }

        try {
            PrintWriter writer = new PrintWriter(dst, "UTF-8");

            writer.println("package sample;");
            writer.println();
            writer.println("import robocode.HitRobotEvent;");
            writer.println("import robocode.Robot;");
            writer.println("import robocode.ScannedRobotEvent;");
            writer.println("import robocode.HitByBulletEvent;");
            writer.println();
            writer.println("public class TankDst extends Robot{");
            writer.println("public void run() {");
            writer.println("while (true) {");
            for (Gene aGenesRun : genesRun) {
                runSwitch(aGenesRun.type, aGenesRun.value, writer);
            }
            writer.println("}");
            writer.println("}");

            writer.println("public void onScannedRobot(ScannedRobotEvent e) {");
            for (Gene aGenesOnScanned : genesOnScanned) {
                runSwitch(aGenesOnScanned.type, aGenesOnScanned.value, writer);
            }
            writer.println("}");

            writer.println("public void onHitRobot(HitRobotEvent event) {");
            for (Gene aGenesOnHit : genesOnHit) {
                runSwitch(aGenesOnHit.type, aGenesOnHit.value, writer);
            }
            writer.println("}");
            writer.println("}");
            writer.close();
        } catch (FileNotFoundException | UnsupportedEncodingException e) {
            System.out.println(e);
        }

    }

    private void runSwitch(int type, double value, PrintWriter writer) {
        switch (type) {
            case 0:
                writer.println("ahead(" + -40 * value + ");");
                break;
            case 1:
                writer.println("ahead(" + 40 * value + ");");
                break;
            case 2:
                writer.println("turnRight(" + 360 * value + ");");
                break;
            case 3:
                writer.println("turnLeft(" + 360 * value + ");");
                break;
            case 4:
                writer.println("turnGunRight(" + 360 * value + ");");
                break;
            case 5:
                writer.println("fire(" + 5 * value + ");");
                break;
            case 6:
                writer.println("turnGunLeft(" + 360 * value + ");");
                break;
            case 7:
                writer.println("fire(" + 5 * value + ");");
                break;
        }
    }
}

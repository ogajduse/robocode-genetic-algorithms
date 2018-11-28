package tanks;

import robocode.BattleResults;
import robocode.control.BattleSpecification;
import robocode.control.BattlefieldSpecification;
import robocode.control.RobocodeEngine;
import robocode.control.RobotSpecification;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.nio.file.Files;
import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;
import java.util.Random;
import java.nio.file.StandardCopyOption;
import java.io.UnsupportedEncodingException;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

import java.io.File;




public class Population {
    private TreeSet<Chromosome> chromosomes;

    String myRobot = "TankCreator";
    String enemyList = "Crazy";

    public Population() {
        chromosomes = new TreeSet<Chromosome>();
        init();
    }

    private void init() {
        for (int i = 0; i < Config.getPopSize(); i++) {
            chromosomes.add(new Chromosome());
        }
    }

    public void showChromosomes() {
        for (Chromosome ch:chromosomes) {
            ch.showGenesInChromosome();
            System.out.println();
        }
    }

    public void evolve(int iters) throws IOException {

        Vector<Double> bestResults = new Vector<Double>();

        for (int i = 0; i < iters; i++) {

            Iterator<Chromosome> iter = chromosomes.iterator();
            for(int j = 0; j < chromosomes.size(); j++){
                Chromosome chrom = iter.next();
                chrom.setFitness(this.runRobocode(chrom.getGenes(), enemyList));
            }

            TreeSet<Chromosome> newChromosomes = new TreeSet<>();
            for (int j = 0; j < Config.getPercBest() * Config.getPopSize(); j++) {
                newChromosomes.add(chromosomes.pollFirst());
            }
            for (int j = 0; j < (Config.getPercCros() / 2) * Config.getPopSize(); j++) {
                Chromosome ch1 = chromosomes.pollFirst();
                Chromosome ch2 = chromosomes.pollFirst();
                crossover(ch1, ch2);
                mutate(ch1);
                mutate(ch2);
                newChromosomes.add(ch1);
                newChromosomes.add(ch2);
            }
            for (int j = 0; j < Config.getPercNew() * Config.getPopSize(); j++) {
                newChromosomes.add(new Chromosome());
            }
            // calc fit for all the new chromosomes
            for (Chromosome chromosome : newChromosomes) {
                chromosome.calcFit();
            }
            chromosomes = new TreeSet<>();
            for (Chromosome chromosome : newChromosomes){
                chromosomes.add(chromosome);
            }


            Iterator<Chromosome> iter2 = chromosomes.iterator();
            bestResults.add(iter2.next().getFitness());


        }

    }


    public void mutate(Chromosome chrom) {
        Random rnd = new Random();
        for (int i = 0; i < 0.1 * Config.getNumOfGenes(); i++) { /* TODO: proc nasobime 0.1* ? */
            int randomNumber = rnd.nextInt(Config.getNumOfGenes());
            Gene gene = chrom.getGene(randomNumber);
            Gene gene2 = new Gene();
            gene.type = gene2.type;
            gene.value = gene2.value;
        }
    }

    public void crossover(Chromosome chrom1, Chromosome chrom2) {
        Random rnd = new Random();
        for (int i = 0; i < 0.1 * Config.getNumOfGenes(); i++) { /* TODO: proc nasobime 0.1* ? */
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

        for (BattleResults result : battleListener.getResults()) {
            System.out.println(result.getTeamLeaderName() + " - " + result.getScore());
        }

        double fitness = battleListener.getResults()[1].getScore();

        // Cleanup our RobocodeEngine
        engine.close();

        // Make sure that the Java VM is shut down properly
        //System.exit(0);

        return fitness;
    }

    public void createTank(Vector<Gene> genes, File dst) {
        Vector<Gene> genesRun;
        Vector<Gene> genesOnScanned;
        Vector<Gene> genesOnHit;

        genesRun = new Vector<>();
        for (int i = 0; i < Config.getPercRun() * Config.getNumOfGenes(); i++) {
            genesRun.add(genes.get(i));
        }
        genesOnScanned = new Vector<>();
        for (int i = 0; i < Config.getPercOnScanned() * Config.getNumOfGenes(); i++) {
            genesOnScanned.add(genes.get(i));
        }
        genesOnHit = new Vector<>();
        for (int i = 0; i < Config.getPercOnHit() * Config.getNumOfGenes(); i++) {
            genesOnHit.add(genes.get(i));
        }

        try{
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
                        for(int i = 0; i < genesRun.size();i++){
                            runSwitch(genesRun.get(i).type,genesRun.get(i).value, writer);
                        }
                    writer.println("}");
                writer.println("}");

                writer.println("public void onScannedRobot(ScannedRobotEvent e) {");
                    for(int i = 0; i < genesOnScanned.size();i++){
                        runSwitch(genesOnScanned.get(i).type,genesOnScanned.get(i).value, writer);
                    }
                writer.println("}");

                writer.println("public void onHitRobot(HitRobotEvent event) {");
                    for(int i = 0; i < genesOnHit.size();i++){
                        runSwitch(genesOnHit.get(i).type,genesOnHit.get(i).value, writer);
                    }
                writer.println("}");
            writer.println("}");
            writer.close();
        }catch(FileNotFoundException e){
            System.out.println(e);
        }catch(UnsupportedEncodingException e){
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

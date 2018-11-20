package tanks;

import java.util.Vector;

import robocode.AdvancedRobot;
import robocode.HitRobotEvent;
import robocode.ScannedRobotEvent;

public class Tancik extends AdvancedRobot {

    public Vector<Gene> genesRun;
    public Vector<Gene> genesOnScanned;
    public Vector<Gene> genesOnHit;

    public Tancik(Vector<Gene> genes) {
        genesRun = new Vector<>();
        for (int i = 0; i < Config.getPercRun() * Config.getNumOfGenes(); i++) {
            genesRun.add(genes.remove(0));

        }

        genesOnScanned = new Vector<>();
        for (int i = 0; i < Config.getPercOnScanned() * Config.getNumOfGenes(); i++) {
            genesOnScanned.add(genes.remove(0));
        }
        genesOnHit = new Vector<>();
        for (int i = 0; i < Config.getPercOnHit() * Config.getNumOfGenes(); i++) {
            genesOnHit.add(genes.remove(0));
        }
    }

    public void run() {

        while (true) {

            for (Gene gene : genesRun) {
                switch (gene.type) {
                    case 0:
                        setAhead(-40000 * gene.value);
                        break;
                    case 1:
                        setAhead(40000 * gene.value);
                        break;
                    case 2:
                        setTurnRight(360 * gene.value);
                        break;
                    case 3:
                        setTurnLeft(360 * gene.value);
                        break;
                    case 4:
                        setTurnGunRight(360 * gene.value);
                        break;
                    case 5:
                        setFire(5 * gene.value);
                        break;
                    case 6:
                        setTurnGunLeft(360 * gene.value);
                        break;
                    case 7:
                        //setDistance(10 * gene.value);
                        break;
                    case 8:
                        setMaxVelocity(5 * gene.value);
                        break;
                }

            }
        }
    }

    public void onScannedRobot(ScannedRobotEvent e) {


        for (Gene gene : genesOnScanned) {
            switch (gene.type) {
                case 0:
                    setAhead(-40000 * gene.value);
                    break;
                case 1:
                    setAhead(40000 * gene.value);
                    break;
                case 2:
                    setTurnRight(360 * gene.value);
                    break;
                case 3:
                    setTurnLeft(360 * gene.value);
                    break;
                case 4:
                    setTurnGunRight(360 * gene.value);
                    break;
                case 5:
                    setFire(5 * gene.value);
                    break;
                case 6:
                    setTurnGunLeft(360 * gene.value);
                    break;
                case 7:
                    //etDistance(10 * gene.value);
                    break;
                case 8:
                    setMaxVelocity(5 * gene.value);
                    break;
            }
        }
    }

    public void onHitRobot(HitRobotEvent event) {

        for (Gene gene : genesOnHit) {
            switch (gene.type) {
                case 0:
                    setAhead(-40000 * gene.value);
                    break;
                case 1:
                    setAhead(40000 * gene.value);
                    break;
                case 2:
                    setTurnRight(360 * gene.value);
                    break;
                case 3:
                    setTurnLeft(360 * gene.value);
                    break;
                case 4:
                    setTurnGunRight(360 * gene.value);
                    break;
                case 5:
                    setFire(5 * gene.value);
                    break;
                case 6:
                    setTurnGunLeft(360 * gene.value);
                    break;
                case 7:
                    System.out.println("7");
                    //setDistance(10 * gene.value);
                    break;
                case 8:
                    setMaxVelocity(5 * gene.value);
                    break;
            }
        }
    }

}
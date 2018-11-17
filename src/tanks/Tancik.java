package tanks;

import java.util.Vector;

import robocode.AdvancedRobot;

public class tancik {

    public class Tancik extends AdvancedRobot{

        public Vector<Gene> genes;
        public Vector<Gene> genesRun;
        public Vector<Gene> genesOnScanned;
        public Vector<Gene> genesOnHit;
        public Tancik(Vector<Gene> genes) {

            this.genesRun = genes;
            this.genesOnScanned = genes;
            this.genesOnHit = genes;
            this.genes = genes;

        }

        public void run() {

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
                        setEnergy((int)(50 * gene.value));
                        break;
                    case 5:
                        setFire(5 * gene.value);
                        break;
                    case 6:
                        setBearing(180 * gene.value);
                        break;
                    case 7:
                        setDistance(10 * gene.value);
                        break;
                    case 8:
                        setMaxVelocity (5* gene.value);
                        break;
                }

            }
        }

        public void onScannedRobot() {

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
                        setEnergy((int)(50 * gene.value));
                        break;
                    case 5:
                        setFire(5 * gene.value);
                        break;
                    case 6:
                        setBearing(180 * gene.value);
                        break;
                    case 7:
                        setDistance(10 * gene.value);
                        break;
                    case 8:
                        setMaxVelocity (5* gene.value);
                        break;
                }
            }
        }

        public void onHitRobot() {

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
                        setEnergy((int)(50 * gene.value));
                        break;
                    case 5:
                        setFire(5 * gene.value);
                        break;
                    case 6:
                        setBearing(180 * gene.value);
                        break;
                    case 7:
                        setDistance(10 * gene.value);
                        break;
                    case 8:
                        setMaxVelocity (5* gene.value);
                        break;
                }
            }
        }
        private void setEnergy(int i) {
            // TODO Auto-generated method stub

        }
        private void setDistance(double d) {
            // TODO Auto-generated method stub

        }
        private void setBearing(double d) {
            // TODO Auto-generated method stub
        }
    }
}
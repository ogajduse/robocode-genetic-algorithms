package tanks;

import java.util.Vector;

import robocode.AdvancedRobot;

public class tancik {

    public class Tancik extends AdvancedRobot{

        public Vector<Gene> genes;
        public Tancik(Vector<Gene> genes) {
            this.genes = genes;
        }

        public void run() {

            for (Gene gene : genes) {
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
                }
            }
        }


        private void setEnergy(int i) {
            // TODO Auto-generated method stub

        }

    }
}
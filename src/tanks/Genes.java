package tanks;

import java.util.Vector;
import java.util.Random;

public class Genes {
    public int type;
    public double value;

    public Genes(){
        Random rnd = new Random();
        this.type=rnd.nextInt(Config.numOfCommands+1);
        this.value = rnd.nextDouble();
    }

}

package tanks;

import java.util.Vector;
import java.util.Random;

public class Gene {
    public int type;
    public double value;

    public Gene(){
        Random rnd = new Random();
        this.type=rnd.nextInt(Config.numOfCommands+1);
        this.value = rnd.nextDouble();
    }

    public void ShowGenes(){
        System.out.println(type + " " + value);
    }

}

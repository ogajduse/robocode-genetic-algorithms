package tanks;

import java.util.Random;

public class Gene {
    public int type; /* typ prikazu, eg. strilej*/
    public double value; /* hodnota, eg. 3 */

    public Gene() {
        Random rnd = new Random();
        this.type = rnd.nextInt(Config.numberOfCommands);
        this.value = rnd.nextDouble();
    }

    public void ShowGenes() {
        System.out.println(type + " " + value);
    }

}

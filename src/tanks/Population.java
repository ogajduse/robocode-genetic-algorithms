package tanks;

import java.util.Vector;
import java.util.Random;


public class Population {
    private Vector<Chromosome> chromosomes;


    public Population() {
        chromosomes = new Vector<Chromosome>();
        init();
    }

    private void init() {
        for (int i = 0; i < Config.getPopSize(); i++) {
            chromosomes.add(new Chromosome());
        }


    }

    public void showChromosomes() {
        for (int i = 0; i < chromosomes.size(); i++) {
            chromosomes.get(i).showGenesInChromosome();
            System.out.println();
        }
    }

    public void evolve(int iters) {
        for (int i = 0; i < iters; i++) {
            

        }

    }


    public void createBestTank() {

    }

    public Chromosome getChromosome(int index){
        return chromosomes.get(index);
    }

    public void mutate(Chromosome chrom) {
        Random rnd = new Random();
        for (int i = 0; i < 0.1 * Config.getNumOfGenes(); i++) {
            int randomNumber = rnd.nextInt(Config.getNumOfGenes());
            Gene gene = chrom.getGenes(randomNumber);
            Gene gene2 = new Gene();
            gene.type = gene2.type;
            gene.value = gene2.value;

        }

    }

    public void crossover(Chromosome chrom1, Chromosome chrom2) {
        Random rnd = new Random();
        for (int i = 0; i < 0.1 * Config.getNumOfGenes(); i++) {
            int randomNumber = rnd.nextInt(Config.getNumOfGenes());
            Gene gen1 = chrom1.getGenes(randomNumber);
            Gene gen2 = chrom2.getGenes(randomNumber);
            int gen1Type = gen1.type;
            double gen1Value = gen1.value;
            gen1.type = gen2.type;
            gen1.value = gen2.value;
            gen2.type = gen1Type;
            gen2.value = gen1Value;
        }


    }


}

package tanks;

import java.util.Iterator;
import java.util.TreeSet;
import java.util.Vector;
import java.util.Random;


public class Population {
    private TreeSet<Chromosome> chromosomes;


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

    public void evolve(int iters) {
        for (int i = 0; i < iters; i++) {
            Iterator<Chromosome> iter = chromosomes.iterator();
            System.out.println("Generation: " + i + ". Fitness of the first chromosome: " + iter.next().getFitness());
            System.out.println("Generation: " + i + ". Fitness of the second chromosome: " + iter.next().getFitness());
            if (i > 2 && chromosomes.first().getFitness() == 0.0){
                break;
            }

            TreeSet<Chromosome> newChromosomes = new TreeSet<>();
            for (int j = 0; j < Config.getPercBest() * Config.getPopSize(); j++) {
                newChromosomes.add(chromosomes.pollFirst());
            }
            for (int j = 0; j < (Config.getPercCros() / 2) * Config.getPopSize(); j++) {
                Chromosome ch1 = chromosomes.pollFirst();
                Chromosome ch2 = chromosomes.pollFirst();
                crossover(ch1, ch2);
                newChromosomes.add(ch1);
                newChromosomes.add(ch2);
            }
            for (int j = 0; j < Config.getPercMuta() * Config.getPopSize(); j++) {
                Chromosome ch1 = chromosomes.pollFirst();
                mutate(ch1);
                newChromosomes.add(ch1);
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
        }

    }

    public void createBestTank() {

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
}

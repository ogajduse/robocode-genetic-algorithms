package tanks;

import java.util.Vector;

public class Chromosome implements Comparable<Chromosome> {
    private Vector<Gene> genes;
    private double fitness;


    public Chromosome(){
        genes = new Vector<Gene>();
        init();
    }

    public Gene getGene(int index) {
        return genes.get(index);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public double calcFit(){
        double fitness = 0;
        for (int i = 0; i < genes.size(); i++) {
            fitness += genes.get(i).type * genes.get(i).value;
        }
        return fitness;
    }

    public void createCode(){

    }

    public void showGenesInChromosome(){
        for (int i = 0; i < genes.size() ; i++) {
            genes.get(i).ShowGenes();
        }
    }

    public void init(){
        for (int i=0 ; i < Config.numOfGenes; i++) {
            this.genes.add(new Gene());
        }
    }

    @Override
    public int compareTo(Chromosome chromosome) {
        if (getFitness() <= chromosome.getFitness()) {
            return -1;
        } else {
            return 1;
        }
    }
}

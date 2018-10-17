package tanks;

import java.util.Vector;

public class Chromosome {
    private Vector<Genes> genes;
    private int fittnes;


    public Chromosome(){
        genes = new Vector<Genes>();


    }

    public double calcFit(){

    }

    public void createCode(){}

    public void createRandomGenes(){}

    public void init(){
        for (int i=0 ; i < Config.numOfGenes; i++) {
            this.genes.add(new Genes());
        }
    }


}

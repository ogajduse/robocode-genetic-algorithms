package tanks;

import java.util.Vector;

public class Population {
    private Vector<Chromosome> chromosomes;
    private Config config;


    public Population(){
        chromosomes = new Vector<Chromosome>();
        config = new Config();
        init();
    }

    public void init(){
        for(int i=0; i<config.getPopSize(); i++ ){
            chromosomes.add(new Chromosome());
        }


    }

    public void evolve(int iters){
        //Dalsi Koment Ahoj
    }

    public void createBestTank(){

    }

    public void mutate(){

    }
    public void crossover(Chromosome Chrom1, Chromosome Chrom2){

    }



}

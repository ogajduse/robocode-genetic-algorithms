package tanks;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.TreeSet;

public class DataFactory {
    private String fileDir = "out/yaml/";
    private Long unixTimestamp;

    public DataFactory() {
        this.unixTimestamp = Instant.now().getEpochSecond();
    }

    public DataFactory(long unixTimestamp) {
        this.unixTimestamp = unixTimestamp;
    }

    public Long getUnixTimestamp() {
        return unixTimestamp;
    }

    public void writeGeneration(TreeSet<Chromosome> chromosomes, Integer iter) {
        String dstDir = this.fileDir + this.unixTimestamp.toString();
        String fileDst = dstDir + "/" + "generation" + iter.toString() + ".yaml";

        ensureDirectoryExists(dstDir);
        writeObject(chromosomes, fileDst);
    }

    public void writeConfig() {
        String dstDir = this.fileDir + this.unixTimestamp.toString();
        String fileDst = dstDir + "/" + "config.yaml";

        HashMap<String, Number> config = new HashMap<>();

        config.put("percRun", Config.getPercRun());
        config.put("percOnHit", Config.getPercOnHit());
        config.put("percOnScanned", Config.getPercOnScanned());
        config.put("percBest", Config.getPercBest());
        config.put("percCros", Config.getPercCros());
        config.put("percNew", Config.getPercNew());

        config.put("numberOfCommands", Config.getNumberOfCommands());
        config.put("numOfGenes", Config.getNumOfGenes());
        config.put("popSize", Config.getPopSize());
        config.put("iter", Config.getIter());

        ensureDirectoryExists(dstDir);
        writeObject(config, fileDst);
    }

    private boolean ensureDirectoryExists(String dstDir) {
        return new File(dstDir).mkdirs();
    }

    private void writeObject(Object object, String dstPath) {
        File file = new File(dstPath);
        try {
            YamlWriter writer = new YamlWriter(new FileWriter(file));
            assert writer != null;
            writer.write(object);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private File[] searchFiles() {
        File dir = new File(fileDir + "/" + this.unixTimestamp.toString());
        File[] files = dir.listFiles((d, name) -> name.startsWith("generation"));
        assert files != null;
        Arrays.sort(files, new AlphanumFileComparator());
        return files;
    }

    private Object readFile(File file) {
        Object object = null;
        try {
            YamlReader reader = new YamlReader(new FileReader(file));
            object = reader.read();
        } catch (FileNotFoundException | YamlException e) {
            e.printStackTrace();
        }
        return object;
    }

    public ArrayList<Chromosome> getGeneration(Integer index) {
        File[] files = searchFiles();
        File file = Arrays.asList(files).get(index);

        Object object = readFile(file);

        return new ArrayList<>((TreeSet<Chromosome>) object);
    }

    public ArrayList<Chromosome> getGeneration() {
        File[] files = searchFiles();
        // get the last generation
        File file = Arrays.asList(files).get(files.length - 1);

        Object object = readFile(file);

        return new ArrayList<>((TreeSet<Chromosome>) object);
    }

    public LinkedList[] getScoreFromRun(){
        File[] files = searchFiles();
        TreeSet<Chromosome> chromosomes;
        LinkedList<Double> bestFirst = new LinkedList<>();
        LinkedList<Double> bestSecond = new LinkedList<>();
        for (File file : files) {
            chromosomes = (TreeSet) readFile(file);
            bestFirst.add(chromosomes.pollFirst().getFitness());
            bestSecond.add(chromosomes.pollFirst().getFitness());
        }
        return new LinkedList[]{bestFirst, bestSecond};
    }

    public XYSeriesCollection createDataSeries() {
        LinkedList<Double>[] lists = this.getScoreFromRun();
        final XYSeries bestFirst = new XYSeries("First");
        final XYSeries bestSecond = new XYSeries("Second");

        for (int i = 0; i < lists[0].size(); i++) {
            bestFirst.add(i, lists[0].get(i));
        }
        for (int i = 0; i < lists[1].size(); i++) {
            bestSecond.add(i, lists[1].get(i));
        }

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(bestFirst);
        dataset.addSeries(bestSecond);
        return dataset;
    }
}

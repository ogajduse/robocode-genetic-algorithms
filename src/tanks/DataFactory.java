package tanks;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
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

    public void writeGeneration(TreeSet chromosomes, Integer iter) {
        String dstDir = this.fileDir + this.unixTimestamp.toString();
        String fileDst = dstDir + "/" + "generation" + iter.toString() + ".yaml";

        ensureDirectoryExists(dstDir);
        writeObject(chromosomes, fileDst);
    }

    public void writeConfig() {
        String dstDir = this.fileDir + this.unixTimestamp.toString();
        String fileDst = dstDir + "/" + "config.yaml";

        HashMap<String, Number> config = new HashMap();

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

    private File[] searchFiles(Integer timestamp) {
        File dir = new File(fileDir + "/" + timestamp.toString());
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

    public TreeSet getGeneration(Integer timestamp, Integer index) {
        File[] files = searchFiles(timestamp);
        File file = Arrays.asList(files).get(index);

        Object object = readFile(file);

        return (TreeSet) object;
    }

    public TreeSet getGeneration(Integer timestamp) {
        File[] files = searchFiles(timestamp);
        // get the last generation
        File file = Arrays.asList(files).get(files.length - 1);

        Object object = readFile(file);

        return (TreeSet) object;
    }
}

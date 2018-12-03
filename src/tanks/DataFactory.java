package tanks;

import com.esotericsoftware.yamlbeans.YamlException;
import com.esotericsoftware.yamlbeans.YamlReader;
import com.esotericsoftware.yamlbeans.YamlWriter;

import java.io.*;
import java.time.Instant;
import java.util.Arrays;
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
        String fileDst = this.fileDir + this.unixTimestamp.toString() + "/" + "generation" + iter.toString() + ".yaml";

        YamlWriter writer = null;
        boolean mkdirs = new File(dstDir).mkdirs();
        File file = new File(fileDst);

        try {
            writer = new YamlWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            assert writer != null;
            writer.write(chromosomes);
            writer.close();
        } catch (YamlException e) {
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

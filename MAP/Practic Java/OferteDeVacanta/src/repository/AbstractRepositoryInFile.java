package repository;

import domain.HasID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public abstract class AbstractRepositoryInFile<ID, E extends HasID<ID>> extends RepositoryInMemory<ID, E> {
    private String fileName;

    public AbstractRepositoryInFile(String fileName) {
        this.fileName=fileName;
        loadData();
    }

    public abstract E extractEntity(String line);

    private void loadData() {
        try (Stream<String> stream = Files.lines(Paths.get(this.fileName))) {
            stream.forEach(line -> {
                E entity = extractEntity(line);
                super.save(entity);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected  void writeToFile(E entity){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName,true))) {
            bw.write(entity.toString());
            bw.newLine();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  void writeAll(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName))) {
            entities.values().forEach(x->writeToFile(x));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
package repository;

import domain.HasID;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

public abstract class AbstractRepositoryInFile<ID, E extends HasID<ID>> extends RepositoryInMemory<ID, E> {
    private String fileName;

    public AbstractRepositoryInFile(String fileName) {
        //super();
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

    @Override
    public E save(E entity) {
        E tmp = super.save(entity);
        if(tmp==null) {
            writeToFile(entity);
            return null;
        }
        return entity;
    }

    @Override
    public E update(E entity) {
        E temp=super.update(entity);
        if(temp==null){
            writeAll();
        }
        return temp;
    }

    @Override
    public E delete(ID id) {
        E temp=super.delete(id);
        if(temp!=null)writeAll();
        return temp;
    }
}
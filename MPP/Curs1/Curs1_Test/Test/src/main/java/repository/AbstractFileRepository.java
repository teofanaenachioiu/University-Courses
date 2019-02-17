package repository;

import domain.HasID;
import domain.MessageTask;
import domain.ValidationException;
import domain.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.stream.Stream;


abstract class AbstractFileRepository<ID, E extends HasID<ID>> extends AbstractRepository<ID,E> {
    private String fileName;

    public AbstractFileRepository(Validator<E> vali, String fileName) {
        super(vali);
        this.fileName=fileName;
        loadData();
    }

    @Override
    public E save(E entity) throws ValidationException {
        E e=super.save(entity);
        if (e==null)
        {
            saveToFile(entity);
        }
        return e;
    }

    @Override
    public E update(E entity) throws ValidationException {
        E e=super.update(entity);
        if (e!=null)
        {
            saveToFile();
        }
        return e;
    }

    @Override
    public E delete(ID id) {
        E r=super.delete(id);
        saveToFile();
        return r;
    }

    void loadData() {
        //Path path = Paths.get("./src/data/Messages.txt");
        Path path = Paths.get(fileName);
        Stream<String> lines; //Stream - A sequence of elements supporting sequential and parallel aggregate operations.

        try {
            lines = Files.lines(path);
            lines.forEach(line -> {
                if (line.compareTo("")!=0) {
                    String[] fields = line.split(";");
                    E t = buildEntity(fields);
                    try {
                        super.save(t);
                    } catch (ValidationException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    abstract E buildEntity(String[] fields);

    void saveToFile(E e){
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, StandardOpenOption.APPEND)) {
            bufferedWriter.write(e.toString());
            bufferedWriter.newLine();
        } catch (IOException ex) { ex.printStackTrace();}
    }

    void saveToFile(){
        Path path = Paths.get(fileName);
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path))
        {
            for (E e: super.findAll()) {
                bufferedWriter.write(e.toString());
                bufferedWriter.newLine();
            }
        } catch (IOException ex) { ex.printStackTrace();}
    }
}

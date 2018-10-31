package sem4.repository;

import sem4.domain.HasID;

import sem4.domain.validator.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public abstract class AbstractFileRepository<ID, E extends HasID<ID>> extends InMemoryRepository<ID,E> {
    String fileName;
    public AbstractFileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();

    }

    private void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while((line = br.readLine())!= null){
                E temp = extractEntity(line);
                super.save(temp);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(String line);

    @Override
    public E save(E entity){
        E returnedEntity = super.save(entity);
        if(returnedEntity == null) {
            writeToFile(entity);
        }
        return returnedEntity;
    }

    protected void writeToFile(E entity){
        try(BufferedWriter bw = new BufferedWriter(new FileWriter(fileName,true))){
            bw.write(entity.toString());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

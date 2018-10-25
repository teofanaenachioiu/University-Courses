package repository;

import domain.HasID;
import validator.Validator;

import java.io.*;

public abstract class AbstractRepoInFile<ID, E extends HasID<ID>> extends  RepoInMemory<ID,E> {
    private String fileName;

    public AbstractRepoInFile(String fileName,Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    private void loadData(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while((line=br.readLine())!=null) {
                E entity = extractEntity(line);
                super.save(entity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract E extractEntity(String line);

    @Override
    public E save(E entity) {
        if(super.save(entity)==null) {
            writeToFile(entity);
            return null;
        }
        return entity;
    }

    @Override
    public E delete(ID id) {
        E temp=super.delete(id);
        if(temp!=null) {
            writeAll();
        }
        return temp;
    }

    @Override
    public E update(E entity) {
        E temp=super.update(entity);
        if(entities.get(entity.getID())!=null){
            writeAll();
            return null;
        }
        return entity;
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

    protected  void writeAll(){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName))) {
            for(E entity:entities.values()) {
                writeToFile(entity);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

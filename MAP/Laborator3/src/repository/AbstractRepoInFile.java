package repository;

import domain.HasID;
import validator.Validator;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Repository abstract in fisier
 * @param <ID> entitatea E are un id de tip ID
 * @param <E> entitatea de stocat in repo
 */
public abstract class AbstractRepoInFile<ID, E extends HasID<ID>> extends  RepoInMemory<ID,E> {
    private String fileName;

    /**
     * Constructorul clasei
     * @param fileName - String (numele fisierului)
     * @param validator - Validator (clasa Validator, validare date)
     */
    public AbstractRepoInFile(String fileName,Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    /**
     * Citirea datelor din fisier
     * @throws FileNotFoundException daca nu exista fisierul
     * @throws IOException daca nu se poate lucra cu fisierul dat
     */
    private void loadData() {
        int i=0;
        try (Stream<String> stream = Files.lines(Paths.get(this.fileName))) {
            stream.forEach(line -> {
                E entity = extractEntity(line);
                super.save(Optional.ofNullable(entity));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
        /**
     * Extrage datele de pe o linie din fisier si creeaza entitatea E
     * @param line - String (o linie din fisier)
     * @return entitatea E citita din fisier
     */
    public abstract E extractEntity(String line);

    /**
     * Salvarea entitate in fisier
     * @param entity - entitatea E de salvat
     * @return entity (entitatea exista deja) sau null (entitatea a fost salvata)
     */
    @Override
    public Optional<E> save(Optional<E> entity) {
        Optional<E> tmp = super.save(entity);
        if(!tmp.isPresent()) {
            writeToFile(entity.get());
            return Optional.empty();
        }
        return entity;
    }

    /**
     * Sterge o entitate din repo pe baza id-ului
     * @param id - entitate de tip ID (id-ul entitatii de sters)
     * @return temp (entitatea stearsa - poate fi si null)
     */
    @Override
    public Optional<E> delete(Optional<ID> id) {
        Optional<E> temp=super.delete(id);
        // ??
        if(temp.isPresent())writeAll();
        return temp;
    }

    /**
     * Actualizeaza o entitate
     * @param entity - entitatea de actualizat
     * @return entity (entitatea nu exista) sau null (entitatea a fost actualizata)
     */
    @Override
    public Optional<E> update(Optional<E> entity) {
        Optional<E> temp=super.update(entity);
        if(!temp.isPresent()){
            writeAll();
        }
        return temp;
    }

    /**
     * Adauga o entitate in fisier
     * @param entity - entitatea de adaugat in fisier
     * @throws FileNotFoundException daca nu exista fisierul
     * @throws IOException daca nu se poate lucra cu fisierul dat
     */
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

    /**
     * Rescrie toate entitatile in fisier
     * @throws FileNotFoundException daca nu exista fisierul
     * @throws IOException daca nu se poate lucra cu fisierul dat
     */
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

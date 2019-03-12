package tasks.repository;

import tasks.model.SortingAlgorithm;
import tasks.model.SortingOrder;
import tasks.model.SortingTask;

import java.io.*;

/**
 * Created by grigo on 10/26/16.
 */
public class SortingTaskFileRepository extends AbstractSortingTaskRepository {
    private String filename;
    public SortingTaskFileRepository(Validator<SortingTask> val, String filename) {
        super(val);
        this.filename=filename;
        readFromFile();
    }

    private void readFromFile(){

       try{
           try(BufferedReader br=new BufferedReader(new FileReader(filename))){
                String line=null;
               while((line=br.readLine())!=null){
                   String[] elems=line.split("[|]");
                   if (elems.length<5) {
                       System.err.println("linie invalida " + line);
                       continue;
                   }
                   SortingTask task=new SortingTask(Integer.parseInt(elems[0]),elems[1], SortingAlgorithm.valueOf(elems[3]), SortingOrder.valueOf(elems[4]),Integer.parseInt(elems[2]));
                   entities.put(task.getId(), task);
               }
            }
        }catch(IOException ex){
            throw new RepositoryException(ex);
        }
    }

    private void writeToFile(){

            try(PrintWriter pw=new PrintWriter(filename)){
                for(SortingTask task:entities.values()){
                    pw.printf("%d|%s|%d|%s|%s",task.getId(),task.getDescriere(),task.getNrElem(),task.getAlg(),task.getOrder());
                    pw.println();
                }
            }catch (FileNotFoundException e) {
                throw new RepositoryException(e);
            }

    }

    @Override
    public void save(SortingTask entity) {
        super.save(entity);
        writeToFile();
    }

    @Override
    public void delete(Integer integer) {
        super.delete(integer);
        writeToFile();
    }
}

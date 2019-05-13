package start;

import model.Categorie;
import model.Proba;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import restClient.ProbeClient;
import restControllers.ServiceException;

public class StartRestClient {
    private final static ProbeClient probeClient = new ProbeClient();

    public static void main(String[] args) {

        Proba probaT = new Proba("Atletism", Categorie.CATEGORIE_12_15);

        try {
            show(()-> System.out.println("Proba de adaugat: "+ probaT));
//            // create
            show(() -> System.out.println("Proba adaugata :" + probeClient.create(probaT)));
            // get all
            show(() -> {
                System.out.println("Lista de probe");
                Proba[] res = probeClient.getAll();
                for (Proba u : res) {
                    System.out.println(u.getID() + ": " + u.getDenumire() +" / " + u.getCatg());
                }
            });
            int size = probeClient.getAll().length;
            Integer idLast = probeClient.getAll()[size-1].getID();
            probaT.setID(idLast);
            // find by id
            show(()->{
                System.out.println("Proba cu id-ul "+idLast + ": "+probeClient.getById(idLast));
            });
            // update
            probaT.setDenumire("AtletismUPDATE");
            show(()->{
                probeClient.update(probaT);
                System.out.println("Se updateaza proba cu id-ul "+idLast + ": "+ probaT);
            });
            // delete
            show(()->{
                probeClient.delete(idLast);
                System.out.println("Se sterge proba cu id-ul "+idLast);
            });

            // get all
            show(() -> {
                System.out.println("Lista de probe");
                for (Proba u : probeClient.getAll()) {
                    System.out.println(u.getID() + ": " + u.getDenumire() +" / " + u.getCatg());
                }
            });
        } catch (RestClientException ex) {
            System.out.println("Exception ... " + ex.getMessage());
        }

    }


    private static void show(Runnable task) {
        try {
            task.run();
        } catch (ServiceException e) {
            System.out.println("Service exception" + e);
        }
    }
}

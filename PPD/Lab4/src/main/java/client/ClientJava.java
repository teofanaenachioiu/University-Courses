package client;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ClientJava {
    private static int MAXIM_BILETE = 3;

    private static List<Integer> getSpectacoleIdList() {
        final String uri = "http://localhost:3000/spectacole";

        RestTemplate restTemplate = new RestTemplate();
        Integer[] result = restTemplate.getForObject(uri, Integer[].class);

        if (result == null) return null;
        return Arrays.asList(result);
    }

    private static List<Integer> getLocuriLibereSpectacol(int idSpectacol) {
        final String uri = "http://localhost:3000/locuri/" + idSpectacol;

        RestTemplate restTemplate = new RestTemplate();
        Integer[] result = restTemplate.getForObject(uri, Integer[].class);

        if (result == null) return null;
        return new ArrayList<>(Arrays.asList(result));
    }


    private static void cumparaBilet(int idSpectacol, int numarBilete, List<Integer> listaLocuri) {
        final String uri = "http://localhost:3000/vanzare";

        VanzareClient vanzareClient = VanzareClient.builder().id(idSpectacol).numar(numarBilete).locuri(listaLocuri).build();

        RestTemplate restTemplate = new RestTemplate();
        try {
            restTemplate.postForObject(uri, vanzareClient, String.class);
            System.out.println("Vanzare acceptata");
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            if (HttpStatus.BAD_REQUEST.equals(httpClientOrServerExc.getStatusCode())) {
                System.out.println("Vanzare anulata");
            } else {
                System.out.println("Hopa. S-a intamplat ceva ...");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            List<Integer> listaIdSpectacole = getSpectacoleIdList();

            if (listaIdSpectacole != null) {
                while (true) {
                    int rnd = new Random().nextInt(listaIdSpectacole.size());
                    int idSpectacol = listaIdSpectacole.get(rnd);
                    System.out.println("Vreau sa compar bilete la spectacolul " + idSpectacol);

                    List<Integer> listaLocuriLibere = getLocuriLibereSpectacol(idSpectacol);

                    if (listaLocuriLibere != null) {

                        int cateBilete = getNrBilete(listaLocuriLibere.size());
                        System.out.println("Numar bilete " + cateBilete);

                        List<Integer> locuri = new ArrayList<>();

                        for (int i = 0; i < cateBilete; i++) {
                            int pozitieLoc = new Random().nextInt(listaLocuriLibere.size());
                            Integer loc = listaLocuriLibere.get(pozitieLoc);

                            locuri.add(loc);
//                        System.out.println("Lista locurilor libere are dimensiunea " + listaLocuriLibere.size()
//                                + ". Vreau sa sterg pozitia " + pozitieLoc);
                            listaLocuriLibere.remove(pozitieLoc);
//                        System.out.println("Loc: " + locuri.get(i));
                        }

                        cumparaBilet(idSpectacol, cateBilete, locuri);
                    }

                    Thread.sleep(5000);
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private static int getNrBilete(int dimListaLocuriLibere) {
        return Math.min(dimListaLocuriLibere, new Random().nextInt(MAXIM_BILETE) + 1);
    }
}

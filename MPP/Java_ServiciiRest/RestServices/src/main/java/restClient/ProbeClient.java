package restClient;

import model.Proba;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import restControllers.ServiceException;

import java.util.concurrent.Callable;

public class ProbeClient {
    private static final String URL = "http://localhost:8080/concurs";

    private RestTemplate restTemplate = new RestTemplate();

    private <T> T execute(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    public Proba[] getAll() {
        return execute(() -> restTemplate.getForObject(URL+"/probe", Proba[].class));
    }

    public Proba getById(Integer id) {
        return execute(() -> restTemplate.getForObject(String.format("%s/probe/%s", URL, id.toString()), Proba.class));
    }

    public Proba create(Proba proba) {

        return execute(() -> restTemplate.postForObject(URL, proba, Proba.class));
    }

    public void update(Proba proba) {
        execute(() -> {
            restTemplate.put(String.format("%s/probe/%s", URL, proba.getID()), proba);
            return null;
        });
    }

    public void delete(Integer id) {
        execute(() -> {
            restTemplate.delete(String.format("%s/probe/%s", URL, id));
            return null;
        });
    }

}

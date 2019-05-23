package restControllers;

import model.Proba;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import repository.ProbaRepository;
import repository.RepositoryException;

@CrossOrigin
@RestController
@RequestMapping("/concurs")
public class ProbeController {
    @Autowired
    private ProbaRepository repository;

    @RequestMapping(value = "/probe", method = RequestMethod.GET)
    public Proba[] getAll() {
        Proba[] probas = new Proba[repository.size()];
        int index = 0;
        for (Proba p : repository.findAll()) {
            probas[index] = p;
            index += 1;
        }
        return probas;
    }

    @RequestMapping(value = "/probe/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){
        Proba proba=repository.findOne(id);
        if (proba==null)
            return new ResponseEntity<String>("Proba inexistenta",HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity<Proba>(proba, HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.POST)
    public Proba create(@RequestBody Proba proba){
        Integer id = repository.save(proba);
        proba.setID(id);
        return proba;
    }

    @RequestMapping(value = "/probe/{id}", method = RequestMethod.PUT)
    public Proba update(@RequestBody Proba proba) {
        System.out.println("Se actualizeaza proba ...");
        repository.update(proba.getID(),proba);
        return proba;

    }

    @RequestMapping(value="probe/{id}", method= RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        System.out.println("Se sterge proba ... "+id.toString());
        try {
            repository.delete(id);
            return new ResponseEntity<Proba>(HttpStatus.OK);
        }catch (RepositoryException ex){
            System.out.println("Ctrl Delete <proba> exception");
            return new ResponseEntity<String>(ex.getMessage(),HttpStatus.BAD_REQUEST);
        }
    }

    @ExceptionHandler(RepositoryException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String probaError(RepositoryException e) {
        return e.getMessage();
    }
}

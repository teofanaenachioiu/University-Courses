package repository;

import model.Categorie;
import model.Proba;

public interface IRepositoryProba extends IRepository<Integer, Proba> {
    public Iterable<Categorie> listaCategorii();
}
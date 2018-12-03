package repository;


import java.util.Optional;

/**
         * CRUD operations repository interface
         * @param <ID> - type E must have an attribute of type ID
         * @param <E> - type of entities saved in repository
         */
public interface CrudRepository<ID, E> {
        /**
         *
         * @param id -the id of the entity to be returned
         * id must not be null
         * @return the entity with the specified id
         * or null - if there is no entity with the given id
         * @throws IllegalArgumentException
         * if id is null.
         */
    Optional<E> findOne(Optional<ID> id);
        /**
         *
         * @return all entities
         */
    Iterable<E> findAll();
        /**
        *
        * @param entity
        * entity must be not null
        * @return null- if the given entity is saved
        * otherwise returns the entity (id already exists)
        * @throws ValidationException
        * if the entity is not valid
        * @throws IllegalArgumentException
        * if the given entity is null. *
        */
    Optional<E> save(Optional<E> entity) throws ValidationException;
        /**
         * removes the entity with the specified id
         * @param id
         * id must be not null
         * @return the removed entity or null if there is no entity with the given id
         * @throws IllegalArgumentException
         * if the given id is null.
         */
    Optional<E> delete(Optional<ID> id);
        /**
         *
         * @param entity
         * entity must not be null
         * @return null - if the entity is updated,
         * otherwise returns the entity - (e.g id does not exist).
         * @throws IllegalArgumentException
         * if the given entity is null.
         * @throws ValidationException
         * if the entity is not valid.
         */
    Optional<E> update(Optional<E> entity);
}

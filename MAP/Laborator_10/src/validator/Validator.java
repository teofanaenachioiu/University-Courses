package validator;

import repository.ValidationException;

/**
 * Interfata Validator
 * @param <E>
 */
public interface Validator<E> {
    /**
     * Operatiile interfetei Validator
     * @param entity - entitatea de validat
     * @throws ValidationException daca entitatea nu e valida
     */
    void validate(E entity) throws ValidationException;
}
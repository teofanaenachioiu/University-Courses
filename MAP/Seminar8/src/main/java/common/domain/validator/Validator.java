package common.domain.validator;

public interface Validator<E> {
    /**
     *
     * @param entity
     * @throws ValidationException
     */
    void validate(E entity) ;
}

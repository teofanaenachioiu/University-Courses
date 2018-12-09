package validation;

public interface Validator<E> {
    public void validate(E entity);
}

package sem5;

//Interfata functionala are o singura metoda abstracta!
// + metode default, statice

@FunctionalInterface
public interface Area<E> {
    float compute(E entity);
}

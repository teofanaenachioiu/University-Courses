import java.util.Objects;

public class Monom {
    private int coefficient;
    private int exponent;

    public Monom() {
        coefficient = 0;
        exponent = 0;
    }

    public Monom(int coefficient, int exponent) {
        this.coefficient = coefficient;
        this.exponent = exponent;
    }

    public int getCoefficient() {
        return coefficient;
    }

    public void setCoefficient(int coefficient) {
        this.coefficient = coefficient;
    }

    public int getExponent() {
        return exponent;
    }

    public void setExponent(int exponent) {
        this.exponent = exponent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Monom monom = (Monom) o;
        return coefficient == monom.coefficient &&
                exponent == monom.exponent;
    }

    @Override
    public int hashCode() {
        return Objects.hash(coefficient, exponent);
    }

    @Override
    public String toString() {
        return coefficient + "," + exponent;
    }
}

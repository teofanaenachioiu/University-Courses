package sem3;

import java.security.InvalidParameterException;
import java.util.Objects;

public class Student implements Comparable
{
    String nume;
    float media;

    public Student(String nume, float media) {
        this.nume = nume;
        this.media = media;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    @Override
    public String toString() {
        return "Student{" +
                "nume='" + nume + '\'' +
                ", media=" + media +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Float.compare(student.getMedia(), getMedia()) == 0 &&
                Objects.equals(getNume(), student.getNume());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNume(), getMedia());
    }

    @Override
    public int compareTo(Object o) {
        if(!(o instanceof Student))
            throw new IllegalArgumentException();
        return this.nume.compareTo(((Student) o).getNume());
    }
}

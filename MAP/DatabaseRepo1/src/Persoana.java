public class Persoana implements hasId<Integer>{
    private int id;
    private String nume;

    public Persoana(int id, String nume) {
        this.id = id;
        this.nume = nume;
    }

    public Integer getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Persoana{");
        sb.append("id=").append(id);
        sb.append(", nume='").append(nume).append('\'');
        sb.append('}');
        return sb.toString();
    }
}

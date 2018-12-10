package domain;

//-	Clasa Client are urmatoarele atribute: clientId: Long,  name: String

import java.util.Objects;

public class Client implements HasID<Long> {
    private Long clientId;
    private String name;

    public Client(Long clientId, String name) {
        this.clientId = clientId;
        this.name = name;
    }

    @Override
    public Long getID() {
        return this.clientId;
    }

    @Override
    public void setID(Long aLong) {
        this.clientId=aLong;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.clientId.toString() + "," + this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return Objects.equals(clientId, client.clientId) &&
                Objects.equals(getName(), client.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, getName());
    }
}

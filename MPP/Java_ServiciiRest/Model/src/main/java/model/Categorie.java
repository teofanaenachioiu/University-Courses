package model;

import java.io.Serializable;

public enum Categorie implements Serializable {
    CATEGORIE_6_8, CATEGORIE_9_11, CATEGORIE_12_15;

    public String getStatus() {
        return this.name();
    }
}

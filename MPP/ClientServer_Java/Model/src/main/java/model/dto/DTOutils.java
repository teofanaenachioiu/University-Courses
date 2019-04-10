package model.dto;

import model.Categorie;
import model.Proba;

public class DTOutils {
    public static ProbaDTO[] getDTO(Proba[] proba, int[] nrParticipanti){
        ProbaDTO[] frDTO=new ProbaDTO[proba.length];
        ProbaDTO probaDTO;

        for(int i=0;i<proba.length;i++) {
            probaDTO = new ProbaDTO(proba[i].getID(), proba[i].getDenumire(), proba[i].getCatg().toString(), nrParticipanti[i]);
            frDTO[i]=probaDTO;
        }
        System.out.println(frDTO.length);
        return frDTO;
    }

    public static Proba[] getFromDTO(ProbaDTO[] proba){
        Proba[] friends=new Proba[proba.length];
        for(int i=0;i<proba.length;i++){
            friends[i]=new Proba(proba[i].getId(),proba[i].getDenumire(), Categorie.valueOf(proba[i].getCategorie()));
        }
        return friends;
    }
}

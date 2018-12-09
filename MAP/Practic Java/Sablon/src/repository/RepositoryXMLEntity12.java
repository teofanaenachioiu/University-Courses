package repository;

import domain.Entity1;
import domain.Entity12;
import domain.Entity2;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import validation.Validator;

import java.time.LocalDate;

public class RepositoryXMLEntity12 extends AbstractRepositoryXML<Pair<Entity1, Entity2>, Entity12> {

    public RepositoryXMLEntity12(String fileName, Validator<Entity12> validator) {
        super(fileName, validator);
    }

    @Override
    public Element createElementfromEntity(Document document, Entity12 entity) {
        Element e=document.createElement("entity12");
        e.setAttribute("e1",entity.getEntity1().toString());
        e.setAttribute("e2",entity.getEntity2().toString());

        Element valoare=document.createElement("valoare");
        valoare.setTextContent(entity.getValoare().toString());
        e.appendChild(valoare);

        return e;
    }

    @Override
    public Entity12 createEntityFromElement(Element element) {
        String e1=element.getAttribute("e1");

        String[] parts1=e1.split("[ ]");
        Entity1 entity1=new Entity1(parts1[0],parts1[1], LocalDate.parse(parts1[2]));

        String e2=element.getAttribute("e2");
        String parts2[]=e2.split("[ ]");
        Entity2 entity2=new Entity2(Integer.valueOf(parts2[0]),parts2[1]);

        Float valoare=Float.valueOf(element.getElementsByTagName("valoare").item(0).getTextContent());

        return new Entity12(entity1,entity2,valoare);
    }
}

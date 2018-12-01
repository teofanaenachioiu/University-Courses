package repository;

import domain.Tema;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import validator.Validator;

public class TemaRepoXML extends AbstractRepoXML<String, Tema> {
    /**
     * Constructorul clasei
     *
     * @param fileName
     * @param validator - entitate Validator (validarea datelor)
     */
    public TemaRepoXML(String fileName, Validator<Tema> validator) {
        super(fileName, validator);
    }

    @Override
    public Element createElementfromEntity(Document document, Tema entity) {
        Element e = document.createElement("tema");
        e.setAttribute("idTema", entity.getID());

        Element desc = document.createElement("descriere");
        desc.setTextContent(entity.getDescriere());
        e.appendChild(desc);

        Element deadline = document.createElement("deadline");
        deadline.setTextContent(entity.getDeadline().toString());
        e.appendChild(deadline);

        Element dataPredare = document.createElement("dataPredare");
        dataPredare.setTextContent(entity.getDataPredare().toString());
        e.appendChild(dataPredare);

        return e;
    }

    @Override
    public Tema createEntityFromElement(Element element) {
        String temaId = element.getAttribute("idTema");
        NodeList nods = element.getChildNodes();
        String desc =element.getElementsByTagName("descriere")
                .item(0)
                .getTextContent();

        String deadline =element.getElementsByTagName("deadline")
                .item(0)
                .getTextContent();

        String dataPredare =element.getElementsByTagName("dataPredare")
                .item(0)
                .getTextContent();
        
        return new Tema(temaId,desc,deadline,dataPredare);
    }
}

package repository;

import domain.Nota;
import javafx.util.Pair;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import validator.Validator;

public class NotaRepoXML extends AbstractRepoXML<Pair<String,String>, Nota> {
    /**
     * Constructorul clasei
     *
     * @param fileName
     * @param validator - entitate Validator (validarea datelor)
     */
    public NotaRepoXML(String fileName, Validator<Nota> validator) {
        super(fileName, validator);
    }

    @Override
    public Element createElementfromEntity(Document document, Nota entity) {
        Element e = document.createElement("nota");
        e.setAttribute("idStudent", entity.getStudentID());
        e.setAttribute("idTema", entity.getTemaID());

        Element data = document.createElement("dataCurenta");
        data.setTextContent(entity.getDataCurenta());
        e.appendChild(data);

        Element notaProf = document.createElement("notaProf");
        notaProf.setTextContent(entity.getNotaProf().toString());
        e.appendChild(notaProf);

        return e;
    }

    @Override
    public Nota createEntityFromElement(Element element) {
        String studentId = element.getAttribute("idStudent");
        String temaId = element.getAttribute("idTema");
        NodeList nods = element.getChildNodes();
        String data =element.getElementsByTagName("dataCurenta")
                .item(0)
                .getTextContent();

        String notaProf =element.getElementsByTagName("notaProf")
                .item(0)
                .getTextContent();

        return new Nota(studentId,temaId,data,notaProf);
    }
}

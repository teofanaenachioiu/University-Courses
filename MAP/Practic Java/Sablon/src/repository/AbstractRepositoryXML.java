package repository;

import domain.HasID;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import validation.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.util.Optional;

public abstract class AbstractRepositoryXML<ID, E extends HasID<ID>> extends RepositoryInMemory<ID,E>{
    private String fileName;

    public AbstractRepositoryXML(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        loadData();
    }

    private void loadData() {
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .parse(this.fileName);

            Element root = document.getDocumentElement();
            NodeList children = root.getChildNodes();
            for(int i=0; i < children.getLength(); i++) {
                Node entityElement = children.item(i);
                if(entityElement instanceof Element) {
                    E entity = createEntityFromElement((Element)entityElement);
                    super.save(entity);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeToFile(){
        try {
            Document document = DocumentBuilderFactory
                    .newInstance()
                    .newDocumentBuilder()
                    .newDocument();
            Element root  = document.createElement("inbox");
            document.appendChild(root);
            super.findAll().forEach(e->{
                Element elem = createElementfromEntity(document,e);
                root.appendChild(elem);
            });

            //write Document to file
            Transformer transformer = TransformerFactory.
                    newInstance().newTransformer();
            transformer.transform(new DOMSource(document),
                    new StreamResult(fileName));

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public abstract Element createElementfromEntity(Document document, E entity);

    public abstract E createEntityFromElement(Element element);

    @Override
    public Optional<E> save(E entity) {
        Optional<E> stuff = super.save(entity);
        if (!stuff.isPresent()){
            writeToFile();
        }
        return stuff;
    }

    @Override
    public Optional<E> delete(ID id) {
        Optional<E> temp=super.delete(id);
        if(temp.isPresent())
            writeToFile();
        return temp;
    }

    @Override
    public Optional<E> update(E entity) {
        Optional<E> temp=super.update(entity);
        if(!temp.isPresent()){
            writeToFile();
        }
        return temp;
    }
}

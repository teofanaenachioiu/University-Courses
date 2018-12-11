package repository;

public class RepositoryXMLEntity1 {
}

//public class RepositoryXMLEntity1 extends AbstractRepositoryXML<String, Entity1> {
//
//    public RepositoryXMLEntity1(String fileName, Validator<Entity1> validator) {
//        super(fileName, validator);
//    }
//
//    @Override
//    public Element createElementfromEntity(Document document, Entity1 entity) {
//        Element e=document.createElement("entity");
//        e.setAttribute("id",entity.getID());
//
//        Element nume=document.createElement("nume");
//        e.setTextContent(entity.getNume());
//        e.appendChild(nume);
//
//        Element data=document.createElement("data");
//        e.setTextContent(entity.getData().toString());
//        e.appendChild(data);
//
//        return e;
//    }
//
//    @Override
//    public Entity1 createEntityFromElement(Element element) {
//        String id=element.getAttribute("id");
//        //NodeList nods=element.getChildNodes();
//
//        String nume=element.getElementsByTagName("nume")
//                .item(0)
//                .getTextContent();
//
//        String data=element.getElementsByTagName("data")
//                .item(0)
//                .getTextContent();
//
//        return new Entity1(id,nume,LocalDate.
//                parse(data));
//    }
//}

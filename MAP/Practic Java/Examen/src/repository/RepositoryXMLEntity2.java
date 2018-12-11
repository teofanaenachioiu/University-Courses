package repository;

public class RepositoryXMLEntity2 {
}
//public class RepositoryXMLEntity2 extends AbstractRepositoryXML<Integer, Entity2> {
//    public RepositoryXMLEntity2(String fileName, Validator<Entity2> validator) {
//        super(fileName, validator);
//    }
//
//    @Override
//    public Element createElementfromEntity(Document document, Entity2 entity) {
//        Element e =document.createElement("entity2");
//        e.setAttribute("id",entity.getID().toString());
//
//        Element denumire=document.createElement("denumire");
//        e.setTextContent(entity.getDenumire());
//        e.appendChild(denumire);
//
//        return e;
//    }
//
//    @Override
//    public Entity2 createEntityFromElement(Element element) {
//        Integer id=Integer.valueOf(element.getAttribute("id"));
//
//        String denumire= element.getElementsByTagName("denumire").item(0).getTextContent();
//        return new Entity2(id,denumire);
//    }
//}
public class Main {
    public static void main(String[] args) {
        System.out.println("Hello!");
        Repository repo=new RepositoryPersoane(new DatabaseHandler());
        System.out.println("Size repo: "+repo.size());
        repo.add(new Persoana(1,"Teofana"));
        System.out.println("Size repo: "+repo.size());
        System.out.println("Persoana cautata: "+repo.find(1).get());
    }
}

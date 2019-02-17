package jurnal;

/**
 * Created by grigo on 2/27/17.
 */
public class TestApp {

    public static void main( String[] args ) {
        TestService service = new TestService();
        System.out.println(service.retrieveMessage());
        System.out.println(service.retrieveMessage());
        service.exampleException();
    }
}
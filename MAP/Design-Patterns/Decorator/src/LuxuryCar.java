/*
Concrete Decorators

Extending the base decorator functionality and modifying the component behavior accordingly.
 */

public class LuxuryCar extends CarDecorator {

    public LuxuryCar(Car c) {
        super(c);
    }

    @Override
    public void assemble(){
        super.assemble();
        System.out.print(" Adding features of Luxury Car.");
    }
}

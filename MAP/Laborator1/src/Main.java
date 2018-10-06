public class Main {

    private static boolean isComplexNumber(String nr){
        return true;
    }

    private static boolean isOperator (String op){
        String [] operators={"+","-","*","/"};
        for (String o: operators) {
            if (o.equals(op))
                return true;
        }
        return false;
    }

    private static boolean validation (String [] args){

        if(args.length%2==0) return false;

        for(int i=0;i<args.length;i++){
            if(i%2==0){
                if(isComplexNumber((args[i]))==false) return false;
            }
            else{
                if(isOperator(args[i])==false) return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        NumarComplex nrs=new NumarComplex("3i");
        System.out.println(nrs.toString());

        NumarComplex nrs1=new NumarComplex("25");
        System.out.println(nrs1.toString());

        NumarComplex nrs2=new NumarComplex("-100-9i");
        System.out.println(nrs2.toString());

        if(validation(args)==true) {
            System.out.println("Parametrii reprezinta o expresie aritmetica.");
        }
        else {
            System.out.println("Parametrii NU reprezinta o expresie aritmetica.");
        }
    }
}

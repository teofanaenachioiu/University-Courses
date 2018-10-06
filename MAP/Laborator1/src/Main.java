public class Main {

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
                try{
                    NumarComplex nr=new NumarComplex(args[i]);
                    System.out.println("Numarul complex este: "+nr.toString());
                }
                catch(NumberFormatException e){
                    return false;
                }
            }
            else{
                if(!isOperator(args[i])) return false;
            }
        }

        return true;
    }

    public static void main(String[] args) {
        if(validation(args)==true) {
            System.out.println("Parametrii reprezinta o expresie aritmetica.");
        }
        else {
            System.out.println("Parametrii NU reprezinta o expresie aritmetica.");
        }
    }
}

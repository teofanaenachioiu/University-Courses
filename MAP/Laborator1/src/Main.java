public class Main {

    private static boolean isComplexNumber(String nr){
        if(nr.charAt(nr.length()-1)!='i') {
            //cazul in care numarul are doar parte reala
            if (nr.charAt(0)=='-'){
                //numarul e negativ; elimin semnul din fata
                nr=nr.substring(1);
            }
            try {
                //verific daca numarul e intreg
                Integer.parseInt(nr);
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
        else {
            //cazul in care numarul are si parte imaginara
            //elimin i-ul de la final
            nr=nr.substring(0,nr.length()-1);
            if(nr.charAt(0)=='-'){
                //daca numarul incepe cu semn, elimin semnul
                nr=nr.substring(1);
            }
            //Split dupa - sau +
            String[] parts = nr.split("[-+]");
            try {
                //cazul "bi"
                Integer.parseInt(parts[0]);
                if (parts.length==2) {
                    //cazul "a+bi"
                    Integer.parseInt(parts[1]);
                }
                return true;
            } catch (NumberFormatException e) {
                return false;
            }
        }
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
        /*NumarComplex nrs=new NumarComplex("3i");
        System.out.println(nrs.toString());

        NumarComplex nrs1=new NumarComplex("25");
        System.out.println(nrs1.toString());

        NumarComplex nrs2=new NumarComplex("-100-9i");
        System.out.println(nrs2.toString());*/

        if(validation(args)==true) {
            System.out.println("Parametrii reprezinta o expresie aritmetica.");
        }
        else {
            System.out.println("Parametrii NU reprezinta o expresie aritmetica.");
        }
    }
}

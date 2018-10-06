public class Main {

    private static boolean isComplexNumber(String nr){
        if(nr.charAt(nr.length()-1)!='i') {
            //cazul in care numarul are doar parte reala
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
            //Split dupa - sau +
            String[] parts = nr.split("[-+]");
            try {
                //Numarul a inceput cu minu => la split parts[0]=""
                if(!parts[0].equals("")) {
                    Integer.parseInt(parts[0]);
                }
                if (parts.length==2) {
                    System.out.println(parts[1]);
                    Integer.parseInt(parts[1]);
                }
                if (parts.length==3) {
                    System.out.println(parts[2]);
                    Integer.parseInt(parts[2]);
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
        if(validation(args)==true) {
            System.out.println("Parametrii reprezinta o expresie aritmetica.");
        }
        else {
            System.out.println("Parametrii NU reprezinta o expresie aritmetica.");
        }
    }
}

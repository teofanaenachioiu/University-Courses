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
                    //System.out.println("Numarul complex este: "+nr.toString());
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
        if(validation(args)) {
            System.out.println("Parametrii reprezinta o expresie aritmetica.");
            NumarComplex rez=new NumarComplex(args[0]);
            NumarComplex []vf=new NumarComplex[args.length/2+1];
            vf[0]=rez;
            int poz=1;
            for(int i=2;i<args.length;i=i+2){
                if(args[i-1].equals("+")) rez.add(new NumarComplex(args[i]));
                if(args[i-1].equals("-")) rez.subtract(new NumarComplex(args[i]));
                if(args[i-1].equals("*")) rez.mul(new NumarComplex(args[i]));
                if(args[i-1].equals("/")) rez.div(new NumarComplex(args[i]));
                vf[poz++]=new NumarComplex(args[i]);
            }
            System.out.println("Rezultatul expresiei este: "+rez.toString());

            Poligon polig=new Poligon(vf);
            System.out.println("Perimetrul poligonului este: "+polig.perimetru());

        }
        else {
            System.out.println("Parametrii NU reprezinta o expresie aritmetica.");
        }
    }
}

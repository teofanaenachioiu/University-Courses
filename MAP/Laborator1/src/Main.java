public class Main {

    private static boolean isOperator (String op){
        String [] operators={"+","-","*","/"};
        for (String o: operators) {
            if (o.equals(op))
                return true;
        }
        return false;
    }

    private static boolean isSameOperator (String op, String o){
        if (o.equals(op))
            return true;
        return false;
    }

    private static boolean validation (String [] args){

        if(args.length%2==0) {
            return false;
        }

        for(int i=0;i<args.length;i++){
            if(i%2==0){
                try{
                    NumarComplex nr=new NumarComplex(args[i]);
                }
                catch(NumberFormatException e){
                    return false;
                }
            }
            else{
                if(i==1) {
                    if(!isOperator(args[1])) return false;
                }
                else if(!isSameOperator(args[i],args[1])) return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        if(validation(args)) {
            System.out.println("Parametrii reprezinta o expresie aritmetica.");


            NumarComplex rez=new NumarComplex(args[0]);
            for(int i=2;i<args.length;i=i+2){
                if(args[i-1].equals("+")) rez.add(new NumarComplex(args[i]));
                if(args[i-1].equals("-")) rez.subtract(new NumarComplex(args[i]));
                if(args[i-1].equals("*")) rez.mul(new NumarComplex(args[i]));
                if(args[i-1].equals("/")) rez.div(new NumarComplex(args[i]));
            }
            System.out.println("Rezultatul expresiei este: "+rez.toString());


            NumarComplex []vf=new NumarComplex[args.length/2+1];
            int poz=0;
            for(int i=0;i< args.length;i=i+2){
                vf[poz++]=new NumarComplex(args[i]);
            }
            Poligon polig=new Poligon(vf);
            System.out.println("Perimetrul poligonului este: "+polig.perimetru());

        }
        else {
            System.out.println("Parametrii NU reprezinta o expresie aritmetica.");
        }
    }
}

public class NumarComplex {
    private int img, real;

    public NumarComplex(){
        this.img = 0;
        this.real = 0;
    }

    public NumarComplex(String nr)  {
        real = 0;
        img = 0;
        if(nr.charAt(nr.length()-1)!='i') {
            //cazul in care numarul are doar parte reala
            real=Integer.parseInt(nr);
        }
        else {
            boolean isNegative = false;
            //cazul in care numarul are si parte imaginara
            //elimin i-ul de la final
            nr = nr.substring(0, nr.length() - 1);

            String[] parts = nr.split("[-+]");
            for(String c:parts)System.out.println("|"+c+"|");
            System.out.println("Lungimea lui parts "+parts.length);
            if(parts.length==0) img=-1; //cazul -i
            if (parts.length>0&&parts[0].equals("")) {
                //Numarul a inceput cu minus => la split parts[0]=""
                isNegative = true;
            }
            if(parts.length==1){
                //cazul bi
                if(isNegative)img=1; //cazul i
                else {
                    //cazul a+i si a-i
                    if(nr.charAt(nr.length()-1)=='-'||nr.charAt(nr.length()-1)=='+') {
                        real=Integer.parseInt(parts[0]);
                        if(nr.indexOf('-')>0)img=-1;
                        else img=1;
                    }
                    else img = Integer.parseInt(parts[0]);
                }
            }
            if (parts.length == 2) {
                if (isNegative) {
                    //cazul -bi
                    if(parts[1].equals("")) img=1;
                    else img = (-1)*Integer.parseInt(parts[1]);
                }
                else {
                    //cazul a+bi
                    if(nr.charAt(nr.length()-1)=='-'||nr.charAt(nr.length()-1)=='+') {
                        real=Integer.parseInt(parts[1]);
                        if(nr.indexOf('-')>0)img=-1;
                        else img=1;
                    }
                    else{
                        real = Integer.parseInt(parts[0]);
                        if (parts[1].equals("")) img = 1;
                        else img = Integer.parseInt(parts[1]);
                        if (nr.indexOf('-') > 0) img = img * (-1);//cazul a-bi
                    }
                }
            }
            if (parts.length == 3) {
                //cazul -a+bi
                real = (-1)*Integer.parseInt(parts[1]);
                if(parts[2].equals("")) img=1;
                else img = Integer.parseInt(parts[2]);
                if(nr.indexOf('-')>0)img=img*(-1);//cazul -a-bi
            }
        }
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getReal() {
        return real;
    }

    public void setReal(int real) {
        this.real = real;
    }

    public NumarComplex add(NumarComplex z)
    {
        this.setReal(this.real + z.getReal());
        this.setImg(this.img + z.getImg());
        return this;
    }

    public NumarComplex subtract(NumarComplex z)
    {
        this.setReal(this.real - z.getReal());
        this.setImg(this.img - z.getImg());
        return this;
    }

    @Override
    public String toString() {
        boolean hasReal = (real!=0);
        boolean hasImaginary = (img!=0);
        if(!hasReal && !hasImaginary) return "0";
        String realNR = "";
        if(hasReal) realNR += real;
        String sign = ( hasReal && (img>0) ? "+" : "" );
        String imgNR = sign+ img +"i";
        if(img==1) imgNR = sign+"i";
        if(img==-1) imgNR = "-i";
        return realNR + (hasImaginary ? imgNR : "");
    }
}

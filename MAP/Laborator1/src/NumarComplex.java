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

            String[] parts = nr.split("[-+]");

            if (parts[0].equals("")) {
                //Numarul a inceput cu minus => la split parts[0]=""
                isNegative = true;
            }
            if(parts.length==1){
                //cazul bi
                parts[0] = parts[0].substring(0, nr.length() - 1);
                if(parts[0].equals("")) {
                    if (nr.charAt(nr.length() - 1) == '-') img = -1;
                    else img = 1;
                }
                else img=Integer.parseInt(parts[0]);
            }
            if (parts.length == 2) {
                if (isNegative) {
                    //cazul -bi
                    parts[1] = parts[1].substring(0, parts[1].length() - 1);
                    if(parts[1].equals(""))img=-1;
                    else img = -1*Integer.parseInt(parts[1]);
                }
                else {
                    //cazul a+bi si a-bi
                    parts[1] = parts[1].substring(0, parts[1].length() - 1);
                    real = Integer.parseInt(parts[0]);
                    if(parts[1].equals(""))img=1;
                    else img = Integer.parseInt(parts[1]);
                    if(nr.indexOf('-')>=0) img=img*(-1);
                    }
                }
            if (parts.length == 3) {
                //cazul -a+bi si -a-bi
                parts[2] = parts[2].substring(0, parts[2].length() - 1);
                real = (-1)*Integer.parseInt(parts[1]);
                if(parts[2].equals("")) img=1;
                else img = Integer.parseInt(parts[2]);
                nr=nr.substring(1);
                if(nr.indexOf('-')>=0) img=img*(-1);
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

    public NumarComplex mul(NumarComplex z)
    {
        this.setReal(this.real*z.getReal()-this.img*z.getImg());
        this.setImg(this.real*z.getImg()+this.img*z.getReal());
        return this;
    }

    public NumarComplex div(NumarComplex z)
    {
        this.setReal((this.real*z.getReal()+this.img*z.getImg())/(z.getReal()*z.getReal()+z.getImg()*z.getImg()));
        this.setImg((z.getReal()*this.img-this.real*z.getImg())/(z.getReal()*z.getReal()+z.getImg()*z.getImg()));
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

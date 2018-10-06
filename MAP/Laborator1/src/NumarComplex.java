public class NumarComplex {
    private int img, real;

    public NumarComplex(){
        this.img = 0;
        this.real = 0;
    }

    public NumarComplex(String value)  {
        real = 0;
        img = 0;
        int i = value.length(); // length of current string to parse

        // if "i" is last character(value has imaginary part)
        if (value.charAt(value.length() - 1) == 'i') {
            i = value.length() - 1;
            img = 1;

            // case "i"
            if (value.length() == 1) {
                return;
            }

            // case "-xi" | "+xi" | "xi"
            for (int j = i - 1; j >= 0; j--) {
                if (value.charAt(j) == '-') {
                    // case "-i"
                    if (j == i - 1) {
                        img *= -1;
                        i = j;
                        break;
                    }
                    img = Integer.valueOf(value.substring(j, i));
                    i = j;
                    break;
                } else if (value.charAt(j) == '+') {
                    img = Integer.valueOf(value.substring(j, i));
                    i = j;
                    break;
                }

                // case "xi" (real part equals zero)
                if (j == 0) {
                    img = Integer.valueOf(value.substring(j, i));
                    return;
                }
            }

        }

        // for real part
        if (i == 0) {
            // cases "-xi" | "+xi", x != 0
            if ((value.charAt(i) == '-' || value.charAt(i) == '+') & (img != 0)) {
                return;
            }

            real = Integer.valueOf(value);
            return;
        }

        real = Integer.valueOf(value.substring(0, i));
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

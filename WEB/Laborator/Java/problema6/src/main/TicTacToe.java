package main;

public class TicTacToe {

    private String[][] table = new String[3][3];

    public TicTacToe() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                table[i][j] = "";
            }
        }
    }

    public boolean isFree(int i, int j){
        return table[i][j].equals("");
    }

    public String getValue(int i, int j){
        return table[i][j];
    }

    public void setValue(int i, int j, String value){
        table[i][j] = value;
    }

    public String getValues() {
        String values = "";
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                values += table[i][j] + ",";
            }
        }
        values = values.substring(0, values.length()-1);
        return values;
    }

    public String linie() {
        for(int i = 0; i < 3; i++){
            if(!table[i][0].equals("") && table[i][0].equals(table[i][1]) && table[i][0].equals(table[i][2])){
                return table[i][0];
            }
        }
        return "";
    }

    public String coloana() {
        for(int i = 0; i < 3; i++){
            if(!table[0][i].equals("") && table[0][i].equals(table[1][i]) && table[0][i].equals(table[2][i])){
                return table[0][i];
            }
        }
        return "";
    }

    public String diagonalaPrincipala() {
        if(!table[0][0].equals("") && table[0][0].equals(table[1][1]) && table[0][0].equals(table[2][2])){
            return table[0][0];
        }
        return "";
    }

    public String diagonalaSecundara() {
        if(!table[0][2].equals("") && table[0][2].equals(table[1][1]) && table[0][2].equals(table[2][0])){
            return table[0][2];
        }
        return "";
    }

    public String verifyWinner() {
        String verify = linie();
        if(!verify.equals("")){
            return verify;
        }
        verify = coloana();
        if(!verify.equals("")){
            return verify;
        }
        verify = diagonalaPrincipala();
        if(!verify.equals("")){
            return verify;
        }
        verify = diagonalaSecundara();
        if(!verify.equals("")){
            return verify;
        }
        return "";
    }

    public boolean verifyDraw() {
        for(int i = 0; i < 3; i++){
            for(int j = 0; j < 3; j++){
                if(table[i][j].equals("")){
                    return false;
                }
            }
        }
        return true;
    }
}

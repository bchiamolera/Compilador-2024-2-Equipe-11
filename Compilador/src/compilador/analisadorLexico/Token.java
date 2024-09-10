package compilador.analisadorLexico;

public class Token {

    private int id;
    private String lexeme;
    private int position;

    public Token(int id, String lexeme, int position) {
        this.id = id;
        this.lexeme = lexeme;
        this.position = position;
    }

    public final int getId() {
        return id;
    }

    public final String getLexeme() {
        return lexeme;
    }

    public final int getPosition() {
        return position;
    }

    public final String getClasse() {
        switch (this.id) {
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
            case 12:
            case 13:
            case 14:
            case 15:
                return "palavra reservada";
            case 16:
                return "identificador\t";
            case 17:
                return "constante_int\t";
            case 18:
                return "constante_float\t";
            case 19:
                return "constante_string";
            case 20:
            case 21:
            case 22:
            case 23:
            case 24:
            case 25:
            case 26:
            case 27:
            case 28:
            case 29:
            case 30:
            case 31:
            case 32:
            case 33:
            case 34:
            case 35:
                return "simbolo especial";
            default:
                return "token desconhecido";
        }
    }

    @Override
    public String toString() {
        return position + "\t" + getClasse() + "\t" + lexeme + "\n";
    }
;
}

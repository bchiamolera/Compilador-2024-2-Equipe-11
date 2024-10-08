package compilador.analisador;

public class LexicalError extends AnalysisError
{
    int id;
    String lexema;
    
    public LexicalError(int id, String msg, String lexema, int position) {
        super(msg, position);
        this.lexema = lexema;
        this.id = id;
    }
    
    public LexicalError (String msg, String lexema, int position)
    {
        super(msg, position);
        this.lexema = lexema;
    }
    
    public LexicalError (int id, String msg, int position) {
        super(msg, position);
        this.id = id;
    }
    
    public LexicalError (String msg, int position)
    {
        super(msg, position);
    }

    public LexicalError (String msg)
    {
        super(msg);
    }
    
    public String getLexeme() {
        return this.lexema;
    }
    
    public int GetId() {
        return this.id;
    }
}

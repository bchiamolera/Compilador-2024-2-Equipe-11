package compilador.analisadorLexico;

public class LexicalError extends AnalysisError
{
    String lexema;
    
    public LexicalError (String msg, String lexema, int position)
    {
        super(msg, position);
        this.lexema = lexema;
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
}

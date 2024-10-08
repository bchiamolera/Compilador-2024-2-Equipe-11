package compilador.analisador;

public class SyntaticError extends AnalysisError
{
    boolean isEof = false;
    
    public SyntaticError(String msg, int position, boolean isEof) {
        super(msg, position);
        this.isEof = isEof;
    }
    
    public SyntaticError(String msg, int position)
	 {
        super(msg, position);
    }

    public SyntaticError(String msg)
    {
        super(msg);
    }
    
    public boolean IsEOF() {
        return this.isEof;
    }
}

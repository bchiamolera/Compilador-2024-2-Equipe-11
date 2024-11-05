package compilador.analisador;

public class Semantico implements Constants
{
    public void executeAction(int action, Token token)	throws SemanticError
    {
        System.out.println("Acao #"+action+", Token: "+token);
    }	
}

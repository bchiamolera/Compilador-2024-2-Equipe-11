package compilador.analisador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
operando1       |   operando2   |   operador    |   tipo resultante
--------------------------------------------------------------------------------
                |               |               |    conforme prefixo do
                |               |               |    identificador, sendo: int64
identificador   |       -       |               |    para i_, float64 para f_,
                |               |               |    string para s_, bool para
                |               |               |    b_
--------------------------------------------------------------------------------
constante_int - int64
constante_float - float64
constante_string - string
true - bool
false - bool
int64 - operadores unários: + - int64
float64 - operadores unários: + - float64
int64 int64 operadores binários: + - * int64
int64
float64
float64
float64
int64
float64
operadores binários: + - * float64
int64 ou float64 int64 ou float64 operador binário: / float64
int64 int64 == != < > bool
float64 float64 == != < > bool
string string == != < > bool
bool - operador unário: ! (not) bool
bool bool operadores binários: &&(and)
||(or) bool
 */
public class Semantico implements Constants {

    private String operador_relacional = "";
    private String codigo_fonte = "";
    private Stack<String> pilha_tipos = new Stack();
    private Stack pilha_rotulos = new Stack();
    private List<String> lista_identificadores = new ArrayList<String>();
    private HashMap<String, String> tabela_simbolos = new HashMap();

    public void executeAction(int action, Token token) throws SemanticError {
        System.out.println("Acao #" + action + ", Token: " + token);

        switch (action) {
            case 100:
                acao_semantica100();
                break;
            case 101:
                acao_semantica101();
            case 102:
                break;
            case 103:
                break;
            case 104:
                break;
            case 105:
                break;
            case 106:
                break;
            case 107:
                break;
            case 108:
                acao_semantica108();
                break;
            case 109:
                break;
            case 110:
                break;
            case 111:
                break;
            case 112:
                break;
            case 113:
                break;
            case 114:
                break;
            case 115:
                break;
            case 116:
                break;
            case 117:
                break;
            case 118:
                break;
            case 119:
                break;
            case 120:
                break;
            case 121:
                break;
            case 122:
                break;
            case 123:
                acao_semantica123();
                break;
            case 124:
                break;
            case 125:
                break;
            case 126:
                break;
            case 127:
                break;
            case 128:
                acao_semantica128(token);
                break;
            case 129:
                acao_semantica129(token);
                break;
            case 130:
                break;
            case 131:
                break;
            default:
                System.out.println("Ação não implementada/não existe");
        }
    }
    
    public String getCode() {
        return this.codigo_fonte;
    }

    /*
    A ação #100 deve gerar código com o cabeçalho do programa objeto.
     */
    private void acao_semantica100() {
        this.codigo_fonte = ".assembly extern mscorlib {}\n"
                + ".assembly _codigo_objeto{}\n"
                + ".module _codigo_objeto.exe\n"
                + ".class public UNICA{\n"
                + ".method static public void _principal() {\n"
                + ".entrypoint\n";
    }

    /*
    A ação #101 deve gerar código com as instruções para finalizar o programa objeto.
     */
    private void acao_semantica101() {
        this.codigo_fonte += "ret\n"
                + "}\n"
                + "}";
    }

    /*
    ação #108:
    - desempilhar um tipo da pilha_tipos;
    - valores do tipo int64 da linguagem fonte são tratados como float64 em IL, portanto devem ser primeiramente
    convertidos para int64 (código: conv.i8).
    - gerar código objeto para escrever o valor conforme o tipo desempilhado (código: call void
    [mscorlib]System.Console::Write(<tipo>), onde <tipo> pode se int64, float64, string ou bool).
     */
    private void acao_semantica108() {
        try {
            String tipo = this.pilha_tipos.pop();
            if (tipo.equals("int64")) {
                codigo_fonte += "conv.i8\n";
            }
            codigo_fonte += "call void [mscorlib]System.Console::Write(" + tipo + ")\n";
        } catch (Exception e) {
            
        }
    }

    /*
    para os operadores aritméticos binários (ações #123, #124, #125, #126):
    - desempilhar dois tipos da pilha_tipos, empilhar o tipo resultante da operação conforme indicado na TABELA DE
    TIPOS;
    - gerar código objeto para efetuar a operação correspondente em IL (código: add, sub, mul ou div,
    respectivamente).
     */
    private void acao_semantica123() {
        try {
            String tipo1 = this.pilha_tipos.pop();
            String tipo2 = this.pilha_tipos.pop();

            this.codigo_fonte += "add\n";

            if (tipo1.equals("int64") && tipo2.equals("int64")) {
                this.pilha_tipos.push("int64");
            } else {
                this.pilha_tipos.push("float64");
            }
        } catch (Exception e) {

        }
    }

    /*
    para constante_int (ação #128):
    - empilhar na pilha_tipos o tipo correspondente conforme indicado na TABELA DE TIPOS;
    - gerar código objeto para carregar o valor da constante (código: ldc.i8 token.getLexeme), observando que a
    constante_int da linguagem fonte deve ser tratada como float64 em IL, portanto deve ser convertida para
    float64 (código: conv.r8).
     */
    private void acao_semantica128(Token token) {
        this.pilha_tipos.push("int64");
        this.codigo_fonte += "ldc.i8 " + token.getLexeme() + "\n"
                + "conv.r8\n";
    }

    /*
    para constante_float (ação #129):
    - empilhar na pilha_tipos o tipo correspondente conforme indicado na TABELA DE TIPOS;
    - gerar código objeto para carregar o valor da constante (código: ldc.r8 token.getLexeme), lembrando que
    constante_float da linguagem fonte é escrita com , (vírgula) e em IL é escrita com . (ponto).
     */
    private void acao_semantica129(Token token) {
        this.pilha_tipos.push("float64");
        this.codigo_fonte += "ldc.r8 " + token.getLexeme().replace(',', '.') + "\n";
    }
}

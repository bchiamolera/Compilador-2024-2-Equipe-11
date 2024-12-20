package compilador.analisador;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

/*
operando1           |       operando2       |              operador             |   tipo resultante
----------------------------------------------------------------------------------------------------------------
                    |                       |                                   |   conforme prefixo do
                    |                       |                                   |   identificador, sendo: int64
identificador       |           -           |                                   |   para i_, float64 para f_,
                    |                       |                                   |   string para s_, bool para
                    |                       |                                   |   b_
----------------------------------------------------------------------------------------------------------------
constante_int       |           -           |                                   |   int64
----------------------------------------------------------------------------------------------------------------
constante_float     |           -           |                                   |   float64
----------------------------------------------------------------------------------------------------------------
constante_string    |           -           |                                   |   string
----------------------------------------------------------------------------------------------------------------
true                |           -           |                                   |   bool
----------------------------------------------------------------------------------------------------------------
false               |           -           |                                   |   bool
----------------------------------------------------------------------------------------------------------------
int64               |           -           |     operadores unários: + -       |   int64
----------------------------------------------------------------------------------------------------------------
float64             |           -           |     operadores unários: + -       |   float64
----------------------------------------------------------------------------------------------------------------
int64               |         int64         |    operadores binários: + - *     |   int64
----------------------------------------------------------------------------------------------------------------
int64               |        float64        |                                   |
float64             |         int64         |    operadores binários: + - *     |   float64
float64             |        float64        |                                   |
----------------------------------------------------------------------------------------------------------------
int64 ou float64    |   int64 ou float64    |       operador binário: /         |   float64
----------------------------------------------------------------------------------------------------------------
int64               |         int64         |             == != < >             |   bool
----------------------------------------------------------------------------------------------------------------
float64             |        float64        |             == != < >             |   bool
----------------------------------------------------------------------------------------------------------------
string              |        string         |             == != < >             |   bool
----------------------------------------------------------------------------------------------------------------
bool                |           -           |           operador unário: !      |   (not) bool
----------------------------------------------------------------------------------------------------------------
bool                |         bool          |   operadores binários: &&(and)    |
                    |                       |   ||(or) bool                     |
----------------------------------------------------------------------------------------------------------------
 */
public class Semantico implements Constants {

    private String operador_relacional = "";
    private String codigo_fonte = "";
    private int countL = 0;
    private final Stack<String> pilha_tipos = new Stack();
    private final Stack<String> pilha_rotulos = new Stack();
    private final List<Token> lista_identificadores = new ArrayList<>();
    private final HashMap<String, String> tabela_simbolos = new HashMap();
    
    public void executeAction(int action, Token token) throws SemanticError {
        //System.out.println("Acao #" + action + ", Token: " + token);
        switch (action) {
            case 100:
                acao_semantica100();
                break;
            case 101:
                acao_semantica101();
                break;
            case 102:
                acao_semantica102();
                break;
            case 103:
                acao_semantica103();
                break;
            case 104:
                acao_semantica104(token);
                break;
            case 105:
                acao_semantica105(token);
                break;
            case 106:
                acao_semantica106(token);
                break;
            case 107:
                acao_semantica107();
                break;
            case 108:
                acao_semantica108();
                break;
            case 109:
                acao_semantica109();
                break;
            case 110:
                acao_semantica110();
                break;
            case 111:
                acao_semantica111();
                break;
            case 112:
                acao_semantica112();
                break;
            case 113:
                acao_semantica113();
                break;
            case 114:
                acao_semantica114();
                break;
            case 115:
                acao_semantica115();
                break;
            case 116:
                acao_semantica116();
                break;
            case 117:
                acao_semantica117();
                break;
            case 118:
                acao_semantica118();
                break;
            case 119:
                acao_semantica119();
                break;
            case 120:
                acao_semantica120();
                break;
            case 121:
                acao_semantica121(token);
                break;
            case 122:
                acao_semantica122();
                break;
            case 123:
                acao_semantica123();
                break;
            case 124:
                acao_semantica124();
                break;
            case 125:
                acao_semantica125();
                break;
            case 126:
                acao_semantica126();
                break;
            case 127:
                acao_semantica127(token);
                break;
            case 128:
                acao_semantica128(token);
                break;
            case 129:
                acao_semantica129(token);
                break;
            case 130:
                acao_semantica130(token);
                break;
            case 131:
                acao_semantica131();
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
                + ".class public _UNICA{\n"
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
    ação #102: para cada identificador da lista_identificadores:
    verificar se o identificador foi declarado, ou seja, se está na tabela_simbolos;
    em caso positivo, encerrar a execução e apontar erro semântico, indicando a linha e apresentando a mensagem
    token.getLexeme já declarado (por exemplo: i_area já declarado);
    em caso negativo:
    (a) inserir o identificador na tabela_simbolos;
    (b) gerar código objeto para declarar o identificador (código: .locals (tipo identificador)), onde o tipo
     do identificador é determinado pelo prefixo, sendo: int64 para i_, float64 para f_, string para
     s_, bool para b_
    limpar a lista_identificadore, após o processamento. 
     */
    private void acao_semantica102() throws SemanticError {
        for (Token token : this.lista_identificadores) {
            String identificador = token.getLexeme();
            if (this.tabela_simbolos.containsKey(identificador)) {
                throw new SemanticError(identificador + " ja declarado", token.getPosition());
            }
            this.codigo_fonte += ".locals(";
            String tipo = "";
            switch (identificador.charAt(0)) {
                case 'i':
                    tipo = "int64";
                    break;
                case 'f':
                    tipo = "float64";
                    break;
                case 's':
                    tipo = "string";
                    break;
                case 'b':
                    tipo = "bool";
                    break;
            }
            this.codigo_fonte += tipo + " " + identificador + ")\n";
            this.tabela_simbolos.put(identificador, tipo);
        }

        this.lista_identificadores.clear();
    }

    /*
    ação #103:
         desempilhar o tipo da <expressão> da pilha_tipos;
         se <expressão> for do tipo int64, primeiramente deve ser convertida para int64 (código: conv.i8);
         gerar o código objeto dup n vezes, onde n é igual a quantidade de identificadores da lista_identificadores
        menos 1;
         para cada identificador da lista_identificadores:
        (a) verificar se o identificador foi declarado, ou seja, se está na tabela_simbolos;
        (b) em caso negativo, encerrar a execução e apontar erro semântico, indicando a linha e apresentando a
        mensagem identificador não declarado (por exemplo: i_area não declarado);
        (c) em caso positivo, gerar código objeto para armazenar o valor da <expressão> em identificador (código:
        stloc identificador), lembrando que se <expressão> for do tipo int64, primeiramente deve ser convertida
        para int64 (código: conv.i8). Assumir que o valor da <expressão> é de tipo compatível com o
        identificador;
         limpar a lista_identificadores, após o processamento. 
     */
    private void acao_semantica103() throws SemanticError {
        String tipo = this.pilha_tipos.pop();
        if (tipo.equals("int64")) {
            this.codigo_fonte += "conv.i8\n";
        }

        for (int i = 0; i < this.lista_identificadores.size() - 1; i++) {
            this.codigo_fonte += "dup\n";
        }

        for (Token token : this.lista_identificadores) {
            String identificador = token.getLexeme();
            if (!this.tabela_simbolos.containsKey(identificador)) {
                throw new SemanticError(identificador + " nao declarado", token.getPosition());
            }
            this.codigo_fonte += "stloc " + identificador + "\n";
        }

        this.lista_identificadores.clear();
    }

    /*
    ação #104:
         guardar identificador (token.getLexeme) na lista_identificadores para uso posterior. 
     */
    private void acao_semantica104(Token token) {
        this.lista_identificadores.add(token);
    }

    /*
    a ação #105: para identificador:
         verificar se o identificador (token.getLexeme) foi declarado, ou seja, se está na tabela_simbolos;
         em caso negativo, encerrar a execução e apontar erro semântico, indicando a linha e apresentando a mensagem
        token.getLexeme não declarado (por exemplo: i_area não declarado);
         em caso positivo:
        (a) gerar código objeto para ler (da entrada padrão) um valor do tipo de identificador, onde o tipo do
        identificador é determinado pelo prefixo, sendo: int64 para i_, float64 para f_, string para s_,
        bool para b_ (verificar no anexo: instruções MSIL ou na lista no. 6 ou lista no. 7);
        (b) gerar código objeto para armazenar o valor lido em identificador (código: stloc token.getLexeme).
     */
    private void acao_semantica105(Token token) throws SemanticError {
        String identificador = token.getLexeme();

        // Verifica se o identificador está na tabela de símbolos
        if (!tabela_simbolos.containsKey(identificador)) {
            throw new SemanticError(identificador + " nao declarado", token.getPosition());
        }

        String tipo = tabela_simbolos.get(identificador);
        codigo_fonte += "call string [mscorlib]System.Console::ReadLine()\n";
        switch (tipo) {
            case "int64":
                codigo_fonte += "call int64 [mscorlib]System.Int64::Parse(string)\n";
                break;
            case "float64":
                codigo_fonte += "call float64 [mscorlib]System.Double::Parse(string)\n";
                break;
            case "bool":
                codigo_fonte += "call bool [mscorlib]System.Boolean::Parse(string)\n";
                break;
            default:
        }

        codigo_fonte += "stloc " + identificador + "\n";
    }

    /*
    a ação #106:
         gerar código objeto para carregar o valor da constante_string (verificar no anexo: instruções MSIL ou na
        lista no. 6 ou lista no. 7);
         gerar código objeto para escrever a constante (código: call void [mscorlib]System.Console::Write
        (string)); 
     */
    private void acao_semantica106(Token token) {
        String constanteString = token.getLexeme();

        codigo_fonte += "ldstr " + constanteString + "\n";

        codigo_fonte += "call void [mscorlib]System.Console::Write(string)\n";
    }

    /*
    ação #107:
         gerar código objeto para escrever quebra de linha na saída padrão. 
     */
    private void acao_semantica107() {
        this.codigo_fonte += "call void [mscorlib]System.Console::WriteLine()\n";
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
        String tipo = this.pilha_tipos.pop();
        if (tipo.equals("int64")) {
            this.codigo_fonte += "conv.i8\n";
        }
        this.codigo_fonte += "call void [mscorlib]System.Console::Write(" + tipo + ")\n";
    }

    /*
    ação #109:
         criar um rótulo (novo_rotulo1) para rotular a primeira instrução após o end;
         empilhar o rótulo (novo_rotulo1) na pilha_rotulos para resolução posterior;
         criar um rótulo (novo_rotulo2);
         gerar código objeto para desviar os comandos da cláusula if caso o resultado da avaliação da <expressão> for
        false (código: brfalse novo_rotulo2);
         empilhar o rótulo (novo_rotulo2) na pilha_rotulos para resolução posterior
     */
    private void acao_semantica109() {
        countL++;
        String novoRotulo1 = "L" + countL;
        pilha_rotulos.push(novoRotulo1);
        countL++;
        String novoRotulo2 = "L" + countL;

        this.codigo_fonte += "brfalse " + novoRotulo2 + "\n";

        pilha_rotulos.push(novoRotulo2);
    }

    /*
    ação #110:
         desempilhar um rótulo da pilha_rotulos (rotulo_desempilhado2);
         desempilhar um rótulo da pilha_rotulos (rotulo_desempilhado1);
         gerar código objeto para desviar para a primeira instrução após o end (código: br rotulo_desempilhado1);
         empilhar o rótulo (rotulo_desempilhado1) na pilha_rotulos para resolução posterior;
         rotular a próxima instrução do código objeto com o rótulo desempilhado (código: rotulo_desempilhado2:). 
     */
    private void acao_semantica110() {
        String rotuloDesempilhado2 = pilha_rotulos.pop();
        String rotuloDesempilhado1 = pilha_rotulos.pop();

        codigo_fonte += "br " + rotuloDesempilhado1 + "\n";
        pilha_rotulos.push(rotuloDesempilhado1);

        codigo_fonte += rotuloDesempilhado2 + ":\n";
    }

    /*
    ação #111:
         desempilhar um rótulo da pilha_rotulos (rotulo_desempilhado);
         rotular a próxima instrução do código objeto com o rótulo desempilhado (código: rotulo_desempilhado:).
     */
    private void acao_semantica111() {
        String rotuloDesempilhado = pilha_rotulos.pop();
        codigo_fonte += rotuloDesempilhado + ":\n";
    }

    /*
   ação #112:
         criar um rótulo (novo_rotulo);
         gerar código objeto para desviar os comandos da cláusula elif caso o resultado da avaliação da <expressão>
        for false (código: brfalse novo_rotulo);
         empilhar o rótulo (novo_rotulo) na pilha_rotulos para resolução posterior.
     */
    private void acao_semantica112() {
        countL++;
        String novoRotulo = "L" + countL;
        codigo_fonte += "brfalse " + novoRotulo + "\n";
        pilha_rotulos.push(novoRotulo);
    }

    /* 
    ação #113 (antes do repeat) deve:
         criar um rótulo (novo_rotulo);
         rotular a próxima instrução do código objeto com o rótulo criado (código: novo_rotulo:).
         empilhar o rótulo (novo_rotulo) na pilha_rotulos para resolução posterior. 
     */
    private void acao_semantica113() {
        countL++;
        String novoRotulo = "L" + countL;
        codigo_fonte += novoRotulo + ":\n";
        pilha_rotulos.push(novoRotulo);
    }

    /*
    ação #114 (após <expressão>) deve:
         desempilhar um rótulo da pilha_rotulos (rotulo_desempilhado);
         gerar código objeto para desviar para o primeiro comando do comando <repetição> caso o resultado da
        avaliação da <expressão> for true (código: brtrue rotulo_desempilhado). 

     */
    private void acao_semantica114() {
        String rotuloDesempilhado = pilha_rotulos.pop();
        codigo_fonte += "brtrue " + rotuloDesempilhado + "\n";
    }

    /*
    ação #115 (após <expressão>) deve:
         desempilhar um rótulo da pilha_rotulos (rotulo_desempilhado);
         gerar código objeto para desviar para o primeiro comando do comando <repetição> caso o resultado da
        avaliação da <expressão> for false (código: brfalse rotulo_desempilhado). 
     */
    private void acao_semantica115() {
        String rotuloDesempilhado = pilha_rotulos.pop();
        codigo_fonte += "brfalse " + rotuloDesempilhado + "\n";
    }

    /*
    para os operadores lógicos binários (ações #116, #117):
         desempilhar dois tipos da pilha_tipos, empilhar o tipo resultante da operação conforme indicado na TABELA DE
        TIPOS;
         gerar código objeto para efetuar a operação correspondente em IL (verificar no anexo: instruções MSIL ou na
        lista no. 6 ou lista no. 7). 
     */
    private void acao_semantica116() {
        this.pilha_tipos.pop();
        this.pilha_tipos.pop();
        this.pilha_tipos.push("bool");
        this.codigo_fonte += "and\n";
    }

    private void acao_semantica117() {
        this.pilha_tipos.pop();
        this.pilha_tipos.pop();
        this.pilha_tipos.push("bool");
        this.codigo_fonte += "or\n";
    }

    /*
    para true (ação #118):
         empilhar na pilha_tipos o tipo correspondente conforme indicado na TABELA DE TIPOS;
         gerar código objeto para carregar o valor da constante (código: ldc.i4.1). 
     */
    private void acao_semantica118() {
        this.pilha_tipos.push("bool");
        this.codigo_fonte += "ldc.i4.1\n";
    }

    /*
    para false (ação #119):
         empilhar na pilha_tipos o tipo correspondente conforme indicado na TABELA DE TIPOS;
         gerar código objeto para carregar o valor da constante (código: ldc.i4.0). 

     */
    private void acao_semantica119() {
        this.pilha_tipos.push("bool");
        this.codigo_fonte += "ldc.i4.0\n";
    }

    /*
    para o operador lógico unário "!" (ação #120):
         gerar código objeto para efetuar a operação correspondente em IL (verificar no anexo: instruções MSIL ou na
        lista no. 6 ou lista no. 7). 
     */
    private void acao_semantica120() {
        this.codigo_fonte += "ldc.i4.1\n"
                + "xor";
    }

    /*
    ação #121:
         guardar em operador_relacional (token.getLexeme) o operador relacional reconhecido pela ação;
     */
    private void acao_semantica121(Token token) {
        this.operador_relacional = token.getLexeme();
    }

    /*
    ação #122:
         desempilhar dois tipos da pilha_tipos, empilhar o tipo resultante da operação conforme indicado na TABELA DE
        TIPOS;
         gerar código objeto para efetuar a operação correspondente em IL conforme o operador relacional armazenado
        em operador_relacional (verificar no anexo: instruções MSIL ou na lista no. 6 ou lista no. 7, verificar em AVA
        > Links e ferramentas > TOMAZELLI, Giancarlo. ...). 
     */
    private void acao_semantica122() {
        this.pilha_tipos.pop();
        this.pilha_tipos.pop();
        this.pilha_tipos.push("bool");

        switch (operador_relacional) {
            case "==":
                this.codigo_fonte += "ceq\n";
                break;
            case "!=":
                this.codigo_fonte += "ceq\n"
                        + "ldc.i4.1\n"
                        + "xor";
                break;
            case ">":
                this.codigo_fonte += "cgt\n";
                break;
            case "<":
                this.codigo_fonte += "clt\n";
                break;
        }
    }

    /*
    para os operadores aritméticos binários (ações #123, #124, #125, #126):
         desempilhar dois tipos da pilha_tipos, empilhar o tipo resultante da operação conforme indicado na TABELA DE
        TIPOS; 
     */
    private void acao_semantica123() {
        String tipo1 = this.pilha_tipos.pop();
        String tipo2 = this.pilha_tipos.pop();

        this.codigo_fonte += "add\n";

        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            this.pilha_tipos.push("int64");
        } else {
            this.pilha_tipos.push("float64");
        }
    }

    private void acao_semantica124() {
        String tipo1 = this.pilha_tipos.pop();
        String tipo2 = this.pilha_tipos.pop();

        this.codigo_fonte += "sub\n";

        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            this.pilha_tipos.push("int64");
        } else {
            this.pilha_tipos.push("float64");
        }
    }

    private void acao_semantica125() {
        String tipo1 = this.pilha_tipos.pop();
        String tipo2 = this.pilha_tipos.pop();

        this.codigo_fonte += "mul\n";

        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            this.pilha_tipos.push("int64");
        } else {
            this.pilha_tipos.push("float64");
        }
    }

    private void acao_semantica126() {
        String tipo1 = this.pilha_tipos.pop();
        String tipo2 = this.pilha_tipos.pop();

        this.codigo_fonte += "div\n";

        if (tipo1.equals("int64") && tipo2.equals("int64")) {
            this.pilha_tipos.push("int64");
        } else {
            this.pilha_tipos.push("float64");
        }
    }

    /*
    A semântica de identificador (ação #127) em <expressão> é a seguinte:
         verificar se o identificador (token.getLexeme) foi declarado, ou seja, se está na tabela_simbolos;
         em caso negativo, encerrar a execução e apontar erro semântico, indicando a linha e apresentando a mensagem
        token.getLexeme não declarado (por exemplo: i_area não declarado);
         em caso positivo:
        (a) empilhar na pilha_tipos o tipo correspondente conforme indicado na TABELA DE TIPOS;
        (b) gerar código objeto para carregar o valor armazenado em identificador (código: ldloc token.getLexeme);
        (c) se identificador for do tipo int64, gerar código objeto para converter o valor para float64 (código:
        conv.r8). 
     */
    private void acao_semantica127(Token token) throws SemanticError {
        // Pegar linha
        if (!this.tabela_simbolos.containsKey(token.getLexeme())) {
            throw new SemanticError(token.getLexeme() + "nao declarado", token.getPosition());
        }

        String tipo = this.tabela_simbolos.get(token.getLexeme());
        this.pilha_tipos.push(tipo);

        this.codigo_fonte += "ldloc " + token.getLexeme() + "\n";

        if (tipo.equalsIgnoreCase("int64")) {
            this.codigo_fonte += "conv.r8\n";
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

    /*
    para constante_string (ação #130):
         empilhar na pilha_tipos o tipo correspondente conforme indicado na TABELA DE TIPOS;
         gerar código objeto para carregar o valor da constante em IL (verificar no anexo: instruções MSIL ou na lista
        no. 6 ou lista no. 7). 
     */
    private void acao_semantica130(Token token) {
        this.pilha_tipos.push("string");
        this.codigo_fonte += "ldstr " + token.getLexeme() + "\n";
    }

    /*
    para o operador aritmético unário "-" (ação #131):
         gerar código objeto para efetuar a operação correspondente em IL (verificar no anexo: instruções MSIL ou na
        lista no. 6 ou lista no. 7). 
     */
    private void acao_semantica131() {
        String tipo = this.pilha_tipos.pop();

        this.codigo_fonte += "ldc.i8 -1\n"
                + "conv.r8\n"
                + "mul\n";
        pilha_tipos.push(tipo);

    }
}

package compilador.GUI;

import compilador.analisador.LexicalError;
import compilador.analisador.Lexico;
import compilador.analisador.SemanticError;
import compilador.analisador.Semantico;
import compilador.analisador.Sintatico;
import compilador.analisador.SyntaticError;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class GUI {

    public JFrame window;

    public JToolBar toolBar;

    public JSplitPane splitPane;

    public JTextArea editor;
    public JScrollPane editorScrollPane;

    public JTextArea messageArea;
    public JScrollPane messageScrollPane;

    public JLabel statusBar;

    public JButton newBtn;
    public JButton openBtn;
    public JButton saveBtn;
    public JButton copyBtn;
    public JButton pasteBtn;
    public JButton cutBtn;
    public JButton compileBtn;
    public JButton teamBtn;

    private String currentPath;

    private final ButtonsActions btnsActions;

    public GUI() {
        btnsActions = new ButtonsActions(this);
        currentPath = "";

        createWindow();
        createToolBar();
        createSplitPane();
        createStatusBar();

        window.setVisible(true);
    }

    private void createWindow() {
        window = new JFrame("Compilador 2024/2");
        window.setSize(910, 600);
        window.setMinimumSize(new Dimension(910, 600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(900, 70));

        CreateNewBtn();
        CreateOpenBtn();
        CreateSaveBtn();
        CreateCopyBtn();
        CreatePasteBtn();
        CreateCutBtn();
        CreateCompileBtn();
        CreateTeamBtn();

        CreateShortcuts();

        window.add(toolBar, BorderLayout.NORTH);
    }

    private void createEditor() {
        editor = new JTextArea();
        editor.setBorder(new NumberedBorder());

        editorScrollPane = new JScrollPane(editor, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    private void createMessageArea() {
        messageArea = new JTextArea();
        messageArea.setEditable(false);
        messageScrollPane = new JScrollPane(messageArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
    }

    private void createSplitPane() {
        createEditor();
        createMessageArea();

        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, editorScrollPane, messageScrollPane);
        splitPane.setResizeWeight(0.7);

        window.add(splitPane, BorderLayout.CENTER);
    }

    private void createStatusBar() {
        statusBar = new JLabel();
        statusBar.setText("Nenhum arquivo aberto");
        statusBar.setPreferredSize(new Dimension(900, 25));

        window.add(statusBar, BorderLayout.SOUTH);
    }

    private void CreateNewBtn() {
        String btnLabel = "Novo";
        String btnShortcut = "Ctrl+N";
        String iconPath = "/icons/novo.png";

        newBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        newBtn.setIcon(scaledIcon);

        newBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        newBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        newBtn.setPreferredSize(new Dimension(80, 80));

        newBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnsActions.NewFile();
            }
        });

        toolBar.add(newBtn);
    }

    private void CreateOpenBtn() {
        String btnLabel = "Abrir";
        String btnShortcut = "Ctrl+O";
        String iconPath = "/icons/abrir.png";

        openBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        openBtn.setIcon(scaledIcon);

        openBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        openBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        openBtn.setPreferredSize(new Dimension(80, 80));

        openBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetCurrentPath(btnsActions.OpenFile());
            }
        });

        toolBar.add(openBtn);
    }

    private void CreateSaveBtn() {
        String btnLabel = "Salvar";
        String btnShortcut = "Ctrl+S";
        String iconPath = "/icons/salvar.png";

        saveBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        saveBtn.setIcon(scaledIcon);

        saveBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        saveBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        saveBtn.setPreferredSize(new Dimension(80, 80));

        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetCurrentPath(btnsActions.SaveFile());
            }
        });

        toolBar.add(saveBtn);
    }

    private void CreateCopyBtn() {
        String btnLabel = "Copiar";
        String btnShortcut = "Ctrl+C";
        String iconPath = "/icons/copiar.png";

        copyBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        copyBtn.setIcon(scaledIcon);

        copyBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        copyBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        copyBtn.setPreferredSize(new Dimension(80, 80));

        copyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.copy();
            }
        });

        toolBar.add(copyBtn);
    }

    private void CreatePasteBtn() {
        String btnLabel = "Colar";
        String btnShortcut = "Ctrl+V";
        String iconPath = "/icons/colar.png";

        pasteBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        pasteBtn.setIcon(scaledIcon);

        pasteBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        pasteBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        pasteBtn.setPreferredSize(new Dimension(80, 80));

        pasteBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.paste();
            }
        });

        toolBar.add(pasteBtn);
    }

    private void CreateCutBtn() {
        String btnLabel = "Recortar";
        String btnShortcut = "Ctrl+X";
        String iconPath = "/icons/recortar.png";

        cutBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        cutBtn.setIcon(scaledIcon);

        cutBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        cutBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        cutBtn.setPreferredSize(new Dimension(80, 80));

        cutBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editor.cut();
            }
        });

        toolBar.add(cutBtn);
    }

    private void CreateCompileBtn() {
        String btnLabel = "Compilar";
        String btnShortcut = "F7";
        String iconPath = "/icons/compilar.png";

        compileBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        compileBtn.setIcon(scaledIcon);

        compileBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        compileBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        compileBtn.setPreferredSize(new Dimension(80, 80));

        compileBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentPath.isEmpty()) {
                    SetCurrentPath(btnsActions.SaveFile());
                    if (currentPath == null || currentPath.isBlank()) {
                        return;
                    }
                }

                messageArea.setText("");
                String[] textoDividido = editor.getText().split("\n");

                Lexico lexico = new Lexico();
                Sintatico sintatico = new Sintatico();
                Semantico semantico = new Semantico();
                lexico.setInput(editor.getText());

                try {
                    sintatico.parse(lexico, semantico);
                    messageArea.setText("Programa compilado com sucesso!");

                    try {
                        // Define o nome do arquivo
                        String path = currentPath.replace(".txt", ".il");
                        File file = new File(path);
                        // Cria o arquivo
                        if (!file.exists()) {
                            file.createNewFile();
                        }

                        FileWriter writer = new FileWriter(file);
                        writer.write(semantico.getCode());
                        writer.close();

                        System.out.println("Conteúdo escrito no arquivo.");
                    } catch (IOException exc) {
                        System.out.println("Ocorreu um erro ao criar o arquivo.");
                    }
                } catch (LexicalError erroLexico) {
                    int posErro = erroLexico.getPosition();
                    int linha = FindLine(posErro, textoDividido);

                    StringBuilder lexemaInvalido = new StringBuilder();
                    while (posErro < editor.getText().length()) {
                        char currentChar = editor.getText().charAt(posErro);
                        if (Character.isWhitespace(currentChar)) {
                            break;
                        }
                        lexemaInvalido.append(currentChar);
                        posErro++;
                    }

                    // Verifica se é erro de cte_string
                    if (erroLexico.GetId() == 19) {
                        messageArea.setText("Erro na linha " + linha + " - " + erroLexico.getMessage());
                    } else {
                        messageArea.setText("Erro na linha " + linha + " - Símbolo '" + lexemaInvalido.toString() + "' " + erroLexico.getMessage());
                    }

                } catch (SyntaticError erroSintatico) {
                    int posErro = erroSintatico.getPosition();
                    int linha = FindLine(posErro, textoDividido);

                    StringBuilder lexemaInvalido = new StringBuilder();
                    boolean maybeIsCteString = false;
                    boolean isCteString = false;

                    while (posErro < editor.getText().length()) {
                        char currentChar = editor.getText().charAt(posErro);
                        if (currentChar == '"') {
                            if (maybeIsCteString) {
                                isCteString = true;
                                break;
                            } else {
                                maybeIsCteString = true;
                            }
                        }
                        if (Character.isWhitespace(currentChar)) {
                            break;
                        }
                        lexemaInvalido.append(currentChar);
                        posErro++;
                    }

                    if (isCteString) {
                        lexemaInvalido = new StringBuilder();
                        lexemaInvalido.append("constante_string");
                    }
                    if (erroSintatico.IsEOF()) {
                        lexemaInvalido = new StringBuilder();
                        lexemaInvalido.append("EOF");
                    }

                    messageArea.setText("Erro na linha " + linha + " - encontrado " + lexemaInvalido + " " + erroSintatico.getMessage());

                    //Trata erros sintáticos
                    //linha 			      sugestão: converter getPosition em linha
                    //símbolo encontrado    sugestão: implementar um método getToken no sintatico
                    //símbolos esperados,   alterar ParserConstants.java, String[] PARSER_ERROR
                    // consultar os símbolos esperados no GALS (em Documentação > Tabela de Análise Sintática): 		
                } catch (SemanticError erroSemantico) {
                    int posErro = erroSemantico.getPosition();
                    int linha = FindLine(posErro, textoDividido);

                    messageArea.setText("Erro na linha " + linha + " - " + erroSemantico.getMessage());
                }

            }
        });

        toolBar.add(compileBtn);
    }

    private int FindLine(int errorPosition, String[] splittedText) {
        int line = 0;
        int qtdCaracteres = splittedText[line].length();
        int posErro = errorPosition;

        while (posErro > qtdCaracteres) {
            line++;
            qtdCaracteres += splittedText[line].length() + 1;
        }

        return line + 1;
    }

    private void CreateTeamBtn() {
        String btnLabel = "Equipe";
        String btnShortcut = "F1";
        String iconPath = "/icons/equipe.png";

        teamBtn = new JButton(btnLabel + " [" + btnShortcut + "]");

        ImageIcon icon = new ImageIcon(getClass().getResource(iconPath));
        Image image = icon.getImage();
        Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(scaledImage);
        teamBtn.setIcon(scaledIcon);

        teamBtn.setVerticalTextPosition(SwingConstants.BOTTOM);
        teamBtn.setHorizontalTextPosition(SwingConstants.CENTER);

        teamBtn.setPreferredSize(new Dimension(80, 80));

        teamBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                btnsActions.ShowTeam();
            }
        });

        toolBar.add(teamBtn);
    }

    private void CreateShortcuts() {
        InputMap inputMap = toolBar.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = toolBar.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK), "novo");
        actionMap.put("novo", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                newBtn.doClick();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK), "abrir");
        actionMap.put("abrir", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openBtn.doClick();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK), "salvar");
        actionMap.put("salvar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveBtn.doClick();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0), "compilar");
        actionMap.put("compilar", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                compileBtn.doClick();
            }
        });

        inputMap.put(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0), "equipe");
        actionMap.put("equipe", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                teamBtn.doClick();
            }
        });
    }

    public void SetCurrentPath(String path) {
        this.currentPath = path;
    }
}

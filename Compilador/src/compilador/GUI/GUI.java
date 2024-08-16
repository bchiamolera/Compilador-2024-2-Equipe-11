/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package compilador.GUI;

import javax.swing.*;
import java.awt.*;

/**
 *
 * @author Bernardo Chiamolera
 */
public class GUI {
    
    JFrame window;
    
    JToolBar toolBar;
    
    JSplitPane splitPane;
    
    JTextArea editor;
    JScrollPane editorScrollPane;
    
    JTextArea messageArea;
    JScrollPane messageScrollPane;
    
    JLabel statusBar;
    
    public GUI() {
        createWindow();
        createToolBar();
        createSplitPane();
        createStatusBar();
        
        window.setVisible(true);
    }
    
    private void createWindow() {
        window = new JFrame("Compilador 2024/2");
        window.setSize(910,600);
        window.setMinimumSize(new Dimension(910, 600));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
    private void createToolBar() {
        toolBar = new JToolBar();
        toolBar.setPreferredSize(new Dimension(900,70));
        
        String[] buttonLabels = {"Novo", "Abrir", "Salvar", "Copiar", "Colar", "Recortar", "Compilar", "Equipe"};
        String[] shortcuts = {"ctrl-n", "ctrl-o", "ctrl-s", "ctrl-c", "ctrl-v", "ctrl-x", "F7", "F1"};
        String[] iconsPaths = {
            "./icons/novo.png",
            "./icons/abrir.png",
            "./icons/salvar.png",
            "./icons/copiar.png",
            "./icons/colar.png",
            "./icons/recortar.png",
            "./icons/compilar.png",
            "./icons/equipe.png"
        };
        
        for (int i = 0; i < buttonLabels.length; i++) {
            JButton button = new JButton(buttonLabels[i] + " [" + shortcuts[i] + "]");
            
            ImageIcon icon = new ImageIcon(iconsPaths[i]);
            Image image = icon.getImage();
            Image scaledImage = image.getScaledInstance(32, 32, Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(scaledImage);
            button.setIcon(scaledIcon);
            
            button.setVerticalTextPosition(SwingConstants.BOTTOM);
            button.setHorizontalTextPosition(SwingConstants.CENTER);
            
            button.setPreferredSize(new Dimension(80, 80));
            
            toolBar.add(button);
        }
        
        window.getContentPane().add(toolBar, BorderLayout.NORTH);
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
        JLabel statusBar = new JLabel("pasta/nome do arquivo");
        statusBar.setPreferredSize(new Dimension(900, 25));
        
        window.add(statusBar, BorderLayout.SOUTH);
    }
    
    
}

package compilador.GUI;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ButtonsActions {
    
    private GUI gui;
    private File currentFile;
    
    public ButtonsActions(GUI gui) {
        this.gui = gui;
        currentFile = null;
    }
    
    public void NewFile() {
        currentFile = null;
        gui.editor.setText("");
        gui.messageArea.setText("");
        gui.statusBar.setText("Nenhum arquivo aberto");
    }
    
    public String OpenFile() {
        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
        fileChooser.setFileFilter(filter);
        int result = fileChooser.showOpenDialog(gui.window);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                gui.editor.read(reader, null);
                reader.close();
                gui.statusBar.setText(currentFile.getPath());
                gui.messageArea.setText("");
                return currentFile.getPath();
            } catch (IOException e) {
                gui.statusBar.setText("Erro ao abrir o arquivo: " + e.getMessage());
            }
            gui.messageArea.setText("");
        }
        return "";
    }
    
    public String SaveFile() {
        if (currentFile == null) {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files (*.txt)", "txt");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(gui.window);
            if (result == JFileChooser.APPROVE_OPTION) {
                currentFile = fileChooser.getSelectedFile();
                if (!currentFile.getName().endsWith(".txt")) {
                    currentFile = new File(currentFile.getAbsolutePath() + ".txt");
                }
            }
        }
        
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
            writer.write(gui.editor.getText());
            writer.close();
            gui.statusBar.setText(currentFile.getPath());
            gui.messageArea.setText("");
            return currentFile.getPath();
        } catch (Exception e) {
            gui.statusBar.setText("Erro ao salvar o arquivo");
        }
        return "";
    }
    
    public void ShowTeam() {
        gui.messageArea.setText(gui.messageArea.getText() + "Equipe: \n-> Bernardo Chiamolera \n-> Gabriel Jorge Utyama de Carvalho.\n");
    }
    
}

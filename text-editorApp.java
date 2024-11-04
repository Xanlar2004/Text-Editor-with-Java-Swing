import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class SimpleTextEditor extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private JTextArea editorArea;
    private JButton btnOpen, btnSave;

    public SimpleTextEditor() {
        super("Simple Text Editor");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        editorArea = new JTextArea();
        JScrollPane scrollContainer = new JScrollPane(editorArea);
        getContentPane().add(scrollContainer, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        getContentPane().add(buttonPanel, BorderLayout.NORTH);

        btnOpen = new JButton("Open File");
        btnOpen.addActionListener(this);
        buttonPanel.add(btnOpen);

        btnSave = new JButton("Save File");
        btnSave.addActionListener(this);
        buttonPanel.add(btnSave);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == btnOpen) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int selection = fileChooser.showOpenDialog(this);
            if (selection == JFileChooser.APPROVE_OPTION) {
                try (BufferedReader br = new BufferedReader(new FileReader(fileChooser.getSelectedFile()))) {
                    StringBuilder content = new StringBuilder();
                    String currentLine;
                    while ((currentLine = br.readLine()) != null) {
                        content.append(currentLine).append("\n");
                    }
                    editorArea.setText(content.toString());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } else if (event.getSource() == btnSave) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));
            int selection = fileChooser.showSaveDialog(this);
            if (selection == JFileChooser.APPROVE_OPTION) {
                try (FileWriter writer = new FileWriter(fileChooser.getSelectedFile())) {
                    writer.write(editorArea.getText());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new SimpleTextEditor();
    }
}

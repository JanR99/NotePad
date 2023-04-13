import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

public class NotePad extends JFrame {
    JTextArea textArea;
    JScrollPane scrollPane;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem newMenuItem, openMenuItem, saveAsMenuItem, exitMenuItem;

    public static void main(String[] args) {
        new NotePad();
    }

    public NotePad() {
        setTitle("Notepad");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        textArea = new JTextArea();
        textArea.setFont(new Font("Arial", Font.PLAIN, 12));
        scrollPane = new JScrollPane(textArea);

        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveAsMenuItem = new JMenuItem("Save As...");
        exitMenuItem = new JMenuItem("Exit");

        newMenuItem.addActionListener(e -> textArea.setText(""));
        openMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(NotePad.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(file));
                    String line = reader.readLine();
                    while (line != null) {
                        textArea.append(line + "\n");
                        line = reader.readLine();
                    }
                    textArea.setText("");
                    reader.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        saveAsMenuItem.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("txt", "txt"));
            int result = fileChooser.showSaveDialog(NotePad.this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (!file.getName().endsWith(".txt")) {
                    file = new File(file.getAbsolutePath() + ".txt");
                }
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(textArea.getText());
                    writer.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        });
        exitMenuItem.addActionListener(e -> System.exit(0));

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);
        menuBar.add(fileMenu);

        setJMenuBar(menuBar);
        add(scrollPane);

        setVisible(true);
    }
}

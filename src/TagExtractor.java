import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.io.*;


import static java.nio.file.StandardOpenOption.CREATE;
public class TagExtractor extends JFrame
{
    JPanel mainPanel;

    // Header panel assets
    JPanel headerPanel;
    JLabel header;
    Font headerFont = new Font("Comfortaa", Font.BOLD, 49);
    Color headerLabelColor = new Color(247, 246, 245, 255);
    Color interfaceButtonColor = new Color(186,185,180);
    Color headerBackgroundColor = new Color(240,130,90,255);



    // Main interface assets, which contains field to select file, as well as textArea to show extracted tags
    JPanel interfacePanel;
    JLabel fileLabel;
    JTextField fileTextField;
    JButton selectFileButton;
    JFileChooser fileChooser;
    JTextArea textArea;
    JScrollPane scrollPane;
    Font interfaceLabelFont = new Font("Arial", Font.PLAIN, 12);
    Color interfaceLabelColor = new Color(44,43,41);
    Color interfaceBackgroundColor = new Color(255,252,246,255);



    // Button Panel
    JPanel buttonPanel;
    JButton exportTagsButton;
    JButton quitButton;


    // Program logic variables
    HashMap<String, Integer> tagsMap = new HashMap<String, Integer>();
    File selectedFile;
    Path file;

    public TagExtractor()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        createInterfacePanel();
        mainPanel.add(interfacePanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private void createHeaderPanel()
    {
        headerPanel = new JPanel();
        headerPanel.setBackground(headerBackgroundColor);
        headerPanel.setLayout(new GridBagLayout());

        header = new JLabel("Tag Extractor");
        header.setFont(headerFont);
        header.setForeground(headerLabelColor);

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        headerPanel.add(header, c);
    }

    private void createInterfacePanel()
    {
        interfacePanel = new JPanel();
        interfacePanel.setBackground(interfaceBackgroundColor);
        interfacePanel.setLayout(new GridBagLayout());

        fileLabel = new JLabel("File:");
        fileLabel.setFont(interfaceLabelFont);
        fileLabel.setForeground(interfaceLabelColor);

        fileTextField = new JTextField();
        fileTextField.setColumns(50);

        selectFileButton = new JButton("Select File");
        selectFileButton.setFont(interfaceLabelFont);
        selectFileButton.setForeground(interfaceLabelColor);
        selectFileButton.setBackground(interfaceButtonColor);
        selectFileButton.addActionListener((ActionEvent ae) -> getSelectedFile());

        GridBagConstraints c = new GridBagConstraints();
        c.anchor = GridBagConstraints.CENTER;
        c.insets = new Insets(5, 5, 5, 5);

        c.gridx = 0;
        c.gridy = 0;
        interfacePanel.add(fileLabel, c);
        c.gridx = 1;
        interfacePanel.add(fileTextField, c);
        c.gridx = 2;
        interfacePanel.add(selectFileButton, c);
    }

    private void getSelectedFile()
    {
        fileChooser = new JFileChooser();

            File workingDirectory = new File(System.getProperty("user.dir"));
            fileChooser.setCurrentDirectory(workingDirectory);

            if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
            {
                selectedFile = fileChooser.getSelectedFile();
                file = selectedFile.toPath();
                fileTextField.setText(String.valueOf(file));
            }
    }
}



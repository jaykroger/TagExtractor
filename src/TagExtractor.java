import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.Scanner;


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
    JButton scanButton;
    JButton exportTagsButton;
    JButton clearButton;
    JButton quitButton;


    // Program logic variables

    File selectedFile;
    Path file;
    File workingDirectory = new File(System.getProperty("user.dir"));
    Path outputFile = Paths.get(workingDirectory.getPath() + "\\src\\Tags_Data.txt");
    Path stopWordsFile = Path.of("src/stopWords.txt");
    HashMap<String, Integer> tagsMap = new HashMap<>();
    ArrayList<String> tagWords = new ArrayList<>();
    ArrayList<String> stopWords = new ArrayList<>();


    public TagExtractor()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        createInterfacePanel();
        mainPanel.add(interfacePanel, BorderLayout.CENTER);

        createButtonPanel();
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel);
    }

    private void createHeaderPanel()
    {
        headerPanel = new JPanel();
        headerPanel.setBackground(headerBackgroundColor);
        headerPanel.setLayout(new GridBagLayout());
        Dimension headerSize = new Dimension(1080, 100);
        headerPanel.setPreferredSize(headerSize);

        header = new JLabel("Tag Extractor");
        header.setFont(headerFont);
        header.setForeground(headerLabelColor);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(header, gbc);
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

        textArea = new JTextArea();
        textArea.setRows(20);
        textArea.setColumns(50);

        scrollPane = new JScrollPane(textArea);


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 20, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        interfacePanel.add(fileLabel, gbc);
        gbc.gridx = 1;
        interfacePanel.add(fileTextField, gbc);
        gbc.gridx = 2;
        interfacePanel.add(selectFileButton, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        interfacePanel.add(scrollPane, gbc);
    }

    private void createButtonPanel()
    {
        buttonPanel = new JPanel();
        buttonPanel.setBackground(interfaceBackgroundColor);
        buttonPanel.setLayout(new GridBagLayout());

        scanButton = new JButton("Scan");
        scanButton.setFont(interfaceLabelFont);
        scanButton.setForeground(interfaceLabelColor);
        scanButton.setBackground(interfaceButtonColor);
        scanButton.addActionListener((ActionEvent ae) -> scanFile());

        exportTagsButton = new JButton("Export Tags");
        exportTagsButton.setFont(interfaceLabelFont);
        exportTagsButton.setForeground(interfaceLabelColor);
        exportTagsButton.setBackground(interfaceButtonColor);
        exportTagsButton.addActionListener((ActionEvent ae) -> writeTagsFile());

        clearButton = new JButton("Clear");
        clearButton.setFont(interfaceLabelFont);
        clearButton.setForeground(interfaceLabelColor);
        clearButton.setBackground(interfaceButtonColor);
        clearButton.addActionListener((ActionEvent ae) ->
        {
            textArea.selectAll();
            textArea.setText("");
        });

        quitButton = new JButton("Quit");
        quitButton.setFont(interfaceLabelFont);
        quitButton.setForeground(interfaceLabelColor);
        quitButton.setBackground(interfaceButtonColor);
        quitButton.addActionListener((ActionEvent ae) -> getQuitConfirm());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(0, 5, 25, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonPanel.add(scanButton, gbc);
        gbc.gridx = 1;
        buttonPanel.add(exportTagsButton, gbc);
        gbc.gridx = 2;
        buttonPanel.add(clearButton, gbc);
        gbc.gridx = 3;
        buttonPanel.add(quitButton, gbc);
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

    private void getQuitConfirm()
    {
        int quitConfirm;
        quitConfirm = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "Exit", JOptionPane.YES_NO_OPTION);

        if (quitConfirm == JOptionPane.YES_OPTION)
        {
            System.exit(0);
        }
    }

    private void scanFile()
    {
        file = Path.of(fileTextField.getText());
        loadStopWords(stopWordsFile);

        try {
            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNext())
            {
                String nextWord = fileScanner.next();
                String regulatedWord = regulateString(nextWord);
                tagWords.add(regulatedWord);
            }
            for (String s : tagWords)
            {
                if (!stopWords.contains(s))
                {
                    if (tagsMap.containsKey(s))
                    {
                        tagsMap.put(s, tagsMap.get(s) + 1);
                    }
                    else
                    {
                        tagsMap.put(s, 1);
                    }
                }

            }

            for (String tag : tagsMap.keySet())
            {
                textArea.append(tag + ", " + tagsMap.get(tag) + "\n");
            }
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private void writeTagsFile()
    {
        try
        {
            OutputStream out = new BufferedOutputStream(Files.newOutputStream(outputFile, CREATE));
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(out));

            for(String t : tagsMap.keySet())
            {
                writer.write(t, 0, t.length());
                writer.write(", ");
                writer.write(String.valueOf(tagsMap.get(t)));
                writer.newLine();
            }

            writer.close();
            JOptionPane.showMessageDialog(null, "Tags successfully exported.", "Export Successful,", JOptionPane.OK_OPTION);

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void loadStopWords(Path p)
    {
        try
        {
            Scanner fileScanner = new Scanner(p);

            while (fileScanner.hasNext())
            {
                String nextWord = fileScanner.next();
                stopWords.add(nextWord);
                System.out.println(nextWord);
            }

        } catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    private String regulateString(String s)
    {
        String regulatedString = s.replaceAll("[^a-zA-Z]+", "");
        return regulatedString.toLowerCase();
    }
}



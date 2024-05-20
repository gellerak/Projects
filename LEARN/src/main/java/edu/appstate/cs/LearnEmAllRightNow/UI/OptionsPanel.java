package edu.appstate.cs.LearnEmAllRightNow.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;
import java.awt.FontFormatException;

public class OptionsPanel extends JPanel {

    private JFrame frame;  // Reference to the main frame
    private JComboBox<String> fontComboBox;
    private JComboBox<Integer> fontSizeComboBox;
    private List<Font> fonts;

    public OptionsPanel(JFrame frame) {
        this.frame = frame;
        setLayout(new BorderLayout());
        initializeFonts();

        fontComboBox = new JComboBox<>(fonts.stream().map(Font::getName).toArray(String[]::new));
        Integer[] fontSizes = {16, 18, 20, 22, 24};
        fontSizeComboBox = new JComboBox<>(fontSizes);

        JPanel controlPanel = new JPanel(new GridLayout(1, 4, 10, 10));
        controlPanel.add(new JLabel("Select Font:"));
        controlPanel.add(fontComboBox);
        controlPanel.add(new JLabel("Select Font Size:"));
        controlPanel.add(fontSizeComboBox);

        controlPanel.setFont(Config.getMainFont());

        add(controlPanel, BorderLayout.NORTH);

        ActionListener fontChangeAction = e -> updateFont();
        fontComboBox.addActionListener(fontChangeAction);
        fontSizeComboBox.addActionListener(fontChangeAction);
    }


    private void initializeFonts() {
        String fontsDirectory = "src/main/java/edu/appstate/cs/LearnEmAllRightNow/UI/Assets/Fonts";

        try {
            fonts = Files.walk(Paths.get(fontsDirectory))
                .filter(path -> path.toString().endsWith(".ttf"))
                .map(path -> {
                    try {
                        return Font.createFont(Font.TRUETYPE_FONT, path.toFile());
                    } 
                    catch (FontFormatException | IOException e) {
                        throw new RuntimeException("Error loading font: " + path, e);
                    }
                })
                .collect(Collectors.toList());

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();

            for (Font font : fonts)
                ge.registerFont(font);
            
        } catch (IOException | RuntimeException e) {
            e.printStackTrace();
            System.out.println("Failed to load fonts from directory: " + fontsDirectory);
            System.out.println("Working Directory = " + System.getProperty("user.dir"));
        }
    }
    private void updateFont() {
        Font selectedFont = fonts.get(fontComboBox.getSelectedIndex());
        int selectedSize = (int) fontSizeComboBox.getSelectedItem();
        Font newFont = selectedFont.deriveFont((float) selectedSize);
        Config.setMainFont(newFont);

        refreshUIComponents(frame);
    }

    private void refreshUIComponents(Component component) {

        if (component instanceof Container)
            for (Component child : ((Container) component).getComponents())
                refreshUIComponents(child);
        
        component.setFont(Config.getMainFont());
        component.revalidate();
        component.repaint();
    }

}


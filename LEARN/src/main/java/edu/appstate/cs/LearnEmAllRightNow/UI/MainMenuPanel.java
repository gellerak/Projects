package edu.appstate.cs.LearnEmAllRightNow.UI;

import javax.swing.*;
import java.awt.*;

public class MainMenuPanel {

    private static Font currentFont = new Font("Sans-Serif", Font.PLAIN, 18);

    public static void setCurrentFont(Font font) {
        currentFont = font;
    }

    public static Font getCurrentFont() {
        return currentFont;
    }

    public static void setupMainFrame(JFrame frame) {

        Config.SetUp();
        frame.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel(new BorderLayout());
        frame.add(contentPanel, BorderLayout.CENTER);

        JPanel navPanel = createNavigationPanel(frame, contentPanel);
        frame.add(navPanel, BorderLayout.SOUTH);

        JLabel instructionLabel = new JLabel("Please select an option from the navigation bar below to begin.", JLabel.CENTER);
        JPanel initialPanel = new JPanel(new BorderLayout());

        instructionLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 18));
        initialPanel.add(instructionLabel, BorderLayout.CENTER);
        contentPanel.add(initialPanel, BorderLayout.CENTER);

        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static JButton createImageButton(String imagePath, Dimension size) {

        ImageIcon icon = new ImageIcon(imagePath);
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(64, 64, Image.SCALE_DEFAULT);
        icon = new ImageIcon(newImg);

        JButton button = new JButton(icon);
        button.setBackground(Color.WHITE);
        button.setOpaque(false);
        button.setHorizontalTextPosition(SwingConstants.CENTER);
        button.setVerticalTextPosition(SwingConstants.BOTTOM);

        return button;
    }

    public static JPanel createNavigationPanel(JFrame frame, JPanel contentPanel) {

        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));

        Dimension buttonSize = new Dimension(64, 64);

        String imagesPrePath = "src/main/java/edu/appstate/cs/LearnEmAllRightNow/UI/Assets/Images";

        JButton createDeckBtn = createImageButton(imagesPrePath + "/deck-icon.png", buttonSize);
        JButton editDeckBtn = createImageButton(imagesPrePath + "/deck-icon.png", buttonSize);
        JButton studyQuizBtn = createImageButton(imagesPrePath + "/study-icon.png", buttonSize);
        JButton optionsBtn = createImageButton(imagesPrePath + "/settings-icon.png", buttonSize);

        createDeckBtn.setPreferredSize(buttonSize);
        editDeckBtn.setPreferredSize(buttonSize);
        studyQuizBtn.setPreferredSize(buttonSize);
        optionsBtn.setPreferredSize(buttonSize);

        createDeckBtn.addActionListener(e -> switchToPanel(contentPanel, DeckPanel.CreateDeckPanel(frame)));
        editDeckBtn.addActionListener(e -> switchToPanel(contentPanel, DeckPanel.EditDeckPanel(frame)));
        studyQuizBtn.addActionListener(e -> switchToPanel(contentPanel, QuizPanel.StudyQuizPanel(frame)));
        optionsBtn.addActionListener(e -> switchToPanel(contentPanel, new OptionsPanel(frame)));  // Switch to OptionsPanel
        navPanel.add(createDeckBtn);
        navPanel.add(editDeckBtn);
        navPanel.add(Box.createRigidArea(new Dimension(20, 0))); 
        navPanel.add(studyQuizBtn);

        return navPanel;
    }


    private static void switchToPanel(JPanel contentPanel, JPanel newPanel) {
        contentPanel.removeAll();
        contentPanel.add(newPanel, BorderLayout.CENTER);
        FontUtils.updateComponentTreeFont(newPanel, currentFont);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}


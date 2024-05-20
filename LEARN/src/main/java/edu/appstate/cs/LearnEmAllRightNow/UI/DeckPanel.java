package edu.appstate.cs.LearnEmAllRightNow.UI;

import javax.swing.*;

import edu.appstate.cs.LearnEmAllRightNow.Card;
import edu.appstate.cs.LearnEmAllRightNow.Database;
import edu.appstate.cs.LearnEmAllRightNow.Deck;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;

public class DeckPanel {

    static int counter = 0;
    private static Deck deck;

    public static JPanel CreateDeckPanel(JFrame frame){

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel title = new JLabel("CREATE DECK");

        title.setForeground(Color.orange);
        title.setFont(Config.getMainFontSizeAltered(30));

        JButton createCardButton = new JButton("Create Card");
        JButton completeDeckButton = new JButton("Complete Deck");

        completeDeckButton.setFont(Config.getMainFont());
        createCardButton.setFont(Config.getMainFont());

        JLabel cardCounter = new JLabel("Cards: " + counter);
        cardCounter.setFont(Config.getMainFont());

        JTextField deckNameTextField = new JTextField();
        JTextField cardQuestionTextField = new JTextField();
        JTextField cardAnswerTextField = new JTextField();

        deckNameTextField.setFont(Config.getMainFont());
        cardQuestionTextField.setFont(Config.getMainFont());
        cardAnswerTextField.setFont(Config.getMainFont());

        cardQuestionTextField.setText("Question" + counter);
        cardAnswerTextField.setText("Answer" + counter);
        
        deck = new Deck("");

        createCardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               deck.addCard(new Card(cardQuestionTextField.getText(), cardAnswerTextField.getText()));
               counter++;
               cardCounter.setText("Cards: " + counter);
               cardQuestionTextField.setText("Question" + counter);
               cardAnswerTextField.setText("Answer" + counter);
            }
        });

        completeDeckButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
              String deckName = deckNameTextField.getText();
              if (deckName.equals("")) {
                  deckNameTextField.setText("Please enter a deck name");
                  return;
              }
              deck.setName(deckName);
              System.out.println(deck);
              Database.saveDeckToFile(deck, deckNameTextField.getText());
            }
        });

        panel.add(title);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(cardCounter);
        panel.add(cardQuestionTextField);
        panel.add(cardAnswerTextField);
        panel.add(createCardButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20))); 
        panel.add(deckNameTextField);
        panel.add(completeDeckButton);

        return panel;
    }

    public static JPanel EditDeckPanel(JFrame frame) 
    {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        JLabel label = new JLabel("Select An Existing Deck To Edit");
        label.setFont(Config.getMainFontSizeAltered(30));
        panel.add(label);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        List<Deck> loadedDecks = Database.loadAllDecks();
        List<JButton> buttonRefs = new ArrayList<>();
        List<Component> cardPanels = new ArrayList<>();

        for (Deck deck : loadedDecks) {

            JButton deckButton = new JButton(deck.getName());
            deckButton.setFont(Config.getMainFont());
            buttonRefs.add(deckButton);

            deckButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JPanel deckEditPanel = new JPanel();
                    deckEditPanel.setLayout(new BorderLayout());

                    JPanel deckPanel = new JPanel();
                    deckPanel.setLayout(new BoxLayout(deckPanel, BoxLayout.Y_AXIS));

                    JScrollPane deckScrollPanel = new JScrollPane(deckPanel);
                    deckScrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

                    panel.remove(label);

                    for (JButton btn : buttonRefs) 
                    {
                        panel.remove(btn);
                    }

                    for (Component comp : cardPanels) 
                    {
                        panel.remove(comp);
                    }
                    cardPanels.clear(); 

                    String decksPath = Database.getDecksPath();
                    Deck loadedDeck = Database.loadDeckFromFile(decksPath + File.separator + deckButton.getText());

                    for (Card card : loadedDeck.getCards())
                    {
                        if (card != null)
                        {
                            JPanel cardPanel = card.toPanel();
                            deckPanel.add(cardPanel);
                            cardPanels.add(cardPanel);
                        }
                    }

                    JButton saveButton = new JButton("Save Changes");
                    saveButton.setFont(Config.getMainFont());

                    JButton deleteButton = new JButton("Delete Deck"); 
                    deleteButton.setFont(Config.getMainFont());


                    JPanel saveAndDelete = new JPanel(new GridLayout(1, 2));
                    saveAndDelete.add(saveButton);
                    saveAndDelete.add(deleteButton);

                    saveButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Database.saveDeckToFile(loadedDeck, deckButton.getText());
                            
                            panel.add(label, 0);

                            panel.remove(deckEditPanel);

                            for (Component comp : cardPanels) 
                            {
                                panel.remove(comp);
                            }
                            cardPanels.clear();

                            for (JButton btn : buttonRefs) 
                            {
                                panel.add(btn);
                            }

                            panel.revalidate();
                            panel.repaint();
                        }
                    });

                    deleteButton.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            Database.deleteDeck(deckButton.getText());
                            
                            panel.add(label, 0);
    
                            panel.remove(deckEditPanel);
    
                            for (Component comp : cardPanels) 
                            {
                                panel.remove(comp);
                            }
                            cardPanels.clear();
    
                            for (JButton btn : buttonRefs) 
                            {
                                if (btn != deckButton)
                                {
                                    panel.add(btn);
                                }
                            }
    
                            panel.revalidate();
                            panel.repaint();
                        }
                    });

                    deckEditPanel.add(deckScrollPanel, BorderLayout.CENTER);

                    JPanel buttonPanel = new JPanel(new BorderLayout());
                    buttonPanel.add(saveAndDelete, BorderLayout.WEST); 
                    buttonPanel.add(deleteButton, BorderLayout.EAST); 
                    deckEditPanel.add(buttonPanel, BorderLayout.SOUTH);

                    panel.add(deckEditPanel);
                    panel.revalidate();
                    panel.repaint();
                }
            });

            panel.add(deckButton);
        }

        return panel;
    }
}


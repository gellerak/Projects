package edu.appstate.cs.LearnEmAllRightNow;

import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serializable;

// A class used to represent a single card. 
public class Card implements Serializable
{
    private static final long serialVersionUID = 1L;
    private String front;   // question
    private String back;    // answer

    /**
     * 2-arg constructor for Card Class
     * 
     * @param front sets the question
     * @param back  sets the answer
     */
    public Card(String front, String back)
    {
        this.front = front;
        this.back = back;
    }

    // returns card question
    public String getFront()
    {
        return front;
    }

    // returns card answer
    public String getBack()
    {
        return back;
    }

    // Set card question
    public void setFront(String front)
    {
        this.front = front;
    }

    // Set card answer
    public void setBack(String back)
    {
        this.back = back;
    }

    public boolean equals(Card other)
    {
        return this.front.equals(other.getFront()) && this.back.equals(other.getBack());
    }

    public String toString()
    {
        return front + " : " + back;
    }

    // Visual Interpretation of Card
    public JPanel toPanel()
    {
        // card display
        JPanel cardPanel = new JPanel();
        cardPanel.setPreferredSize(new Dimension(200, 200)); 
        cardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); 

        // front and back of card
        JLabel frontLabel = new JLabel("<html><center>" + front + "</center></html>");
        JLabel backLabel = new JLabel("<html><center>" + back + "</center></html>");

        // edit card button
        JButton editButton = new JButton("Edit");

        editButton.addActionListener(e -> {
            // edit front and back of card
            JTextField frontField = new JTextField(front);
            JTextField backField = new JTextField(back);
            // save/cancel edits buttons
            JButton saveButton = new JButton("Save");
            JButton cancelButton = new JButton("Cancel");
            
            saveButton.addActionListener(e2 -> {
                // update front/back of card
                frontLabel.setText("<html><center>" + frontField.getText() + "</center></html>");
                backLabel.setText("<html><center>" + backField.getText() + "</center></html>");

                setFront(frontField.getText());
                setBack(backField.getText());

                cardPanel.removeAll();
                cardPanel.add(frontLabel);
                cardPanel.add(backLabel);
                cardPanel.add(editButton);
                cardPanel.revalidate();
                cardPanel.repaint();
            });

            cancelButton.addActionListener(e2 -> {
                cardPanel.removeAll();
                cardPanel.add(frontLabel);
                cardPanel.add(backLabel);
                cardPanel.add(editButton);
                cardPanel.revalidate();
                cardPanel.repaint();
            });

            cardPanel.removeAll();
            cardPanel.add(frontField);
            cardPanel.add(backField);
            cardPanel.add(saveButton);
            cardPanel.add(cancelButton);
            cardPanel.revalidate();
            cardPanel.repaint();
        });
 
        cardPanel.add(frontLabel);
        cardPanel.add(backLabel);
        cardPanel.add(editButton);
        
        return cardPanel;
    }
}

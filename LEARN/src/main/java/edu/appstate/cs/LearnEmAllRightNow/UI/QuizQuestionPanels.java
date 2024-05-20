package edu.appstate.cs.LearnEmAllRightNow.UI;

import edu.appstate.cs.LearnEmAllRightNow.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.*;
import java.util.ArrayList;

public class QuizQuestionPanels {

    public static JPanel FillInTheBlankPanel(FillInTheBlankQuestion question, Boolean[] answers, int i) {

       JPanel panel = new JPanel(new GridBagLayout());
       GridBagConstraints gbc = new GridBagConstraints();
       gbc.gridwidth = GridBagConstraints.REMAINDER;
       gbc.fill = GridBagConstraints.HORIZONTAL;

       Card card = question.getCard();

       JLabel front = new JLabel(card.getFront());
       front.setFont(Config.getMainFont());
       front.setForeground(Color.DARK_GRAY);
       panel.add(front, gbc);

       JTextField answerField = new JTextField(20);
       answerField.setFont(Config.getMainFont());
       panel.add(answerField, gbc);

       answers[i] = false;
       answerField.getDocument().addDocumentListener(new DocumentListener() {

           private void updateAnswer() {
               answers[i] = answerField.getText().equals(card.getBack());
           }

           @Override
           public void insertUpdate(DocumentEvent e) {
               updateAnswer();
           }

           @Override
           public void removeUpdate(DocumentEvent e) {
               updateAnswer();
           }

           @Override
           public void changedUpdate(DocumentEvent e) {
               updateAnswer();
           }
       });

       return panel;
    }
 

    public static JPanel MultipleChoicePanel(MultipleChoiceQuestion question, Boolean[] answers, int i) {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Card card = question.getCard();

        JLabel front = new JLabel(card.getFront());
        front.setFont(Config.getMainFont());
        front.setForeground(Color.DARK_GRAY);

        panel.add(front, gbc);

        ButtonGroup group = new ButtonGroup();
        for (int j = 0; j < question.getChoices().size(); j++) {
            Card choiceCard = question.getChoices().get(j);
            String choiceText = choiceCard.getBack();
            JRadioButton choice = new JRadioButton(choiceText);
            choice.setFont(Config.getMainFont());
            group.add(choice);
            panel.add(choice, gbc);

            choice.addActionListener(e -> {
                answers[i] = choice.getText().equals(card.getBack());
            });
        }

        return panel;
    }
 
    
    
    public static JPanel MatchQuizPanel(MatchQuizQuestion question, Boolean[] answers, int i) {

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ArrayList<Card> cards = question.getFront();
        ArrayList<String> possibleAnswers = question.getBack();

        for (int j = 0; j < cards.size(); j++) {

            final int finalJ = j;

            JLabel frontLabel = new JLabel(cards.get(finalJ).getFront());
            panel.add(frontLabel, gbc);

            frontLabel.setFont(Config.getMainFont());

            JComboBox<String> answerDropdown = new JComboBox<>();

            for (String answer : possibleAnswers)
                answerDropdown.addItem(answer);
            
            panel.add(answerDropdown, gbc);

            answerDropdown.addActionListener(e -> {
                answers[i + finalJ] = answerDropdown.getSelectedItem().equals(cards.get(finalJ).getBack());
            });
        }

        return panel;
    }



}


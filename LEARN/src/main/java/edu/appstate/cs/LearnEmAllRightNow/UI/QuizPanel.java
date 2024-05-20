package edu.appstate.cs.LearnEmAllRightNow.UI;

import javax.swing.*;
import java.awt.*;
import java.util.List;

import edu.appstate.cs.LearnEmAllRightNow.Database;
import edu.appstate.cs.LearnEmAllRightNow.Deck;
import edu.appstate.cs.LearnEmAllRightNow.FillInTheBlankQuestion;
import edu.appstate.cs.LearnEmAllRightNow.MatchQuizQuestion;
import edu.appstate.cs.LearnEmAllRightNow.MultipleChoiceQuestion;
import edu.appstate.cs.LearnEmAllRightNow.Question;
import edu.appstate.cs.LearnEmAllRightNow.Quiz;

public class QuizPanel {

    public static JPanel StudyQuizPanel(JFrame frame) {
        JPanel panel = new JPanel();
        JLabel title = new JLabel("STUDY QUIZ");
        JLabel desc = new JLabel("Select a deck to start studying it!");

        title.setFont(Config.getMainFont());
        desc.setFont(Config.getMainFont());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        title.setForeground(Color.GREEN);

        panel.add(title);
        panel.add(desc);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        List<Deck> decks = Database.loadAllDecks();

        if (decks.isEmpty()) {
            panel.add(new JLabel("No decks available. Please create some decks first."));
        } else {
            for (Deck deck : decks) {

                JButton deckButton = new JButton(deck.getName());
                deckButton.addActionListener(e -> {
                    frame.setContentPane(createDeckStudyPanel(deck, frame));
                    frame.revalidate();
                    frame.repaint();
                });

                panel.add(deckButton);
            }
        }

        return panel;
    }

    static Boolean[] answerList;
    static Integer score = 0;

    private static JPanel createDeckStudyPanel(Deck deck, JFrame frame) {
        Quiz quiz = new Quiz(deck);
        answerList = new Boolean[quiz.getQuestions().length];

        JPanel studyPanel = new JPanel();
        studyPanel.setLayout(new BoxLayout(studyPanel, BoxLayout.Y_AXIS));

        JLabel deckName = new JLabel("Now Studying: " + deck.getName());
        deckName.setForeground(Color.ORANGE);
        deckName.setFont(Config.getMainFont()); 
        deckName.setAlignmentX(Component.CENTER_ALIGNMENT);

        studyPanel.add(deckName);

        JPanel questionsPanel = new JPanel();
        questionsPanel.setLayout(new BoxLayout(questionsPanel, BoxLayout.Y_AXIS));

        questionsPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        for (int i = 0; i < quiz.getQuestions().length; i++) {
            Question question = quiz.getQuestions()[i];
            if (question != null && question.getType() != null) {

                JPanel questionPanel = new JPanel();
                questionPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                switch (question.getType()) {
                    case "FillInTheBlankQuestion":
                        questionPanel.add(QuizQuestionPanels.FillInTheBlankPanel((FillInTheBlankQuestion) question, answerList, i));
                        break;
                    case "MultipleChoiceQuestion":
                        questionPanel.add(QuizQuestionPanels.MultipleChoicePanel((MultipleChoiceQuestion) question, answerList, i));
                        break;
                    case "MatchQuizQuestion":
                        questionPanel.add(QuizQuestionPanels.MatchQuizPanel((MatchQuizQuestion) question, answerList, i));
                        break;
                }

                questionPanel.add(Box.createRigidArea(new Dimension(0, 20)));
                questionsPanel.add(questionPanel);
            }
            else
                System.out.println("ERROR OCCURED WITH QUESTION");
        }

        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setPreferredSize(new Dimension(500, 400));

        studyPanel.add(scrollPane);

        JButton backButton = new JButton("Return To Deck Quiz Selection");
        backButton.addActionListener(e -> {
            frame.setContentPane(StudyQuizPanel(frame));
            frame.revalidate();
            frame.repaint();
        });


        JButton finishedButton = new JButton("Complete Quiz");
        finishedButton.addActionListener(e -> {
            frame.setContentPane((FinishedQuizPanel(frame)));
            frame.revalidate();
            frame.repaint();
        });

        backButton.setFont(Config.getMainFont());
        finishedButton.setFont(Config.getMainFont());

        studyPanel.add(backButton);
        studyPanel.add(finishedButton);

        return studyPanel;
    }

    private static JPanel FinishedQuizPanel(JFrame frame) {
        score = 0;
        // calc score
        for (Boolean boolean1 : answerList)
            if (boolean1 != null && boolean1)
                score++;
        score *= (100 / answerList.length);

        JPanel panel = new JPanel();
        JLabel label = new JLabel("Final Score: " + score);
        label.setFont(Config.getMainFont());

        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        if (score > 90) label.setForeground(Color.GREEN);
        else if (score > 70) label.setForeground(Color.ORANGE);
        else label.setForeground(Color.RED);

        JButton returnButton = new JButton("Return To Quiz Selection");
        returnButton.addActionListener(e -> {
            frame.setContentPane((StudyQuizPanel(frame)));
            frame.revalidate();
            frame.repaint();
        });

        returnButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(Box.createRigidArea(new Dimension(0, 20))); 
        panel.add(Box.createRigidArea(new Dimension(0, 10)));

        panel.add(label);
        panel.add(returnButton);

        return panel;
    }

}


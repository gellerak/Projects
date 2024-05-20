package edu.appstate.cs.LearnEmAllRightNow;
import java.io.Serializable;
import java.util.ArrayList;

public class Quiz implements Serializable {
    private Question[] questions;
    private Deck deck;

    public Quiz(Deck deck) {
        this.deck = deck;
        initializeQuiz();
    }

    private void initializeQuiz() {
        int size = deck.getSize();
        if (size < 6) {
            throw new IllegalArgumentException("Deck must have at least 6 cards. Size: " + size);
        }

        while (size % 3 != 0) {
            size++;
        }

        Card[][] sections = partitionCardsIntoSections(size);
        createQuestionsFromSections(sections);
    }

    private Card[][] partitionCardsIntoSections(int size) {
        int third = size / 3;
        Card[] cards = deck.getCards();
        Card[][] sections = new Card[3][third];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < third; j++) {
                sections[i][j] = cards[(i * third + j) % deck.getSize()];
            }
        }
        return sections;
    }

    private void createQuestionsFromSections(Card[][] sections) {
        questions = new Question[sections[0].length + sections[1].length + 1]; // +1 for the match quiz
        int currentQuestion = 0;

        // Fill in the blank
        for (Card card : sections[0]) {
            questions[currentQuestion++] = new FillInTheBlankQuestion(card);
        }

        // Multiple Choice
        for (Card card : sections[1]) {
            questions[currentQuestion++] = createMultipleChoiceQuestion(card);
        }

        // Matching
        questions[currentQuestion] = new MatchQuizQuestion(sections[2]);
    }

    private MultipleChoiceQuestion createMultipleChoiceQuestion(Card correctCard) {
        ArrayList<Card> choices = new ArrayList<>();
        while (choices.size() < 3) {
            Card randomCard = deck.getCards()[(int) (Math.random() * deck.getSize())];
            if (!randomCard.equals(correctCard) && !choices.contains(randomCard)) {
                choices.add(randomCard);
            }
        }
        choices.add(correctCard);
        return new MultipleChoiceQuestion(correctCard, choices);
    }

    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Question question : questions) {
            if (question != null) {
                str.append(question.toString()).append("\n");
            }
        }
        return str.toString();
    }

    public Question[] getQuestions() {
        return questions;
    }
}

package edu.appstate.cs.LearnEmAllRightNow;

import javax.swing.*;

import edu.appstate.cs.LearnEmAllRightNow.UI.Config;
import edu.appstate.cs.LearnEmAllRightNow.UI.MainMenuPanel;

public class App {

    public static void main(String[] args) {

        JFrame frame = new JFrame("LEARN");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 400);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);

        MainMenuPanel.setupMainFrame(frame);

        frame.setVisible(true);
    }

    public static void quizTest() {
        Deck deck = new Deck("Test Deck");
        deck.addCard(new Card("What is the capital of France?", "Paris"));
        deck.addCard(new Card("What is the capital of Spain?", "Madrid"));
        deck.addCard(new Card("What is the capital of Germany?", "Berlin"));
        deck.addCard(new Card("What is the capital of Italy?", "Rome"));
        deck.addCard(new Card("What is the capital of the United States?", "Washington D.C."));
        deck.addCard(new Card("What is the capital of Canada?", "Ottawa"));
        deck.addCard(new Card("What is the capital of Mexico?", "Mexico City"));
        deck.addCard(new Card("What is the capital of Brazil?", "Brasilia"));
        deck.addCard(new Card("What is the capital of Argentina?", "Buenos Aires"));
        deck.addCard(new Card("What is the capital of Australia?", "Canberra"));
        deck.addCard(new Card("What is the capital of Japan?", "Tokyo"));
        deck.addCard(new Card("What is the capital of South Korea?", "Seoul"));
        deck.addCard(new Card("What is the capital of China?", "Beijing"));
        deck.addCard(new Card("What is the capital of India?", "New Delhi"));
        deck.addCard(new Card("What is the capital of Russia?", "Moscow"));
        deck.addCard(new Card("What is the capital of South Africa?", "Pretoria"));
        deck.addCard(new Card("What is the capital of Egypt?", "Cairo"));
        deck.addCard(new Card("What is the capital of Nigeria?", "Abuja"));
        deck.addCard(new Card("What is the capital of Kenya?", "Nairobi"));
        deck.addCard(new Card("What is the capital of Saudi Arabia?", "Riyadh"));
        deck.addCard(new Card("What is the capital of Iran?", "Tehran"));
        deck.addCard(new Card("What is the capital of Iraq?", "Baghdad"));
        deck.addCard(new Card("What is the capital of Afghanistan?", "Kabul"));
        deck.addCard(new Card("What is the capital of Pakistan?", "Islamabad"));
        deck.addCard(new Card("What is the capital of Bangladesh?", "Dhaka"));
        deck.addCard(new Card("What is the capital of Thailand?", "Bangkok"));
        deck.addCard(new Card("What is the capital of Indonesia?", "Jakarta"));

        Quiz quiz = new Quiz(deck);
        System.out.println(quiz);
    }
}


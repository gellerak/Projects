package edu.appstate.cs.LearnEmAllRightNow;

import java.util.ArrayList;
import java.util.Collections;

public class MultipleChoiceQuestion extends Question {
  private Card question;
  private ArrayList<Card> choices;
  private String type;

  public MultipleChoiceQuestion (Card question, ArrayList<Card> choices) {
    this.question = question;
    this.choices = choices;
    this.type = "MultipleChoiceQuestion";

    // shuffle the choices
    Collections.shuffle(this.choices);
  }

  public Card getCard() {
    return question;
  }

  public ArrayList<Card> getChoices() {
    return choices;
  }

  public String toString() {
    String questionString = question.getFront();
    String choicesString = "";
    for (int i = 0; i < choices.size(); i++) {
      choicesString += (char)(i + 97) + ". " + choices.get(i).getBack() + "\n";
    }
    return questionString + "\n" + choicesString;
  }
}

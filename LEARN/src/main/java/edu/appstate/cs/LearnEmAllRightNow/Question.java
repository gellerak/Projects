package edu.appstate.cs.LearnEmAllRightNow;

public class Question {

  private String type;

  public boolean check (Card card, String front, String back) {
    return (front.equals(card.getFront()) && back.equals(card.getBack()));
  }

  public String getType() {
    return this.getClass().getSimpleName();
  }

}

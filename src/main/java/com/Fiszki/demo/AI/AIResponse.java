package com.Fiszki.demo.AI;

import java.util.List;

public class AIResponse {

   private List<String> wrongAnswers;

   public AIResponse(List<String> wrongAnswers) {
      this.wrongAnswers = wrongAnswers;
   }

   public List<String> getWrongAnswers() {
      return wrongAnswers;
   }

   public void setWrongAnswers(List<String> wrongAnswers) {
      this.wrongAnswers = wrongAnswers;
   }
}
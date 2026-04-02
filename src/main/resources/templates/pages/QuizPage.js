import { useEffect, useState } from "react";
import { apiFetch } from "../api";
import { useParams } from "react-router-dom";

export default function QuizPage() {
  const { quizId } = useParams();

  const [question, setQuestion] = useState(null);
  const [selected, setSelected] = useState("");
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    loadNextQuestion();
  }, [quizId]);

  async function loadNextQuestion() {
    try {
      const data = await apiFetch(`/quiz/${quizId}/next`);

      if (!data) {
        await finishQuiz();
        return;
      }

      setQuestion(data);
      setSelected("");
    } catch (err) {
      await finishQuiz();
    }
  }

  async function sendAnswer() {
    if (!selected) return;

    try {
      await apiFetch("/quiz/answer", {
        method: "POST",
        body: JSON.stringify({
          questionId: question.id,
          chosenAnswer: selected,
        }),
      });

      await loadNextQuestion();
    } catch (err) {
      setError(err.message);
    }
  }

  async function finishQuiz() {
    try {
      await apiFetch(`/quiz/finish/${quizId}`, {
        method: "POST",
      });

      const data = await apiFetch(`/quiz/${quizId}/result`);
      setResult(data);
      setQuestion(null);
    } catch (err) {
      setError(err.message);
    }
  }

  if (error) return <p className="error">{error}</p>;

  if (result) {
    return (
      <div className="card">
        <h1>Wynik quizu</h1>
        <p>Punkty: {result.score}</p>
        <p>Wszystkie pytania: {result.totalQuestions}</p>
        <p>Procent: {result.percentage}%</p>
      </div>
    );
  }

  if (!question) return <p>Ładowanie quizu...</p>;

  return (
    <div className="card">
      <h1>Quiz</h1>
      <h2>{question.question}</h2>

<div className="answers">
  {question.options.map((option, index) => (
    <div
      key={option}
      className={`answer-card ${selected === option ? "selected" : ""}`}
      onClick={() => setSelected(option)}
    >
      <span className="letter">
        {String.fromCharCode(65 + index)}:
      </span>
      <span>{option}</span>
    </div>
  ))}
</div>

      <button onClick={sendAnswer} disabled={!selected}>
        Zatwierdź odpowiedź
      </button>
    </div>
  );
}
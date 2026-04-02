import { useState } from "react";
import { apiFetch } from "../api";
import { useNavigate, useParams } from "react-router-dom";

export default function NewFlashcardPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [form, setForm] = useState({
    question: "",
    correctAnswer: "",
    wrongAnswer1: "",
    wrongAnswer2: "",
    wrongAnswer3: "",
  });

  const [loadingAI, setLoadingAI] = useState(false);
  const [error, setError] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    try {
      const options = [
        form.wrongAnswer1,
        form.wrongAnswer2,
        form.wrongAnswer3,
      ].filter((opt) => opt && opt.trim() !== "");

      if (options.length < 3) {
        setError("Dodaj co najmniej 3 błędne odpowiedzi");
        return;
      }

      await apiFetch("/flashcards", {
        method: "POST",
        body: JSON.stringify({
          question: form.question,
          correctAnswer: form.correctAnswer,
          options: options,
          deckId: id,
        }),
      });

      navigate(`/decks/${id}`);
    } catch (err) {
      setError(err.message);
    }
  }

  async function generateAI() {
    if (!form.question || !form.correctAnswer) {
      setError("Najpierw wpisz pytanie i poprawną odpowiedź");
      return;
    }

    try {
      setLoadingAI(true);
      setError("");

      // 🔥 FIX: używamy apiFetch zamiast fetch
      const data = await apiFetch("/ai/generate-options", {
        method: "POST",
        body: JSON.stringify({
          question: form.question,
          correctAnswer: form.correctAnswer,
        }),
      });

      setForm((prev) => ({
        ...prev,
        wrongAnswer1: data?.wrongAnswers?.[0] || "",
        wrongAnswer2: data?.wrongAnswers?.[1] || "",
        wrongAnswer3: data?.wrongAnswers?.[2] || "",
      }));
    } catch (err) {
      setError(err.message);
    } finally {
      setLoadingAI(false);
    }
  }

  return (
    <div className="card">
      <h1>Nowa fiszka</h1>

      <form onSubmit={handleSubmit}>
        <textarea
          placeholder="Pytanie"
          value={form.question}
          onChange={(e) =>
            setForm({ ...form, question: e.target.value })
          }
        />

        <input
          placeholder="Poprawna odpowiedź"
          value={form.correctAnswer}
          onChange={(e) =>
            setForm({ ...form, correctAnswer: e.target.value })
          }
        />

        <button
          type="button"
          onClick={generateAI}
          disabled={loadingAI}
        >
          {loadingAI ? "Generuję..." : "🤖 Generuj błędne odpowiedzi"}
        </button>

        <input
          placeholder="Błędna odpowiedź 1"
          value={form.wrongAnswer1}
          onChange={(e) =>
            setForm({ ...form, wrongAnswer1: e.target.value })
          }
        />

        <input
          placeholder="Błędna odpowiedź 2"
          value={form.wrongAnswer2}
          onChange={(e) =>
            setForm({ ...form, wrongAnswer2: e.target.value })
          }
        />

        <input
          placeholder="Błędna odpowiedź 3"
          value={form.wrongAnswer3}
          onChange={(e) =>
            setForm({ ...form, wrongAnswer3: e.target.value })
          }
        />

        <button type="submit">Dodaj fiszkę</button>
      </form>

      {error && <p className="error">{error}</p>}
    </div>
  );
}
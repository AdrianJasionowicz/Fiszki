import { useEffect, useState } from "react";
import { apiFetch } from "../api";
import { Link, useParams, useNavigate } from "react-router-dom";

export default function DeckDetailsPage() {
  const { id } = useParams();
  const navigate = useNavigate();

  const [deck, setDeck] = useState(null);
  const [flashcards, setFlashcards] = useState([]);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    loadData();
  }, [id]);

  async function loadData() {
    try {
      setLoading(true);

      const deckData = await apiFetch(`/decks/${id}`);
      const flashcardsData = await apiFetch(`/flashcards/deck/${id}`);

      setDeck(deckData);
      setFlashcards(flashcardsData);
    } catch (err) {
      setError(err.message);
    } finally {
      setLoading(false);
    }
  }

  async function handleDelete() {
    if (!window.confirm("Na pewno chcesz usunąć ten deck?")) return;

    try {
      await apiFetch(`/decks/${id}`, {
        method: "DELETE",
      });

      alert("Deck usunięty ✔️");
      navigate("/decks");
    } catch (err) {
      if (err.message.includes("403")) {
        alert("Nie masz uprawnień ❌");
      } else {
        alert(err.message);
      }
    }
  }

  async function startQuiz() {
    try {
      const data = await apiFetch(`/quiz/start/${id}`, {
        method: "POST",
      });

      navigate(`/quiz/${data.id}`);
    } catch (err) {
      alert(err.message);
    }
  }

  if (loading) return <p className="center-text">Ładowanie...</p>;
  if (error) return <p className="error">{error}</p>;
  if (!deck) return <p>Brak danych</p>;

  return (
    <div className="container">
      {/* 🔥 HEADER */}
      <div className="deck-header">
        <div className="deck-info">
          <h1>{deck.name}</h1>
          <p>{deck.description}</p>
        </div>

        <div className="deck-actions">
          <Link className="btn btn-secondary" to={`/decks/${id}/flashcards/new`}>
            + Dodaj fiszkę
          </Link>

          <button onClick={startQuiz} className="btn btn-primary">
            Start quizu
          </button>

          <button onClick={handleDelete} className="btn btn-danger">
            Usuń deck
          </button>
        </div>
      </div>

      {/* 🔥 FLASHCARDS */}
      <div className="grid">
        {flashcards.map((card) => (
          <div className="flashcard" key={card.id}>
            <h3>{card.question}</h3>
            <p>
              <strong>Odpowiedź:</strong> {card.correctAnswer}
            </p>
          </div>
        ))}
      </div>
    </div>
  );
}
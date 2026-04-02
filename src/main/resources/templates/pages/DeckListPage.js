import { useEffect, useState } from "react";
import { apiFetch } from "../api";
import { Link } from "react-router-dom";

export default function DeckListPage() {
  const [decks, setDecks] = useState([]);
  const [error, setError] = useState("");

  useEffect(() => {
    loadDecks();
  }, []);

  async function loadDecks() {
    try {
      const data = await apiFetch("/decks");
      setDecks(data);
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div className="container">
      {/* HEADER */}
      <div className="list-header">
        <h1>Moje decki</h1>

        <Link className="btn btn-primary" to="/decks/new">
          + Nowy deck
        </Link>
      </div>

      {error && <p className="error">{error}</p>}

      {/* GRID */}
      <div className="grid">
        {decks.map((deck) => (
          <div className="deck-card" key={deck.id}>
            <h3>{deck.name}</h3>
            <p>{deck.description}</p>

            <div className="deck-actions">
              <Link className="btn btn-secondary" to={`/decks/${deck.id}`}>
                Szczegóły
              </Link>

              <Link className="btn btn-primary" to={`/decks/${deck.id}`}>
                Quiz
              </Link>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
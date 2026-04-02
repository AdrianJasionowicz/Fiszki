import { useState } from "react";
import { useNavigate } from "react-router-dom";
import { apiFetch } from "../api";

export default function NewDeckPage() {
  const [form, setForm] = useState({
    name: "",
    description: "",
  });

  const [error, setError] = useState("");
  const navigate = useNavigate();

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    try {
      await apiFetch("/decks", {
        method: "POST",
        body: JSON.stringify(form),
      });

      navigate("/decks");
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div className="center">
      <div className="card form-card">
        <h1>Nowy deck</h1>

        <form onSubmit={handleSubmit}>
          <input
            placeholder="Nazwa decka"
            value={form.name}
            onChange={(e) =>
              setForm({ ...form, name: e.target.value })
            }
          />

          <input
            placeholder="Opis (opcjonalnie)"
            value={form.description}
            onChange={(e) =>
              setForm({ ...form, description: e.target.value })
            }
          />

          <button type="submit">Utwórz deck</button>
        </form>

        {error && <p className="error">{error}</p>}
      </div>
    </div>
  );
}
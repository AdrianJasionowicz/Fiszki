import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";

export default function LoginPage() {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

async function handleSubmit(e) {
  e.preventDefault();
  setError("");

  try {
    const res = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(form),
      credentials: "include",
    });

    if (!res.ok) {
      throw new Error("Login failed");
    }

    navigate("/decks");

  } catch (err) {
    setError("Błąd logowania");
  }
}

  return (
    <div className="card">
      <h1>Logowanie</h1>
      <form onSubmit={handleSubmit} className="form">
        <input
          placeholder="Login"
          value={form.username}
          onChange={(e) => setForm({ ...form, username: e.target.value })}
        />
        <input
          type="password"
          placeholder="Hasło"
          value={form.password}
          onChange={(e) => setForm({ ...form, password: e.target.value })}
        />
        <button type="submit">Zaloguj</button>
      </form>
      {error && <p className="error">{error}</p>}
      <p>
        Nie masz konta? <Link to="/register">Zarejestruj się</Link>
      </p>
    </div>
  );
}
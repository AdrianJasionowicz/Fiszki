import { useState } from "react";
import { apiFetch } from "../api";

export default function SettingsPage() {
  const [form, setForm] = useState({
    username: "",
    firstName: "",
    email: "",
    oldPassword: "",
    newPassword: "",
  });

  const [message, setMessage] = useState("");
  const [error, setError] = useState("");

  function handleChange(e) {
    setForm({
      ...form,
      [e.target.name]: e.target.value,
    });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setMessage("");
    setError("");

    try {
      await apiFetch("/User", {
        method: "PUT",
        body: JSON.stringify(form),
      });

      setMessage("✅ Zaktualizowano dane!");
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <div className="card">
      <h2>Ustawienia konta</h2>

      <form onSubmit={handleSubmit}>
        <input
          name="username"
          placeholder="Nowy username"
          value={form.username}
          onChange={handleChange}
        />

        <input
          name="firstName"
          placeholder="Imię"
          value={form.firstName}
          onChange={handleChange}
        />

        <input
          name="email"
          placeholder="Email"
          value={form.email}
          onChange={handleChange}
        />

        <hr />

        <input
          type="password"
          name="oldPassword"
          placeholder="Stare hasło"
          value={form.oldPassword}
          onChange={handleChange}
          required
        />

        <input
          type="password"
          name="newPassword"
          placeholder="Nowe hasło"
          value={form.newPassword}
          onChange={handleChange}
        />

        <button type="submit">Zapisz zmiany</button>
      </form>

      {message && <p className="success">{message}</p>}
      {error && <p className="error">{error}</p>}
    </div>
  );
}
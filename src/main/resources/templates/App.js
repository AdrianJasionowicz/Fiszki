import { BrowserRouter, Routes, Route, Link, Navigate, useNavigate } from "react-router-dom";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import DeckListPage from "./pages/DeckListPage";
import NewDeckPage from "./pages/NewDeckPage";
import DeckDetailsPage from "./pages/DeckDetailsPage";
import NewFlashcardPage from "./pages/NewFlashcardPage";
import QuizPage from "./pages/QuizPage";
import SettingsPage from "./pages/SettingsPage";
import "./App.css";

function Navbar() {
  const navigate = useNavigate();

  function handleLogout() {
    // 🔥 usuwamy cookie przez backend (najlepiej)
    fetch("http://localhost:8080/auth/logout", {
      method: "POST",
      credentials: "include"
    });

    navigate("/login");
  }

  return (
    <nav className="nav">
      <div className="nav-left">
        <Link to="/decks">Fiszki</Link>
      </div>

      <div className="nav-right">
        <Link to="/decks">Decki</Link>
        <Link to="/settings">⚙️ Ustawienia</Link>
        <button onClick={handleLogout} className="logout-btn">
          Wyloguj
        </button>
      </div>
    </nav>
  );
}

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <main className="container">
        <Routes>
          <Route path="/" element={<Navigate to="/decks" />} />

          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />

          <Route path="/decks" element={<DeckListPage />} />
          <Route path="/decks/new" element={<NewDeckPage />} />
          <Route path="/decks/:id" element={<DeckDetailsPage />} />
          <Route path="/decks/:id/flashcards/new" element={<NewFlashcardPage />} />
          <Route path="/quiz/:quizId" element={<QuizPage />} />

          {/* 🔥 SETTINGS */}
          <Route path="/settings" element={<SettingsPage />} />
        </Routes>
      </main>
    </BrowserRouter>
  );
}

export default App;
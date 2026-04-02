Fiszki – aplikacja do nauki z użyciem quizów

Pełnostackowa aplikacja webowa umożliwiająca naukę przy użyciu fiszek oraz quizów.
Użytkownik może tworzyć zestawy, dodawać pytania i sprawdzać swoją wiedzę w trybie testowym.

Projekt powstał w celu przećwiczenia architektury Spring Boot, autoryzacji JWT oraz integracji backend–frontend (React).

Funkcjonalności
Tworzenie i zarządzanie zestawami fiszek
Dodawanie fiszek zawierających:
pytanie
poprawną odpowiedź
błędne odpowiedzi (opcje)
Tryb quizu:
losowa kolejność pytań
wybór odpowiedzi (multiple choice)
zliczanie punktów
Logowanie i rejestracja (JWT)
Wynik quizu z procentowym podsumowaniem
Generowanie błędnych odpowiedzi (AI – opcjonalnie)
Technologie
Backend
Java 23
Spring Boot
Spring Security (JWT)
Spring Data JPA
Hibernate
H2 Database
Frontend
React
React Router
Fetch API
Uruchomienie projektu
Backend
mvn spring-boot:run

Aplikacja dostępna pod:
http://localhost:8080

Frontend
npm install
npm start

Aplikacja dostępna pod:
http://localhost:3000

Autor

Adrian Jasionowicz

Informacje

Projekt wykonany w celach edukacyjnych w celu rozwinięcia umiejętności:

tworzenia aplikacji full-stack
implementacji autoryzacji JWT
projektowania API
debugowania problemów

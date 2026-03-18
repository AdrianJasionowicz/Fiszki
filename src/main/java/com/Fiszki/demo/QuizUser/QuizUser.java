package com.Fiszki.demo.QuizUser;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.LoginUser.LoginUser;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class QuizUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String Name;
    private String Email;

    @OneToOne(mappedBy = "quizUser")
    private LoginUser loginUser;
    @OneToMany(mappedBy = "quizUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Deck> deckList;
}

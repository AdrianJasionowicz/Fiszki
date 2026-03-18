package com.Fiszki.demo.QuizUser;

import com.Fiszki.demo.Deck.Deck;
import com.Fiszki.demo.Deck.DeckDTO;
import com.Fiszki.demo.Deck.DeckRepository;
import com.Fiszki.demo.Exception.UserNotFoundException;
import com.Fiszki.demo.LoginUser.LoginUser;
import com.Fiszki.demo.LoginUser.LoginUserRepository;
import com.Fiszki.demo.LoginUser.LoginUserService;
import com.Fiszki.demo.Mapper.DeckMapper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuizUserService {

    private final LoginUserRepository loginUserRepository;
    private final LoginUserService loginUserService;
    private final DeckMapper deckMapper;
    private final DeckRepository deckRepository;

    public QuizUserService(LoginUserRepository loginUserRepository, LoginUserService loginUserService, DeckMapper deckMapper, DeckRepository deckRepository) {
        this.loginUserRepository = loginUserRepository;
        this.loginUserService = loginUserService;
        this.deckMapper = deckMapper;
        this.deckRepository = deckRepository;
    }


}
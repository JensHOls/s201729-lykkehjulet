package com.example.s201729lykkehjulet.model

const val INCORRECT_LETTER = 1
const val TYPE_LETTER = 2
const val SPIN_WHEEL = 3

// list of all possible words
val wordsList: List<Word> =
    listOf(
        Word("animal", "lion"),
        Word("animal", "wolf"),
        Word("animal", "zebra"),
        Word("animal", "giraph"),
        Word("animal", "orangutan"),
        Word("animal", "gorilla"),
        Word("animal", "squirrel"),
        Word("animal", "hippopotamus"),
        Word("animal", "crocodile"),
        Word("animal", "bear"),
        Word("animal", "buffalo"),
        Word("animal", "chimpanzee"),
        Word("capital city", "London"),
        Word("capital city", "Dubai"),
        Word("capital city", "Copenhagen"),
        Word("capital city", "Berlin"),
        Word("capital city", "Oslo"),
        Word("capital city", "Moscow"),
        Word("capital city", "Paris"),
        Word("capital city", "Beijing"),
        Word("capital city", "Tokyo"),
        Word("capital city", "Sydney"),
        Word("capital city", "Jerusalem"),
        Word("capital city", "Baghdad"),
        Word("capital city", "Madrid"),
        Word("capital city", "Athens"),
        )

// list of all possible bonuses
val bonusList: List<Bonus> =
    listOf(
        Bonus(3, "bankrupt", "your score is set to 0", 0),
        Bonus(2, "miss turn", "you lose a life point", 0),
        Bonus(1, "extra turn", "you gain a life point",0),
        Bonus(0, "1 point bonus", "gain 1 point for each letter guessed", 1),
        Bonus(0, "2 point bonus", "gain 2 point for each letter guessed", 2),
        Bonus(0, "3 point bonus", "gain 3 point for each letter guessed", 3),
        Bonus(0, "4 point bonus", "gain 4 point for each letter guessed", 4),
        Bonus(0, "5 point bonus", "gain 5 point for each letter guessed", 5)
    )
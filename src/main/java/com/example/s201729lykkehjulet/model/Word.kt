package com.example.s201729lykkehjulet.model

// class to hold the information of a word
class Word(nCategory: String, nWord: String) {

    private var _word: String = nWord
    val word: String
        get() = _word

    private var _category: String = nCategory
    val category: String
        get() = _category

}
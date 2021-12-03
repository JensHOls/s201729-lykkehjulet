package com.example.s201729lykkehjulet

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.s201729lykkehjulet.model.Bonus
import com.example.s201729lykkehjulet.model.Word
import com.example.s201729lykkehjulet.model.bonusList
import com.example.s201729lykkehjulet.model.wordsList

class GameViewModel : ViewModel() {

    private lateinit var wordObject: Word

    private lateinit var _lettersGuessed: MutableList<Boolean>

    private lateinit var _bonus: Bonus

    private var _bonusTriggered = true
    val bonusTriggered: Boolean
        get() = _bonusTriggered

    val category: String
        get() = wordObject.category

    // User interface related information set up as LiveData
    // to be coordinated with the views. Saved as MutableLiveData
    // locally so it can be manipulated and returned as none mutable LiveData to outsiders
    private val _wordToDisplay = MutableLiveData<String>()
    val wordToDisplay: LiveData<String>
        get() = _wordToDisplay

    private val _score = MutableLiveData<Int>(0)
    val score: LiveData<Int>
        get() = _score

    private val _lives = MutableLiveData<Int>(5)
    val lives: LiveData<Int>
        get() = _lives

    private val _bonusMessage = MutableLiveData<String>()
    val bonusMessage: LiveData<String>
        get() = _bonusMessage


    init {
        getWord()
        initializeLettersGuessed()
        updateDisplayedWord()
    }

    private fun getWord() {
        wordObject = wordsList.random()
    }

    fun isWordGuessed(): Boolean {
        for (char in _lettersGuessed) {
            if (!char)
                return false
        }
        return true
    }

    private fun increaseScore(points: Int) {
        _score.value = (_score.value)?.plus(points)
    }

    // execute effect of current bonus and set the bonus to triggered
    private fun executeBonusEffect(correctGuesses: Int) {
        when (_bonus.type) {
            0 -> increaseScore(_bonus.scoreChange * correctGuesses)
            1 -> _lives.value = (_lives.value)?.inc()
            2 -> _lives.value = (_lives.value)?.dec()
            3 -> _score.value = (_score.value)?.times(0)
        }
        _bonusTriggered = true
    }

    // Get random bonus from bonusList and save the "name" of the bonus
    private fun getBonus() {
        _bonus = bonusList.random()
        _bonusMessage.value = _bonus.name
    }

    // Updates the word to display based on which letters have been guessed.
    private fun updateDisplayedWord() {
        val wordToCharArr = wordObject.word.toCharArray()
        var wordToDisplayChar = ""

        for ((i, bool) in _lettersGuessed.withIndex()) {
            if (bool) {
                wordToDisplayChar += wordToCharArr[i]
            } else {
                wordToDisplayChar += "#"
            }
        }
        _wordToDisplay.value = wordToDisplayChar
    }

    //Passes letter and checks if it is part of word.
    //Updates the letters guessed.
    //Returns number of occurrences of letter.
    private fun guessLetter(letter: String): Int {
        var count = 0
        for ((i, char) in wordObject.word.withIndex()) {
            if (char.toString() == letter && !_lettersGuessed[i]) {
                _lettersGuessed[i] = true
                count++
            }
        }
        if (count < 1) {
            _lives.value = (_lives.value)?.dec()
        }
        return count
    }

    // Function to handle events related to guessing a letter/letters in the hidden word
    fun handleLetterGuess(letter: String): Boolean {
        var correctGuess = false
        val count = guessLetter(letter)
        if (count > 0) correctGuess = true
        updateDisplayedWord()
        executeBonusEffect(count)
        return correctGuess
    }

    // Function to get a new bonus and execute it's effect if it is not bonus
    // that gives points based on number of letters correctly guessed.
    fun handleBonus() {
        getBonus()
        if (_bonus.type > 0) {
            executeBonusEffect(0)
            return
        }
        // If bonus effect wasn't used, bonusUsed is set to false
        _bonusTriggered = false
    }

    // Function to initialize a boolean array used to track which letters
    // in the hidden word have been guessed.
    private fun initializeLettersGuessed() {
        _lettersGuessed = arrayListOf()
        val charArray = wordObject.word.toCharArray()
        for (chars in charArray) {
            _lettersGuessed.add(false)
        }
    }
    fun noLives(): Boolean {
        return _lives.value!! < 1
    }


    // Reinitialize data in viewModel as if it was created anew
    fun reinitializeData() {
        _score.value = (_score.value)?.times(0)
        _lives.value?.times(0)?.plus(5)
        _bonusTriggered = true
        getWord()
        initializeLettersGuessed()
        updateDisplayedWord()
    }
}
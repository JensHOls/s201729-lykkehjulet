package com.example.s201729lykkehjulet.model

// class the hold information of a bonus from the wheel of fortune
class Bonus(type: Int, name: String, message: String, scoreChange: Int) {

    private var _type: Int = type
    val type: Int
        get() = _type

    private var _message: String = message
    val message: String
        get() = _message

    private var _name: String = name
    val name: String
        get() = _name

    private var _scoreChange: Int = scoreChange
    val scoreChange: Int
        get() = _scoreChange

}
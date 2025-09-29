package com.example.olddragonrpg.model

class D6 : Dice {
    override fun roll(times: Int): List<Int> {
        return (1..times).map {(1..6).random()}
    }
}
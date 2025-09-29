package com.example.olddragonrpg.model

interface Dice {
    fun roll(times:Int): List<Int>
}
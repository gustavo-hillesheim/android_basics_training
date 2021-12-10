package com.example.android.unscramble.ui.game

import android.text.Spannable
import android.text.SpannableString
import android.text.style.TtsSpan
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private var wordsList = mutableListOf<String>()
    private lateinit var currentWord: String
    private val _score = MutableLiveData(0)
    val score: LiveData<Int> get() = _score
    private val _currentWordCount = MutableLiveData(0)
    val currentWordCount: LiveData<Int> get() = _currentWordCount
    private var _currentScrambledWord = MutableLiveData<String>()
    val currentScrambledWord = Transformations.map(_currentScrambledWord) {
        if (it == null) {
            SpannableString("")
        } else {
            val scrambledWord = it.toString()
            val spannable = SpannableString(scrambledWord)
            spannable.setSpan(
                TtsSpan.VerbatimBuilder(scrambledWord).build(),
                0, scrambledWord.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE
            )
            spannable
        }
    }

    private fun getNextWord() {
        currentWord = allWordsList.random()
        if (wordsList.contains(currentWord)) {
            getNextWord()
        } else {
            val tempWord = currentWord.toCharArray()
            tempWord.shuffle()
            while (String(tempWord).equals(currentWord, false)) {
                tempWord.shuffle()
            }
            _currentScrambledWord.value = String(tempWord)
            _currentWordCount.value = _currentWordCount.value?.inc()
            wordsList.add(currentWord)
        }
    }

    private fun increaseScore() {
        _score.value = _score.value?.plus(SCORE_INCREASE)
    }

    fun nextWord(): Boolean {
        return if (_currentWordCount.value!! < MAX_NO_OF_WORDS) {
            getNextWord()
            true
        } else false
    }

    fun isUserWordCorrect(playerWord: String): Boolean {
        if (playerWord.equals(currentWord, true)) {
            increaseScore()
            return true
        }
        return false
    }

    fun reinitializeData() {
        _score.value = 0
        _currentWordCount.value = 0
        wordsList.clear()
        getNextWord()
    }

    init {
        getNextWord()
    }
}
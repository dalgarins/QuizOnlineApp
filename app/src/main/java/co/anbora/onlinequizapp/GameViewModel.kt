package co.anbora.onlinequizapp

import android.arch.core.util.Function
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import android.arch.lifecycle.ViewModel
import co.anbora.onlinequizapp.domain.model.Question
import co.anbora.onlinequizapp.domain.model.User
import kotlin.collections.ArrayList

class GameViewModel: ViewModel {

    private val categoryId: MutableLiveData<String> = MutableLiveData()
    private val user: MutableLiveData<User> = MutableLiveData()

    private val questions: MutableLiveData<List<Question>> = MutableLiveData()
    private val currentQuestion: LiveData<Question>

    private val indexQuestion: MutableLiveData<Int> = MutableLiveData()

    constructor() {
        currentQuestion = Transformations.switchMap(indexQuestion, Function {
            val v :MutableLiveData<Question> = MutableLiveData()
            v.value = this.questions.value!![it]
            return@Function v
        })
    }

    fun getCurrentUser(): LiveData<User> {
        return user
    }

    fun setCurrentUser(user: User) {
        this.user.value = user
    }

    fun getCategoryQuizGame(): LiveData<String> {
        return categoryId
    }

    fun setCategoryQuizGame(categoryId: String) {
        this.categoryId.value = categoryId
    }

    fun getQuestions(): LiveData<List<Question>> {
        if (questions.value == null) {
            questions.value = ArrayList()
        }
        return questions
    }

    fun setQuestions(questions: List<Question>) {
        this.questions.value = questions
    }

    fun getCurrentQuestion(): LiveData<Question> {
        return this.currentQuestion
    }

    fun setCurrentQuestion(index: Int) {
        if (this.questions.value!!.isEmpty()) {
            return
        }
        indexQuestion.value = index
    }

}
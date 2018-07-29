package co.anbora.onlinequizapp

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import co.anbora.onlinequizapp.domain.model.Question
import co.anbora.onlinequizapp.domain.model.User
import kotlin.collections.ArrayList

class GameViewModel: ViewModel() {

    private val categoryId: MutableLiveData<String> = MutableLiveData()
    private val user: MutableLiveData<User> = MutableLiveData()
    private val questions: MutableLiveData<List<Question>> = MutableLiveData()

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

}
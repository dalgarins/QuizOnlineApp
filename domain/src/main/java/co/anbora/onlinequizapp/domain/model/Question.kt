package co.anbora.onlinequizapp.domain.model

data class Question(val question: String = "",
                    val answerA: String = "",
                    val answerB: String = "",
                    val answerC: String = "",
                    val answerD: String = "",
                    val categoryId: String = "", val isImageQuestion: String = "")
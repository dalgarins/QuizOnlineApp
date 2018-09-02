package co.anbora.onlinequizapp

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.anbora.onlinequizapp.databinding.ActivityDoneBinding
import co.anbora.onlinequizapp.domain.model.QuestionScore
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class DoneActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDoneBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var questionScore: DatabaseReference

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_done)

        database = FirebaseDatabase.getInstance()
        questionScore = database.getReference("Question_Score")

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        binding.btnTryAgain.setOnClickListener {
            val intent: Intent = Intent(this@DoneActivity, HomeActivity::class.java)
            startActivity(intent)
            finish()
        }

        val extra: Bundle? = intent.extras
        if (extra != null) {
            val score: Int = extra.getInt("SCORE")
            val totalQuestion: Int = extra.getInt("TOTAL")
            val correctAnswer: Int = extra.getInt("CORRECT")

            binding.txtTotalScore.text = String.format("SCORE: %d", score)
            binding.txtTotalQuestions.text = String.format("PASSED: %d / %d", correctAnswer, totalQuestion)

            binding.doneProgressBar.max = totalQuestion
            binding.doneProgressBar.progress = correctAnswer

            questionScore.child(String.format("%s_%s",
                                    viewModel.getCurrentUser().value!!.userName,
                                    viewModel.getCategoryQuizGame().value))
                    .setValue(QuestionScore(String.format("%s_%s",
                            viewModel.getCurrentUser().value!!.userName,
                            viewModel.getCategoryQuizGame().value),
                            viewModel.getCurrentUser().value!!.userName,
                            score.toString()))

        }

    }
}

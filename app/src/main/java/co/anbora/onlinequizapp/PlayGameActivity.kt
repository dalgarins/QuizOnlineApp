package co.anbora.onlinequizapp

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import co.anbora.onlinequizapp.databinding.ActivityPlayGameBinding
import com.bumptech.glide.Glide
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PlayGameActivity : AppCompatActivity(), View.OnClickListener {

    private val INTERVAL: Long = 1000
    private val TIMEOUT: Long = 7000

    private lateinit var countDown: CountDownTimer

    private var progressValue: Int = 0
    private var index: Int = 0
    private var score: Int = 0
    private var thisQuestion: Int = 0
    private var totalQuestion: Int = 0
    private var correctAnswer: Int = 0

    private lateinit var database: FirebaseDatabase
    private lateinit var questions: DatabaseReference

    private lateinit var binding: ActivityPlayGameBinding

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_play_game)
        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        database = FirebaseDatabase.getInstance()
        questions = database.getReference("Questions")
    }

    override fun onClick(view: View) {

        countDown.cancel()
        if (index < totalQuestion) {
            val clickedButton: Button = view as Button

            if (clickedButton.text.equals(viewModel.getQuestions().value?.get(index)?.correctAnswer)) {

                score += 10
                correctAnswer++
                showQuestion(++index)

            } else {

                val intent: Intent = Intent(this@PlayGameActivity, DoneActivity::class.java)
                val bundle: Bundle = Bundle()
                bundle.putInt("SCORE", score)
                bundle.putInt("TOTAL", totalQuestion)
                bundle.putInt("CORRECT", correctAnswer)
                intent.putExtras(bundle)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun showQuestion(index: Int) {

        if (index < totalQuestion) {
            this.thisQuestion++
            binding.questionText.text = String.format("%d / %d", thisQuestion, totalQuestion)
            binding.progressBar.progress = 0
            progressValue = 0

            if (viewModel.getQuestions().value?.get(index)?.isImageQuestion.equals("true")) {
                Glide.with(applicationContext)
                        .load(viewModel.getQuestions().value?.get(index)?.question)
                        .into(binding.questionImage)
                binding.questionImage.visibility = View.VISIBLE
                binding.questionText.visibility = View.INVISIBLE
            } else {
                binding.questionText.text = viewModel.getQuestions().value?.get(index)?.question

                binding.questionImage.visibility = View.INVISIBLE
                binding.questionText.visibility = View.VISIBLE
            }

            binding.btnAnswerA.text = viewModel.getQuestions().value?.get(index)?.answerA
            binding.btnAnswerB.text = viewModel.getQuestions().value?.get(index)?.answerB
            binding.btnAnswerC.text = viewModel.getQuestions().value?.get(index)?.answerC
            binding.btnAnswerD.text = viewModel.getQuestions().value?.get(index)?.answerD

            countDown.start()
        } else {

            val intent: Intent = Intent(this@PlayGameActivity, DoneActivity::class.java)
            val bundle: Bundle = Bundle()
            bundle.putInt("SCORE", score)
            bundle.putInt("TOTAL", totalQuestion)
            bundle.putInt("CORRECT", correctAnswer)
            intent.putExtras(bundle)
            startActivity(intent)
            finish()

        }

    }

    override fun onResume() {
        super.onResume()

        totalQuestion = viewModel.getQuestions().value!!.size
        countDown = object: CountDownTimer(TIMEOUT, INTERVAL) {
            override fun onFinish() {
                countDown.cancel()
                showQuestion(++index)
            }

            override fun onTick(milisec: Long) {
                binding.progressBar.progress = progressValue
                progressValue++
            }
        }
        showQuestion(index)
    }
}

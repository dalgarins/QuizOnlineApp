package co.anbora.onlinequizapp

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import co.anbora.onlinequizapp.databinding.ActivityPlayGameBinding
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

    }
}

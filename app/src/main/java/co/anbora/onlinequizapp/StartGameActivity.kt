package co.anbora.onlinequizapp

import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.anbora.onlinequizapp.databinding.ActivityStartGameBinding
import co.anbora.onlinequizapp.domain.model.Question
import com.google.firebase.database.*
import java.util.*

class StartGameActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStartGameBinding

    private lateinit var database: FirebaseDatabase
    private lateinit var questions: DatabaseReference

    private lateinit var viewModel: GameViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_start_game)

        database = FirebaseDatabase.getInstance()
        questions = database.getReference("Questions")

        viewModel = ViewModelProviders.of(this).get(GameViewModel::class.java)

        loadQuestions(viewModel.getCategoryQuizGame().value!!)

        binding.btnPlay.setOnClickListener {
            val intent: Intent = Intent(this@StartGameActivity, PlayGameActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun loadQuestions(categoryId: String) {

        viewModel.setQuestions(ArrayList())

        questions.orderByChild("CategoryId").equalTo(categoryId)
                .addValueEventListener(object : ValueEventListener {
                    override fun onCancelled(error: DatabaseError) {

                    }

                    override fun onDataChange(dataSnapshot: DataSnapshot) {

                        var questionList: MutableList<Question> = ArrayList()

                        for (data: DataSnapshot in dataSnapshot.children) {
                            var question: Question? = data.getValue(Question::class.java)
                            questionList.add(question!!)
                        }

                        viewModel.setQuestions(questionList)
                    }
                })

        //Collections.shuffle(viewModel.getQuestions())

    }
}

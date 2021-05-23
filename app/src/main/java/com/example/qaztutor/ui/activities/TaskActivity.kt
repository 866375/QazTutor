package com.example.qaztutor.ui.activities

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import com.bumptech.glide.Glide
import com.example.qaztutor.MainActivity
import com.example.qaztutor.R
import com.example.qaztutor.databinding.ActivityTaskBinding
import com.example.qaztutor.models.QuestionData
import com.example.qaztutor.models.Task
import com.example.qaztutor.models.User
import com.example.qaztutor.network.RetrofitClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TaskActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityTaskBinding
    private lateinit var mActivity: Activity
    private lateinit var mToggle: ActionBarDrawerToggle
    private lateinit var mToolbar: androidx.appcompat.widget.Toolbar

    //private var questionList: ArrayList<QuestionData>? = null
    private lateinit var questionList: ArrayList<QuestionData>
    private var currentPosition: Int = 1
    private var selecedOption: Int = 0
    private var score: Int = 0

    @SuppressLint("WrongConstant")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

        mActivity = this

        setUserName()

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        mBinding.toolBar.navHam.setOnClickListener {
            mBinding.drawerLayout.openDrawer(Gravity.START)
        }

        mBinding.navView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navHome -> {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    finishAffinity()
                    var intent = Intent(mActivity, MainActivity::class.java)
                    intent.putExtra("fragment_id", "home")
                    startActivity(intent)
                }

                R.id.navAccount -> {
                    finishAffinity()
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    var intent = Intent(mActivity, AccountActivity::class.java)
                    startActivity(intent)
                }

                R.id.navCourses -> {
                    mBinding.drawerLayout.closeDrawer(GravityCompat.START)
                    finishAffinity()
                    var intent = Intent(mActivity, MainActivity::class.java)
                    intent.putExtra("fragment_id", "courses")
                    startActivity(intent)
                }

                R.id.navSettings -> Toast.makeText(
                    mActivity,
                    "Settings clicked",
                    Toast.LENGTH_SHORT
                ).show()
            }
            true
        }


        val task = intent.getSerializableExtra("task") as Task
        mBinding.unitTitleTextView.text = task.name

        questionList = ArrayList<QuestionData>()

        RetrofitClient.instance.getQuestions(task.id)
            .enqueue(object : Callback<List<QuestionData>> {
                override fun onResponse(
                    call: Call<List<QuestionData>>, response: Response<List<QuestionData>>
                ) {
                    if (response.isSuccessful) {
                        for (item: QuestionData in response.body()!!) {
                            questionList.add(item)
                        }
                        setQuestion()
                    } else {
                        Toast.makeText(mActivity, response.message(), Toast.LENGTH_SHORT).show()
                    }

                }

                override fun onFailure(call: Call<List<QuestionData>>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })


        //questionList = setData.getQuestion()

        //setQuestion()

        mBinding.card1.setOnClickListener {
            selectedOptionStyle(mBinding.card1, 1)
        }

        mBinding.card2.setOnClickListener {
            selectedOptionStyle(mBinding.card2, 2)
        }

        mBinding.card3.setOnClickListener {
            selectedOptionStyle(mBinding.card3, 3)
        }


        mBinding.card4.setOnClickListener {
            selectedOptionStyle(mBinding.card4, 4)
        }

        mBinding.nextBtn.setOnClickListener {
            if (selecedOption != 0) {
                val question = questionList!![currentPosition - 1]
                if (selecedOption != question.correct_ans) {
                    setColor(selecedOption, R.color.red)
                } else {
                    score++;
                }
                setColor(question.correct_ans, R.color.green)
                if (currentPosition == questionList!!.size) {

                }
//                    submit.text="FINISH"
                else {

                }
//                    submit.text="Go to Next"
            } else {
                currentPosition++
                when {
                    currentPosition <= questionList!!.size -> {
                        setQuestion()
                    }
                    else -> {
                        mBinding.resultLayout.visibility = View.VISIBLE
                        mBinding.totalScoreTextView.text =
                            "${score}" + "/" + "${questionList!!.size}"
                    }
                }
            }
            selecedOption = 0
        }

        mBinding.finishBtn.setOnClickListener {
            val intent = Intent()
            intent.putExtra("quiz_result", "${score} out of ")
            setResult(RESULT_OK, intent)
            finish()
        }
    }


//    private fun setUpAnswersRecyclerView(lessons: ArrayList<Answer>) {
//        val layoutManager = LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false)
//        val mAnswersAdapter = AnswersAdapter(lessons)
//        mBinding.answersRecyclerView.layoutManager = layoutManager
//        mBinding.answersRecyclerView.setHasFixedSize(true)
//        mBinding.answersRecyclerView.adapter = mAnswersAdapter
//
//        mAnswersAdapter.onItemClick = {
//            Toast.makeText(mActivity, it.answer_body + "clicked", Toast.LENGTH_SHORT).show()
//            it.checked = true
//            mAnswersAdapter.notifyDataSetChanged()
//        }
//    }

    private fun setUserName() {
        FirebaseDatabase.getInstance().getReference("Users")
            .child(FirebaseAuth.getInstance().currentUser!!.uid)
            .get().addOnSuccessListener {
                val user = it.getValue(User::class.java)
                mBinding.toolBar.userNameTextView.setText(user!!.name)
            }
    }

    fun setColor(opt: Int, color: Int) {
        when (opt) {
            1 -> {
                mBinding.card1.background = ContextCompat.getDrawable(this, color)
            }
            2 -> {
                mBinding.card2.background = ContextCompat.getDrawable(this, color)
            }
            3 -> {
                mBinding.card3.background = ContextCompat.getDrawable(this, color)
            }
            4 -> {
                mBinding.card4.background = ContextCompat.getDrawable(this, color)
            }
        }
    }

    fun setOptionStyle() {
        mBinding.card1.background = ContextCompat.getDrawable(mActivity, R.color.white)
        Glide.with(mActivity).load(R.drawable.quiz_pick_background).into(mBinding.checkedImageView1)

        mBinding.card2.background = ContextCompat.getDrawable(mActivity, R.color.white)
        Glide.with(mActivity).load(R.drawable.quiz_pick_background).into(mBinding.checkedImageView2)

        mBinding.card3.background = ContextCompat.getDrawable(mActivity, R.color.white)
        Glide.with(mActivity).load(R.drawable.quiz_pick_background).into(mBinding.checkedImageView3)

        mBinding.card4.background = ContextCompat.getDrawable(mActivity, R.color.white)
        Glide.with(mActivity).load(R.drawable.quiz_pick_background).into(mBinding.checkedImageView4)
    }

    fun selectedOptionStyle(view: CardView, opt: Int) {

        setOptionStyle()
        selecedOption = opt
        view.background = ContextCompat.getDrawable(this, R.color.selected_color)
        view.radius = R.dimen.card_radius.toFloat()

        when (opt) {
            1 -> {
                Glide.with(mActivity).load(R.drawable.selected_img).into(mBinding.checkedImageView1)
            }
            2 -> {
                Glide.with(mActivity).load(R.drawable.selected_img).into(mBinding.checkedImageView2)
            }
            3 -> {
                Glide.with(mActivity).load(R.drawable.selected_img).into(mBinding.checkedImageView3)
            }
            4 -> {
                Glide.with(mActivity).load(R.drawable.selected_img).into(mBinding.checkedImageView4)
            }
        }
    }

    fun setQuestion() {
        val question = questionList!![currentPosition - 1]
        setOptionStyle()

        mBinding.progressTextView.text = "${currentPosition}" + "/" + "${questionList!!.size}"

        mBinding.contentTextView.text = question.question
        mBinding.answerBodyTextView1.text = question.option_one
        mBinding.answerBodyTextView2.text = question.option_two
        mBinding.answerBodyTextView3.text = question.option_three
        mBinding.answerBodyTextView4.text = question.option_four

    }
}
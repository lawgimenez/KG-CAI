package com.example.kg_cai;

import static android.os.SystemClock.sleep;
import static com.example.kg_cai.SetsActivity.setsIDs;
import static com.example.kg_cai.SplashActivity.catList;
import static com.example.kg_cai.SplashActivity.selected_cat_index;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.animation.Animator;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.ArrayMap;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.example.kg_cai.helpers.Question;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class QuestionActivity extends AppCompatActivity implements View.OnClickListener {


    private TextView txtTimer, txtQuestion, txtQuesNumber;
    private Button btnOption1, btnOption2, btnOption3, btnOption4;
    private FirebaseFirestore firebaseFirestore;
    public static int setNo;

    private ImageView imgHelp;

    private List<Question> questionList; //from Question class

    private Dialog loadingDialog;

    private int quesNum,score;
    private int hint;

    private CountDownTimer countDownTimer;

    Animation rotateLogoAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        firebaseFirestore = FirebaseFirestore.getInstance();

        loadingDialog = new Dialog(QuestionActivity.this);
        loadingDialog.setContentView(R.layout.loading_progress_bar); //initialize the loading dialog
        loadingDialog.setCancelable(false);
        loadingDialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        loadingDialog.show();

        imgHelp = findViewById(R.id.img_help);

        txtTimer = findViewById(R.id.tvCountdown_question);
        txtQuestion = findViewById(R.id.tvQuestion_question);
        txtQuesNumber = findViewById(R.id.tvQuesNumber_question);

        btnOption1 = findViewById(R.id.btnOption1_question);
        btnOption2 = findViewById(R.id.btnOption2_question);
        btnOption3 = findViewById(R.id.btnOption3_question);
        btnOption4 = findViewById(R.id.btnOption4_question);

        setNo = getIntent().getIntExtra("setNo",1); //it is from setsAdapter class

        btnOption1.setOnClickListener(this);
        btnOption2.setOnClickListener(this);
        btnOption3.setOnClickListener(this);
        btnOption4.setOnClickListener(this);

        questionList = new ArrayList<>();

        rotateLogoAnim();

        imgHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHint();
            }
        });

        getQuestionList();

        score = 0;
    }


    private void getQuestionList() {
        questionList.clear();

        firebaseFirestore.collection("QUIZ").document(catList.get(selected_cat_index).getId())
                .collection(setsIDs.get(setNo)).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        Map<String, QueryDocumentSnapshot> docList = new ArrayMap<>();

                        for(QueryDocumentSnapshot doc : queryDocumentSnapshots)
                        {
                            docList.put(doc.getId(),doc); //pass the id of the QueryDocumentSnapshot loop
                        }

                        QueryDocumentSnapshot quesListDoc  = docList.get("QUESTIONS_LIST");

                        String count = quesListDoc.getString("COUNT"); //get the value of the COUNT in question_list

                        for(int i=0; i < Integer.valueOf(count); i++)
                        {
                            String quesID = quesListDoc.getString("Q" + (i+1) + "_ID"); //get the id of the question in question_list

                            QueryDocumentSnapshot quesDoc = docList.get(quesID);

                            questionList.add(new Question( //add int questionList
                                    quesDoc.getString("QUESTION"),
                                    quesDoc.getString("A"),
                                    quesDoc.getString("B"),
                                    quesDoc.getString("C"),
                                    quesDoc.getString("D"),
                                    Integer.valueOf(quesDoc.getString("ANSWER"))
                            ));

                        }
                        //once the questions are ready it will call the setQuestion method
                        setQuestion();

                        loadingDialog.dismiss();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(QuestionActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                        loadingDialog.dismiss();
                    }
                });

    }

    private void setQuestion() {
        txtTimer.setText(String.valueOf(15)); //timer start from the value 30

        txtQuestion.setText(questionList.get(0).getQuestion()); //assigning the value into their specific palettes
        btnOption1.setText(questionList.get(0).getOptionA());
        btnOption2.setText(questionList.get(0).getOptionB());
        btnOption3.setText(questionList.get(0).getOptionC());
        btnOption4.setText(questionList.get(0).getOptionD());

        txtQuesNumber.setText(String.valueOf(1)+"/"+String.valueOf(questionList.size())); //setting the question number feature


        startTimer();

        quesNum = 0;
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(15000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                txtTimer.setText(String.valueOf(millisUntilFinished/1000)); //setText of what was the remaining time
                if(millisUntilFinished < 10000){
                    txtTimer.setBackgroundResource(R.drawable.round_timer_bg_red);
                }else{
                    txtTimer.setBackgroundResource(R.drawable.round_timer_bg);
                }
            }

            @Override
            public void onFinish() {
                changeQuestion();
            }
        };
        countDownTimer.start(); //start the countdown
    }


    @Override
    public void onClick(View v) {
        int selectedOption = 0;

        switch (v.getId()){
            case R.id.btnOption1_question:
                selectedOption = 1;
                break;

            case R.id.btnOption2_question:
                selectedOption = 2;
                break;

            case R.id.btnOption3_question:
                selectedOption = 3;
                break;

            case R.id.btnOption4_question:
                selectedOption = 4;
                break;

            default:
        }

        countDownTimer.cancel();
        checkAnswer(selectedOption,v);
    }


    private void checkAnswer(int selectedOption, View view) {
        if(selectedOption==questionList.get(quesNum).getAnswer()) //if the selectedOption is same to the answer
        {   //right answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
            score++;
        } else {
            //wrong answer
            ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));

            switch (questionList.get(quesNum).getAnswer()) { //look into the selected button if its correct
                case 1:
                    btnOption1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 2:
                    btnOption2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 3:
                    btnOption3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;
                case 4:
                    btnOption4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                    break;

                default:
            }
        }
        changeQuestion(); //then change the question
    }

    private void getHint() {
        hint++;
        switch (questionList.get(quesNum).getAnswer()) { //look into the selected button if its correct
            case 1:
                btnOption1.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                sleep(1200); //sleep 1 second to load data and open login activity
                break;
            case 2:
                btnOption2.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                sleep(1200); //sleep 1 second to load data and open login activity
                break;
            case 3:
                btnOption3.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                sleep(1200); //sleep 1 second to load data and open login activity
                break;
            case 4:
                btnOption4.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
                sleep(1200); //sleep 1 second to load data and open login activity
                break;
        }
        countDownTimer.cancel();

        if(hint>=1){

            imgHelp.setVisibility(View.INVISIBLE);
            imgHelp.setClickable(false);
        }

        changeQuestion();

    }



    private void changeQuestion() {
        if(quesNum < questionList.size()-1)
        { //if there's question remaining
            quesNum++;

            playAnim(txtQuestion,0,0); //calling animation method
            playAnim(btnOption1,0,1);
            playAnim(btnOption2,0,2);
            playAnim(btnOption3,0,3);
            playAnim(btnOption4,0,4);

            txtQuesNumber.setText(String.valueOf(quesNum+1)+"/"+String.valueOf(questionList.size())); //setting up what number will be in the quiz


            //txtTimer.setText(String.valueOf(60)); //10 seconds timer
            startTimer(); //start the timer again

        }else{
            //if theres no question left
            //go to quiz result activity

            Intent intent = new Intent(QuestionActivity.this, QuizResultActivity.class);
            intent.putExtra("SCORE", String.valueOf(score));
            intent.putExtra("SCORE_OVER", String.valueOf(questionList.size()));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);


        }
    }

    private void playAnim(View view,final int value,int viewNum) //method that plays flipping animation on the buttons
    {
        view.animate().alpha(value).scaleX(value).scaleY(value).setDuration(500)//500 milliseconds to back to its original size
                .setStartDelay(100).setInterpolator(new DecelerateInterpolator()).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(value==0){
                    switch (viewNum){
                        case 0:
                            ((TextView)view).setText(questionList.get(quesNum).getQuestion());
                            break;
                        case 1:
                            ((TextView)view).setText(questionList.get(quesNum).getOptionA());
                            break;
                        case 2:
                            ((TextView)view).setText(questionList.get(quesNum).getOptionB());
                            break;
                        case 3:
                            ((TextView)view).setText(questionList.get(quesNum).getOptionC());
                            break;
                        case 4:
                            ((TextView)view).setText(questionList.get(quesNum).getOptionD());
                            break;
                    }

                    if(viewNum != 0)
                        ((Button)view).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B0B0B0"))); //back to its original color after next question

                    playAnim(view,1,viewNum);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        countDownTimer.cancel();

    }

    private void rotateLogoAnim() {

        rotateLogoAnim = AnimationUtils.loadAnimation(this, R.anim.logoanim);
        imgHelp.startAnimation(rotateLogoAnim);


    }

}
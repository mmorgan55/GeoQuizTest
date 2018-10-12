package edu.cnm.deepdive.geoquiztest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

  private static final String TAG = "MainActivity";
  private static final String KEY_INDEX = "index";

  private Button mTrueButton;
  private Button mFalseButton;
  private ImageButton mNextButton;
  private ImageButton mBackButton;
  private TextView mQuestionTextView;

  private Question[] mQuestionBank = new Question[]{
      new Question(R.string.question_oceania, true),
      new Question(R.string.question_oceans, true),
      new Question(R.string.question_mideast, false),
      new Question(R.string.question_africa, false),
      new Question(R.string.question_americas, true),
      new Question(R.string.question_asia, true),
  };

  private int mCurrentIndex = 0;
  private int quizScore;
  private int questionsAnswered;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    Log.d(TAG, "onCreate(Bundle) called");
    setContentView(R.layout.activity_main);
    quizScore = 0;
    questionsAnswered = 0;

    if (savedInstanceState != null) {
      mCurrentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }

    mQuestionTextView = findViewById(R.id.question_text_view);
    mQuestionTextView.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
      }
    });

    mTrueButton = findViewById(R.id.true_button);
    mTrueButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(true);
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
      }
    });

    mFalseButton = findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
        mTrueButton.setEnabled(false);
        mFalseButton.setEnabled(false);
      }
    });

    mNextButton = findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
      }
    });

    mBackButton = findViewById(R.id.back_button);
    mBackButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        if ((mCurrentIndex - 1) % mQuestionBank.length == -1) {
          mCurrentIndex = mQuestionBank.length - 1;
        } else {
          mCurrentIndex = ((mCurrentIndex - 1) % mQuestionBank.length);
        }

        updateQuestion();
        mTrueButton.setEnabled(true);
        mFalseButton.setEnabled(true);
      }
    });

    updateQuestion();
  }

  @Override
  protected void onStart() {
    super.onStart();
    Log.d(TAG, "onStart() called");

  }

  @Override
  protected void onStop() {
    super.onStop();
    Log.d(TAG, "onStop() called");
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    Log.d(TAG, "onDestroy() called");
  }

  @Override
  protected void onPause() {
    super.onPause();
    Log.d(TAG, "onPause() called");
  }

  @Override
  protected void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstanceState");
    savedInstanceState.putInt(KEY_INDEX, mCurrentIndex);
  }

  @Override
  protected void onResume() {
    super.onResume();
    Log.d(TAG, "onResume() called");
  }

  private void updateQuestion() {
    int question = mQuestionBank[mCurrentIndex].getmTextResId();
    mQuestionTextView.setText(question);
  }

  private void checkAnswer(boolean userPressedTrue) {
    boolean answerIsTrue = mQuestionBank[mCurrentIndex].ismAnswerTrue();
    int messageResId;
    if (userPressedTrue == answerIsTrue) {
      messageResId = R.string.correct_toast;
      quizScore++;
      questionsAnswered++;
    } else {
      messageResId = R.string.incorrect_toast;
      questionsAnswered++;
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();

    if (questionsAnswered == 6) {
      Toast.makeText(this,
                     "You got " + quizScore + "/" + questionsAnswered + " correct!",
                     Toast.LENGTH_SHORT).show();
    }
  }
}

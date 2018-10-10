package edu.cnm.deepdive.geoquiztest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

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

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

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
      }
    });

    mFalseButton = findViewById(R.id.false_button);
    mFalseButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        checkAnswer(false);
      }
    });

    mNextButton = findViewById(R.id.next_button);
    mNextButton.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        mCurrentIndex = (mCurrentIndex + 1) % mQuestionBank.length;
        updateQuestion();
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
      }
    });

    updateQuestion();
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
    } else {
      messageResId = R.string.incorrect_toast;
    }

    Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
  }
}

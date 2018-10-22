package ru.vavtech.septemberworkout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import ru.vavtech.septemberworkout.Model.Workout;
import ru.vavtech.septemberworkout.Model.WorkoutList;
import ru.vavtech.septemberworkout.R;
import ru.vavtech.septemberworkout.utils.Constants;

public class WorkoutDetailActivity extends AppCompatActivity {
    private TextView title;
    private TextView recordDate;
    private TextView recordRepsCount;
    private TextView recordWeight;
    private TextView description;
    private TextView weight;
    private ImageView image;
    private SeekBar weightSeekBar;
    private EditText repsCountEditText;
    private Button saveRecordButton;
    private ImageButton shareButton;
    Workout workout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);

        Intent intent = getIntent();
        int index = intent.getIntExtra(Constants.WORKOUT_INDEX, 0);
        workout = WorkoutList.getInstance().getWorkouts().get(index);

//        Workout workout = WorkoutList
//                .getInstance()
//                .getWorkouts()
//                .get(getIntent()
//                        .getIntExtra("workout_index",0));

        initGUI(workout);
        addListeners();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title.getText().toString());
        outState.putString("description", description.getText().toString());
        outState.putInt("recordRepsCount", Integer.parseInt(recordRepsCount.getText().toString()));
        outState.putString("recordDate", recordDate.getText().toString());
        outState.putInt("recordWeight", Integer.parseInt(recordWeight.getText().toString()));
        Toast.makeText(this, "onSaveInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        Toast.makeText(WorkoutDetailActivity.this, "onStart", Toast.LENGTH_SHORT).show();
        super.onStart();
    }

    @Override
    protected void onResume() {
        Toast.makeText(WorkoutDetailActivity.this, "onResume", Toast.LENGTH_SHORT).show();
        super.onResume();
    }

    @Override
    protected void onPause() {
        Toast.makeText(WorkoutDetailActivity.this, "onPause", Toast.LENGTH_SHORT).show();
        super.onPause();
    }

    @Override
    protected void onStop() {
        Toast.makeText(WorkoutDetailActivity.this, "onStop", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Toast.makeText(WorkoutDetailActivity.this, "onRestart", Toast.LENGTH_SHORT).show();
        super.onRestart();
    }

    @Override
    protected void onDestroy() {
        Toast.makeText(WorkoutDetailActivity.this, "onDestroy", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        Toast.makeText(this, "onRestoreInstanceState", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_share:
                Toast.makeText(WorkoutDetailActivity.this, "Поделиться", Toast.LENGTH_SHORT).show();
                shareRecord();
                return true;
            case R.id.action_settings:
                return true;
            case R.id.action_quit:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void shareRecord() {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Сообщение");
        sendIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(sendIntent);
        }
    }

    private void addListeners() {
        weightSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                weight.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(WorkoutDetailActivity.this, "Поделиться", Toast.LENGTH_SHORT).show();
                shareRecord();
            }
        });

        saveRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!repsCountEditText.getText().toString().isEmpty()) {
                    if ((Integer.parseInt(recordWeight.getText().toString()) * Integer.parseInt(recordRepsCount.getText().toString())) <
                            (weightSeekBar.getProgress() * Integer.parseInt(repsCountEditText.getText().toString()))) {
                        workout.setRecordWeight(weightSeekBar.getProgress());
                        workout.setRecordRepsCount(Integer.valueOf(repsCountEditText.getText().toString()));
                        workout.setRecordDate(new Date());
                        initGUI(workout);

                    }
                }
            }
        });
    }

    private void initGUI(Workout workout) {
        title = findViewById(R.id.workout_detail_title);
        title.setText(workout.getTitle());
        recordDate = findViewById(R.id.workout_detail_record_date);
        recordDate.setText(workout.getFormattedRecordDate());
        recordRepsCount = findViewById(R.id.workout_detail_record_reps_count);
        recordRepsCount.setText(String.valueOf(workout.getRecordRepsCount()));
        recordWeight = findViewById(R.id.workout_detail_record_weight);
        recordWeight.setText(String.valueOf(workout.getRecordWeight()));
        description = findViewById(R.id.workout_detail_description);
        description.setText(workout.getDescription());

        weight = findViewById(R.id.workout_detail_weight);
        weightSeekBar = findViewById(R.id.workout_detail_weight_seek_bar);
        repsCountEditText = findViewById(R.id.workout_detail_reps_count_edit_text);

        saveRecordButton = findViewById(R.id.workout_detail_save_button);
        shareButton = findViewById(R.id.button_share);
    }
}

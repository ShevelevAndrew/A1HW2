package ru.vavtech.septemberworkout.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.ShareActionProvider;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Date;

import ru.vavtech.septemberworkout.Model.Workout;
import ru.vavtech.septemberworkout.R;

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

    private ShareActionProvider mShareActionProvider;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_detail);
        Workout workout = new Workout("Подтягивания", "Подтягивания на перекладине", 0, new Date(), 0);
        initGUI(workout);
        addListeners();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
          getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_share);


        return true;
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

        saveRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!repsCountEditText.getText().toString().isEmpty()) {
                    if ((Integer.parseInt(recordWeight.getText().toString()) * Integer.parseInt(recordRepsCount.getText().toString())) <
                            (weightSeekBar.getProgress() * Integer.parseInt(repsCountEditText.getText().toString()))) {
                        Workout workoutNewRec = new Workout("Жим", "Жим лежа",
                                Integer.parseInt(repsCountEditText.getText().toString()), new Date(),
                                weightSeekBar.getProgress());
                        initGUI(workoutNewRec);
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

        final String[] data = {getString(R.string.pulling_up), getString(R.string.squat), getString(R.string.barbell_bench_press)};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        final Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        int indSpiner =  Integer.parseInt(intent.getStringExtra("workout"));
        spinner.setSelection(indSpiner);
        spinner.setPrompt(getString(R.string.сhoose_exercise));

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                title.setText(data[position]);
                // Toast.makeText(getBaseContext(), "Position = " + data[position], Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });




    }


}

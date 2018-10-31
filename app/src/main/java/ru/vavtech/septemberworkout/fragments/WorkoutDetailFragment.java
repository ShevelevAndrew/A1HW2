package ru.vavtech.septemberworkout.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import java.util.Date;

import ru.vavtech.septemberworkout.Model.Workout;
import ru.vavtech.septemberworkout.Model.WorkoutList;
import ru.vavtech.septemberworkout.R;

public class WorkoutDetailFragment extends Fragment {
    private static final String WORKOUT_INDEX = "workoutIndex";
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

    public static WorkoutDetailFragment initFragment(int workoutIndex) {
        WorkoutDetailFragment fragment = new WorkoutDetailFragment();
        Bundle arguments = new Bundle();

        arguments.putInt(WORKOUT_INDEX, workoutIndex);
        fragment.setArguments(arguments);
        return fragment;

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_workout_detail, container, false);
        workout = WorkoutList.getInstance().getWorkouts().get(getArguments().getInt(WORKOUT_INDEX));
        initGUI(root, workout);
        addListeners();

        return root;
    }


//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        outState.putString("title", title.getText().toString());
//        outState.putString("description", description.getText().toString());
//        outState.putInt("recordRepsCount", Integer.parseInt(recordRepsCount.getText().toString()));
//        outState.putString("recordDate", recordDate.getText().toString());
//        outState.putInt("recordWeight", Integer.parseInt(recordWeight.getText().toString()));
//        Toast.makeText(getContext(), "onSaveInstanceState", Toast.LENGTH_SHORT).show();
//    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.action_share:
//                shareRecord();
//                return true;
//            case R.id.action_settings:
//                return true;
//            case R.id.action_quit:
//                onBackPressed();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }

    private void shareRecord() {
        // Create the text message with a string
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, "Сообщение");
        sendIntent.setType("text/plain");

        // Verify that the intent will resolve to an activity
        if (sendIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
                YoYo.with(Techniques
                        .RubberBand)
                        .duration(700)
                        .repeat(2)
                        .playOn(shareButton);
               // shareRecord();
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
                        initGUI(v, workout);

                    }
                }
            }
        });
    }

    private void initGUI(View view, Workout workout) {
        title = view.findViewById(R.id.workout_detail_title);
        title.setText(workout.getTitle());
        recordDate = view.findViewById(R.id.workout_detail_record_date);
        recordDate.setText(workout.getFormattedRecordDate());
        recordRepsCount = view.findViewById(R.id.workout_detail_record_reps_count);
        recordRepsCount.setText(String.valueOf(workout.getRecordRepsCount()));
        recordWeight = view.findViewById(R.id.workout_detail_record_weight);
        recordWeight.setText(String.valueOf(workout.getRecordWeight()));
        description = view.findViewById(R.id.workout_detail_description);
        description.setText(workout.getDescription());

        weight = view.findViewById(R.id.workout_detail_weight);
        weightSeekBar = view.findViewById(R.id.workout_detail_weight_seek_bar);
        repsCountEditText = view.findViewById(R.id.workout_detail_reps_count_edit_text);

        saveRecordButton = view.findViewById(R.id.workout_detail_save_button);
        shareButton = view.findViewById(R.id.button_share);
    }
}

package ru.vavtech.septemberworkout.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ru.vavtech.septemberworkout.Model.Workout;
import ru.vavtech.septemberworkout.R;
import ru.vavtech.septemberworkout.interfaces.OnListItemClickListener;
import ru.vavtech.septemberworkout.utils.CircleTransform;

class WorkoutViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView description;
    private TextView recordDate;
    private TextView recordRepsCount;
    private TextView recordWeight;
    private CardView cardView;
    private ImageView imageView;

    public WorkoutViewHolder(@NonNull final View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.list_item_title_text_view);
        description = itemView.findViewById(R.id.list_item_deskription_text_view);
        recordDate = itemView.findViewById(R.id.list_item_record_date);
        recordRepsCount = itemView.findViewById(R.id.list_item_record_reps_count);
        recordWeight = itemView.findViewById(R.id.list_item_record_weight);
        cardView = itemView.findViewById(R.id.cardView);
        imageView = itemView.findViewById(R.id.list_item_image_view);
    }

    public void bindView(Workout workout, final int index, final OnListItemClickListener listener) {
        title.setText(workout.getTitle());
        description.setText(workout.getDescription());
        recordWeight.setText(String.valueOf(workout.getRecordWeight()));
        recordRepsCount.setText(String.valueOf(workout.getRecordRepsCount()));
        recordDate.setText(workout.getFormattedRecordDate());

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onListItemClickListener(index);
            }
        });
        Picasso
                .get()
                .load("http://i.imgur.com/DvpvklR.png")
                .fit()
                .transform(new CircleTransform())
                .into(imageView);
    }
}


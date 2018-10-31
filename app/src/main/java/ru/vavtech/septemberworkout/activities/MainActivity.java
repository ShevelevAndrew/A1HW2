package ru.vavtech.septemberworkout.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import ru.vavtech.septemberworkout.R;
import ru.vavtech.septemberworkout.fragments.WorkoutDetailFragment;
import ru.vavtech.septemberworkout.fragments.WorkoutListFragment;
import ru.vavtech.septemberworkout.interfaces.OnListItemClickListener;

public class MainActivity extends AppCompatActivity implements OnListItemClickListener {
    FragmentManager fragmentManager;
    WorkoutListFragment listFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listFragment = new WorkoutListFragment();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            transaction.replace(R.id.container, listFragment);
            transaction.commit();
        } else {
            WorkoutDetailFragment detailFragment = WorkoutDetailFragment.initFragment(0);
            transaction.add(R.id.list_container, listFragment);
            transaction.add(R.id.detail_container, detailFragment);
            transaction.commit();
        }


    }

    @Override
    public void onListItemClickListener(int index) {
        WorkoutDetailFragment detailFragment = WorkoutDetailFragment.initFragment(index);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            fragmentManager
                    .beginTransaction()
                    .add(R.id.container, detailFragment)
                    .addToBackStack("")
                    .commit();
        } else {
            fragmentManager.beginTransaction()
                    .replace(R.id.list_container, listFragment)
                    .replace(R.id.detail_container, detailFragment)
                    .commit();

        }
    }
}

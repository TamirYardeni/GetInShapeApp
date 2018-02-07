package tmrapps.getinshapeapp.Exercise;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseDetailsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseDetailsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseDetailsFragment extends Fragment {

    private static final String EXERCISE_ID = "id";

    private String exerciseId;

    private OnFragmentInteractionListener mListener;

    private ExerciseViewModel exerciseViewModel;

    private View detailsView;

    public ExerciseDetailsFragment() {
        // Required empty public constructor
    }

    public static ExerciseDetailsFragment newInstance(String param1) {
        ExerciseDetailsFragment fragment = new ExerciseDetailsFragment();
        Bundle args = new Bundle();
        args.putString(EXERCISE_ID, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            exerciseId = getArguments().getString(EXERCISE_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        this.detailsView = inflater.inflate(R.layout.fragment_exercise_details, container, false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.exerciseDetailsHeader);
        exerciseViewModel.getExerciseById(this.exerciseId).observe(this, (exerciseDetails) -> {
            if (exerciseDetails != null) {
                this.showDetailsView(exerciseDetails);
                String url = exerciseDetails.getUrl();
                if (url != null) {
                    exerciseViewModel.getExerciseImage(exerciseDetails.getUrl()).observe(this, (bitmap) -> {
                        if (bitmap != null) {
                            this.showExerciseImageView(bitmap);
                        }

                        //progressBar.setVisibility(View.GONE);
                    });
                }
            }

            //progressBar.setVisibility(View.GONE);
        });

        return this.detailsView;
    }

    private void showDetailsView(Exercise exercise) {
        TextView exerciseName = (TextView)this.detailsView.findViewById(R.id.exerciseNameTxt);
        TextView exerciseData = (TextView)this.detailsView.findViewById(R.id.exerciseDataTxt);
        TextView exerciseNotes = (TextView)this.detailsView.findViewById(R.id.exerciseNotesTxt);
        //view.findViewById(R.id.exerciseImage);

        //Exercise exercise = getExerciseDetails();

        exerciseName.setText(exercise.getName());
        exerciseData.setText(exercise.getData());
        exerciseNotes.setText(exercise.getNote());
    }

    public void showExerciseImageView(Bitmap bitmap) {
        ImageView exerciseImage = (ImageView)this.detailsView.findViewById(R.id.exerciseImage);
        exerciseImage.setImageBitmap(bitmap);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }

        this.exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /*public Exercise getExerciseDetails() {
        Exercise exercise = new Exercise();
        String name = "כפיפות בטן בישיבה";
        String data = "1. שב זקוף עם הגב לקיר \n 2.עשה כפיפת בטן \n עלה חזרה עד למעלה";
        String note = "אל תשכח לנשום";
        exercise.setName(name);
        exercise.setData(data);
        exercise.setNotes(note);

        return exercise;
    }*/

    public interface OnFragmentInteractionListener {

    }
}

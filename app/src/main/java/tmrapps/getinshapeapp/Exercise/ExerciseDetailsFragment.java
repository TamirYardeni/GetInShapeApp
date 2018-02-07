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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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

    private ProgressBar progressBarOfImage;

    private View detailsView;

    private TextView exerciseName;

    private TextView exerciseData;

    private TextView exerciseNotes;

    private ImageView exerciseImage;

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
        this.exerciseName = (TextView)this.detailsView.findViewById(R.id.exerciseNameTxt);
        this.exerciseData = (TextView)this.detailsView.findViewById(R.id.exerciseDataTxt);
        this.exerciseNotes = (TextView)this.detailsView.findViewById(R.id.exerciseNotesTxt);
        this.exerciseImage = (ImageView) this.detailsView.findViewById(R.id.exerciseImage);
        clearViewElements();
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.exerciseDetailsHeader);
        this.progressBarOfImage = this.detailsView.findViewById(R.id.progressBarOfImage);
        this.progressBarOfImage.setVisibility(View.VISIBLE);
        exerciseViewModel.getExerciseById(this.exerciseId).observe(this, (exerciseDetails) -> {
            if (exerciseDetails != null) {
                this.showDetailsView(exerciseDetails);
                String url = exerciseDetails.getUrl();
                if (url != null) {
                    exerciseViewModel.getExerciseImage(exerciseDetails.getUrl()).observe(this, (bitmap) -> {
                        if (bitmap != null) {
                            this.showExerciseImageView(bitmap);
                        } else {
                            Toast.makeText(getActivity(), R.string.imageNotFound,
                                    Toast.LENGTH_LONG).show();
                        }

                        this.progressBarOfImage.setVisibility(View.GONE);
                    });
                }
                else {
                    this.progressBarOfImage.setVisibility(View.GONE);

                }
            }
        });

        return this.detailsView;
    }

    private void showDetailsView(Exercise exercise) {
        this.exerciseName.setText("שם התרגיל: " + exercise.getName());
        this.exerciseData.setText("תוכן התרגיל: " + exercise.getData());
        this.exerciseNotes.setText("הערות: " + exercise.getNote());
    }

    public void showExerciseImageView(Bitmap bitmap) {
        this.exerciseImage.setImageBitmap(bitmap);
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
        clearViewElements();
    }

    /**
     * Clears the exercise details text and bitmap because this view
     * Open again and should show different exercise details
     */
    private void clearViewElements() {
        this.exerciseName.setText("");
        this.exerciseData.setText("");
        this.exerciseNotes.setText("");
        this.exerciseImage.setImageDrawable(null);
        this.exerciseImage.setImageBitmap(null);
        this.exerciseImage.setImageResource(0);
    }

    public interface OnFragmentInteractionListener {

    }
}

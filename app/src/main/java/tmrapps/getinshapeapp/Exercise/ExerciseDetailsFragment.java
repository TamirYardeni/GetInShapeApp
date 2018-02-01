package tmrapps.getinshapeapp.Exercise;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    // TODO: Rename and change types of parameters
    private String exerciseId;

    private OnFragmentInteractionListener mListener;

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
        View view = inflater.inflate(R.layout.fragment_exercise_details, container, false);

        TextView exerciseName = (TextView)view.findViewById(R.id.exerciseNameTxt);
        TextView exerciseData = (TextView)view.findViewById(R.id.exerciseDataTxt);
        TextView exerciseNotes = (TextView)view.findViewById(R.id.exerciseNotesTxt);
        //view.findViewById(R.id.exerciseImage);

        Exercise exercise = getExerciseDetails();

        exerciseName.setText(exercise.getName());
        exerciseData.setText(exercise.getData());
        exerciseNotes.setText(exercise.getNotes());

        return view;
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
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public Exercise getExerciseDetails() {
        Exercise exercise = new Exercise();
        String name = "כפיפות בטן בישיבה";
        String data = "1. שב זקוף עם הגב לקיר \n 2.עשה כפיפת בטן \n עלה חזרה עד למעלה";
        String note = "אל תשכח לנשום";
        exercise.setName(name);
        exercise.setData(data);
        exercise.setNotes(note);

        return exercise;
    }

    public interface OnFragmentInteractionListener {

    }
}

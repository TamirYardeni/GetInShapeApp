package tmrapps.getinshapeapp.Exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseFragment extends Fragment {

    private static final String ROLE_TYPE= "roleType";

    private RoleType role;

    private ExerciseFragment.OnFragmentInteractionListener mListener;

    private ExerciseFragment.ExerciseAdapter exercisesAdapter;

    private ListView list;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    public static ExerciseFragment newInstance(String param1) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ROLE_TYPE, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String roleName = getArguments().getString(ROLE_TYPE);
            role = RoleType.valueOf(roleName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exerciseListView = inflater.inflate(R.layout.fragment_exercise, container, false);

        list = exerciseListView.findViewById(R.id.exerciseList);
        exercisesAdapter = new ExerciseFragment.ExerciseAdapter(this);
        list.setAdapter(exercisesAdapter);

        FloatingActionButton addExerciseBtn = exerciseListView.findViewById(R.id.addExerciseBtn);
        if (role == RoleType.USER) {
            addExerciseBtn.hide();
        } else if (role == RoleType.ADMIN) {
            addExerciseBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.onShowCreateExercise("1");
                }
            });
        }

        return exerciseListView;
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

    public void showExerciseDetails(String exerciseId){
        mListener.onShowExerciseDetails(exerciseId);
    }

    public interface OnFragmentInteractionListener {
        void onShowExerciseDetails(String id);
        void onShowCreateExercise(String categoryId);
    }

    class ExerciseAdapter extends BaseAdapter {

        ExerciseFragment exerciseFrag;
        List<Exercise> data = new LinkedList<>();

        public ExerciseAdapter(ExerciseFragment fragment) {
            data.add(new Exercise());
            data.add(new Exercise());
            data.add(new Exercise());
            data.add(new Exercise());
            data.add(new Exercise());

            this.exerciseFrag = fragment;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Exercise getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            Exercise exercise = data.get(i);

            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.category_row, null);

                TextView exerciseName = view.findViewById(R.id.categoryTxt);
                exerciseName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int index =(int)view.getTag();
                        exerciseFrag.showExerciseDetails(data.get(index).getId());
                    }
                });
            }

            TextView exerciseName = view.findViewById(R.id.categoryTxt);
            /*exerciseName.setText(exercise.getName());*/
            exerciseName.setText(i + "תרגיל");
            exerciseName.setTag(i);

            return view;
        }
    }
}

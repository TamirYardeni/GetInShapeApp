package tmrapps.getinshapeapp.ExerciseList;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Exercise.ExerciseViewModel;
import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseInCategory;
import tmrapps.getinshapeapp.ExerciseList.Model.ExerciseListItem;
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

    private static final String CATEGORY_ID= "categoryId";

    private static final String CATEGORY_NAME= "categoryName";


    private RoleType role;

    private String categoryId;

    private ExerciseFragment.OnFragmentInteractionListener mListener;

    private ExerciseListViewModel exerciseListViewModel;

    private ExerciseViewModel exerciseViewModel;

    private ExerciseFragment.ExerciseAdapter exercisesAdapter;

    private ListView list;

    private ProgressBar progressBar;
    private String categoryName;

    public ExerciseFragment() {
        // Required empty public constructor
    }

    public static ExerciseFragment newInstance(String param1, String param2, String param3) {
        ExerciseFragment fragment = new ExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ROLE_TYPE, param1);
        args.putString(CATEGORY_ID, param2);
        args.putString(CATEGORY_NAME, param3);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String roleName = getArguments().getString(ROLE_TYPE);
            this.categoryId = getArguments().getString(CATEGORY_ID);
            this.categoryName = getArguments().getString(CATEGORY_NAME);
            role = RoleType.valueOf(roleName);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exerciseListView = inflater.inflate(R.layout.fragment_exercise, container, false);

        progressBar = exerciseListView.findViewById(R.id.progressBarExerciseList);
        progressBar.setVisibility(View.VISIBLE);

        /*this.exerciseListViewModel.getExerciseOfCategory(this.categoryId).observe(this, (exerciseListItems) -> {
            if (exerciseListItems != null) {
                this.exercisesAdapter.data = exerciseListItems.getExercisesInCategory();

            } else {
                // In case there is no exercises for this category
                this.exercisesAdapter.data = new ArrayList<>();
            }
            this.exercisesAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });*/

        exerciseViewModel.getExerciseByCategory(this.categoryId).observe(this, (exerciseListItems) -> {
            if (exerciseListItems != null) {
                this.exercisesAdapter.data = exerciseListItems;

            } else {
                // In case there is no exercises for this category
                this.exercisesAdapter.data = new ArrayList<>();
            }
            this.exercisesAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.GONE);
        });

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
                    mListener.onShowCreateExercise(categoryId, categoryName);
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

        //this.exerciseListViewModel = ViewModelProviders.of(this).get(ExerciseListViewModel.class);
        this.exerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        /*this.exerciseViewModel.getAll();*/
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
        void onShowCreateExercise(String categoryId, String categoryName);
    }

    class ExerciseAdapter extends BaseAdapter {

        ExerciseFragment exerciseFrag;
        List<ExerciseInCategory> data = new LinkedList<>();

        public ExerciseAdapter(ExerciseFragment fragment) {
            this.exerciseFrag = fragment;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public ExerciseInCategory getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            ExerciseInCategory exerciseInCategory = data.get(i);

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
            exerciseName.setText(exerciseInCategory.getName());
            exerciseName.setTag(i);

            return view;
        }
    }
}

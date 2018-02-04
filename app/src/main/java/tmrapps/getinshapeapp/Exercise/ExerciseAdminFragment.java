package tmrapps.getinshapeapp.Exercise;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
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

import tmrapps.getinshapeapp.Category.Model.AddCategoryDialog;
import tmrapps.getinshapeapp.Category.Model.Category;
//import tmrapps.getinshapeapp.Exercise.Model.DBexercise;
//import tmrapps.getinshapeapp.Exercise.Model.ModelSql;
import tmrapps.getinshapeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ExerciseAdminFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ExerciseAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExerciseAdminFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private ExerciseAdminFragment.ExerciseAdminAdapter categoriesAdapter;

    public ExerciseAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ExercieAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ExerciseAdminFragment newInstance(String param1, String param2) {
        ExerciseAdminFragment fragment = new ExerciseAdminFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View exerciseView = inflater.inflate(R.layout.fragment_exercise_admin, container, false);

        ListView list = exerciseView.findViewById(R.id.categoryListAdmin);
        categoriesAdapter = new ExerciseAdminFragment.ExerciseAdminAdapter(this);
        list.setAdapter(categoriesAdapter);

        FloatingActionButton addCategoryBtn = exerciseView.findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryDialog newFragment = new
                        AddCategoryDialog();
                newFragment.show(ExerciseAdminFragment.this.getActivity().getSupportFragmentManager(),
                        "TAG");
            }
        });

        // Inflate the layout for this fragment
        return exerciseView;
    }

    public void showCategory(String categoryId) {
        mListener.onShowCategory(categoryId);
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

    public interface OnFragmentInteractionListener {
        void onShowCategory(String categoryId);
    }

    class ExerciseAdminAdapter extends BaseAdapter {

        ExerciseAdminFragment exerciseFrag;
        List<Category> data = new LinkedList<>();

        public ExerciseAdminAdapter(ExerciseAdminFragment fragment) {
            this.exerciseFrag = fragment;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Category getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.category_row, null);
                TextView categoryName = view.findViewById(R.id.categoryTxt);
                final Category cat = data.get(i);
                categoryName.setText(cat.getName());
                categoryName.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        exerciseFrag.showCategory(cat.getId());
                    }
                });
            }

            return view;
        }
    }
}

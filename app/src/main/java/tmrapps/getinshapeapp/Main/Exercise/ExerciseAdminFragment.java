package tmrapps.getinshapeapp.Main.Exercise;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Category.Model.AddCategoryDialog;
import tmrapps.getinshapeapp.Category.Model.Category;
import tmrapps.getinshapeapp.Main.Motivation.Model.Motivation;
import tmrapps.getinshapeapp.Main.Motivation.Model.MotivationRepository;
import tmrapps.getinshapeapp.Main.Motivation.MotivationFragment;
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
        ExerciseAdminFragment.ExerciseAdminAdapter myAdapter = new ExerciseAdminFragment.ExerciseAdminAdapter();
        list.setAdapter(myAdapter);

        FloatingActionButton addCategoryBtn = exerciseView.findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogFragment newFragment = new
                        AddCategoryDialog();
                newFragment.show(ExerciseAdminFragment.this.getActivity().getSupportFragmentManager(),
                        "TAG");
            }
        });
        // Inflate the layout for this fragment
        return exerciseView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    class ExerciseAdminAdapter extends BaseAdapter {

        List<Category> data = new LinkedList<>();

        public ExerciseAdminAdapter() {
            for (int i = 0; i < 8; i++) {
                data.add(new Category("ידיים"));
            }
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
                Category cat = data.get(i);
                categoryName.setText(cat.getName());
                return view;
            }

            return view;
        }
    }
}

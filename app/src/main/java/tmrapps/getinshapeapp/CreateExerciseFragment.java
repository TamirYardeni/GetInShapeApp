package tmrapps.getinshapeapp;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


public class CreateExerciseFragment extends Fragment {

    private static final String ARG_PARAM_CATEGORY = "categoryId";

    private String categoryId;

    private OnFragmentInteractionListener mListener;

    public CreateExerciseFragment() {
        // Required empty public constructor
    }

    public static CreateExerciseFragment newInstance(String param1) {
        CreateExerciseFragment fragment = new CreateExerciseFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM_CATEGORY, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            categoryId = getArguments().getString(ARG_PARAM_CATEGORY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_exercise, container, false);

        TextView textCategoryName = view.findViewById(R.id.textCategoryName);
        textCategoryName.setText("בטן");

        Button imageBtn = view.findViewById(R.id.chooseImgBtn);
        imageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        Button createNewExercise = view.findViewById(R.id.createNewExerciseBtn);
        createNewExercise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

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

    public interface OnFragmentInteractionListener {

    }
}

package tmrapps.getinshapeapp.Main;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;

import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.Model.User;
import tmrapps.getinshapeapp.User.UserViewModel;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MainUserFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MainUserFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainUserFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private UserViewModel userViewModel;

    private User user;

    private View mainView;

    private ProgressBar progressBar;

    public MainUserFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainUserFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainUserFragment newInstance(String param1, String param2) {
        MainUserFragment fragment = new MainUserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_main_user, container, false);

        progressBar = mainView.findViewById(R.id.mainProgressBar);
        progressBar.setVisibility(View.VISIBLE);

        hideAllUIElements();
        
        return mainView;
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

        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        userViewModel.getUser().observe(this, (user) -> {
            this.user = user;
            if (user.getRoleType() == 0) {
                showUserView();
            } else if (user.getRoleType() == 1) {
                showAdminView();
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void showUserView() {
        Button personalAreaBtn = (Button) mainView.findViewById(R.id.personalAreaBtn);
        Button exerciseBtn = (Button) mainView.findViewById(R.id.exerciseBtn);
        Button motivationBtn = (Button) mainView.findViewById(R.id.motivationBtn);
        LinearLayout layout = mainView.findViewById(R.id.userLayout);
        layout.setVisibility(View.VISIBLE);

        personalAreaBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShowPersonalArea();
                }
            }
        });

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShowExercise();
                }
            }
        });

        motivationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onShowMotivation();
                }
            }
        });

        // Hide the progress bar
        progressBar.setVisibility(View.GONE);
    }

    private void showAdminView() {
        // Hide the progress bar
        progressBar.setVisibility(View.GONE);

        LinearLayout adminLayout = mainView.findViewById(R.id.adminLayout);
        adminLayout.setVisibility(View.VISIBLE);

        Button exerciseBtn = (Button) mainView.findViewById(R.id.exerciseAdminBtn);
        Button motivationBtn = (Button) mainView.findViewById(R.id.motivationAdminBtn);

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onShowExerciseAdmin();
                }
            }
        });

        motivationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onShowMotivationAdmin();
                }
            }
        });
    }

    private void hideAllUIElements() {
        LinearLayout userLayout = mainView.findViewById(R.id.userLayout);
        LinearLayout adminLayout = mainView.findViewById(R.id.adminLayout);
        userLayout.setVisibility(View.GONE);
        adminLayout.setVisibility(View.GONE);
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

        // Note for the main activity that the personal area button was clicked
        void onShowPersonalArea();

        // Note for the main activity that the exercise button was clicked
        void onShowExercise();

        // Note for the main activity that the motivation button was clicked
        void onShowMotivation();

        // Note for the main activity that the manage exercises button was clicked
        void onShowExerciseAdmin();

        // Note for the main activity that the manage motivation button was clicked
        void onShowMotivationAdmin();
    }
}

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
import tmrapps.getinshapeapp.User.RoleType;
import tmrapps.getinshapeapp.User.UserViewModel;

/**
 * This is the main fragment
 * It has 2 different layouts that appears according to the permissions yhe user have.
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

    public static MainUserFragment newInstance(String param1, String param2) {
        MainUserFragment fragment = new MainUserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mainView = inflater.inflate(R.layout.fragment_main_user, container, false);

        // Show spinner because we want to get user from firebase db
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

        // Create viewModel instance
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);

        // Get the suer from the viewModel
        userViewModel.getUser().observe(this, (user) -> {
            this.user = user;

            // Show layout according to the permission
            if (user.getRoleType() == 0) {
                showUserView();
            } else if (user.getRoleType() == 1) {
                showAdminView();
            }

            mListener.onHasRoleType(this.user);
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * Make the regular user view elements visible
     */
    private void showUserView() {
        Button personalAreaBtn = (Button) mainView.findViewById(R.id.personalAreaBtn);
        Button exerciseBtn = (Button) mainView.findViewById(R.id.exerciseBtn);
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

        // Hide the progress bar
        progressBar.setVisibility(View.GONE);
    }

    /**
     * Make the admin view elements visible
     */
    private void showAdminView() {
        // Hide the progress bar
        progressBar.setVisibility(View.GONE);

        LinearLayout adminLayout = mainView.findViewById(R.id.adminLayout);
        adminLayout.setVisibility(View.VISIBLE);

        Button exerciseBtn = (Button) mainView.findViewById(R.id.exerciseAdminBtn);

        exerciseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener != null){
                    mListener.onShowExerciseAdmin();
                }
            }
        });
    }

    /**
     * Hide all the elements in the fragment because there are no permissions yet
     * So we dont know wich view to show.
     */
    private void hideAllUIElements() {
        LinearLayout userLayout = mainView.findViewById(R.id.userLayout);
        LinearLayout adminLayout = mainView.findViewById(R.id.adminLayout);
        userLayout.setVisibility(View.GONE);
        adminLayout.setVisibility(View.GONE);
    }

    public interface OnFragmentInteractionListener {

        // Note for the main activity that the personal area button was clicked
        void onShowPersonalArea();

        // Note for the main activity that the exercise button was clicked
        void onShowExercise();

        // Note for the main activity that the manage exercises button was clicked
        void onShowExerciseAdmin();

        void onHasRoleType(User user);
    }
}

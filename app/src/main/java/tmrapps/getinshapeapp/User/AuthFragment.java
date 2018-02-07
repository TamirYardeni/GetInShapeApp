package tmrapps.getinshapeapp.User;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.Model.User;

/**
 * The fragment of the authentication
 */
public class AuthFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private ProgressBar prog;

    public AuthFragment() {
        // Required empty public constructor
    }

    public static AuthFragment newInstance() {
        AuthFragment fragment = new AuthFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    /**
     * The create of the auth fragment view
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_auth, container, false);
        Button signBtn = (Button) view.findViewById(R.id.signBtn);

        prog = view.findViewById(R.id.progressBar2);

        signBtn.setOnClickListener(v -> {
            if (mListener != null) {
                prog.setVisibility(View.VISIBLE);
                mListener.onEntranceFragmentInteraction();
                // Add click listener for log in to the app
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
        prog.setVisibility(View.GONE);

        mListener = null;
    }


    public interface OnFragmentInteractionListener {
        void onEntranceFragmentInteraction();
    }
}


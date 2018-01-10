package tmrapps.getinshapeapp.User;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmrapps.getinshapeapp.R;

public class AuthActivity extends AppCompatActivity implements AuthFragment.OnFragmentInteractionListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    @Override
    public void onEntranceFragmentInteraction() {

    }
}

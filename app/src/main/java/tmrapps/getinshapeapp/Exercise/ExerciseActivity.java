package tmrapps.getinshapeapp.Exercise;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmrapps.getinshapeapp.Category.Model.AddCategoryDialog;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

public class ExerciseActivity extends AppCompatActivity implements ExerciseFragment.OnFragmentInteractionListener,
        ExerciseAdminFragment.OnFragmentInteractionListener, AddCategoryDialog.OnCategoryDialogInteractionListener{
    private RoleType role;

    private Fragment exerciseFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        this.role = RoleType.valueOf(getIntent().getStringExtra("ROLE_TYPE"));

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        if (this.role == RoleType.USER) {
            this.exerciseFrag = new ExerciseFragment();
        } else if (role == RoleType.ADMIN) {
            this.exerciseFrag = new ExerciseAdminFragment();
        }

        fragmentTransaction.add(R.id.exerciseContent, this.exerciseFrag);
        fragmentTransaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onShowCategory(String categoryId) {

    }

    @Override
    public void onCategoryAdded(String categoryName) {
        System.out.print("aaaa");
        if (role == RoleType.ADMIN) {
            //this.exerciseFrag.
        }
    }
}

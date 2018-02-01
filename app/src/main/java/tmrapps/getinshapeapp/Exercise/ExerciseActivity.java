package tmrapps.getinshapeapp.Exercise;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import tmrapps.getinshapeapp.Category.CategoryFragment;
import tmrapps.getinshapeapp.Category.Model.AddCategoryDialog;
import tmrapps.getinshapeapp.CreateExerciseFragment;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

public class ExerciseActivity extends AppCompatActivity implements ExerciseFragment.OnFragmentInteractionListener,
        ExerciseAdminFragment.OnFragmentInteractionListener, CategoryFragment.OnFragmentInteractionListener, ExerciseDetailsFragment.OnFragmentInteractionListener,
        CreateExerciseFragment.OnFragmentInteractionListener {
    private RoleType role;

    private Fragment categoryFrag;
    private Fragment exerciseListFrag;
    private Fragment exerciseDetailsFrag;
    private Fragment createExerciseFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);

        this.role = RoleType.valueOf(getIntent().getStringExtra("ROLE_TYPE"));

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        this.categoryFrag = new CategoryFragment();

        fragmentTransaction.add(R.id.exerciseContent, this.categoryFrag);
        fragmentTransaction.commit();
    }

    @Override
    public void onShowCategory(String categoryId) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        this.exerciseListFrag = new ExerciseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("roleType", this.role.name());
        this.exerciseListFrag.setArguments(bundle);

        fragmentTransaction.replace(R.id.exerciseContent, this.exerciseListFrag);
        fragmentTransaction.addToBackStack(this.categoryFrag.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onShowExerciseDetails(String id) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        this.exerciseDetailsFrag = new ExerciseDetailsFragment();
        Bundle bundle = new Bundle();
        bundle.putString("id", id);
        this.exerciseDetailsFrag.setArguments(bundle);

        fragmentTransaction.replace(R.id.exerciseContent, this.exerciseDetailsFrag);
        fragmentTransaction.addToBackStack(this.exerciseListFrag.getClass().getName());
        fragmentTransaction.commit();
    }

    @Override
    public void onShowCreateExercise(String categoryId) {
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();

        this.createExerciseFrag = new CreateExerciseFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", categoryId);
        this.createExerciseFrag.setArguments(bundle);

        fragmentTransaction.replace(R.id.exerciseContent, this.createExerciseFrag);
        fragmentTransaction.addToBackStack(this.exerciseListFrag.getClass().getName());
        fragmentTransaction.commit();
    }
}

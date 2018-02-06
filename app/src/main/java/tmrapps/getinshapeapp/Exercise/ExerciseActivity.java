package tmrapps.getinshapeapp.Exercise;

import android.graphics.Bitmap;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import tmrapps.getinshapeapp.Category.CategoryFragment;
import tmrapps.getinshapeapp.Exercise.Model.Exercise;
import tmrapps.getinshapeapp.ExerciseList.ExerciseFragment;
import tmrapps.getinshapeapp.R;
import tmrapps.getinshapeapp.User.RoleType;

public class ExerciseActivity extends AppCompatActivity implements ExerciseFragment.OnFragmentInteractionListener,
        CategoryFragment.OnFragmentInteractionListener, ExerciseDetailsFragment.OnFragmentInteractionListener,
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

        this.onShowCategory();
    }

    public void onShowCategory(){
        this.categoryFrag = CategoryFragment.newInstance(this.role);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.add(R.id.exerciseContent, this.categoryFrag);
        transaction.commit();
    }

    @Override
    public void onShowExercisesOfCategory(String categoryId) {
        this.exerciseListFrag = ExerciseFragment.newInstance(this.role.name(), categoryId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.exerciseListFrag);
        transaction.addToBackStack(this.categoryFrag.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onShowExerciseDetails(String id) {
        this.exerciseDetailsFrag = ExerciseDetailsFragment.newInstance(id);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.exerciseDetailsFrag);
        transaction.addToBackStack(this.exerciseListFrag.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onShowCreateExercise(String categoryId) {
        this.createExerciseFrag = CreateExerciseFragment.newInstance(categoryId);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.createExerciseFrag);
        transaction.addToBackStack(this.exerciseListFrag.getClass().getName());
        transaction.commit();
    }

    @Override
    public void onCreateExercise(Exercise exercise, Bitmap exerciseImage) {
        this.exerciseListFrag = ExerciseFragment.newInstance(this.role.name(), exercise.getCategoryId());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.exerciseContent, this.exerciseListFrag);
        transaction.addToBackStack(this.categoryFrag.getClass().getName());
        transaction.commit();
    }

/*    @Override
    protected void onResume() {
        super.onResume();
        this.onShowCategory();
    }*/
}

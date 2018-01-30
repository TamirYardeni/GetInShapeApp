package tmrapps.getinshapeapp.Category;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
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
import tmrapps.getinshapeapp.R;

public class CategoryFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    private CategoryFragment.CategoryAdapter categoriesAdapter;

    private ListView list;

    public CategoryFragment() {
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
    public static CategoryFragment newInstance(String param1, String param2) {
        CategoryFragment fragment = new CategoryFragment();
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

        list = exerciseView.findViewById(R.id.categoryListAdmin);
        categoriesAdapter = new CategoryFragment.CategoryAdapter(this);
        list.setAdapter(categoriesAdapter);

        FloatingActionButton addCategoryBtn = exerciseView.findViewById(R.id.addCategoryBtn);
        addCategoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AddCategoryDialog newFragment = new
                        AddCategoryDialog();
                newFragment.setHandlerListener(new AddCategoryDialog.OnCategoryDialogInteractionListener() {
                    @Override
                    public void onCategoryAdded(String categoryName) {
                        addCategory(categoryName);
                    }
                });

                newFragment.show(CategoryFragment.this.getActivity().getSupportFragmentManager(),
                        "TAG");
            }
        });

        // Inflate the layout for this fragment
        return exerciseView;
    }

    private void addCategory(String categoryName) {
        boolean isExist = false;
        for (int counter = 0; counter < this.categoriesAdapter.data.size(); counter++) {
            if (this.categoriesAdapter.data.get(counter).getName().equals(categoryName)) {
                isExist = true;
                break;
            }
        }

        if (!isExist) {
            this.categoriesAdapter.data.add(new Category(categoryName));
            this.list.setAdapter(categoriesAdapter);
            //TODO: notifyItemInserted
            //this.categoriesAdapter.notifyItemInserted(this.categoriesAdapter.data.size()-1);
        }
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
        void onShowCategory(String categoryId);
    }

    class CategoryAdapter extends BaseAdapter {

        CategoryFragment exerciseFrag;
        List<Category> data = new LinkedList<>();

        public CategoryAdapter(CategoryFragment fragment) {
            data.add(new Category("ידיים"));
            data.add(new Category("בטן"));
            data.add(new Category("גב"));
            data.add(new Category("ישבן"));
            data.add(new Category("חזה"));

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

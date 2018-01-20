package tmrapps.getinshapeapp.Main.Motivation;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.LinkedList;
import java.util.List;

import tmrapps.getinshapeapp.Main.Motivation.Model.Motivation;
import tmrapps.getinshapeapp.Main.Motivation.Model.MotivationRepository;
import tmrapps.getinshapeapp.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MotivationAdminFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MotivationAdminFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MotivationAdminFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public MotivationAdminFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MotivationAdminFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MotivationAdminFragment newInstance(String param1, String param2) {
        MotivationAdminFragment fragment = new MotivationAdminFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View motivationView = inflater.inflate(R.layout.fragment_motivation_admin, container, false);

        ListView list = motivationView.findViewById(R.id.motivationAdminList);
        MotivationAdminFragment.MotivationAdminAdapter myAdapter = new MotivationAdminAdapter();
        list.setAdapter(myAdapter);

        // Inflate the layout for this fragment
        return motivationView;
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
        void onFragmentInteraction(Uri uri);
    }

    class MotivationAdminAdapter extends BaseAdapter {

        List<Motivation> data = new LinkedList<>();

        public MotivationAdminAdapter() {
            for (int i = 0; i < 100; i++) {
                data.add(new Motivation(i + "", "https://firebasestorage.googleapis.com/v0/b/getinshape-f4acd.appspot.com/o/motivation%2FIMG-20180117-WA0001.jpg?alt=media&token=2fac5e31-5340-4c42-b0b9-bc1b79a86466"));
            }
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Motivation getItem(int i) {
            return data.get(i);
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.motivation_row, null);
                final ImageView imageView = view.findViewById(R.id.imageMotivationView);
                final Motivation st = data.get(i);
                if (i == 1) {
                    /*if (*//*st.imageUrl != null && !st.imageUrl.isEmpty() && !st.imageUrl.equals("")*//*true) {
                        *//*progressBar.setVisibility(View.VISIBLE);*//*
                        MotivationRepository.instace.getImage(st.getImageSrc(), new MotivationRepository.GetImageListener() {
                            @Override
                            public void onSuccess(Bitmap image) {
                                String tagUrl = imageView.getTag().toString();
                               *//* if (tagUrl.equals(st.imageUrl)) {*//*
                                imageView.setImageBitmap(image);
                                 *//*   progressBar.setVisibility(View.GONE);
                                }*//*
                            }

                            @Override
                            public void onFail() {
                                *//*progressBar.setVisibility(View.GONE);*//*
                            }
                        });
                    }*/

                    MotivationRepository.instace.saveImage(((BitmapDrawable)imageView.getDrawable()).getBitmap(),
                            st.getId() + ".jpeg", new MotivationRepository.SaveImageListener() {
                                @Override
                                public void complete(String url) {
                                    st.setImageSrc(url);
                            /*MotivationRepository.instace.addStudent(st);*/
                            /*setResult(RESAULT_SUCCESS);
                            progressBar.setVisibility(GONE);*/
                            /*finish();*/
                                }

                                @Override
                                public void fail() {
                                    //notify operation fail,...
                            /*setResult(RESAULT_SUCCESS);
                            progressBar.setVisibility(GONE);
                            finish();*/
                                }
                            });
                }
            }
            /*image*/
            return view;
        }
    }
}

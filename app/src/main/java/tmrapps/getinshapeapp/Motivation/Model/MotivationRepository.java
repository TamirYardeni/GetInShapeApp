package tmrapps.getinshapeapp.Motivation.Model;

import android.graphics.Bitmap;
import android.util.Log;
import android.webkit.URLUtil;

import tmrapps.getinshapeapp.Main.FirebaseModel;

/**
 * Created by tamir on 1/19/2018.
 */

public class MotivationRepository {
    public final static MotivationRepository instace = new MotivationRepository();

    /*private ModelMem modelMem;
    private ModelSql modelSql;*/
    private FirebaseModel modelFirebase;

    private MotivationRepository() {
        /*modelMem = new ModelMem();
        modelSql = new ModelSql(MyApplication.getMyContext());*/
        modelFirebase = FirebaseModel.getInstance();

    }

    public interface SaveImageListener {
        void complete(String url);
        void fail();
    }

    public void saveImage(final Bitmap imageBmp, final String name, final SaveImageListener listener) {

        modelFirebase.saveImage(imageBmp, name, new SaveImageListener() {
            @Override
            public void complete(String url) {
                String fileName = URLUtil.guessFileName(url, null, null);
                /*saveImageToFile(imageBmp,fileName);*/
                listener.complete(url);
            }

            @Override
            public void fail() {
                listener.fail();
            }
        });


    }


    public interface GetImageListener{
        void onSuccess(Bitmap image);
        void onFail();
    }
    public void getImage(final String url, final GetImageListener listener) {
        /*//check if image exsist localy
        String fileName = URLUtil.guessFileName(url, null, null);
        Bitmap image = loadImageFromFile(fileName);*/

       /* if (image != null){
            Log.d("TAG","getImage from local success " + fileName);
            listener.onSuccess(image);
        }else {*/
        FirebaseModel.getInstance().getImage(url, new GetImageListener() {
            @Override
            public void onSuccess(Bitmap image) {
                String fileName = URLUtil.guessFileName(url, null, null);
                Log.d("TAG","getImage from FB success " + fileName);
                    /*saveImageToFile(image,fileName);*/
                listener.onSuccess(image);
            }

            @Override
            public void onFail() {
                Log.d("TAG","getImage from FB fail ");
                listener.onFail();
            }
        });

        //}
    }

}

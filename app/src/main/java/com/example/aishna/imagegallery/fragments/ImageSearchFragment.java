package com.example.aishna.imagegallery.fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.aishna.imagegallery.R;
import com.example.aishna.imagegallery.activities.ImageActivity;
import com.example.aishna.imagegallery.activities.ImageDisplayActivity;
import com.example.aishna.imagegallery.Adapters.ImageAdapter;
import com.example.aishna.imagegallery.api.ApiManager;
import com.example.aishna.imagegallery.api.FlickrApi;
import com.example.aishna.imagegallery.api.ImgurApi;
import com.example.aishna.imagegallery.application.MyApplication;
import com.example.aishna.imagegallery.broadcasts.NetworkChangeReceiver;
import com.example.aishna.imagegallery.model.flickr.FlickrResponse;
import com.example.aishna.imagegallery.model.flickr.Photos;
import com.example.aishna.imagegallery.model.imgur.ImgurData;
import com.example.aishna.imagegallery.model.imgur.ImgurResponse;
import com.example.aishna.imagegallery.realm.RealmAllPhotos;
import com.example.aishna.imagegallery.realm.RealmManager;
import com.example.aishna.imagegallery.realm.flickr.RealmFlickrPhotos;
import com.example.aishna.imagegallery.realm.imgur.RealmImgurData;
import com.example.aishna.imagegallery.utils.FilterUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
import timber.log.Timber;

import static android.view.View.GONE;


public class ImageSearchFragment extends Fragment {

    public final static String EXTRA_MESSAGE = "com.example.aishna.imagegallery.MESSAGE";
    public static GridView mGridView;
    private static String mTag;
    private static ImgurApi mImgurApi;
    private static FlickrApi mFlickrApi;
    public List<String> mImages;
    @Bind(R.id.autocomplete_search)
    protected AutoCompleteTextView mSearchTag;
    @Bind(R.id.progress_bar)
    protected ProgressBar mBar;
    @Bind(R.id.imagebutton_search)
    protected ImageButton mSearch;
    @Inject
    protected NetworkChangeReceiver receiver;
    private InputMethodManager mImm;
    private ImageAdapter mImageAdapter;
    private IntentFilter mWifiStateIntentFilter;
    private Retrofit mImgurRetrofit, mFlickrRetrofit;
    private ImageActivity mActivity;


    public static FlickrResponse runFlickr() throws IOException {

        Call<FlickrResponse> call = mFlickrApi.getPhotos(mTag);
        FlickrResponse response = call.execute().body();

        return response;
    }

    public static ImgurResponse runImgur() throws IOException {

        Call<ImgurResponse> call = mImgurApi.getPhotos(mTag);
        ImgurResponse response = call.execute().body();

        return response;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ButterKnife.bind(mActivity);
        ((MyApplication) mActivity.getApplication()).getComponent().inject(this);

        mImgurRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.imgur.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mFlickrRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.flickr.com/")
                .addConverterFactory(SimpleXmlConverterFactory.create())
                .build();
        mFlickrApi = mFlickrRetrofit.create(FlickrApi.class);
        mImgurApi = mImgurRetrofit.create(ImgurApi.class);

        mImm = (InputMethodManager) mActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
        mImages = new ArrayList<>();
        mImageAdapter = new ImageAdapter(mActivity, mImages);
        mWifiStateIntentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);

        String[] history = RealmManager.getSearchHistory(mActivity);
        try {
            ArrayAdapter<String> adapter = new ArrayAdapter(mActivity, android.R.layout.simple_list_item_1, history);

            mSearchTag.setAdapter(adapter);
        } catch (NullPointerException e) {
            Timber.e("No history");
        }


        mGridView = (GridView) mActivity.findViewById(R.id.gridview_images);

        mGridView.setAdapter(mImageAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long mylng) {
                String mImage = mImages.get(position);

                ImageDisplayActivity.callIntent(mActivity, mImage);
                mActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
            }
        });
    }

    private void displayImages(List<String> imageList) {

        shuffle(imageList); //mImages has Imgur and Flickr images in order, we want to shuffle them
        mGridView.setVisibility(View.VISIBLE);
        mImageAdapter.notifyDataSetChanged();
    }

    public void shuffle(List<String> imageList) {
        int numberOfImages = imageList.size();
        Random random = new Random();
        for (int i = numberOfImages - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            // Simple swap
            String one = imageList.get(i);
            String two = imageList.get(index);
            imageList.set(i, two);
            imageList.set(index, one);
        }
        mImages.addAll(imageList);
    }

    @OnFocusChange(R.id.autocomplete_search)
    public void showHistory() {
        mSearchTag.showDropDown();
    }

    @OnTextChanged(R.id.autocomplete_search)
    public void showHistoryOnTextChange() {
        mSearchTag.showDropDown();
        mGridView.setVisibility(View.GONE);
    }

    @OnClick(R.id.imagebutton_search)
    public void search(View v) {
        mImages.clear();
        mImageAdapter.notifyDataSetChanged();
        mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        mTag = mSearchTag.getText().toString().toLowerCase();
        if (!mTag.trim().equals("")) {
            mGridView.setVisibility(GONE);
            if (receiver.isConnected()) {
                mBar.setVisibility(View.VISIBLE);
                new PostTask().execute();
            } else {

                Toast.makeText(mActivity, R.string.status_Notconnected, Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(mActivity.getApplicationContext(), R.string.tag, Toast.LENGTH_SHORT).show();
        }
    }

    @OnEditorAction(R.id.autocomplete_search)
    public boolean searchEnterkey(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            mImm.hideSoftInputFromWindow(v.getWindowToken(), 0);
            search(v);
            return true;
        }
        return false;
    }

    @Override
    public void onPause() {
        super.onPause();
        mActivity.unregisterReceiver(receiver);
    }

    @Override
    public void onResume() {
        super.onResume();

        mActivity.registerReceiver(receiver, mWifiStateIntentFilter);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (ImageActivity) context;
    }

    private class PostTask extends AsyncTask<Void, Integer, List<String>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<String> doInBackground(Void... params) {

            ImgurData imgurData;
            Photos flickrPhotos;
            List<String> imageList = new ArrayList<>();

            RealmAllPhotos realmPhotos = RealmManager.getRealmObject(mTag, mActivity);

            if (realmPhotos == null) {
                realmPhotos = new RealmAllPhotos();
                realmPhotos.setTag(mTag);
            }

            //check imgur
            if (realmPhotos.getRealmImgurData() == null) {

                ImgurResponse response = ApiManager.getImgurResponseByApiCall();

                if (response != null) {
                    imgurData = response.imgurData;
                    if (imgurData != null) {
                        //adding to database
                        RealmManager.saveImgurData(realmPhotos, imgurData);
                    }
                }
            }
            //check flickr
            if (realmPhotos.getRealmFlickrPhotos() == null) {

                FlickrResponse flickrResponse = ApiManager.getFlickPhotosByApiCall();
                //adding to database
                if (flickrResponse != null) {
                    flickrPhotos = flickrResponse.photos;
                    if (flickrPhotos.photoList != null) {
                        RealmManager.saveFlickrPhotos(realmPhotos, flickrPhotos);
                    }
                }
            }

            //getting from database
            RealmImgurData realmImgurData = realmPhotos.getRealmImgurData();
            imgurData = RealmManager.getImgurDataFromRealmImgurData(realmImgurData);

            RealmFlickrPhotos realmFlickrPhoto = realmPhotos.getRealmFlickrPhotos();
            flickrPhotos = RealmManager.getFlickrPhotosFromRealmFlickrPhotos(realmFlickrPhoto);

            if (realmFlickrPhoto == null && realmImgurData == null) {
                return null;

            } else {
                //saving the object

                RealmManager.saveRealmAllPhotosObject(realmPhotos, mActivity);

                //adding images to imagelist
                FilterUtils.addImgurImages(imageList, imgurData);
                FilterUtils.addFlickrImages(imageList, flickrPhotos);

                return imageList;
            }
        }

        @Override
        protected void onPostExecute(List<String> imageList) {
            super.onPostExecute(imageList);
            mBar.setVisibility(GONE);
            if (imageList == null) {
                Timber.d("imagelist is null");
                mImages.clear();

                Toast.makeText(mActivity.getApplicationContext(), R.string.sorry, Toast.LENGTH_SHORT).show();

            } else {
                displayImages(imageList);
            }
        }
    }

}


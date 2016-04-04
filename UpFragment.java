package boldband.com.RedCarpet;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.GridView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link UpFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link UpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UpFragment extends Fragment {

    static String movie_Name_up;
    static String release_Date_up;
    static String vote_Average_up;
    static String overView_up;
    static String poster_Path_up;
    static int idUp;
    GridView gridView;
    //some arrays indicate to elements from JSON objects within JSON Array (i.e results).
    String[] postersUrls;
    Integer[] idsUpResults;
    String[] overviews;
    String[] originalTitles;
    String[] releaseDates;
    String[] voteAverages;
    Double[] popular;
    Context context;
    ProgressDialog pDialog;
    public static String key=null;
    //Internet status flag

    CoordinatorLayout coordinatorLayout ;


    public UpFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // startActivity(new Intent(getActivity(),RefreshAlert.class).putExtras(b));
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.show();
            FetchUpMoviesTask();
        }

        else {
//            coordinatorLayout = (CoordinatorLayout)getActivity().findViewById(R.id.snackbarPosition);
//            Snackbar snackbar = Snackbar
//                    .make(coordinatorLayout, "Message is deleted", Snackbar.LENGTH_LONG)
//                    .setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View view) {
////                            Snackbar snackbar1 = Snackbar.make(coordinatorLayout, "Message is restored!", Snackbar.LENGTH_SHORT);
////                            snackbar1.show();
//                        }
//                    });
//
//            snackbar.show();
            return inflater.inflate(R.layout.retry, container, false);
        }
        return inflater.inflate(R.layout.fragment_up, container, false);
    }


    public void FetchUpMoviesTask() {
//        Button button = (Button)getActivity().findViewById(R.id.btnR);
//        button.setVisibility(View.GONE);
        final String BASE_URL = "http://api.themoviedb.org/3/movie/upcoming?api_key=9f0321305d65985c03206b7972699b51&page=1";

        //define some needed JSON fields..
        final String RESULTS = "results";
        final String ID = "id";
        final String ORIGINAL_TITLE = "original_title";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String VOTE_AVERAGE = "vote_average";
        final String POPULARITY = "popularity";




        //JSON data starts with object so, i arrange it like OBJECT THEN ARRAY..
        //  JSONObject jsonObject = new JSONObject(moviesStr);

        JsonObjectRequest FetchUpMoviesTask = new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray resultsArray = response.getJSONArray(RESULTS);

                    if (pDialog != null) {
                        pDialog.dismiss();
                        pDialog = null;
                    }

                    //initialize the arrays..
                    postersUrls = new String[resultsArray.length()];
                    idsUpResults = new Integer[resultsArray.length()];
                    originalTitles = new String[resultsArray.length()];
                    overviews = new String[resultsArray.length()];
                    releaseDates = new String[resultsArray.length()];
                    voteAverages = new String[resultsArray.length()];
                    popular = new Double[resultsArray.length()];
                    final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500/";

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject idsObject = resultsArray.getJSONObject(i);
                        int id = idsObject.getInt(ID);
                        idsUpResults[i] = id;

                        System.out.println(idsUpResults);

                        String originalTitle = idsObject.getString(ORIGINAL_TITLE);
                        originalTitles[i] = originalTitle;


                        String overview = idsObject.getString(OVERVIEW);
                        overviews[i] = overview;

                        String releaseDate = idsObject.getString(RELEASE_DATE);
                        releaseDates[i] = releaseDate;

                        String voteAverage = idsObject.getString(VOTE_AVERAGE);
                        voteAverages[i] = voteAverage;

                        Double popularity = idsObject.getDouble(POPULARITY);
                        popular[i] = popularity;


                        JSONObject postersObject = resultsArray.getJSONObject(i);
                        String posterPath = postersObject.getString(POSTER_PATH);
                        postersUrls[i] = BASE_POSTER_URL + posterPath;


                        if (postersUrls != null) {
                            GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), postersUrls);
                            gridView = (GridView) getActivity().findViewById(R.id.usage_up_gridview);
                            gridView.setAdapter(gridViewAdapter);

                        }
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                idUp = idsUpResults[position];
                                                                movie_Name_up = originalTitles[position];
                                                                release_Date_up = releaseDates[position];
                                                                vote_Average_up = voteAverages[position];
                                                                overView_up = overviews[position];
                                                                poster_Path_up = postersUrls[position];

                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("upMovieName", originalTitles[position]);
                                                                bundle.putString("upReleaseDate", releaseDates[position]);
                                                                bundle.putString("upVoteAverage", voteAverages[position]);
                                                                bundle.putString("upOverview", overviews[position]);
                                                                bundle.putString("upPosterPath", postersUrls[position]);
                                                                bundle.putInt("idUp", idsUpResults[position]);
                                                                bundle.putString("fragmentName", "up");
                                                                SharedPreferences settings =
                                                                        getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = settings.edit();
                                                                editor.putString("fragmentName", "up");
                                                                editor.commit();



//
                                                                startActivity(new Intent(getActivity(), MovieDetail.class));

                                                            }
                                                        }
                        );


                        if (pDialog != null) {
                            pDialog.dismiss();
                            pDialog = null;
                        }


                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });


        MySingelton.getInstance(getActivity()).addToRequestQueue(FetchUpMoviesTask);
        //MyApplication.getInstance().addToRequestQueue(movieReq);
//        MyApplication.getInstance().addToRequestQueue(FetchPopularMoviesTask);


    }


}


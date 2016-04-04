package boldband.com.RedCarpet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

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
 * {@link TopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TopFragment extends Fragment {

    GridView gridView ;

    //some arrays indicate to elements from JSON objects within JSON Array (i.e results).
    String [] postersUrls ;
    Integer [] idsTopResults;
    String [] overviews;
    String [] originalTitles;
    String [] releaseDates ;
    String [] voteAverages ;
    Double [] popular;
    Context context ;
    ProgressDialog pDialog ;
    static String movie_Name_top;
    static String release_Date_top;
    static String vote_Average_top;
    static String overView_top;
    static String poster_Path_top;
    static int idTop;

    public TopFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.show();
            FetchTopMoviesTask();

        }

        else {
            //  android.support.v4.app.FragmentManager fm = getFragmentManager();

            return inflater.inflate(R.layout.retry, container, false);
//            RefreshAlert alter=new RefreshAlert();
//            alter.show(getFragmentManager(),"aa");
//              Toast.makeText(getActivity(), "no Internet connection", Toast.LENGTH_SHORT).show();
        }
        return inflater.inflate(R.layout.fragment_top, container, false);
    }





    public void FetchTopMoviesTask()
    {
        final String BASE_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=9f0321305d65985c03206b7972699b51&page=1";

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

        JsonObjectRequest FetchTopMoviesTask=new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray resultsArray = response.getJSONArray(RESULTS);


                    if (pDialog != null) {
                        pDialog.dismiss();
                        pDialog = null;
                    }

                    //initialize the arrays..
                    postersUrls = new String[resultsArray.length()] ;
                    idsTopResults = new Integer[resultsArray.length()];
                    originalTitles = new String[resultsArray.length()];
                    overviews = new String[resultsArray.length()];
                    releaseDates = new String[resultsArray.length()];
                    voteAverages = new String[resultsArray.length()];
                    popular = new Double[resultsArray.length()];
                    final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500/";

                    for (int i=0 ;i<resultsArray.length() ; i++){
                        JSONObject idsObject = resultsArray.getJSONObject(i);
                        int id = idsObject.getInt(ID);
                        idsTopResults[i] = id ;

                        System.out.println(idsTopResults);

                        String originalTitle = idsObject.getString(ORIGINAL_TITLE);
                        originalTitles[i] = originalTitle;


                        String overview = idsObject.getString(OVERVIEW);
                        overviews[i]= overview ;

                        String releaseDate = idsObject.getString(RELEASE_DATE);
                        releaseDates[i]= releaseDate ;

                        String voteAverage = idsObject.getString(VOTE_AVERAGE);
                        voteAverages[i] = voteAverage;

                        Double popularity = idsObject.getDouble(POPULARITY);
                        popular[i] = popularity;


                        JSONObject postersObject = resultsArray.getJSONObject(i);
                        String posterPath = postersObject.getString(POSTER_PATH);
                        postersUrls[i] = BASE_POSTER_URL+posterPath;




                        if (postersUrls != null) {
                            GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), postersUrls);
                            gridView = (GridView) getActivity().findViewById(R.id.usage_top_gridview);
                            gridView.setAdapter(gridViewAdapter);

                        }
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                idTop = idsTopResults[position];
                                                                movie_Name_top = originalTitles[position];
                                                                release_Date_top = releaseDates[position];
                                                                vote_Average_top = voteAverages[position];
                                                                overView_top = overviews[position];
                                                                poster_Path_top = postersUrls[position];

                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("topMovieName", originalTitles[position]);
                                                                bundle.putString("topReleaseDate", releaseDates[position]);
                                                                bundle.putString("topVoteAverage", voteAverages[position]);
                                                                bundle.putString("topOverview", overviews[position]);
                                                                bundle.putString("topPosterPath", postersUrls[position]);
                                                                bundle.putInt("idTop", idsTopResults[position]);
                                                                bundle.putString("fragmentName", "top");

                                                                SharedPreferences settings =
                                                                        getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = settings.edit();
                                                                editor.putString("fragmentName", "top");
                                                                editor.commit();
//
                                                                startActivity(new Intent(getActivity(), MovieDetail.class));

                              /*  FrameLayout frameLayout = (FrameLayout)getActivity().findViewById(R.id.container);
                                if (frameLayout!=null){
                                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container,
                                            new MovieDetail()).commit();
//                        startActivity(new Intent(getActivity(), MovieDetail.class).putExtras(bundle));
                                }
                                else {startActivity(new Intent(getActivity(), MovieDetail.class).putExtras(bundle));
                                    getActivity().overridePendingTransition(R.animator.anim, R.animator.anim_out);}*/

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


        MySingelton.getInstance(getActivity()).addToRequestQueue(FetchTopMoviesTask);
        //MyApplication.getInstance().addToRequestQueue(movieReq);
//        MyApplication.getInstance().addToRequestQueue(FetchPopularMoviesTask);






    }
}

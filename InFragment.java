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
 * {@link InFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link InFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InFragment extends Fragment {


    GridView gridView ;

    //some arrays indicate to elements from JSON objects within JSON Array (i.e results).
    String [] inpostersUrls ;
    Integer [] inidsInResults;
    String [] inoverviews;
    String [] inoriginalTitles;
    String [] inreleaseDates ;
    String [] invoteAverages ;
    Double [] inpopular;
    Context context ;
    ProgressDialog pDialog ;
    static String movie_Name_in;
    static String release_Date_in;
    static String vote_Average_in;
    static String overView_in;
    static String poster_Path_in;
    static int idIn;

    public InFragment() {
        // Required empty public constructor
        //  FetchInMoviesTask();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        // startActivity(new Intent(getActivity(),RefreshAlert.class).putExtras(b));
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();

        if (info != null) {
            pDialog = new ProgressDialog(getActivity());
            pDialog.show();
            FetchInMoviesTask();

        }

        else {
            //  android.support.v4.app.FragmentManager fm = getFragmentManager();

//            RefreshAlert alter=new RefreshAlert();
//            alter.show(getFragmentManager(),"aa");
            return inflater.inflate(R.layout.retry, container, false);
//              Toast.makeText(getActivity(), "no Internet connection", Toast.LENGTH_SHORT).show();
        }
        return inflater.inflate(R.layout.fragment_in, container, false);
    }





    public void FetchInMoviesTask() {
        final String BASE_URL = "http://api.themoviedb.org/3/movie/now_playing?api_key=9f0321305d65985c03206b7972699b51&page=1";

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

        JsonObjectRequest FetchInMoviesTask = new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    JSONArray resultsArray = response.getJSONArray(RESULTS);

                    if (pDialog != null) {
                        pDialog.dismiss();
                        pDialog = null;
                    }

                    //initialize the arrays..
                    inpostersUrls = new String[resultsArray.length()];
                    inidsInResults = new Integer[resultsArray.length()];
                    inoriginalTitles = new String[resultsArray.length()];
                    inoverviews = new String[resultsArray.length()];
                    inreleaseDates = new String[resultsArray.length()];
                    invoteAverages = new String[resultsArray.length()];
                    inpopular = new Double[resultsArray.length()];
                    final String BASE_POSTER_URL = "http://image.tmdb.org/t/p/w500/";

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject idsObject = resultsArray.getJSONObject(i);
                        int id = idsObject.getInt(ID);
                        inidsInResults[i] = id;

                        System.out.println(inidsInResults);

                        String originalTitle = idsObject.getString(ORIGINAL_TITLE);
                        inoriginalTitles[i] = originalTitle;


                        String overview = idsObject.getString(OVERVIEW);
                        inoverviews[i] = overview;

                        String releaseDate = idsObject.getString(RELEASE_DATE);
                        inreleaseDates[i] = releaseDate;

                        String voteAverage = idsObject.getString(VOTE_AVERAGE);
                        invoteAverages[i] = voteAverage;

                        Double popularity = idsObject.getDouble(POPULARITY);
                        inpopular[i] = popularity;


                        JSONObject postersObject = resultsArray.getJSONObject(i);
                        String posterPath = postersObject.getString(POSTER_PATH);
                        inpostersUrls[i] = BASE_POSTER_URL + posterPath;


                        if (inpostersUrls != null) {
                            GridViewAdapter gridViewAdapter = new GridViewAdapter(getActivity(), inpostersUrls);
                            gridView = (GridView) getActivity().findViewById(R.id.usage_in_gridview);
                            gridView.setAdapter(gridViewAdapter);

                        }
                        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                                            @Override
                                                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                                                idIn = inidsInResults[position];
                                                                movie_Name_in = inoriginalTitles[position];
                                                                release_Date_in = inreleaseDates[position];
                                                                vote_Average_in = invoteAverages[position];
                                                                overView_in = inoverviews[position];
                                                                poster_Path_in = inpostersUrls[position];

                                                                Bundle bundle = new Bundle();
                                                                bundle.putString("inMovieName", inoriginalTitles[position]);
                                                                bundle.putString("inReleaseDate", inreleaseDates[position]);
                                                                bundle.putString("inVoteAverage", invoteAverages[position]);
                                                                bundle.putString("inOverview", inoverviews[position]);
                                                                bundle.putString("inPosterPath", inpostersUrls[position]);
                                                                bundle.putInt("idIn", inidsInResults[position]);
                                                                bundle.putString("fragmentName", "in");


                                                                SharedPreferences settings =
                                                                        getActivity().getSharedPreferences("name", Context.MODE_PRIVATE);
                                                                SharedPreferences.Editor editor = settings.edit();
                                                                editor.putString("fragmentName", "in");
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


        MySingelton.getInstance(getActivity()).addToRequestQueue(FetchInMoviesTask);
        //MyApplication.getInstance().addToRequestQueue(movieReq);
//        MyApplication.getInstance().addToRequestQueue(FetchPopularMoviesTask);



    }


}

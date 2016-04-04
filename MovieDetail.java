package boldband.com.RedCarpet;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ahmedabouelfadle on 06/12/15.
 */
public class MovieDetail extends AppCompatActivity {


    private static final String TAG = MainActivity.class.getSimpleName();
    String duration;
    String firstReviewAuthor;
    String firstReview;
    String secondReviewAuthor;
    String secondContent;
    String firstTrailerName;
    String firstSource;
    String secondTrailerName;
    String firstTrailerSource;
    String secondTrailerSource;
    String secondSource;
    TextView tvFirstReview ;
    TextView tvSecondReview ;
    TextView tvFirstReviewAuthor;
    TextView tvSecondReviewAuthor;
    public ProgressDialog pDialog;

    String fragmentName= "";


    ImageView ivMiniPoster;
    TextView tvReleaseDate,tvDuration,tvVoteAverage,tvMovieName,tvOverview;




    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        ivMiniPoster= (ImageView) findViewById(R.id.ivMiniPoster);
        tvReleaseDate= (TextView) findViewById(R.id.tvReleaseDate);
        tvDuration=(TextView) findViewById(R.id.tvDuration);
        tvVoteAverage=(TextView) findViewById(R.id.tvVoteAverage);
        tvMovieName=(TextView) findViewById(R.id.tvMovieName);
        tvOverview = (TextView) findViewById(R.id.tvOverview);



        /*Intent intent=new Intent();
        fragmentName= String.valueOf(intent.getBundleExtra("popMovieName"));
        Toast.makeText(MovieDetail.this, "hhhhhhhhhhhhhh"+fragmentName, Toast.LENGTH_SHORT).show();*/
        // SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences settings =
                this.getSharedPreferences("name", Context.MODE_PRIVATE);
        fragmentName=settings.getString("fragmentName","top");

//        Toast.makeText(MovieDetail.this, "hhhhhhhhhhhhhh"+fragmentName, Toast.LENGTH_SHORT).show();





        if (fragmentName.equals("up"))
        {
            Picasso
                    .with(this)
                    .load(UpFragment.poster_Path_up)
                    .into(ivMiniPoster);

            int id=UpFragment.idUp;
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if (info != null) {
                pDialog = new ProgressDialog(this);
                pDialog.show();

                FetchReviewsTask(id);
                FetchTrailersTask(id);
                FetchDurationTask(id);

            }


            //  tvReleaseDate.setText(UpFragment.release_Date_up);
            tvReleaseDate.setText(UpFragment.release_Date_up);
            // tvVoteAverage.setText(UpFragment.vote_Average_up);
            tvVoteAverage.setText(UpFragment.vote_Average_up + "/10");
            tvMovieName.setText(UpFragment.movie_Name_up + " \uD83D\uDC51 ");
            tvMovieName.setTypeface(Typeface.SANS_SERIF);

            tvDuration.setText(duration);

            tvOverview.setText(" \uD83D\uDCD6 " + UpFragment.overView_up);
            tvOverview.setTypeface(Typeface.MONOSPACE);


//            Toast.makeText(MovieDetail.this, "upppppp", Toast.LENGTH_SHORT).show();
        }
        else if (fragmentName.equals("top"))
        {
            int id=TopFragment.idTop;

            Picasso
                    .with(this)
                    .load(TopFragment.poster_Path_top)
                    .into(ivMiniPoster);


            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if (info != null) {
                pDialog = new ProgressDialog(this);
                pDialog.show();

                FetchReviewsTask(id);
                FetchTrailersTask(id);
                FetchDurationTask(id);

            }

            tvDuration.setText(duration);
            // tvReleaseDate.setText(TopFragment.release_Date_top);
            tvReleaseDate.setText(TopFragment.release_Date_top);
            // tvVoteAverage.setText(TopFragment.vote_Average_top);
            tvVoteAverage.setText(TopFragment.vote_Average_top + "/10");
            tvMovieName.setText(TopFragment.movie_Name_top+ " \uD83D\uDC51 ");
            tvMovieName.setTypeface(Typeface.SANS_SERIF);

            tvOverview.setText(" \uD83D\uDCD6 " + TopFragment.overView_top);
            tvOverview.setTypeface(Typeface.MONOSPACE);

//            Toast.makeText(MovieDetail.this, "name   "+TopFragment.movie_Name_top, Toast.LENGTH_SHORT).show();



        }
        else if (fragmentName.equals("in"))
        {
            int id=InFragment.idIn;
            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if (info != null) {
                pDialog = new ProgressDialog(this);
                pDialog.show();

                FetchReviewsTask(id);
                FetchTrailersTask(id);
                FetchDurationTask(id);

            }

            Picasso
                    .with(this)
                    .load(InFragment.poster_Path_in)
                    .into(ivMiniPoster);

            tvDuration.setText(duration);
            // tvReleaseDate.setText(InFragment.release_Date_in);
            tvReleaseDate.setText(InFragment.release_Date_in);
            // tvVoteAverage.setText(InFragment.vote_Average_in);
            tvVoteAverage.setText(InFragment.vote_Average_in + "/10");
            tvMovieName.setText(InFragment.movie_Name_in+ " \uD83D\uDC51 ");
            tvMovieName.setTypeface(Typeface.SANS_SERIF);

            tvOverview.setText(" \uD83D\uDCD6 " + InFragment.overView_in);
            tvOverview.setTypeface(Typeface.MONOSPACE);

        }
        else if (fragmentName.equals("pop"))
        {
            int id=PopFragment.idPop;

            ConnectivityManager cm = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();

            if (info != null) {
                pDialog = new ProgressDialog(this);
                pDialog.show();

                FetchReviewsTask(id);
                FetchTrailersTask(id);
                FetchDurationTask(id);

            }

            Picasso
                    .with(this)
                    .load(PopFragment.poster_Path_pop)
                    .into(ivMiniPoster);

            tvDuration.setText(duration);
            //tvReleaseDate.setText(PopFragment.release_Date_pop);
            tvReleaseDate.setText(PopFragment.release_Date_pop);
            // tvVoteAverage.setText(PopFragment.vote_Average_pop);
            tvVoteAverage.setText(PopFragment.vote_Average_pop + "/10");
            tvMovieName.setText(PopFragment.movie_Name_pop+ " \uD83D\uDC51 ");
            tvMovieName.setTypeface(Typeface.SANS_SERIF);

            tvOverview.setText(" \uD83D\uDCD6 " + PopFragment.overView_pop);
            tvOverview.setTypeface(Typeface.MONOSPACE);


        }
        else {
            Toast.makeText(MovieDetail.this, "error", Toast.LENGTH_SHORT).show();
        }


    }


//public void getShowImages(int id){
//    final String FILE_PATH = "file_path";
//    final String BASE_URL = "http://api.themoviedb.org/3/movie/"+id+"/images?api_key=9f0321305d65985c03206b7972699b51";
//    JsonObjectRequest JsonGetShowImages = new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
//        @Override
//        public void onResponse(JSONObject response) {
//            try {
//
//
////                duration =response.getString(RUNTIME);
//
//
//                TextView tvDuration = (TextView)findViewById(R.id.tvDuration);
//
//
//                tvDuration.setText(duration+" min.");
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }, new Response.ErrorListener() {
//        @Override
//        public void onErrorResponse(VolleyError error) {
//            VolleyLog.d(TAG, "Error: " + error.getMessage());
//
//        }
//        });

//}

    public void FetchReviewsTask(int idd)
    {

        // int idd = ViewingActivityFragment.idd;
        //the BASE_URL for build url for getting JSON data..
        final String BASE_URL = "http://api.themoviedb.org/3/movie/"+idd+"/reviews?api_key=9f0321305d65985c03206b7972699b51";

        // Toast.makeText(getActivity(), "idddddddd"+idd, Toast.LENGTH_SHORT).show();
        JsonObjectRequest FetchReviewsTask=new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                final String RESULTS = "results";
                final String CONTENT = "content";
                final String AUTHOR = "author";

                //JSON data starts with object so, i arrange it like OBJECT THEN ARRAY..
                //  JSONObject jsonObject = new JSONObject(moviesStr);
                JSONArray resultsArray = null;
                try {

                    resultsArray = response.getJSONArray(RESULTS);
                    if (pDialog != null) {
                        pDialog.dismiss();
                        pDialog = null;
                    }
                    JSONObject firstObject = resultsArray.getJSONObject(0);
                    firstReviewAuthor =firstObject.getString(AUTHOR);
                    // firstReviewAuthor= firstAuthorName;

                    firstReview= firstObject.getString(CONTENT);
                    // firstReview= content ;

                    JSONObject secondObject = resultsArray.getJSONObject(1);
                    secondReviewAuthor =secondObject.getString(AUTHOR);
                    //secondReviewAuthor= secondAuthorName;

                    secondContent = secondObject.getString(CONTENT);
                    //  this.secondContent= secondContent1;

                    tvFirstReview = (TextView)findViewById(R.id.tvFirstReview);
                    tvSecondReview =(TextView)findViewById(R.id.tvSecondReview);
                    tvFirstReviewAuthor= (TextView)findViewById(R.id.tvFirstAuthor);
                    tvSecondReviewAuthor=(TextView)findViewById(R.id.tvSecondAuthor);
                    ImageView ivFirstAuthor = (ImageView)findViewById(R.id.ivFirstAuthor);
                    ImageView ivSecondAuthor = (ImageView)findViewById(R.id.ivSecondAuthor);

                    tvFirstReviewAuthor.setText(firstReviewAuthor);
                    tvFirstReviewAuthor.setTypeface(Typeface.MONOSPACE);
                    tvFirstReview.setText(firstReview);
                    tvFirstReview.setTypeface(Typeface.SANS_SERIF);
                    tvSecondReviewAuthor.setText(secondReviewAuthor);
                    tvSecondReviewAuthor.setTypeface(Typeface.MONOSPACE);
                    tvSecondReview.setText(secondContent);
                    tvSecondReview.setTypeface(Typeface.SANS_SERIF);

                } catch (JSONException e) {
                    e.printStackTrace();
                }





            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });


        MySingelton.getInstance(this).addToRequestQueue(FetchReviewsTask);

//        MyApplication.getInstance().addToRequestQueue(FetchReviewsTask);
    }


    public void FetchTrailersTask(final int idd)
    {




        // int iddd = ViewingActivityFragment.idd;
        TextView tvFirstTrailerName = (TextView)findViewById(R.id.tvFirstTrailer);
        TextView tvSecondTrailerName =(TextView)findViewById(R.id.tvSecondTrailer);

        ImageView ivPlayFirstTrailer = (ImageView)findViewById(R.id.ivPlayTrailer1);
        ImageView ivPlaySecondTrailer = (ImageView)findViewById(R.id.ivPlayTrailer2);
        //the BASE_URL for build url for getting JSON data..
        final String BASE_URL = "http://api.themoviedb.org/3/movie/"+idd+"/trailers?api_key=9f0321305d65985c03206b7972699b51";
        final String MYKEY = "9f0321305d65985c03206b7972699b51";



        //define some needed JSON fields..
        final String YOUTUBE = "youtube";
        final String NAME = "name";
        final String SOURCE = "source";


        JsonObjectRequest FetchTrailersTask=new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {


                //JSON data starts with object so, i arrange it like OBJECT THEN ARRAY..
                //  JSONObject jsonObject = new JSONObject(moviesStr);
                JSONArray resultsArray = null;
                try {

                    JSONArray resultsArr = response.getJSONArray(YOUTUBE);

                    JSONObject firstObject = resultsArr.getJSONObject(0);
                    firstTrailerName =firstObject.getString(NAME);

                    firstTrailerSource= firstObject.getString(SOURCE);

                    JSONObject secondObject = resultsArr.getJSONObject(1);
                    secondTrailerName =secondObject.getString(NAME);

                    secondTrailerSource = secondObject.getString(SOURCE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }



                TextView tvFirstTrailerName = (TextView)findViewById(R.id.tvFirstTrailer);
                TextView tvSecondTrailerName =(TextView)findViewById(R.id.tvSecondTrailer);

                ImageView ivPlayFirstTrailer = (ImageView)findViewById(R.id.ivPlayTrailer1);
                ImageView ivPlaySecondTrailer = (ImageView)findViewById(R.id.ivPlayTrailer2);

                tvFirstTrailerName.setText(firstTrailerName);
                tvFirstTrailerName.setTypeface(Typeface.SANS_SERIF);
                tvSecondTrailerName.setText(secondTrailerName);
                tvSecondTrailerName.setTypeface(Typeface.SANS_SERIF);


                //   new FetchReviewsTask().execute();
                //   FetchReviewsTask(idd);
                FetchReviewsTask(idd);

                ivPlayFirstTrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com" +
                                "/watch?v=" + firstSource)));
                    }
                });

                ivPlaySecondTrailer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com" +
                                "/watch?v=" + secondSource)));
                    }
                });
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if(error instanceof NoConnectionError) {
                    //  error = "No internet Access, Check your internet connection.";
                    //Toast.makeText(getActivity(), "haaaaaaaaaaamed", Toast.LENGTH_SHORT).show();
                }
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        //  MyApplication.getInstance().addToRequestQueue(FetchTrailersTask);
        MySingelton.getInstance(this).addToRequestQueue(FetchTrailersTask);



    }

    public void FetchDurationTask(int idd)
    {
        // TextView tvDuration;
        // int id = MovieDetail.id;
        final String RUNTIME = "runtime";

        final String BASE_URL = "http://api.themoviedb.org/3/movie/"+idd+"?api_key=9f0321305d65985c03206b7972699b51";

        JsonObjectRequest FetchDurationTask=new JsonObjectRequest(BASE_URL, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    duration =response.getString(RUNTIME);


                    TextView tvDuration = (TextView)findViewById(R.id.tvDuration);


                    tvDuration.setText(duration+" min.");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

            }
        });

        MySingelton.getInstance(this).addToRequestQueue(FetchDurationTask);

    }


}

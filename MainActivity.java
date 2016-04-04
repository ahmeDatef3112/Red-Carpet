package boldband.com.RedCarpet;

import android.graphics.drawable.AnimatedVectorDrawable;
        import android.support.design.widget.TabLayout;
        import android.support.v4.app.Fragment;
        import android.support.v4.app.FragmentManager;
        import android.support.v4.app.FragmentPagerAdapter;
        import android.support.v4.view.ViewPager;
import android.os.Bundle;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
import android.view.animation.Interpolator;
        import android.widget.ImageView;
        import android.widget.TextView;

        import java.util.ArrayList;
        import java.util.List;


public class MainActivity extends AppCompatActivity {
    public Toolbar toolbar;
    public TabLayout tabLayout;
    public ViewPager viewPager;
    public ImageView iv;
    public TextView text;
    public AnimatedVectorDrawable searchToBar;
    public AnimatedVectorDrawable barToSearch;
    public float offset;
    public Interpolator interp;
    public int duration;
    public boolean expanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

//        iv = (ImageView) findViewById(R.id.search);
//        text = (TextView) findViewById(R.id.text);
//        searchToBar = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_search_to_bar);
//        barToSearch = (AnimatedVectorDrawable) getResources().getDrawable(R.drawable.anim_bar_to_search);
//        interp = AnimationUtils.loadInterpolator(this, android.R.interpolator.linear_out_slow_in);
//        duration = getResources().getInteger(R.integer.duration_bar);
//        // iv is sized to hold the search+bar so when only showing the search icon, translate the
//        // whole view left by half the difference to keep it centered
//        offset = -71f * (int) getResources().getDisplayMetrics().scaledDensity;
//        iv.setTranslationX(offset);
    }
//
//    @SuppressLint("NewApi")
//    public void animate(View view) {
//
//        if (!expanded) {
//            iv.setImageDrawable(searchToBar);
//            searchToBar.start();
//            iv.animate().translationX(0f).setDuration(duration).setInterpolator(interp);
//            text.animate().alpha(1f).setStartDelay(duration - 100).setDuration(100).setInterpolator(interp);
//        } else {
//            iv.setImageDrawable(barToSearch);
//            barToSearch.start();
//            iv.animate().translationX(offset).setDuration(duration).setInterpolator(interp);
//            text.setAlpha(0f);
//        }
//        expanded = !expanded;
//    }
    private void setupViewPager(ViewPager viewPager) {

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new UpFragment(), "Up Coming");
        adapter.addFragment(new InFragment(), "In Theaters");
        adapter.addFragment(new PopFragment(), "Most Popular");
        adapter.addFragment(new TopFragment(), "Top Rated");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}

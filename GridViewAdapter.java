package boldband.com.RedCarpet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Me on 9/26/2015.
 */
public class GridViewAdapter extends ArrayAdapter{
    private Context context;
    private LayoutInflater inflater;
    private String[] imageUrls;

    public GridViewAdapter(Context context, String[] imageUrls) {
        super(context, R.layout.gridview_item, imageUrls);

        this.context = context;
        this.imageUrls = imageUrls;

        inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView ;
        if (convertView==null) {
            convertView = inflater.inflate(R.layout.gridview_item, parent, false);
//            imageView = (ImageView)convertView;
////            Resources resources = Resources.getSystem();
////            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60,
////                    resources.getDisplayMetrics());
////            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
////            imageView.setLayoutParams(new GridView.LayoutParams((int)px, (int)px));
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        }
//        else {imageView = (ImageView)convertView;}

        Picasso
                .with(context)
                .load(imageUrls[position])
//                .fit() // will explain later
                .into((ImageView) convertView);

        return convertView;
    }

    @Override
    public int getCount() {
        return imageUrls.length;
    }
}

package edu.nyu.scps.jaxon.gallery;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.parse.ParseObject;

import java.util.List;

/**
 * Created by jaxonian on 8/14/15.
 */
public class GalleryAdapter extends BaseAdapter {
    private Context context;
    List<ParseObject> parseObjects;

    public GalleryAdapter(Context context, List<ParseObject> parseObjects) {
        this.context = context;
        this.parseObjects = parseObjects;
    }

    @Override
    public int getCount() {
        return parseObjects.size();
    }

    @Override
    public Object getItem(int position) {
        return parseObjects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout linearLayout;



        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            linearLayout = (LinearLayout)inflater.inflate(R.layout.gallery_view, null);
        } else {
            linearLayout = (LinearLayout)convertView;
        }

        ParseObject parseObject = parseObjects.get(position);



        ImageView imageView = (ImageView)linearLayout.findViewById(R.id.image1);

        // BEGIN TRYING TO GET IMAGES


       imageView.setImageResource(R.drawable.drawing_s1);

        // END TRYING TO GET IMAGES

        TextView textView = (TextView)linearLayout.findViewById(R.id.text1);
        textView.setText(parseObject.getString("name"));
        textView = (TextView)linearLayout.findViewById(R.id.text2);
        textView.setText("Category: " +  parseObject.getString("category") + "\n"
                + "Price:  $" + parseObject.getInt("price") +".00" + "\n"
                + "ID: " +parseObject.getObjectId());
        return linearLayout;
    }
}


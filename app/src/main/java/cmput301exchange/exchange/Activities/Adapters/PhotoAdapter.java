package cmput301exchange.exchange.Activities.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import cmput301exchange.exchange.R;

public class PhotoAdapter extends ArrayAdapter<Bitmap> {
    private final Context context;
    private final ArrayList<Bitmap> photos;

    public PhotoAdapter(Context context, ArrayList<Bitmap> photos) {
        super(context, 0, photos);
        this.context = context;
        this.photos = photos;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent){

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.list_photo, parent, false);
        ImageView photo = (ImageView) rowView.findViewById(R.id.photo);

        final Bitmap thisPhoto = photos.get(position);
        photo.setImageBitmap(thisPhoto);

        return rowView;
    }
}

package cmput301exchange.exchange.Activities.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

import cmput301exchange.exchange.R;

public class PhotoAdapter extends ArrayAdapter<Bitmap> {
    public PhotoAdapter(Context context, ArrayList<Bitmap> photos) {
        super(context, 0, photos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            // inflate row layout and assign to 'row'
        }
        final Bitmap thisPhoto = getItem(position);
        final ImageView photo = (ImageView) row.findViewById(R.id.photo);
        photo.setImageBitmap(thisPhoto);

        return row;
    }
}

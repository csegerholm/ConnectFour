package cms549.connectfour;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ChipImageSpinnerAdapter extends ArrayAdapter<Chip> {

        private static LayoutInflater inflater = null;

    public ChipImageSpinnerAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public ChipImageSpinnerAdapter(Context context, int resource, List<Chip> items) {
            super(context, resource, items);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {
            return getView(position, convertView, parent);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if(v==null){
                v = inflater.inflate(R.layout.row_with_image, null);
            }

            Chip ch = getItem(position);

            if (ch != null) {
                ImageView img = (ImageView) v.findViewById(R.id.img);
                TextView name = (TextView) v.findViewById(R.id.name);

                if (img != null) {
                    img.setImageResource(ch.id);
                }

                if (name != null) {
                    name.setText(ch.color);
                }

            }

            return v;
        }


}

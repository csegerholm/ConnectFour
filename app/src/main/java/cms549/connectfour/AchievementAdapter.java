package cms549.connectfour;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class AchievementAdapter extends ArrayAdapter<Achievement> {

        private static LayoutInflater inflater = null;

        public AchievementAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        public AchievementAdapter(Context context, int resource, List<Achievement> items) {
            super(context, resource, items);
            inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            View v = convertView;
            if(v==null){
                v = inflater.inflate(R.layout.row_achievement, null);
            }

            Achievement t = getItem(position);

            if (t != null) {
                TextView name = (TextView) v.findViewById(R.id.name);

                TextView desc = (TextView) v.findViewById(R.id.desc);
                ImageView img = (ImageView) v.findViewById(R.id.chip);

                if (name != null) {
                    name.setText(t.getName());
                }

                if (desc != null) {
                    desc.setText(t.getDesc());
                }
                if(img!=null){
                    img.setImageResource(t.getCircleID());
                }

            }

            return v;
        }

    }



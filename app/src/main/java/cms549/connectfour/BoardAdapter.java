package cms549.connectfour;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;


public class BoardAdapter extends BaseAdapter {

    Context context;
    ArrayList<Move> list;

    public BoardAdapter(GameScreen gameScreen, ArrayList<Move> boardAsList) {
        context = gameScreen;
        list = boardAsList;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {

            Move m =list.get(position);
            TextView textView = new TextView(context);
            textView.setBackground(context.getDrawable(m.picID));
            return textView;


        } else {
            Move m =list.get(position);
            convertView.setBackground(context.getDrawable(m.picID));
            return convertView;
        }
    }
}

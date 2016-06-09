package rtrk.pnrs.statistics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import rtrk.pnrs.clockgame.R;

/**
 * Created by Igor1 on 13/04/2016.
 */
public class MyAdapter extends BaseAdapter {

    ArrayList<OneItem> mList;
    private Context context;

    public MyAdapter(Context ccontext) {
        mList = new ArrayList<OneItem>();
        context = ccontext;
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void addItem(OneItem oi) {
        mList.add(oi);
        notifyDataSetChanged();
    }

    public void removeItem(int i) {
        mList.remove(i);
        notifyDataSetChanged();
    }

    public void update(OneItem[] items) {
        mList.clear();

        if(items != null) {
            for (int i = 0; i < items.length; i++) {
                mList.add(items[i]);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View initView = convertView;
        if(initView == null) {
            initView = inflater.inflate(R.layout.item_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewRight = (TextView) initView.findViewById(R.id.textViewRight);
            viewHolder.textViewLeft = (TextView) initView.findViewById(R.id.textViewLeft);
            viewHolder.textViewCenter = (TextView) initView.findViewById(R.id.textViewCenter);
            viewHolder.imageViewCenter = (ImageView) initView.findViewById(R.id.imageViewCenter);

            initView.setTag(viewHolder);
        }

        OneItem oneItem = (OneItem) getItem(position);

        ViewHolder viewHolder = (ViewHolder) initView.getTag();

        viewHolder.imageViewCenter.setImageDrawable(oneItem.imageCenter);
        viewHolder.textViewCenter.setText(oneItem.textCenter);
        viewHolder.textViewRight.setText(oneItem.textRight);
        viewHolder.textViewLeft.setText(oneItem.textLeft);

        return initView;
    }
}

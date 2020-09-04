package sarthak.com.dobytext;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter {
    SharedPreferences sharedpreferences;
    private String[] features;
    private String[] desc;
    private int[] images;
    private Context mContext;

    CustomAdapter(Context context, String[] features,String[] desc,int[] images)
    {
        super(context,R.layout.custom_row);
        this.features = features;
        this.desc = desc;
        this.images=images;
        this.mContext = context;
    }
    @Override
    public int getCount() {
        return features.length;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ViewHolder mViewHolder;
        mViewHolder = new ViewHolder();
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert mInflater != null;
            convertView = mInflater.inflate(R.layout.custom_row, parent, false);
            mViewHolder.imageView =  convertView.findViewById(R.id.imageView);
            mViewHolder.textView =  convertView.findViewById(R.id.textView);
            mViewHolder.textView27 =  convertView.findViewById(R.id.textView27);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        mViewHolder.imageView.setImageResource(images[position]);
        mViewHolder.textView.setText(features[position]);
        mViewHolder.textView27.setText(desc[position]);


        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
        TextView textView;
        TextView textView27;
    }
}

package cz.icure.funrss;

import java.util.List;

import cz.icure.funrss.RSSReaderUtils.FeedItem;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyFeedsAdapter extends BaseAdapter {
	private Activity activity;
    private List<FeedItem> data;
    private static LayoutInflater inflater=null;
	
	public LazyFeedsAdapter(Activity a, List<FeedItem> d) {
		this.activity = a;
		this.data = d;
		inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return this.data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).id;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.feesd_list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.title); // title
        TextView description = (TextView)vi.findViewById(R.id.description); // artist name
        ImageView thumb_image =(ImageView)vi.findViewById(R.id.list_image); // thumb image
 
        FeedItem item = data.get(position);
 
        title.setText(item.title);
        description.setText(item.description);
        thumb_image.setImageResource(R.drawable.rihanna);
        return vi;
	}

	public void notifyDataSetChanged(List<FeedItem> d) {
		this.data = d;
		super.notifyDataSetChanged();
	}

}

package cz.icure.funrss;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class LazyItemsAdapter extends BaseAdapter {
	private Activity activity;
    private List<RSSItem> data;
    private static LayoutInflater inflater=null;
	
	public LazyItemsAdapter(Activity a, List<RSSItem> d) {
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
		return data.get(position).getId();
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi=convertView;
        if(convertView==null)
            vi = inflater.inflate(R.layout.items_list_row, null);
 
        TextView title = (TextView)vi.findViewById(R.id.items_title); // title
        TextView description = (TextView)vi.findViewById(R.id.items_description); // artist name
        TextView date = (TextView)vi.findViewById(R.id.items_date); 
 
        RSSItem item = data.get(position);
 
        title.setText("" + item.getTitle());
        description.setText( "" + item.getDescription());
        date.setText("" + DateFormat.format("hh:mm, dd.MM. yyyy ", item.getDateTime()));
        
        return vi;
	}

	public void notifyDataSetChanged(List<RSSItem> d) {
		this.data = d;
		super.notifyDataSetChanged();
	}

}

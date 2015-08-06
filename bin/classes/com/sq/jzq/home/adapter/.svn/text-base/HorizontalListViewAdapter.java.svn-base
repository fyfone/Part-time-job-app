package com.sq.jzq.home.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sq.jzq.R;

public class HorizontalListViewAdapter extends BaseAdapter{  
    private List<String> mIconIDs;  
    private List<String> mTitles;  
    private Context mContext;  
    private LayoutInflater mInflater;  
//    private int selectIndex = -1;  
  public HorizontalListViewAdapter(Context context) {
	  this.mContext = context; 
	  mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);  
  }
    public void setData( List<String> titles, List<String> ids){  
        this.mIconIDs = ids;  
        this.mTitles = titles;  
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);  
    }  
    @Override  
    public int getCount() {  
        return mTitles.size();  
    }  
    @Override  
    public Object getItem(int position) {  
        return position;  
    }  
  
    @Override  
    public long getItemId(int position) {  
        return position;  
    }  
  
    @Override  
    public View getView(int position, View convertView, ViewGroup parent) {  
        ViewHolder holder;  
        if(convertView==null){  
            holder = new ViewHolder();  
            convertView = mInflater.inflate(R.layout.gallery_item, null);  
            holder.mImage=(ImageView)convertView.findViewById(R.id.imageView);  
            holder.mTitle=(TextView)convertView.findViewById(R.id.textView);  
            convertView.setTag(holder);  
        }else{  
            holder=(ViewHolder)convertView.getTag();  
        }  
        holder.mTitle.setText(mTitles.get(position)); 
        ImageLoader.getInstance().displayImage(mIconIDs.get(position), holder.mImage);
       
  
        return convertView;  
    }  
  
    private static class ViewHolder {  
        private TextView mTitle ;  
        private ImageView mImage;  
    }  
//    private Bitmap getPropThumnail(int id){  
//        Drawable d = mContext.getResources().getDrawable(id);  
//        Bitmap b = BitmapUtil.drawableToBitmap(d);  
////      Bitmap bb = BitmapUtil.getRoundedCornerBitmap(b, 100);  
//        int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);  
//        int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);  
//          
//        Bitmap thumBitmap = ThumbnailUtils.extractThumbnail(b, w, h);  
//          
//        return thumBitmap;  
//    }  
//    public void setSelectIndex(int i){  
//        selectIndex = i;  
//    }  
}
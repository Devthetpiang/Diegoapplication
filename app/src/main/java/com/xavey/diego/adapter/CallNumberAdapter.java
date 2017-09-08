package com.xavey.diego.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xavey.diego.R;
import com.xavey.diego.activity.CreateActivity;
import com.xavey.diego.activity.DashboardActivity;
import com.xavey.diego.api.model.CallNumber;
import com.xavey.diego.fragment.ProspectsFragment;

import java.util.List;

/**
 * Created by astk on 24/4/16.
 */
public class CallNumberAdapter extends BaseAdapter {
    private Context mContext;
    private List<CallNumber> mNumbers;
    private LayoutInflater mInflater;

    public CallNumberAdapter(List<CallNumber> numbers, Context context) {
        this.mNumbers = numbers;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mNumbers == null ? 0 : mNumbers.size();
    }

    public List<CallNumber> getItems() {
        return mNumbers;
    }

    public CallNumber getItem(int position) {
        return mNumbers == null ? null : mNumbers.get(position);
    }

    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View child, ViewGroup parent) {
        ViewHolder mHolder;

        final CallNumber number = mNumbers.get(position);

        if (child == null) {
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = mInflater.inflate(R.layout.list_item_prospect, null);
            mHolder = new ViewHolder();
            mHolder.tvProsNumber = (TextView) child.findViewById(R.id.list_item_pro_number);
            mHolder.tvProsStatus = (TextView) child.findViewById(R.id.list_item_pro_status);
            child.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) child.getTag();
        }

        mHolder.tvProsNumber.setText(number.getNumber());
        mHolder.tvProsStatus.setText(number.getStatus());

        return child;
    }

    static class ViewHolder{
        TextView tvProsNumber;
        TextView tvProsStatus;
    }
}

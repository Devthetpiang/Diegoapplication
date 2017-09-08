package com.xavey.diego.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.xavey.diego.activity.CreateActivity;
import com.xavey.diego.api.model.Meller;
import com.xavey.diego.R;
import com.xavey.diego.helper.AppValues;
import com.xavey.diego.helper.DBHelper;
import com.xavey.diego.helper.UtilityHelper;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by astk on 24/4/16.
 */
public class MellerAdapter extends BaseAdapter {
    private Context mContext;
    private List<Meller> mMellers;
    private LayoutInflater mInflater;

    public MellerAdapter(List<Meller> users, Context context) {
        this.mMellers = users;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return mMellers == null ? 0 : mMellers.size();
    }

    public List<Meller> getItems() {
        return mMellers;
    }

    public Meller getItem(int position) {
        return mMellers == null ? null : mMellers.get(position);
    }

    public long getItemId(int position) {
        return -1;
    }

    @Override
    public View getView(int position, View child, ViewGroup parent) {
        ViewHolder mHolder;

        final Meller user = mMellers.get(position);

        if (child == null) {
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = mInflater.inflate(R.layout.list_item_referral, null);
            mHolder = new ViewHolder();
            mHolder.tvFullName = (TextView) child.findViewById(R.id.list_item_ref_name);
            mHolder.tvNumber = (TextView) child.findViewById(R.id.list_item_ref_number);
            mHolder.tvGender = (TextView) child.findViewById(R.id.list_item_ref_gender);
            mHolder.tvDOB = (TextView) child.findViewById(R.id.list_item_ref_dob);
            mHolder.tvCreatedOn = (TextView) child.findViewById(R.id.list_item_ref_created);
            mHolder.tvReferrer = (TextView) child.findViewById(R.id.list_item_ref_referrer);
            mHolder.tvStatus = (TextView) child.findViewById(R.id.list_item_ref_status);
            child.setTag(mHolder);
        } else {
            mHolder = (ViewHolder) child.getTag();
        }

        mHolder.tvFullName.setText(user.getFull_name());
        mHolder.tvNumber.setText(user.getPhone());
        mHolder.tvGender.setText(user.getGender());
        mHolder.tvDOB.setText(UtilityHelper.getDateTime(user.getDob(), true));
        if (user.getCreatedOn() != null) {
            mHolder.tvCreatedOn.setText(user.getCreatedOn().toString());
        }
        else {
            Toast.makeText(mContext, "Invalid created date.", Toast.LENGTH_SHORT).show();
        }
        mHolder.tvReferrer.setText(user.getReferrer());
        mHolder.tvStatus.setText(user.getStatus());

        if(user.getStatus().equals(AppValues.getInstance().sync_ok)){
            mHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.green_500));
        }
        else if(user.getStatus().equals(AppValues.getInstance().sync_none)){
            mHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.grey_500));
        }
        else{
            mHolder.tvStatus.setTextColor(mContext.getResources().getColor(R.color.red_500));
        }
        return child;
    }

    static class ViewHolder{
        TextView tvFullName;
        TextView tvNumber;
        TextView tvGender;
        TextView tvDOB;

        TextView tvCreatedOn;
        TextView tvReferrer;
        TextView tvStatus;
    }
}

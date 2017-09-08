package com.xavey.diego.fragment;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.xavey.diego.R;
import com.xavey.diego.activity.CreateActivity;
import com.xavey.diego.adapter.MellerAdapter;
import com.xavey.diego.api.model.CallNumber;
import com.xavey.diego.api.model.Meller;
import com.xavey.diego.helper.AppValues;
import com.xavey.diego.helper.DBHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ReferralsFragment extends Fragment implements AdapterView.OnItemClickListener, AdapterView.OnItemLongClickListener {

    private Context mContext;
    private DBHelper dbHelper = null;
    private ListView lvMeller;

    public ReferralsFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mContext = getActivity().getApplicationContext();

        View rootView = inflater.inflate(R.layout.fragment_referrals, container, false);
        lvMeller = (ListView) rootView.findViewById(R.id.listview_referrals);
        dbHelper = new DBHelper(mContext);

        try {
            AppValues.getInstance().mellers = dbHelper.getMellers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        AppValues.getInstance().mellerAdapter = new MellerAdapter(AppValues.getInstance().mellers, mContext);
        lvMeller.setAdapter(AppValues.getInstance().mellerAdapter);
        lvMeller.setOnItemClickListener(this);
        lvMeller.setOnItemLongClickListener(this);
        return rootView;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Meller m = AppValues.getInstance().mellers.get(position);
        Intent intent = new Intent(getActivity(), CreateActivity.class);
        intent.putExtra(CreateActivity.EXTRA_ID, m.getPhone());
        startActivity(intent);
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
        DBHelper dbH = new DBHelper(mContext);
        dbH.updateRegStatus(AppValues.getInstance().mellers.get(position).getPhone(), AppValues.getInstance().sync_skip, null);
        return true;
    }



}
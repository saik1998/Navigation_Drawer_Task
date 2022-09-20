package com.firstapp.navigation_drawer_task.ui.calllog;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.CallLog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.firstapp.navigation_drawer_task.MainActivity;
import com.firstapp.navigation_drawer_task.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallLogFragment extends Fragment {
    ViewPager2 viewPager2;
    SliderAdapter sliderAdapter;

    RecyclerView recyclerView_calllog;
    CalllogModel calllogModel;
    CallLogAdapter calllogAdapter;
    List<CalllogModel> calllogModelList = new ArrayList<>();

//    Context context;
//    RecyclerView recyclerView_calllog;
//    CallLogModel callLogModel;
//    CallLogAdapter callLogAdapter;
//    List<CallLogModel> callLogModelList=new ArrayList<>();

//    //Content Providers
//    Cursor cursor;
//    ContentResolver contentResolver;
//    Button load;

    //Content Providers
    Cursor cursor;
    ContentResolver contentResolver;
    int[] drawables = {R.drawable.tele1, R.drawable.tele2, R.drawable.tele3, R.drawable.tele4, R.drawable.tele5,};


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Object CallLogFragment;
//    private Object CallLogFragment;

    public CallLogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallLogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallLogFragment newInstance(String param1, String param2) {
        CallLogFragment fragment = new CallLogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_call_log, container, false);

        recyclerView_calllog = root.findViewById(R.id.recycler_view);


        recyclerView_calllog.setHasFixedSize(true);
        recyclerView_calllog.setLayoutManager(new LinearLayoutManager(getContext()));

        calllogAdapter = new CallLogAdapter(getContext(), calllogModelList);
        recyclerView_calllog.setAdapter(calllogAdapter);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 201);
        }
        else
        {
            loadallCallLogs();
        }




        viewPager2=root.findViewById(R.id.viewpager2);
        sliderAdapter = new SliderAdapter(getContext(), drawables);
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
        viewPager2.setAdapter(sliderAdapter);


        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(8));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float v = 1 - Math.abs(position);
                page.setScaleY(0.8f + v * 0.2f);
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        return root;

    }
    private void loadallCallLogs() {

        contentResolver = getContext().getContentResolver();

        Uri uri = CallLog.Calls.CONTENT_URI;
        String[] projections = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE};
        String selection = null;
        String[] args = null;
        String order = "" + CallLog.Calls.DATE + " DESC";

        cursor = contentResolver.query(uri, projections, selection, args, order);

        if (cursor.getCount() > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));


                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy =   HH:mm");
                String dateString = formatter.format(new Date(Long.parseLong(time)));


                calllogModel = new CalllogModel(name, number, dateString, duration, type);
                calllogModelList.add(calllogModel);
                calllogAdapter.notifyDataSetChanged();
            }

        } else {
            Toast.makeText(getContext(), "No Call Logs Found", Toast.LENGTH_SHORT).show();
        }
        }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 201:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    loadallCallLogs();
                } else {
                    Toast.makeText(getContext(), "Call Log Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }


//        findViewById(R.id.Calllog).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                permissionMethod();
//            }
//
//        });








}




















































































//        recyclerView_calllog=root.findViewById(R.id.recycler_view);
//
//        //permissions
//        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CALL_LOG) != PackageManager.PERMISSION_GRANTED) {
//            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CALL_LOG}, 201);
//        } else {
//            loadallCallLogs();
//        }
//        recyclerView_calllog.setHasFixedSize(true);
//        recyclerView_calllog.setLayoutManager(new LinearLayoutManager(getContext()));
//
//        calllogAdapter = new CallLogAdapter(getContext(), calllogModelList);
//        recyclerView_calllog.setAdapter(calllogAdapter);
//
//        return root;
//
////        recyclerView_calllog.findViewById(R.id.recycler_view);
//
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//        switch (requestCode) {
//            case 201:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    loadallCallLogs();
//                } else {
//                    Toast.makeText(getContext(), "Call Log Permission Denied", Toast.LENGTH_SHORT).show();
//                }
//                break;
//        }
//    }
//    //    @Override
////    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
////        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//
//
//    private void loadallCallLogs() {
//        contentResolver = getContext().getContentResolver();
//
//        Uri uri = CallLog.Calls.CONTENT_URI;
//        String[] projections = {CallLog.Calls.CACHED_NAME, CallLog.Calls.NUMBER, CallLog.Calls.DATE, CallLog.Calls.DURATION, CallLog.Calls.TYPE};
//        String selection = null;
//        String[] args = null;
//        String order = "" + CallLog.Calls.DATE + " DESC";
//
//        cursor = contentResolver.query(uri, projections, selection, args, order);
//
//        if (cursor.getCount() > 0 && cursor != null) {
//            while (cursor.moveToNext()) {
//                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(CallLog.Calls.CACHED_NAME));
//                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(CallLog.Calls.NUMBER));
//                @SuppressLint("Range") String time = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DATE));
//                @SuppressLint("Range") String duration = cursor.getString(cursor.getColumnIndex(CallLog.Calls.DURATION));
//                @SuppressLint("Range") String type = cursor.getString(cursor.getColumnIndex(CallLog.Calls.TYPE));
//
//
//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy =   HH:mm");
//                String dateString = formatter.format(new Date(Long.parseLong(time)));
//
//
//                calllogModel = new CalllogModel(name, number, dateString, duration, type);
//                calllogModelList.add(calllogModel);
//                calllogAdapter.notifyDataSetChanged();
//            }
//
//        } else {
//            Toast.makeText(getContext(), "No Call Logs Found", Toast.LENGTH_SHORT).show();
//        }

//viewPager2=root.findViewById(R.id.viewpager1);
//        sliderAdapter = new SliderAdapter(getContext(), drawables);
//        viewPager2.setClipToPadding(false);
//        viewPager2.setClipChildren(false);
//        viewPager2.setOffscreenPageLimit(3);
//        viewPager2.getChildAt(0).setOverScrollMode(View.OVER_SCROLL_NEVER);
//        viewPager2.setAdapter(sliderAdapter);
//
//
//        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
//        compositePageTransformer.addTransformer(new MarginPageTransformer(8));
//        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
//@Override
//public void transformPage(@NonNull View page, float position) {
//        float v = 1 - Math.abs(position);
//        page.setScaleY(0.8f + v * 0.2f);
//        }
//        });
//        viewPager2.setPageTransformer(compositePageTransformer);









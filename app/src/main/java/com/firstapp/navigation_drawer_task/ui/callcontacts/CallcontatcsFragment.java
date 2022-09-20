package com.firstapp.navigation_drawer_task.ui.callcontacts;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firstapp.navigation_drawer_task.R;
import com.firstapp.navigation_drawer_task.ui.calllog.SliderAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CallcontatcsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CallcontatcsFragment extends Fragment {
    RecyclerView recycler;
    ContactModel contactModel;
    ContactAdapter contactAdapter;
    List<ContactModel> contactModelList=new ArrayList<>();

    List<ContactModel> filterlist =new ArrayList<>();

    Cursor cursor;
    ContentResolver contentResolver;
    SearchView searchView;

    AlertDialog alertDialog;

    TextView count;

    ViewPager2 viewPager2;
    SliderAdapter sliderAdapter;
    int[] drawables = {R.drawable.tele1, R.drawable.tele2, R.drawable.tele3, R.drawable.tele4, R.drawable.tele5,};


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CallcontatcsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CallcontatcsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CallcontatcsFragment newInstance(String param1, String param2) {
        CallcontatcsFragment fragment = new CallcontatcsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_callcontatcs, container, false);


        recycler = root.findViewById(R.id.recycler_contact);
        searchView = root.findViewById(R.id.search_view_contact);
        count = root.findViewById(R.id.text_view_contact);

        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(getContext()));

        contactAdapter = new ContactAdapter(getContext(), contactModelList);
        recycler.setAdapter(contactAdapter);

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_CONTACTS}, 101);
        }
        else
        {
            readAllContacts();
        }
        viewPager2 = root.findViewById(R.id.viewpagercontact);
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
    private void readAllContacts() {

        contactModelList.clear();
        contentResolver = getContext().getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection = {ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME, ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Photo.PHOTO_URI};
        String selection = null;
        String[] args = null;
        String order = ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " asc";

        cursor = contentResolver.query(uri, projection, selection, args, order);
//
//        Cursor cursor =  managedQuery(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.RAW_CONTACT_ID, null, null);


        if (cursor.getCount() > 0 && cursor != null) {
            while (cursor.moveToNext()) {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                @SuppressLint("Range") String number = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                @SuppressLint("Range") String photo = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Photo.PHOTO_URI));


                contactModel=new ContactModel(name,number,photo);
                contactModelList.add(contactModel);


                contactAdapter = new ContactAdapter(getContext(), contactModelList);
                recycler.setAdapter(contactAdapter);


            }
        }
        else
        {
            Toast.makeText(getContext(), "No contacts found in your phone", Toast.LENGTH_SHORT).show();
        }



        searchView.setQueryHint("Search among" + " " + contactModelList.size() + " " + "contact(s)");//count will be adding the search view

        searchView.clearFocus();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {

                filterlist.clear();

                if (s.toString().isEmpty()) {
                    count.setVisibility(View.GONE);//once search the count count will automatically  gone.

                    recycler.setAdapter(new ContactAdapter(getContext(), contactModelList));
                    contactAdapter.notifyDataSetChanged();
                } else
                {
                    Filter(s.toString());
                }
                return true;
            }
        });
//
    }

    private void Filter(String text)
    {
        count.setVisibility(View.VISIBLE);//whenever you want see the all contacts in search view

        for (ContactModel post: contactModelList)
        {
            if(post.getName().toLowerCase().contains(text.toLowerCase()))
            {
                filterlist.add(post);
            }
        }
        recycler.setAdapter(new ContactAdapter(getContext(),filterlist));

        contactAdapter.notifyDataSetChanged();

        count.setText(" "+filterlist.size()+ " "+ "CONTATCS FOUND");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case 101:
                if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
                {
                    readAllContacts();
                }
                else
                {
                    Toast.makeText(getContext(), "Permission Denied", Toast.LENGTH_SHORT).show();
                }
                break;


        }
    }








}









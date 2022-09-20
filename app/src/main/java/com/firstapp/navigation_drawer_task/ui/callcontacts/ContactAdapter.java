package com.firstapp.navigation_drawer_task.ui.callcontacts;

import android.content.Context;
import android.hardware.lights.LightState;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.navigation_drawer_task.R;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ContactHolder> {
    Context context;
    List<ContactModel>contactModelList=new ArrayList<>();

    public ContactAdapter(Context context, List<ContactModel> contactModelList) {
        this.context = context;
        this.contactModelList = contactModelList;
    }

    @NonNull
    @Override
    public ContactAdapter.ContactHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.custom_contact,parent,false);

        return new ContactHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactAdapter.ContactHolder holder, int i) {
        ContactModel contactModel=contactModelList.get(i);
        holder.contactname.setText(contactModelList.get(i).getName());
        holder.contactnumber.setText(contactModelList.get(i).getNumber());
        holder.shapeableImageView.setImageURI(contactModelList.get(i).getImageUri());

        if (contactModelList.get(i).getImageUri()!=null)
        {
            holder.shapeableImageView.setImageURI(Uri.parse(""+contactModelList.get(i).getImage()));

        }
        else
        {
            Log.i(null,""+contactModelList.get(i).getImage());

        }


    }

    @Override
    public int getItemCount() {
        return contactModelList.size();
    }

    public class ContactHolder extends RecyclerView.ViewHolder {
        TextView contactname,contactnumber;

        ShapeableImageView shapeableImageView;
        ImageView contactimage;
        public ContactHolder(@NonNull View itemView) {

            super(itemView);

            contactname=itemView.findViewById(R.id.contact_name);
            contactnumber=itemView.findViewById(R.id.contact_number);
            shapeableImageView=itemView.findViewById(R.id.contact_shap);

        }
    }
}

//    ContactModel contactModel=contactModelList.get(i);
//
//        holder.name.setText(contactModelList.get(i).getName());
//                holder.number.setText(contactModelList.get(i).getNumber());
//                holder.shapeableImageView.setImageURI(contactModelList.get(i).getImageUri());
//
//                if (contactModelList.get(i).getImage()!=null)
//                {
//                holder.shapeableImageView.setImageURI(Uri.parse(""+contactModelList.get(i).getImage()));
//
//                }
//                else
//                {
//                Log.i(null,""+contactModelList.get(i).getImage());
//
//                }
//
//                }
//
//@Override
//public int getItemCount() {
//        return contactModelList.size();
//        }
//
//public class holder extends RecyclerView.ViewHolder {
//    ShapeableImageView shapeableImageView;
//    TextView name;
//    TextView number;
//
//
//    public holder(@NonNull View itemView) {
//        super(itemView);
//
//        shapeableImageView=itemView.findViewById(R.id.contact_shap);
//        name=itemView.findViewById(R.id.contact_name);
//        number=itemView.findViewById(R.id.contact_number);


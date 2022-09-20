package com.firstapp.navigation_drawer_task.ui.calllog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firstapp.navigation_drawer_task.R;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CallLogAdapter extends RecyclerView.Adapter<CallLogAdapter.CallHolder> {
    Context context;
    List<CalllogModel> callLogModelList = new ArrayList<>();

    public CallLogAdapter(Context context, List<CalllogModel> callLogModelList) {
        this.context = context;
        this.callLogModelList = callLogModelList;
    }

    @NonNull
    @Override
    public CallHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_call_log, parent, false);
        return new CallHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CallHolder holder, int position) {
        holder.name.setText(callLogModelList.get(position).getName());
        holder.number.setText(callLogModelList.get(position).getNumber());
        holder.time.setText(callLogModelList.get(position).getTime());
        holder.duration.setText(callLogModelList.get(position).getDuration() + " Sec");

         holder.type.setText(callLogModelList.get(position).getType());





        String callDuration=callLogModelList.get(position).duration;
        int seconds=Integer.parseInt(callDuration);


        int p1=seconds%60;//30
        int p2=seconds/60;//
        int p3=p2%60;
        p2=p2/60;

        if(p3>1)
        {
            holder.duration.setText(p2+"hrs:"+p3+"mins:"+p1+"secs");

        }
        else if(p3==1)
        {
            holder.duration.setText(p3+"mins"+p1+"secs");

        }
        else if(p3==0 && p1==0)
        {
            holder.duration.setText(p1+"secs");

        }
        else if(p3==0)
        {
            holder.duration.setText(p1+"secs");

        }
        else
        {
            holder.duration.setText(p3+"mins"+p1+"secs");
        }


        if (callLogModelList.get(position).getType().equals("1")) {
            holder.type.setText("INCOMING_TYPE");
        } else if (callLogModelList.get(position).getType().equals("2")) {
            holder.type.setText("OUTGOING_TYPE");
        } else if (callLogModelList.get(position).getType().equals("3")) {
            holder.type.setText("MISSED_TYPE");
        } else if (callLogModelList.get(position).getType().equals("4")) {
            holder.type.setText("VOICEMAIL_TYPE");
        } else if (callLogModelList.get(position).getType().equals("5")) {
            holder.type.setText("REJECTED_TYPE");
        } else if (callLogModelList.get(position).getType().equals("6")) {
            holder.type.setText("BLOCKED_TYPE");
        } else if (callLogModelList.get(position).getType().equals("7")) {
            holder.type.setText("ANSWERED_EXTERNALLY_TYPE");
        } else {
            /*
            * INCOMING_TYPE
OUTGOING_TYPE
MISSED_TYPE
VOICEMAIL_TYPE
REJECTED_TYPE
BLOCKED_TYPE
ANSWERED_EXTERNALLY_TYPE*/
        }
    }

    public static int[] splitToComponentTimes(BigDecimal bigDecimal) {
        long longval = bigDecimal.longValue();
        int hours = (int) (longval / 3600);
        int remainder = (int) (longval - hours * 3600);
        int mins = remainder / 60;
        remainder = remainder - mins * 60;
        int secs = remainder;

        int[] ints = {hours, mins, secs};
        return ints;

    }

    @Override
    public int getItemCount() {
        return callLogModelList.size();
    }

    public static class CallHolder extends RecyclerView.ViewHolder {

        TextView name, number, time, duration, type;

        public CallHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            number = itemView.findViewById(R.id.number);
            time = itemView.findViewById(R.id.timeStamp);
            duration = itemView.findViewById(R.id.duration);
            type = itemView.findViewById(R.id.type);


        }
    }
}



//        public static String convertSecond(int seconds) {
//            int h = seconds / 3600;
//            int m = (seconds % 3600) / 60;
//            int s = seconds % 60;
//            String sh = (h > 0 ? String.valueOf(h) + " " + "h" : " ");
//            String sm = (h < 10 && m > 0 && h > 0 ? "0" : " ") + (m > 0 ? (h > 0 && s == 0 ? String.valueOf(m) : String.valueOf(m) + " " + "min") : "");
//            String ss = (s == 0 && (h > 0 || m > 0) ? "" : (s < 10 && (h > 0 || m > 0) ? "0" : "") + String.valueOf(s) + " " + "sec");
//
//            return sh + (h > 0 ? " " : "") + sm + (m > 0 ? " " : "") + ss;
//        }
//
//        int seconds = 3661;
//        String duration1 = convertSecond(seconds);
//    }


//        public static int[] splitToComponentTimes(BigDecimal bigDecimal)
//        {
//            long longval=bigDecimal.longValue();
//            int hours= (int) (longval/3600);
//            int remainder= (int) (longval-hours*3600);
//            int mins=remainder/60;
//            remainder=remainder-mins*60;
//            int secs=remainder;
//
//            int[] ints={hours,mins,secs};
//            return ints;
//
//        }

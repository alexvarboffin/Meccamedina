package com.walhalla.ytlib.message;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;

import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.walhalla.ytlib.R;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.VH> {

    public interface MessageAdapterCallback{
        String getLoggedInUserName();
    }

    private static final int TYPE_OUT_MESSAGE = 0;
    private static final int TYPE_IN_MESSAGE = 1;

    private List<Message> data;

    static class VH extends RecyclerView.ViewHolder {
        private TextView messageText;
        private TextView messageUser;
        private TextView messageTime;

        public VH(@NonNull View v) {
            super(v);
            messageText = v.findViewById(R.id.message_text);
            messageUser = v.findViewById(R.id.message_user);
            messageTime = v.findViewById(R.id.message_time);
        }

        protected void bind(Message model, int position) {
            messageText.setText(model.text);
            messageUser.setText(model.user);
            // Format the date before showing it
            messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)", model.time));
        }
    }

    private MessageAdapterCallback activity;

    public MessageAdapter(MessageAdapterCallback activity, List<Message> data/*, DatabaseReference ref*/) {
        this.activity = activity;
        this.data = data;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        VH viewHolder;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        if (viewType == TYPE_OUT_MESSAGE) {
            final View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_out_message, null, false);
            viewHolder = new VH(layoutView);
        } else if (viewType == TYPE_IN_MESSAGE) {
            View v2 = inflater.inflate(R.layout.item_in_message, parent, false);
            viewHolder = new VH(v2);
        } else {
            View view = inflater.inflate(R.layout.item_in_message, parent, false);
            viewHolder = new VH(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        Message chatMessage = data.get(position);
        holder.bind(chatMessage, position);
    }

    @Override
    public int getItemViewType(int position) {
        // return a value between 0 and (getViewTypeCount - 1)
        //return position % 2;

        if (data.get(position).user_id.equals(activity.getLoggedInUserName())) {
            return TYPE_OUT_MESSAGE;
        } else {
            return TYPE_IN_MESSAGE;
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
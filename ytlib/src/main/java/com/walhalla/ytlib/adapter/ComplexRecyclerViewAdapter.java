package com.walhalla.ytlib.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.walhalla.ytlib.R;
import com.walhalla.ytlib.domen.APIError;
import com.walhalla.ytlib.domen.YT_Entry;
import com.walhalla.ytlib.domen.YT_MediaGroup;

import java.util.ArrayList;
import java.util.List;


public class ComplexRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public interface ChildItemClickListener {
        void onClick(View v, int position);
    }

    private final int TYPE_LESSON = 111;
    private final int TYPE_ERROR = 121;
    private final int TYPE_DEFAULT = 131;

    private ChildItemClickListener childItemClickListener;


    private List<Object> arr = new ArrayList<>();
    private Context mContext;

    // Provide MyWeakReference suitable constructor (depends on the kind of dataset)
    public ComplexRecyclerViewAdapter(List<Object> arr) {
        this.arr = arr;
    }

    public ComplexRecyclerViewAdapter() {
    }

//    public ComplexRecyclerViewAdapter(Object err) {
//        if (arr == null) {
//            arr = new ArrayList<>();
//        }
//        arr.add(err);
//    }

    public ComplexRecyclerViewAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setChildItemClickListener(ChildItemClickListener listener) {
        childItemClickListener = listener;
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return (arr == null) ? 0 : arr.size();
    }

    //Returns the view type of the item at position for the purposes of view recycling.
    @Override
    public int getItemViewType(int position) {
        if (arr.get(position) instanceof YT_Entry) {
            return TYPE_LESSON;
        } else if (arr.get(position) instanceof APIError) {
            return TYPE_ERROR;
        }
        return TYPE_DEFAULT;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        if (viewType == TYPE_LESSON) {
            View v1 = inflater.inflate(R.layout.row_entry_item, viewGroup, false);
            return new LessonViewHolder(v1, childItemClickListener);
        } else if (viewType == TYPE_ERROR) {
            View v2 = inflater.inflate(R.layout.row_item_error, viewGroup, false);
            return new ViewHolder2(v2);
        }
        View v = inflater.inflate(android.R.layout.simple_list_item_1, viewGroup, false);
        return new RecyclerViewSimpleTextViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case TYPE_LESSON:
                LessonViewHolder vh1 = (LessonViewHolder) holder;
                configureLessonViewHolder(vh1, position);
                break;

            case TYPE_ERROR:
                ViewHolder2 vh2 = (ViewHolder2) holder;
                configureViewHolder2(vh2, position);
                break;

            default:
                RecyclerViewSimpleTextViewHolder vh = (RecyclerViewSimpleTextViewHolder) holder;
                configureDefaultViewHolder(vh, position);
                break;
        }
    }


    //private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
    //vh.getLabel().setText((CharSequence) arr.get(position));
    //}

    private void configureLessonViewHolder(LessonViewHolder holder, int position) {
        YT_Entry entry = (YT_Entry) arr.get(position);
        YT_MediaGroup mg = entry.getMedia_group();

        if (entry != null) {

//            @@@@@(mContext)
//                    .load(mg.getMedia_thumbnail())
//                    //.animate(R.anim.abc_fade_in)
//                    //.centerCrop()
//                    .into(holder.imageView);
//
            holder.text1.setText(entry.getId());
////            holder.tvName.setText(YT_Entry.getName());
////            holder.tvSteps.setText(YT_Entry.getSteps());
////            holder.tvRate.setText(YT_Entry.getRate());
////            holder.tvLock.setText(YT_Entry.getLock());
////            holder.tvOrder.setText(YT_Entry.getOrder());
////            holder.tvBlack.setText(YT_Entry.getIcon());
////            holder.tvDiamond.setText(YT_Entry.getDate());
//
        }
    }

    public void swap(List<Object> data) {
//        if (data == null || data.size() == 0) {
//            return;
//        }
//        if (arr != null && arr.size() > 0) {
//            arr.clear();
//        }
//        arr.clear();
//        arr.addAll(data);
//        this.notifyDataSetChanged();

        this.arr = data;
        this.notifyDataSetChanged();
    }

    private void configureViewHolder2(ViewHolder2 vh2, int positon) {
        //vh2.getImageView().setImageResource(R.drawable.sample_golden_gate);
        APIError error = (APIError) arr.get(positon);
        if (error != null) {
            vh2.error_msg.setText(error.error);
        }
    }

    //========================================================================================
    private static class LessonViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final ChildItemClickListener presenter;
        ImageView imageView;
        TextView text1;
//
//        @BindView(R.id.tv_id)
//        TextView tvId;
//        @BindView(R.id.tv_name)
//        TextView tvName;
//        @BindView(R.id.tv_steps)
//        TextView tvSteps;
//        @BindView(R.id.tv_rate)
//        TextView tvRate;
//        @BindView(R.id.tv_lock)
//        TextView tvLock;
//        @BindView(R.id.tv_order)
//        TextView tvOrder;
//        @BindView(R.id.tv_black)
//        TextView tvBlack;
//        @BindView(R.id.tv_diamond)
//        TextView tvDiamond;

        LessonViewHolder(View v, ChildItemClickListener presenter) {
            super(v);
            v.setOnClickListener(this);
            imageView = v.findViewById(R.id.iv_icon);
            text1 = v.findViewById(android.R.id.text1);

            this.presenter = presenter;
        }


        @Override
        public void onClick(View v) {
            presenter.onClick(v, getAdapterPosition());//clicker...
        }
    }

    private void configureDefaultViewHolder(RecyclerViewSimpleTextViewHolder vh, int position) {
        //vh.getLabel().setText((CharSequence) arr.get(position));
    }

    class RecyclerViewSimpleTextViewHolder extends RecyclerView.ViewHolder {
        TextView text1;

        RecyclerViewSimpleTextViewHolder(View itemView) {
            super(itemView);
            text1 = itemView.findViewById(android.R.id.text1);
        }
    }


    //Err
    final private static class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView response;
        TextView error_msg;

        ViewHolder2(View v2) {
            super(v2);
            response = v2.findViewById(R.id.tv_response);
            error_msg = v2.findViewById(R.id.tv_error_msg);
        }
    }
}

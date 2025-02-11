package com.walhalla.ytlib.adapter;

import android.animation.Animator;
import android.content.Context;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.walhalla.ytlib.R;
import com.walhalla.ytlib.adapter.holder.VideoRowViewHolder;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.walhalla.ytlib.databinding.ItemVideoBinding;
import com.walhalla.ytlib.domen.ListEntryUI;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.recyclerview.internal.ViewHelper;

public class ComplexAdapter extends UltimateViewAdapter<RecyclerView.ViewHolder> {


    private final Context context;
    private final Drawable drawable;
    private List<ListEntryUI> DATA;

    public ComplexAdapter(Context context, ArrayList<ListEntryUI> list) {
        this.context = context;
        this.DATA = list;
        this.drawable = context.getResources().getDrawable(R.drawable.no_thumbnail);
    }


    //Variables
    private int mLastPosition = 5;
    private final Interpolator mInterpolator = new LinearInterpolator();

    public RecyclerView.ViewHolder getViewHolder(View var1) {
        return new UltimateRecyclerviewViewHolder(var1);
    }

//    @Override
//    public RecyclerView.ViewHolder newFooterHolder(View view) {
//        return new UltimateRecyclerviewViewHolder<>(view);
//    }
//
//    @Override
//    public RecyclerView.ViewHolder newHeaderHolder(View view) {
//        return new UltimateRecyclerviewViewHolder<>(view);
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        @NonNull ItemVideoBinding binding = ItemVideoBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new VideoRowViewHolder(binding);
    }


    @Override
    public int getItemCount() {
        return DATA.size();
    }

    @Override
    public int getAdapterItemCount() {
        return DATA.size();
    }

    @Override
    public long generateHeaderId(int position) {
        return 0L;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        ListEntryUI current;

        if (position < getItemCount()
                && (customHeaderView != null ? position <= DATA.size() : position < DATA.size())
                && (customHeaderView == null || position > 0)) {

            current = DATA.get(customHeaderView != null ? position - 1 : position);


            int var3;
            if (holder.getAdapterPosition() < this.getItemCount()) {
                label48:
                {
                    if (this.customHeaderView != null) {
                        if (holder.getAdapterPosition() > this.DATA.size()) {
                            break label48;
                        }
                    } else if (holder.getAdapterPosition() >= this.DATA.size()) {
                        break label48;
                    }

                    if (this.customHeaderView == null || holder.getAdapterPosition() > 0) {

                        if (this.customHeaderView != null) {
                            var3 = holder.getAdapterPosition() - 1;
                        } else {
                            var3 = holder.getAdapterPosition();
                        }


                        if (holder instanceof VideoRowViewHolder) {
                            VideoRowViewHolder viewHolder = ((VideoRowViewHolder) holder);
                            viewHolder.bind(current);
                        }

//                    this.IMAGE_LOADER.get((String)var7.get("url"),
//                            ImageLoader.getImageListener(((ComplexAdapter.ViewHolder)holder).mImgThumbnail, 2130903040, 2130903040));
                    }
                }
            }

            if (holder.getAdapterPosition() <= this.mLastPosition) {
                ViewHelper.clear(holder.itemView);
            } else {
                Animator[] animations = this.getAdapterAnimations(holder.itemView, AdapterAnimationType.SlideInLeft);
                int var4 = animations.length;

                for (var3 = 0; var3 < var4; ++var3) {
                    Animator var8 = animations[var3];
                    var8.setDuration(300L).start();
                    var8.setInterpolator(this.mInterpolator);
                }

                this.mLastPosition = holder.getAdapterPosition();
            }
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.stick_header_item, parent, false);
        return new RecyclerView.ViewHolder(view) {
        };
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        TextView textView = holder.itemView.findViewById(R.id.stick_text);
        ListEntryUI listEntry = getItem(position);
        //textView.setText(String.valueOf(listEntry.toString().charAt(0))); //First letter in header

        //holder.itemView.setBackgroundColor(111parseColor("#AA70DB93"));
        holder.itemView.setBackgroundResource(R.color.adapter_bg_color);
//        ImageView imageView = holder.itemView.findViewById(R.id.stick_img);
//
//        SecureRandom imgGen = new SecureRandom();
//        switch (imgGen.nextInt(3)) {
//            case 0:
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                break;
//            case 1:
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                break;
//            case 2:
//                imageView.setImageResource(R.mipmap.ic_launcher);
//                break;
//        }
    }

    public ListEntryUI getItem(int position) {
        if (customHeaderView != null)
            position--;
        // URLogs.d("position----"+position);
        if (position >= 0 && position < DATA.size())
            return DATA.get(position);
        else return new ListEntryUI();
    }
//    @Override
//    public void onItemDismiss(int position) {
//        if (position > 0) {
//            remove(position);
//            // notifyItemRemoved(position);
////        notifyDataSetChanged();
//            super.onItemDismiss(position);
//        }
//
//    }

    /**
     * Update data from adapter
     *
     * @param arr
     */
    public void swap(List<ListEntryUI> arr) {
        if (arr == null || arr.isEmpty()) {
            return;
        }
        if (DATA == null) {
            DATA = new ArrayList<>();
        } else {
            if (!DATA.isEmpty()) {
                DATA.clear();
            }
        }
        DATA.addAll(arr);
        notifyDataSetChanged();
    }
}

package com.walhalla.ytlib.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.walhalla.ytlib.R;


public class ImageUtils {

    public static void loadThumbnails(Context context, String urlThumbnails, ImageView imgThumbnail) {
        try {
            Glide.with(context)
                    .load(urlThumbnails)
                    .diskCacheStrategy(DiskCacheStrategy.ALL) // Кэширование всех версий изображения (оригинал и преобразованные)
                    .skipMemoryCache(false)
                    .error(
//                                                GlideApp.with(viewHolder.getmImgThumbnail())
//                                                .load(R.drawable.default_live)
                            R.drawable.default_live
                    )
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(@NonNull Drawable resource, @NonNull Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(imgThumbnail);
        } catch (Exception ignored) {
        }
    }
}

package com.walhalla.ytlib.adapter.holder;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.marshalchen.ultimaterecyclerview.UltimateRecyclerviewViewHolder;
import com.walhalla.ui.plugins.Launcher;
import com.walhalla.ytlib.R;
import com.walhalla.ytlib.databinding.ItemVideoBinding;
import com.walhalla.ytlib.domen.ListEntryUI;
import com.walhalla.ytlib.utils.ImageUtils;


//<Object>
public class VideoRowViewHolder extends UltimateRecyclerviewViewHolder {


    private final ItemVideoBinding binding;

    public VideoRowViewHolder(ItemVideoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public void bind(ListEntryUI current) {


        ImageUtils.loadThumbnails(binding.getRoot().getContext()
                , current.getUrlThumbnails()
                , binding.imgThumbnail);
        binding.txtDuration.setText(current.getDuration());
        binding.txtPublishedAt.setText(current.getPublishedAt());
        binding.txtTitle.setText(current.title);
        binding.poweredbyYouTube.setOnClickListener(v -> {
            showPopupMenu(v, current, getAdapterPosition());
        });
    }

    PopupMenu popup;
    private void showPopupMenu(View v, ListEntryUI current, int adapterPosition) {
        popup = new PopupMenu(v.getContext(), v);
        MenuInflater inflater = popup.getMenuInflater();
        Menu menu = popup.getMenu();
        inflater.inflate(R.menu.popup_topic, menu);
        //MenuUtils.setForceShowIcon(popup);

        popup.setOnMenuItemClickListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.action_open_on_youtube) {
                if (popup != null) {
                    popup.dismiss();
                }
                Launcher.openBrowser(v.getContext(), "https://www.youtube.com/watch?v="+current.videoId);
                return true;
            }
//                case R.id.action_share_image:
//                    popup.dismiss();
//                    showWatermark(tv_quotes_watermark);
//                    Bitmap bitmap = Bitmap.createBitmap(relativeLayout.getWidth(), relativeLayout.getHeight(),
//                            Bitmap.Config.ARGB_8888);
//                    Canvas canvas = new Canvas(bitmap);
//                    relativeLayout.draw(canvas);
//                    Uri uri = presenter.getLocalBitmapUri(bitmap);
//                    hideWatermark(tv_quotes_watermark);
//                    String appName = getString(R.string.app_name);
//
//                    String extra = status.text;
//                    if (QTextUtils.isAuthorNotEmpty(status.author)) {
//                        extra = extra + "\n" + "— " + status.author + "\n" + appName;
//                    }
//
//                    Intent intent = Share.makeImageShare(extra);
//                    intent.putExtra(Intent.EXTRA_STREAM, uri);
//                    intent.putExtra(Intent.EXTRA_SUBJECT, appName);
//                    //intent.putExtra(Intent.EXTRA_TITLE, appName);
//
//                    //BugFix
//                    //java.lang.SecurityException: Permission Denial: reading androidx.core.content.FileProvider
//                    Intent chooser = Intent.createChooser(intent, appName);
//                    List<ResolveInfo> resInfoList = getActivity().getPackageManager()
//                            .queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);
//                    for (ResolveInfo resolveInfo : resInfoList) {
//                        String packageName = resolveInfo.activityInfo.packageName;
//                        getActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
//                                | Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                    }
//
//                    getActivity().startActivity(chooser);
//                    Toast.makeText(getActivity(), share_as_image, Toast.LENGTH_SHORT).show();
//                    return true;
            return false;
        });
        popup.show();
    }
}
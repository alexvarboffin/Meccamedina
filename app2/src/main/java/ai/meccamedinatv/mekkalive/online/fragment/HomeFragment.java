//package com.walhalla.meccamedina.fragment;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.DefaultItemAnimator;
//import androidx.recyclerview.widget.DividerItemDecoration;
//import androidx.recyclerview.widget.GridLayoutManager;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.RadioButton;
//
//import com.walhalla.meccamedina.domen.APIError;
//import com.walhalla.meccamedina.mvp.presenter.Home_F_Presenter;
//
//import com.walhalla.meccamedina.mvp.view.Home_F_View;
//
//import com.walhalla.meccamedina.adapter.ComplexRecyclerViewAdapter;
//
//import ai.meccamedinatv.mekkalive.online.R;
//
//import moxy.presenter.InjectPresenter;
//
//import com.google.android.material.snackbar.Snackbar;
//
//import com.walhalla.meccamedina.fragment.common.BaseFragment;
//
//import java.util.ArrayList;
//import java.util.List;

//public class HomeFragment extends BaseFragment implements Home_F_View {
//
//    private static final String ARG_KEY = "arg_key";
//
//    @InjectPresenter
//    Home_F_Presenter mHomePresenter;
//
//    private static final String KEY_LAYOUT_MANAGER = "layoutManager";
//    private static final int SPAN_COUNT = 2;
//
//    //Variables
//    private ComplexRecyclerViewAdapter mAdapter;
//    private String key;
//
//    private enum LayoutManagerType {
//        GRID_LAYOUT_MANAGER,
//        LINEAR_LAYOUT_MANAGER
//    }
//
//    protected LayoutManagerType mCurrentLayoutManagerType;
//
//    protected RadioButton mLinearLayoutRadioButton;
//    protected RadioButton mGridLayoutRadioButton;
//
//    protected RecyclerView mRecyclerView;
//    protected RecyclerView.LayoutManager mLayoutManager;
//
//    List<Object> mDataset = new ArrayList<>();
//
////    public HomeFragment() {
////    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//
//        if (savedInstanceState != null) {
//            key = savedInstanceState.getString(ARG_KEY);
//        }
//        if (getArguments() != null) {
//            key = getArguments().getString(ARG_KEY);
//        }
//        // Initialize dataset, this data would usually come from MyWeakReference local content provider or
//        // remote server.
//        initDataset();
//    }
//
//    private void initDataset() {
//        mDataset.add(new APIError("Error"));
//    }
//
//    public static HomeFragment newInstance(String key) {
//        HomeFragment fragment = new HomeFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_KEY, key);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(@NonNull final LayoutInflater inflater, final ViewGroup container,
//                             final Bundle savedInstanceState) {
//
//
//        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
//        mRecyclerView = rootView.findViewById(R.id.recycler_view);
//
//        // LinearLayoutManager is used here, this will layout the elements in MyWeakReference similar fashion
//        // to the way ListView would layout elements. The RecyclerView.LayoutManager defines how
//        // elements are laid out.
//        mLayoutManager = new LinearLayoutManager(getActivity());
//
//        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
//
//        if (savedInstanceState != null) {
//            // Restore saved layout manager type.
//            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
//                    .getSerializable(KEY_LAYOUT_MANAGER);
//        }
//        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);
//
//        if (mAdapter == null) {
//            mAdapter = new ComplexRecyclerViewAdapter(mDataset);
//            mAdapter.setChildItemClickListener(mHomePresenter);
//        }
//        // Set CustomAdapter as the adapter for RecyclerView.
//        mRecyclerView.setAdapter(mAdapter);
//
////        mLinearLayoutRadioButton = rootView.findViewById(R.id.linear_layout_rb);
////        mLinearLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
////            }
////        });
////        mGridLayoutRadioButton = rootView.findViewById(R.id.grid_layout_rb);
////        mGridLayoutRadioButton.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
////            }
////        });
//        return rootView;
//    }
//
//    /**
//     * Set RecyclerView's LayoutManager to the one given.
//     *
//     * @param layoutManagerType Type of layout manager to switch to.
//     */
//    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
//        int scrollPosition = 0;
//
//        // If MyWeakReference layout manager has already been set, get current scroll position.
//        if (mRecyclerView.getLayoutManager() != null) {
//            scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
//                    .findFirstCompletelyVisibleItemPosition();
//        }
//
//        switch (layoutManagerType) {
//            case GRID_LAYOUT_MANAGER:
//                mLayoutManager = new GridLayoutManager(getActivity(), SPAN_COUNT);
//                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
//                break;
//            case LINEAR_LAYOUT_MANAGER:
//                mLayoutManager = new LinearLayoutManager(getActivity());
//                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
//                break;
//            default:
//                mLayoutManager = new LinearLayoutManager(getActivity());
//                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
//        }
//
//        // use this if you want the mRecyclerView to look like MyWeakReference vertical list view
//        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        final int columns = getResources().getInteger(R.integer.gallery_columns);
//
//        //mRecyclerView.setItemAnimator(new SlideInUpAnimator());
//        //mRecyclerView.setLayoutManager(mLayoutManager);
//
//        // use this if you want the mRecyclerView to look like MyWeakReference grid view
//        mRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), columns));
//
////        mRecyclerView.addItemDecoration(
////                new SimpleDividerItemDecoration(getResources())
////                //new ItemOffsetDecoration(20)
////                //new PrettyItemDecoration()
////        );
//
//        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
//        mRecyclerView.scrollToPosition(scrollPosition);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull final View view, final Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle savedInstanceState) {
//        // Save currently selected layout manager.
//        savedInstanceState.putSerializable(KEY_LAYOUT_MANAGER, mCurrentLayoutManagerType);
//        if (key != null) {
//            savedInstanceState.putString(ARG_KEY, key);
//        }
//        super.onSaveInstanceState(savedInstanceState);
//    }
//
//    //MVP
//    @Override
//    public void showData(List<Object> arr) {
//        DLog.d(arr.get(1));
//        mAdapter.swap(arr);
//    }
//
//    @Override
//    public void showError(int res) {
//        showError(getString(res));
//    }
//
//    @Override
//    public void showError(String error) {
//        DLog.d(error);
//        //Toast.makeText(getContext(), error, Toast.LENGTH_SHORT).show();
//        if (getActivity() != null) {
//            Snackbar.make(getActivity().getWindow().getDecorView(),
//                    "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
//        }
//    }
//
//    @Override
//    public void getVideoRequest(String[] yt_entry_ids, int position) {
//        startActivity(PlayerActivity.getInstance(getContext(), yt_entry_ids, position, key));
//    }
//}

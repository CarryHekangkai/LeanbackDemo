package com.listgrid.demo;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.DiffCallback;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;

import com.listgrid.demo.bean.Model;
import com.listgrid.demo.bean.Movie;
import com.listgrid.demo.presenter.ClassifyMainPresenter;
import com.listgrid.demo.presenter.ClassifyMorePresenter;
import com.listgrid.demo.presenter.GridViewPresenter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends Activity {

    private ConstraintLayout mLayout;
    private ImageView mLeftBar;

    private VerticalGridView mRv1;
    private VerticalGridView mRv2;
    private VerticalGridView mGridRv;

    private ArrayObjectAdapter mainAdapter;
    private ArrayObjectAdapter moreAdapter;
    private ArrayObjectAdapter mGridAdapter;

    private String[] stringList;  // 条目列表
    private List<Movie> mGridList; // 内容列表
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        mLayout = findViewById(R.id.mLayout);
        mLeftBar = (ImageView) findViewById(R.id.leftBar);

        mRv1 = (VerticalGridView) findViewById(R.id.rv_catalog1);
        mRv2 = (VerticalGridView) findViewById(R.id.rv_catalog2);
        mGridRv = (VerticalGridView) findViewById(R.id.listGridView);

        
        // 初始化一级分类
        initRv1();

        // 初始化更多分类
        initRv2(stringList);

        // 初始化GridView
        getMovie1Data();
        initGridRv();

        mRv2.requestFocus();

    }

    private void initRv1(){
        stringList = Model.LISTVIEWTXT;    // 获取数据

        mRv1.setNumColumns(1);
        ClassifyMainPresenter mainPresenter = new ClassifyMainPresenter();
        mainAdapter = new ArrayObjectAdapter(mainPresenter);

        mainAdapter.addAll(0, Arrays.asList(stringList));
        ItemBridgeAdapter mainItemBridge = new ItemBridgeAdapter(mainAdapter);
        mRv1.setAdapter(mainItemBridge);

        mRv1.addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
            @Override
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelected(parent, child, position, subposition);
                stringList = Model.MORELISTTXT[position];
                moreAdapter.clear();
                moreAdapter.addAll(0, Arrays.asList(stringList));
            }

            @Override
            public void onChildViewHolderSelectedAndPositioned(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelectedAndPositioned(parent, child, position, subposition);
            }
        });

        mainPresenter.setOnItemEventListener(new ClassifyMainPresenter.OnItemClickListener() {

            @Override
            public void onItemClick(View v) {
                ObjectAnimator translation = ObjectAnimator.ofFloat(mLayout, "translationX",  0f);
                translation.setDuration(500);
                translation.start();
                mRv1.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mLeftBar.setVisibility(View.VISIBLE);
                    }
                },500);
                mRv2.requestFocus();
            }

            @Override
            public void onItemFocus(View v, boolean hasFocus) {
                if (hasFocus){
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                        mRv1.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(1).start();
                    }else {
                        mRv1.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                        mRv1.invalidate();
                    }
                }else {
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                        mRv1.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(0).start();
                    }else {
                        mRv1.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    }
                }
            }
        });

//        FocusHighlightHelper.setupHeaderItemFocusHighlight(mainItemBridge);
    }

    private void initRv2(String[] array){

        mRv2.setNumColumns(1);

        ClassifyMorePresenter morePresenter = new ClassifyMorePresenter();
        moreAdapter = new ArrayObjectAdapter(morePresenter);

        moreAdapter.addAll(0, Arrays.asList(array));
        ItemBridgeAdapter moreItemBridge = new ItemBridgeAdapter(moreAdapter);
        mRv2.setAdapter(moreItemBridge);

        mRv2.addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
            @Override
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelected(parent, child, position, subposition);

                if (position%2==0){
                    getMovie1Data();
                }else {
                    getMovie2Data();
                }
                if (mGridRv != null && !mGridRv.isComputingLayout()){   // 避免初始化的时候crash
                    mGridAdapter.setItems(mGridList, new DiffCallback() {
                        @Override
                        public boolean areItemsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
                            return true;
                        }

                        @Override
                        public boolean areContentsTheSame(@NonNull Object oldItem, @NonNull Object newItem) {
//                            Logger.i("areContentsTheSame:"+(((Movie)oldItem).getId() == ((Movie)newItem).getId()));
                            return ((Movie)oldItem).getId() == ((Movie)newItem).getId();
                        }

                        @Override
                        public Object getChangePayload(@NonNull Object oldItem, @NonNull Object newItem) {
//                            Logger.i("getChangePayload");
                            Bundle payload = new Bundle();
                            if (((Movie)oldItem).getId() != ((Movie)newItem).getId()) {
                                payload.putInt("KEY_PIC", ((Movie) newItem).getId());
                            }
                            if (!((Movie)oldItem).getName().equals(((Movie) newItem).getName())) {
                                payload.putString("KEY_NAME", ((Movie) newItem).getName());
                            }
                            if (payload.size() == 0) { //如果没有变化 就传空
                                return null;
                            }
                            return payload;
                        }
                    });
//                    mGridAdapter.clear();
//                    mGridAdapter.addAll(0, mGridList);
                    mGridRv.scrollToPosition(0);
                }
            }

            @Override
            public void onChildViewHolderSelectedAndPositioned(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelectedAndPositioned(parent, child, position, subposition);

            }
        });

        morePresenter.setOnItemEventListener(new ClassifyMorePresenter.OnItemClickListener() {
            @Override
            public void onItemLeftClick() {
                float dis = mRv1.getMeasuredWidth() - mLeftBar.getMeasuredWidth();
                ObjectAnimator translation = ObjectAnimator.ofFloat(mLayout, "translationX",  dis);
                translation.setDuration(500);
                translation.start();

                mLeftBar.setVisibility(View.INVISIBLE);
                mRv1.requestFocus();

            }

            @Override
            public void onItemRightClick() {
                mGridRv.requestFocus();
            }

            @Override
            public void onItemFocus(boolean hasFocus) {
                if (hasFocus){
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                        mRv2.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(1).start();
                    }else {
                        mRv2.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                        mRv2.invalidate();
                    }
                }else {
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                        mRv2.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(0).start();
                    }else {
                        mRv2.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    }
                }

            }
        });

//        FocusHighlightHelper.setupHeaderItemFocusHighlight(moreItemBridge);
    }

    private void initGridRv(){


        mGridRv.setNumColumns(5);

        GridViewPresenter gridPresenter = new GridViewPresenter();
        mGridAdapter = new ArrayObjectAdapter(gridPresenter);

        mGridAdapter.addAll(0, mGridList);
        ItemBridgeAdapter gridItemBridge = new ItemBridgeAdapter(mGridAdapter);
        mGridRv.setAdapter(gridItemBridge);

        mGridRv.addOnChildViewHolderSelectedListener(new OnChildViewHolderSelectedListener() {
            @Override
            public void onChildViewHolderSelected(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelected(parent, child, position, subposition);

            }

            @Override
            public void onChildViewHolderSelectedAndPositioned(RecyclerView parent, RecyclerView.ViewHolder child, int position, int subposition) {
                super.onChildViewHolderSelectedAndPositioned(parent, child, position, subposition);

            }
        });

        gridPresenter.setOnItemEventListener(new GridViewPresenter.OnItemClickListener() {
            @Override
            public void onItemLeftClick() {
                mRv2.requestFocus();
            }
        });

//        FocusHighlightHelper.setupHeaderItemFocusHighlight(gridItemBridge);
    }

    private void getMovie1Data(){
        mGridList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            movie = new Movie(Model.imgList1[i], Model.nameList1[i], getString(R.string.introduce));
            mGridList.add(movie);
        }
    }

    private void getMovie2Data(){
        mGridList = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            movie = new Movie(Model.imgList2[i], Model.nameList2[i], getString(R.string.introduce));
            mGridList.add(movie);
        }
    }

}

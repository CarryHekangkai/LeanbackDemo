package com.listgrid.demo;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.FocusHighlightHelper;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.OnChildViewHolderSelectedListener;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.RecyclerView;
import com.listgrid.demo.bean.Model;
import com.listgrid.demo.bean.Movie;
import com.listgrid.demo.presenter.ClassifyMainPresenter;
import com.listgrid.demo.presenter.ClassifyMorePresenter;
import com.listgrid.demo.presenter.GridViewPresenter;
import com.listgrid.demo.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
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

        mDrawerLayout = findViewById(R.id.drawer_layout);
        mLeftBar = findViewById(R.id.leftBar);

        mRv1 = findViewById(R.id.rv_catalog1);
        mRv2 = findViewById(R.id.rv_catalog2);
        mGridRv = findViewById(R.id.listGridView);

        // 初始化侧边栏
        initDrawerView();

        // 初始化更多分类
        initRv2(stringList);

        // 初始化GridView
        getMovie1Data();
        initGridRv();

        // 开启侧边栏
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.openDrawer(GravityCompat.START);
        mDrawerLayout.getChildAt(0).setTranslationX(Utils.Dp2px(this, 285));
        mRv1.requestFocus();

    }

    private void initDrawerView(){
//        mLeftBar.setOnFocusChangeListener(new View.OnFocusChangeListener() {
//            @Override
//            public void onFocusChange(View view, boolean b) {
//                if (b){
//                    mLeftBar.setVisibility(View.GONE);
//                    mDrawerLayout.openDrawer(GravityCompat.START);
//                    mDrawerLayout.setScrimColor(Color.TRANSPARENT);
//                }
//            }
//        });

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                float scale = 1 - slideOffset;
//                Logger.i("scale:"+scale);

                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
//                mContent.invalidate();
            }

            @Override
            public void onDrawerOpened(@NonNull View drawerView) {
                mRv1.requestFocus();
            }

            @Override
            public void onDrawerClosed(@NonNull View drawerView) {
                mLeftBar.setVisibility(View.VISIBLE);
                mRv2.requestFocus();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });

        initRv1();  // 初始化一级分类列表
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
                mDrawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        FocusHighlightHelper.setupHeaderItemFocusHighlight(mainItemBridge);
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
                    mGridAdapter.clear();
                    mGridAdapter.addAll(0, mGridList);
//                    mListRv.scrollToPosition(0);
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
                mLeftBar.setVisibility(View.GONE);
                mDrawerLayout.openDrawer(GravityCompat.START);
            }

            @Override
            public void onItemRightClick() {
                mGridRv.requestFocus();
            }
        });

        FocusHighlightHelper.setupHeaderItemFocusHighlight(moreItemBridge);
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

        FocusHighlightHelper.setupHeaderItemFocusHighlight(gridItemBridge);
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

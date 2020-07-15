package com.listgrid.demo.presenter;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.FocusFinder;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.Presenter;

import com.listgrid.demo.R;
import com.listgrid.demo.bean.Movie;
import com.listgrid.demo.utils.Utils;
import com.listgrid.demo.utils.logger.Logger;

import java.util.List;

public class GridViewPresenter extends Presenter {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gridview, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        MyViewHolder holder = (MyViewHolder) viewHolder;

        final View root = holder.view;
        final ImageView mIvPic = holder.mIvPic;
        final TextView mTvName = holder.mTvName;
        final TextView mTvIntroduce = holder.mTvIntroduce;

        Movie movie = (Movie) item;

        mIvPic.setBackground(root.getContext().getResources().getDrawable(movie.getId()));
        mTvName.setText(movie.getName());
        mTvIntroduce.setText(movie.getIntroduce());

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus){
                    mTvIntroduce.setVisibility(View.VISIBLE);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, Utils.Dp2px(view.getContext(),225));
                    mIvPic.setLayoutParams(layoutParams);
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                        view.animate().scaleX(1.06f).scaleY(1.06f).setDuration(100).translationZ(1).start();
                    }else {
                        view.animate().scaleX(1.06f).scaleY(1.06f).setDuration(100).start();
                        view.invalidate();
                    }
                }else {
                    mTvIntroduce.setVisibility(View.GONE);
                    ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams(
                            ViewGroup.LayoutParams.MATCH_PARENT, Utils.Dp2px(view.getContext(),255));
                    mIvPic.setLayoutParams(layoutParams);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        view.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(0).start();
                    }else {
                        view.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    }
                }
            }
        };

        root.setOnFocusChangeListener(onFocusChangeListener);

        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                Logger.i("keyEvent.getAction()："+ keyEvent.getAction());
//                Logger.i("keyEvent.getKeyCode()："+keyEvent.getKeyCode());
//                Logger.i("i："+i);
                /**
                 * 修复VerticalGridView间跳转偶尔失败
                 */
                if (i == KeyEvent.KEYCODE_DPAD_LEFT && keyEvent.getAction() == KeyEvent.ACTION_DOWN) {
                    FocusFinder finder = FocusFinder.getInstance();
                    View left = finder.findNextFocus((ViewGroup) view.getParent(), view, View.FOCUS_LEFT);
//                    Logger.i("left:"+left);
                    if (left == null && onItemEventListener != null) {
//                        Logger.i("onItemLeftClick");
                        onItemEventListener.onItemLeftClick();
                        return true;
                    }
                }
                return false;

            }
        });
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item,  List<Object> payloads) {
        Logger.i("payloads.isEmpty():"+payloads.isEmpty());
        if (payloads.isEmpty()) {
            onBindViewHolder(viewHolder, item);
        } else {
            MyViewHolder holder = (MyViewHolder) viewHolder;
            final ImageView mIvPic = holder.mIvPic;
            final TextView mTvName = holder.mTvName;

            Bundle payload = (Bundle) payloads.get(0);
            for (String key : payload.keySet()) {
                switch (key) {
                    case "KEY_PIC":
                        //这里可以用payload里的数据，不过data也是新的 也可以用
                        mIvPic.setBackground(mIvPic.getContext().getResources().getDrawable(payload.getInt("KEY_PIC")));
                        break;
                    case "KEY_NAME":
                        mTvName.setText(payload.getString("KEY_NAME"));
                        break;
                    default:
                        break;
                }
            }
        }
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    static class MyViewHolder extends Presenter.ViewHolder {

        private ImageView mIvPic;
        private TextView mTvName;
        private TextView mTvIntroduce;

        public MyViewHolder(View itemView) {
            super(itemView);
            mIvPic = (ImageView) itemView.findViewById(R.id.iv_picture);
            mTvName = (TextView) itemView.findViewById(R.id.tv_name);
            mTvIntroduce = (TextView) itemView.findViewById(R.id.tv_introduce);
        }
    }

    private OnItemClickListener onItemEventListener;

    public interface OnItemClickListener {
        /**
         * Called when a view has been clicked.
         */
        void onItemLeftClick();
    }

    public void setOnItemEventListener(@Nullable OnItemClickListener l) {
        this.onItemEventListener = l;
    }


}

package com.listgrid.demo.presenter;

import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.leanback.widget.Presenter;

import com.listgrid.demo.R;

public class ClassifyMorePresenter extends Presenter {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_lsit, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {

        MyViewHolder holder = (MyViewHolder) viewHolder;

        final View root = holder.view;
        final View bgView = holder.bgView;
        final TextView mTitle = holder.tvTitle;
        final ImageView mIvTag = holder.mIvTag;

        mTitle.setText(item.toString());

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus){
                root.setSelected(false);
                bgView.setSelected(true);
                mIvTag.setVisibility(View.INVISIBLE);
                if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                    bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(1).start();
                    mTitle.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(1).start();
                }else {
                    bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    bgView.invalidate();
                    mTitle.invalidate();
                }
            }else {
                bgView.setSelected(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(0).start();
                }else {
                    bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                }
            }

            if (onItemEventListener != null){
                onItemEventListener.onItemFocus(hasFocus);
            }

            }
        };

        root.setOnFocusChangeListener(onFocusChangeListener);

        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP){
                        return true;
                    }
                    root.setSelected(true);
                    mIvTag.setVisibility(View.VISIBLE);
                    if (onItemEventListener != null){
                        onItemEventListener.onItemRightClick();
                    }
                    return true;
                }
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT){
                    if (keyEvent.getAction() == KeyEvent.ACTION_UP){
                        return true;
                    }
                    if (onItemEventListener != null){
                        onItemEventListener.onItemLeftClick();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    static class MyViewHolder extends Presenter.ViewHolder {

        private View bgView;
        private TextView tvTitle;
        private ImageView mIvTag;

        public MyViewHolder(View view) {
            super(view);
            bgView = view.findViewById(R.id.bg_more_item);
            tvTitle = (TextView) view.findViewById(R.id.tv_more_title);
            mIvTag = (ImageView) view.findViewById(R.id.iv_tag);
        }
    }

    private OnItemClickListener onItemEventListener;

    public interface OnItemClickListener {
        /**
         * Called when a view has been clicked.
         *
         */
        void onItemLeftClick();

        void onItemRightClick();

        void onItemFocus(boolean hasFocus);
    }

    public void setOnItemEventListener(@Nullable OnItemClickListener l) {
        this.onItemEventListener = l;
    }
}

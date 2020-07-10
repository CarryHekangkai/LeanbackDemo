package com.listgrid.demo.presenter;

import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.leanback.widget.Presenter;

import com.listgrid.demo.R;
import com.listgrid.demo.utils.logger.Logger;

public class ClassifyMorePresenter extends Presenter {

    private boolean canLeft = false;

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_more_lsit, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {

        MyViewHolder holder = (MyViewHolder) viewHolder;

        final View root = holder.view;
        final TextView mTitle = holder.tvTitle;
        final ImageView mIvTag = holder.mIvTag;

        mTitle.setText(item.toString());

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
            if (hasFocus){
                root.setSelected(false);
                mIvTag.setVisibility(View.INVISIBLE);
            }
            }
        };

        root.setOnFocusChangeListener(onFocusChangeListener);

        root.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
//                Logger.i("keyEvent.getAction()："+ keyEvent.getAction());
//                Logger.i("keyEvent.getKeyCode()："+keyEvent.getKeyCode());
//                Logger.i("canLeft："+canLeft);
                if(keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
//                    canLeft =true;
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
                    if (!canLeft && onItemEventListener != null){
                        onItemEventListener.onItemLeftClick();
                    }
//                    canLeft = false;
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
        private TextView tvTitle;
        private ImageView mIvTag;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_more_title);
            mIvTag = view.findViewById(R.id.iv_tag);
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
    }

    public void setOnItemEventListener(@Nullable OnItemClickListener l) {
        this.onItemEventListener = l;
    }
}

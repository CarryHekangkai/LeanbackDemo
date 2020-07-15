package com.listgrid.demo.presenter;

import android.os.Build;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.leanback.widget.Presenter;

import com.listgrid.demo.R;
import com.listgrid.demo.utils.logger.Logger;

public class ClassifyMainPresenter extends Presenter {

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_main_lsit, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {

        MyViewHolder holder = (MyViewHolder) viewHolder;

        final View root = holder.view;
        final View bgView = holder.bgView;
        final TextView title = holder.tvTitle;
        title.setText(item.toString());

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    bgView.setSelected(true);
                    title.setSelected(true);
                    if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP){
                        bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(1).start();
                        title.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(1).start();
                    }else {
                        bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                        bgView.invalidate();
                        title.invalidate();
                    }
                }else {
                    bgView.setSelected(false);
                    title.setSelected(false);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).translationZ(0).start();
                    }else {
                        bgView.animate().scaleX(1f).scaleY(1f).setDuration(100).start();
                    }
                }

                if (onItemEventListener != null){
                    onItemEventListener.onItemFocus(title, hasFocus);
                }
            }
        };

        title.setOnFocusChangeListener(onFocusChangeListener);

        title.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT){
                    if (onItemEventListener != null){
                        onItemEventListener.onItemClick(view);
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

        View bgView;
        TextView tvTitle;

        public MyViewHolder(View view) {
            super(view);
            bgView = view.findViewById(R.id.bg_main_item);
            tvTitle = (TextView) view.findViewById(R.id.tv_main_title);
        }
    }

    private OnItemClickListener onItemEventListener;

    public interface OnItemClickListener {
        /**
         * Called when a view has been clicked.
         *
         * @param v               The view that was clicked.
         */
        void onItemClick(View v);

        void onItemFocus(View v, boolean hasFocus);
    }

    public void setOnItemEventListener(@Nullable OnItemClickListener l) {
        this.onItemEventListener = l;
    }
}

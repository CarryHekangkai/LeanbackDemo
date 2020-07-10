package com.listgrid.demo.presenter;

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

        final TextView title = holder.tvTitle;
        title.setText(item.toString());

        View.OnFocusChangeListener onFocusChangeListener = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    title.setSelected(true);
                }else {
                    title.setSelected(false);
                }
            }
        };

        title.setOnFocusChangeListener(onFocusChangeListener);

        holder.view.setOnKeyListener(new View.OnKeyListener() {
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
        TextView tvTitle;

        public MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_main_title);
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
    }

    public void setOnItemEventListener(@Nullable OnItemClickListener l) {
        this.onItemEventListener = l;
    }
}

package com.ubook.ubookapp.notify;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ubook.ubookapp.R;

import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder> {
    List<NotifyData> notifyDatas;
    Context context;
    private OnItemNotifyClickListener listener;

    public NotifyAdapter(List<NotifyData> notifyDatas, Context context) {
        this.notifyDatas = notifyDatas;
        this.context = context;
    }

    public void setListener(OnItemNotifyClickListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.item_notify, viewGroup, false);
        return new NotifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder notifyViewHolder, int i) {
        notifyViewHolder.ivNotify.setImageResource(notifyDatas.get(i).getImage());
        notifyViewHolder.tvTitle.setText(notifyDatas.get(i).getTitle());
        notifyViewHolder.tvBody.setText(notifyDatas.get(i).getBody());
        NotifyData notifyData = notifyDatas.get(i);
        notifyViewHolder.setOnNotifyitemClick(listener, notifyData);
    }

    @Override
    public int getItemCount() {
        return notifyDatas.size();
    }

    //
    public class NotifyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivNotify;
        TextView tvTitle, tvBody;

        public NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            ivNotify = itemView.findViewById(R.id.ivNotify);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvBody = itemView.findViewById(R.id.tvBody);

        }

        //
        public void setOnNotifyitemClick(final OnItemNotifyClickListener onNotifyitemClick, final NotifyData notifyData) {
            itemView.setOnClickListener(v -> {
                if (onNotifyitemClick != null) {
                    onNotifyitemClick.onItemNotifyClick(notifyData, getAdapterPosition());
                }
            });
        }
    }

    //
    public interface OnItemNotifyClickListener {
        void onItemNotifyClick(NotifyData notifyData, int position);
    }
}

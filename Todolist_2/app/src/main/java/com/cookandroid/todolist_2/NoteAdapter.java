package com.cookandroid.todolist_2;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private List<TodoItem> items;
    private OnDeleteClickListener onDeleteClickListener;
    private OnCheckedChangeListener onCheckedChangeListener;


    // 삭제 클릭 리스너 인터페이스 정의
    public interface OnDeleteClickListener {
        void onDeleteClick(int position);
    }
    // 체크 상태 변경 리스너 인터페이스 정의
    public interface OnCheckedChangeListener {
        void onCheckedChanged(int position, boolean isChecked);
    }
    // 삭제 클릭 리스너 설정 메서드
    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }
    // 체크 상태 변경 리스너 설정 메서드
    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
        this.onCheckedChangeListener = listener;
    }


    public void setItems(List<TodoItem> items) {
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_todo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        TodoItem todoItem = items.get(position);
        holder.bind(todoItem);

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDeleteClickListener != null) {
                    onDeleteClickListener.onDeleteClick(position);
                }
            }
        });

        holder.checkBox.setOnCheckedChangeListener(null);  //
        holder.checkBox.setChecked(todoItem.isChecked());  // 체크박스 상태를 현재 아이템의 상태로 설정
        holder.checkBox.setOnCheckedChangeListener((buttonView, isChecked) -> {
            try {
                if (onCheckedChangeListener != null) {
                    onCheckedChangeListener.onCheckedChanged(position, isChecked);
                }
                // 텍스트에 가로줄
                if (isChecked) {
                    holder.textTodo.setPaintFlags(holder.textTodo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                } else {
                    holder.textTodo.setPaintFlags(holder.textTodo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
                }
            } catch (Exception e) {
                e.printStackTrace(); //
            }
        });
    }

    @Override
    public int getItemCount() {
        return items == null ? 0 : items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView textTodo;
        private Button btnDelete;
        private CheckBox checkBox;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textTodo = itemView.findViewById(R.id.text_todo);
            btnDelete = itemView.findViewById(R.id.btn_del);
            checkBox = itemView.findViewById(R.id.checkBox);
        }

        public void bind(TodoItem todoItem) {
            textTodo.setText(todoItem.getTodo());

            checkBox.setChecked(todoItem.isChecked());
            // 체크 상태에 따라 가로줄 설정
            if (todoItem.isChecked()) {
                textTodo.setPaintFlags(textTodo.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                textTodo.setPaintFlags(textTodo.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
            }
        }
    }
}
package com.cookandroid.todolist_2;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private List<TodoItem> todoList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        initUI(rootView);
        todoList = new ArrayList<>();
        adapter.setItems(todoList);

        return rootView;
    }

    private void initUI(View rootView) {
        recyclerView = rootView.findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        adapter = new NoteAdapter();


        adapter.setOnDeleteClickListener(new NoteAdapter.OnDeleteClickListener() {
            @Override
            public void onDeleteClick(int position) {
                deleteTodoItem(position);
            }
        });
        adapter.setOnDeleteClickListener(position -> deleteTodoItem(position));
        adapter.setOnCheckedChangeListener((position, isChecked) -> {
            TodoItem todoItem = todoList.get(position);
            todoItem.setChecked(isChecked);
            adapter.notifyItemChanged(position);
            updateTodoCount(); // 체크 상태 변경 후 개수 업데이트  ///
        });
        recyclerView.setAdapter(adapter);
    }

    public void addTodoItem(String todo) {
        TodoItem todoItem = new TodoItem(todo, false);
        todoList.add(todoItem);
        adapter.setItems(todoList);
        adapter.notifyDataSetChanged();
        updateTodoCount(); // 아이템 추가 후 개수 업데이트
    }
    public void deleteTodoItem(int position) {
        todoList.remove(position);
        adapter.notifyDataSetChanged();
        updateTodoCount();
    }
    public int getTodoCount() {
        return todoList.size();
    }
    private void updateTodoCount() {
        TextView countTextView = getActivity().findViewById(R.id.count);
        int incompleteCount = 0;
        for (TodoItem item : todoList) {
            if (!item.isChecked()) {
                incompleteCount++;
            }
        }
        countTextView.setText("할 일이 " + incompleteCount+"개 남았습니다.");
    }
}
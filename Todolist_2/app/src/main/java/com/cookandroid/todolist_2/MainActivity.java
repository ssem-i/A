package com.cookandroid.todolist_2;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity  implements NoteAdapter.OnDeleteClickListener {

    Fragment mainFragment;
    EditText todoEditText;
    Button btn_add;
    private TextView countTextView;
    NoteAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainFragment = new MainFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, mainFragment).commit();

        // NoteAdapter 초기화
        adapter = new NoteAdapter();
        adapter.setOnDeleteClickListener(this); // MainActivity의 OnDeleteClickListener

        btn_add = findViewById(R.id.btn_add);
        todoEditText = findViewById(R.id.inputToDo);
        countTextView = findViewById(R.id.count);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addTodo();
            }
        });
    }

    private void addTodo() {
        String todo = todoEditText.getText().toString().trim();

        if (!todo.isEmpty()) {
            ((MainFragment) mainFragment).addTodoItem(todo);
            todoEditText.setText("");
            Toast.makeText(getApplicationContext(), "할 일이 추가되었습니다.", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getApplicationContext(), "할 일을 입력해주세요.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteClick(int position) {
        // 아이템 삭제
        ((MainFragment) mainFragment).deleteTodoItem(position); // MainFragment에서 삭제 메서드 호출
        Toast.makeText(getApplicationContext(), "할 일이 삭제되었습니다.", Toast.LENGTH_SHORT).show();
    }
    //public void updateTodoCount() {
   //     int count = ((MainFragment) mainFragment).getTodoCount();
    //    countTextView.setText("할 일 개수: " + count);
    //}
}
//package com.example.selfdicwithjetpack;
//
//import android.app.Activity;
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.example.selfdic.ItemBean;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
///**
// * Created by TpOut on 2020/5/25.<br>
// * Email address: 416756910@qq.com<br>
// */
//public class EditAct extends AppCompatActivity {
//
//    public static final String KEY_DATA = "KEY_DATA";
//    public static final String KEY_SRC = "KEY_SRC";
//    public static final String KEY_RESULT = "KEY_RESULT";
//    public static final String KEY_SENTENCE = "KEY_SENTENCE";
//    public static final int KEY_NUMBER = 566;
//
//    public static void launch(Activity actContext, String src, String result, String sentence) {
//        Intent intent = new Intent(actContext, EditAct.class);
//        intent.putExtra(KEY_SRC, src);
//        intent.putExtra(KEY_RESULT, result);
//        intent.putExtra(KEY_SENTENCE, sentence);
//        actContext.startActivityForResult(intent, KEY_NUMBER);
//    }
//
//    TextView src;
//    TextView result;
//    EditText et;
//    TextView sentence;
//    Button btn;
//
//    ItemBean mItemBean;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.edit_act);
//        src = findViewById(R.id.tv_src);
//        result = findViewById(R.id.tv_result);
//        et = findViewById(R.id.et);
//        sentence = findViewById(R.id.tv_sentence);
//        btn = findViewById(R.id.btn);
//
//        src.setText(getIntent().getStringExtra(KEY_SRC));
//        result.setText(getIntent().getStringExtra(KEY_RESULT));
//        sentence.setText(getIntent().getStringExtra(KEY_SENTENCE));
//
//        mItemBean = new ItemBean(src.getText().toString(), result.getText().toString(), sentence.getText().toString());
//
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (!TextUtils.isEmpty(result.getText().toString())) {
//                    mItemBean.setQueryResult(et.getText().toString());
//                }
//                setResult(RESULT_OK);
//                finish();
//            }
//        });
//    }
//
//    @Override
//    public void finish() {
//        super.finish();
//        mItemBean.save();
//    }
//}

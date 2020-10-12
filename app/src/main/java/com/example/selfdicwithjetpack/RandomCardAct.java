//package com.example.selfdicwithjetpack;
//
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.TextView;
//
//import com.example.selfdic.ItemBean;
//
//import org.litepal.crud.DataSupport;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
///**
// * Created by TpOut on 2020/5/24.<br>
// * Email address: 416756910@qq.com<br>
// */
//public class RandomCardAct extends AppCompatActivity {
//
//    private static final int MAX_SIZE_MOST = 5;
//    private List<ItemBean> list = new ArrayList<>();
//    private int mIndex = 0;
//    private int max = 0;
//
//    TextView src;
//    TextView result;
//    TextView sentence;
//
//    Button pre;
//    Button next;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.random_card_act);
//        src = findViewById(R.id.src);
//        result = findViewById(R.id.result);
//        sentence = findViewById(R.id.sentence);
//        pre = findViewById(R.id.tv_prev);
//        next = findViewById(R.id.tv_next);
//
//        List<ItemBean> all = DataSupport.findAll(ItemBean.class);
//        int size = all.size();
//        max = Math.min(MAX_SIZE_MOST, size);
//        Log.d("屠龙宝刀", "大小：" + size);
//
//        for (int i = max; i > 0; i--) {
//            int index = (int) (Math.random() * size);
//            list.add(all.get(index));
//            all.remove(index);
//            size--;
//        }
//        int length = list.size();
//        for (int i = 0; i < length; i++) {
//            Log.d("屠龙宝刀", "词组 " + i + " ：" + list.get(i));
//        }
//
//        refresh();
//        if (list.size() > 1) {
//            next.setEnabled(true);
//        }
//    }
//
//    public void prev(View v) {
//        mIndex--;
//        if (mIndex == 0) {
//            pre.setEnabled(false);
//            next.setEnabled(true);
//        }
//        refresh();
//    }
//
//    public void next(View v) {
//        mIndex++;
//        if (mIndex == max - 1) {
//            pre.setEnabled(true);
//            next.setEnabled(false);
//        }
//        refresh();
//    }
//
//    private void refresh() {
//        ItemBean itemBean = list.get(mIndex);
//        src.setText(itemBean.getQuerySrc());
//        result.setText(itemBean.getQueryResult());
//        sentence.setText(itemBean.getSentence());
//    }
//}

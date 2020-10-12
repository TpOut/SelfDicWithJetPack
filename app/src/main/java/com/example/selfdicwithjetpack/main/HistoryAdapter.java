//package com.example.selfdicwithjetpack.main;
//
//import com.example.selfdicwithjetpack.R;
//
//import java.util.List;
//
//import androidx.annotation.LayoutRes;
//
// class HistoryAdapter<T extends ItemBean> extends BaseQuickAdapter<T, BaseViewHolder> {
//
//        public HistoryAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
//            super(layoutResId, data);
//        }
//
//        @Override
//        protected void convert(BaseViewHolder helper, T item) {
//            helper.setText(R.id.item_query, "查询：" + item.getQuerySrc());
//            helper.setText(R.id.item_result, "结果：" + item.getQueryResult());
//        }
//    }
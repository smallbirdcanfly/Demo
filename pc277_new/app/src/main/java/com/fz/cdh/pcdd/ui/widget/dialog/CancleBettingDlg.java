package com.fz.cdh.pcdd.ui.widget.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.app.PcddApp;
import com.fz.cdh.pcdd.impl.IBetOdds;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.CancelOrderResult;
import com.fz.cdh.pcdd.network.bean.GameOddsInfo;
import com.fz.cdh.pcdd.network.bean.GameTypeInfo;
import com.fz.cdh.pcdd.network.bean.LatestChoiceInfo;
import com.fz.cdh.pcdd.network.request.CancelOrderRequest;
import com.fz.cdh.pcdd.network.request.GetLatestChoiceDataRequest;
import com.fz.cdh.pcdd.ui.WebLoadActivity;
import com.fz.cdh.pcdd.ui.adapter.BetPanelPagerAdapter;
import com.fz.cdh.pcdd.ui.adapter.CancleBettingListAdapter;
import com.fz.cdh.pcdd.ui.fragment.BetDXDSFragment;
import com.fz.cdh.pcdd.ui.fragment.BetNumberFragment;
import com.fz.cdh.pcdd.ui.fragment.BetSpecialFragment;
import com.fz.cdh.pcdd.ui.fragment.WebLoadFragment;
import com.fz.cdh.pcdd.ui.widget.refresh.PullLoadMoreRecyclerView;
import com.fz.cdh.pcdd.util.Arith;
import com.fz.cdh.pcdd.util.BitmapTool;
import com.fz.cdh.pcdd.util.T;
import com.fz.cdh.pcdd.util.ViewUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaoge on 2017/2/21.
 */

public class CancleBettingDlg extends DialogFragment {

    private ListView listView;
    private List<LatestChoiceInfo> gameTypeInfo;
    private   CancelOrderAdapter cancelOrderAdapter;
    private OnOrderCancelListener mListener;
    public static CancleBettingDlg getInstance(List<LatestChoiceInfo> gameTypeInfo) {
        CancleBettingDlg instance = new CancleBettingDlg();
        Bundle b = new Bundle();

        b.putString("data", new Gson().toJson(gameTypeInfo));
        instance.setArguments(b);
        return instance;
    }
    public static JsonObject parse(String jsonData){
        JsonObject jsonObject = null;
        try{
            JsonParser jsonParser = new JsonParser();
            jsonObject = jsonParser.parse(jsonData).getAsJsonObject();
        }catch(Exception e){
            e.printStackTrace();
        }
        return jsonObject;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setGravity(Gravity.BOTTOM);
        getDialog().setCanceledOnTouchOutside(true);
        String jsonStr=getArguments().getString("data");
        gameTypeInfo = new Gson().fromJson(jsonStr,new TypeToken<List<LatestChoiceInfo>>(){}.getType());
        //gameTypeInfo = (LatestChoiceInfo) getArguments().getSerializable("data");
        View view = inflater.inflate(R.layout.dlg_cancle_betting, container);
        listView = (ListView) view.findViewById(R.id.rvData);
       // CancleBettingListAdapter adapter =new CancleBettingListAdapter(getContext(),gameTypeInfo);
        cancelOrderAdapter=new CancelOrderAdapter(getActivity(),gameTypeInfo);
        listView.setAdapter(cancelOrderAdapter);;
       // cancelOrderAdapter.notifyDataSetChanged();
        return view;
    }


    public void show(FragmentManager manager, String tag,OnOrderCancelListener listener) {
        show(manager, tag);
        this.mListener=listener;
    }

    @Override
    public void onResume() {
        super.onResume();
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, BitmapTool.dp2px(PcddApp.applicationContext, 300));
    }
    private BetOddsCallback callback;

    public void setBetOddsCallback(BetOddsCallback callback) {
        this.callback = callback;
    }

    public interface BetOddsCallback {
        public void onBet(GameOddsInfo oddsInfo, double betPoint);
    }
    public class CancelOrderAdapter extends BaseAdapter{
        private Context mContext;
        private List<LatestChoiceInfo> mDatas;
        public CancelOrderAdapter(Context context,List<LatestChoiceInfo> datas){
            this.mContext=context;
            this.mDatas=datas;

        }
        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return mDatas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
             View view=LayoutInflater.from(mContext).inflate(R.layout.dialog_cancle_item,null);
            TextView tvAccountTime= (TextView) view.findViewById(R.id.tvAccountTime);
            TextView tvAccountContent= (TextView) view.findViewById(R.id.tvAccountContent);
            TextView tvAccountMoney= (TextView) view.findViewById(R.id.tvAccountMoney);
            LinearLayout cancleOrder= (LinearLayout) view.findViewById(R.id.cancleOrder);
            LatestChoiceInfo data=mDatas.get(position);
            tvAccountTime.setText(data.getChoice_no());
            tvAccountContent.setText(data.getChoice_name());
            tvAccountMoney.setText(data.getPoint()+"");
            cancleOrder.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*mDatas.remove(position);
                    cancelOrderAdapter.notifyDataSetChanged();*/
                    cancelOrder(mDatas.get(position));
                }
            });
            return view;
        }
    }
    /**
     * 获取下单数据数据
     * @param latestChoiceInfo
     */
    public void cancelOrder(final LatestChoiceInfo latestChoiceInfo) {
        CancelOrderRequest req = new CancelOrderRequest();
        req.choice_id = latestChoiceInfo.getId()+"";
        req.user_id = latestChoiceInfo.getUser_id()+"";
        req.choice_no=latestChoiceInfo.getChoice_no()+"";
        req.game_type=latestChoiceInfo.getGame_type()+"";
        HttpResultCallback<CancelOrderResult> callback = new HttpResultCallback<CancelOrderResult>() {
            @Override
            public void onStart() {
            }

            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
                T.showShort(e.getMessage());
            }

            @Override
            public void onNext(CancelOrderResult result) {
                if("1".equalsIgnoreCase(result.getStatus())) {
                    T.showShort("撤单成功");
                    gameTypeInfo.remove(latestChoiceInfo);
                    cancelOrderAdapter.notifyDataSetChanged();
                    if (null != mListener) {
                        mListener.onCancel();
                    }
                }else{
                    T.showShort("撤单失败");
                }
            }
        };
        MySubcriber s = new MySubcriber(getActivity(), callback, true, "获取数据");
        ApiInterface.cancelOrder(req, s);
    }
    public interface OnOrderCancelListener{
        void onCancel();
    }
}

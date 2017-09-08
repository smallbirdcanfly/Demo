package com.fz.cdh.pcdd.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.listener.OnItemClickListener;
import com.fz.cdh.pcdd.R;
import com.fz.cdh.pcdd.impl.BannerImageHolderView;
import com.fz.cdh.pcdd.impl.OnLoginClickListener;
import com.fz.cdh.pcdd.network.ApiInterface;
import com.fz.cdh.pcdd.network.HttpResultCallback;
import com.fz.cdh.pcdd.network.MySubcriber;
import com.fz.cdh.pcdd.network.bean.BannerInfo;
import com.fz.cdh.pcdd.network.bean.HomeDataInfo;
import com.fz.cdh.pcdd.network.request.BannerListRequest;
import com.fz.cdh.pcdd.network.request.HomeDataRequest;
import com.fz.cdh.pcdd.ui.LevelSelectActivity;
import com.fz.cdh.pcdd.ui.MoreActivity;
import com.fz.cdh.pcdd.ui.ProxyActivity;
import com.fz.cdh.pcdd.ui.WebLoadActivity;
import com.fz.cdh.pcdd.ui.base.BaseFragment;
import com.fz.cdh.pcdd.ui.widget.dialog.ChatBetFlowDlg;
import com.fz.cdh.pcdd.ui.widget.dialog.HomePageDlg;
import com.fz.cdh.pcdd.util.CheckUtil;
import com.fz.cdh.pcdd.util.CustomTools;
import com.fz.cdh.pcdd.util.GuideViewUtil;
import com.fz.cdh.pcdd.util.T;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by hang on 2017/1/16.
 * 首页
 */

public class HomeFragment extends BaseFragment implements View.OnClickListener {

    private ConvenientBanner banner;

    private List<BannerInfo> bannerList;
    private List<String> bannerImgRes = new ArrayList<String>();
    private ImageView ivHomeFlow;
    private GuideViewUtil mGuideViewUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void init(View rootView) {
        banner = getView(R.id.bannerHome);
        initListener();
        mGuideViewUtil = new GuideViewUtil(getActivity(), R.drawable.guid_1,1);
        loadData();
        loadBanner();
    }

    private void loadData() {
        HomeDataRequest req = new HomeDataRequest();
        HttpResultCallback callback = new HttpResultCallback<HomeDataInfo>() {
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
            public void onNext(HomeDataInfo info) {
                setText(R.id.tvTotalEarnPoint, info.point+"");
                setText(R.id.tvTotalmembers, info.user_count+"");
                setText(R.id.tvTotalEarnRate, info.bili*100+"");
            }
        };
        MySubcriber<HomeDataInfo> s = new MySubcriber<HomeDataInfo>(activity, callback, false, "");
        ApiInterface.getHomeData(req, s);
    }

    private void loadBanner() {
        BannerListRequest req = new BannerListRequest();
        req.place = "1";
        HttpResultCallback callback = new HttpResultCallback<List<BannerInfo>>() {
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
            public void onNext(List<BannerInfo> data) {
                bannerList = data;
                bannerImgRes.clear();
                Observable.from(data)
                    .observeOn(Schedulers.newThread())
                    .map(new Func1<BannerInfo, String>() {
                        @Override
                        public String call(BannerInfo bannerInfo) {
                            return bannerInfo.banner_imgurl;
                        }
                    })
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<String>() {
                        @Override
                        public void onCompleted() {
                            setupBannerView();
                        }

                        @Override
                        public void onError(Throwable e) {
                        }

                        @Override
                        public void onNext(String s) {
                            bannerImgRes.add(s);
                        }
                    });
            }
        };
        MySubcriber<List<BannerInfo>> s = new MySubcriber<List<BannerInfo>>(activity, callback, false, "");
        ApiInterface.getBannerList(req, s);
    }

    public void setupBannerView() {
        banner.setPages(new CBViewHolderCreator() {
            @Override
            public Object createHolder() {
                return new BannerImageHolderView();
            }
        }, bannerImgRes)
            .setPageIndicator(new int[]{R.drawable.dot_nor, R.drawable.dot_sel})
            .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.CENTER_HORIZONTAL)
            .setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    BannerInfo info = bannerList.get(position);
                    if(1==info.is_go&&0==info.flag) {
                        Intent it = new Intent(activity, WebLoadActivity.class);
                        it.putExtra(WebLoadFragment.PARAMS_TITLE, info.banner_name);
                        String url = ApiInterface.WAP_BANNER_DETAIL + "?banner_id=" + info.id;
                        it.putExtra(WebLoadFragment.PARAMS_URL, url);
                        startActivity(it);
                    }else{
                        if(1==info.is_go&&1==info.flag){
                            //跳转二维码
                           Intent share= new Intent(activity, ProxyActivity.class);
                            share.putExtra("from","bannar");
                            startActivity(share);
                        }
                    }
                }
            });
    }

    public void initListener() {
        ivHomeFlow= getView(R.id.ivHomeFlow);
        getView(R.id.ivSidebar).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivCusSvr).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivHomeFlow).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlBeijing28).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.rlCanada28).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivBeijingExplain).setOnClickListener(new OnLoginClickListener(activity, this));
        getView(R.id.ivCanadaExplain).setOnClickListener(new OnLoginClickListener(activity, this));
    }

    @Override
    public void onStart() {
        super.onStart();
        banner.startTurning(3000);
    }

    @Override
    public void onStop() {
        super.onStop();
        banner.stopTurning();
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.ivSidebar:
                startActivity(new Intent(activity, MoreActivity.class));
                activity.overridePendingTransition(R.anim.slide_in_from_top, R.anim.slide_out_to_bottom);
                break;

            case R.id.ivCusSvr:
                CheckUtil.startCusSvr(activity);
                break;

            case R.id.ivHomeFlow:
//                CustomTools.showdownview(getActivity(),ivHomeFlow);
                ivHomeFlow.setImageResource(R.drawable.close_rota);
                HomePageDlg homePageDlg= new HomePageDlg(activity, new HomePageDlg.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        ivHomeFlow.setImageResource(R.drawable.btn_flow);
                    }
                });
                homePageDlg.showAsDropDown(ivHomeFlow);
                break;

            case R.id.rlBeijing28:
                Intent bj = new Intent(activity, LevelSelectActivity.class);
                bj.putExtra("type", 1);
                startActivity(bj);
                break;

            case R.id.rlCanada28:
                Intent ca = new Intent(activity, LevelSelectActivity.class);
                ca.putExtra("type", 2);
                startActivity(ca);
                break;

            case R.id.ivBeijingExplain:
                Intent bjExp = new Intent(activity, WebLoadActivity.class);
                bjExp.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                bjExp.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_BEIJING28_EXPLAIN);
                startActivity(bjExp);
                break;

            case R.id.ivCanadaExplain:
                Intent caExp = new Intent(activity, WebLoadActivity.class);
                caExp.putExtra(WebLoadFragment.PARAMS_TITLE, "玩法说明");
                caExp.putExtra(WebLoadFragment.PARAMS_URL, ApiInterface.WAP_CANADA28_EXPLAIN);
                startActivity(caExp);
                break;
        }
    }
}

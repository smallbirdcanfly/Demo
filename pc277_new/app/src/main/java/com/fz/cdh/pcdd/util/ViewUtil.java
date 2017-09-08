package com.fz.cdh.pcdd.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.lang.reflect.Field;

public class ViewUtil {
    /**
     * 计算ListView的高度
     */
    public static void setListViewHeightBasedOnChildren(ListView listView,
                                                        int attHeight) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight
                + (listView.getDividerHeight() * (listAdapter.getCount() - 1))
                + attHeight;
        listView.setLayoutParams(params);
    }

    /**
     * 计算GridView的高度
     */
    public static void setGridViewHeightBasedOnChildren(GridView gridView) {
        // 获取GridView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int rows;
        int columns = 0;
        int horizontalBorderHeight = 0;
        Class<?> clazz = gridView.getClass();
        try {
            // 利用反射，取得每行显示的个数
            Field column = clazz.getDeclaredField("mRequestedNumColumns");
            column.setAccessible(true);
            columns = (Integer) column.get(gridView);
            // 利用反射，取得横向分割线高度
            Field horizontalSpacing = clazz
                    .getDeclaredField("mRequestedHorizontalSpacing");
            horizontalSpacing.setAccessible(true);
            horizontalBorderHeight = (Integer) horizontalSpacing.get(gridView);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 判断数据总数除以每行个数是否整除。不能整除代表有多余，需要加一行
        if (listAdapter.getCount() % columns > 0) {
            rows = listAdapter.getCount() / columns + 1;
        } else {
            rows = listAdapter.getCount() / columns;
        }
        int totalHeight = 0;
        for (int i = 0; i < rows; i++) { // 只计算每项高度*行数
            View listItem = listAdapter.getView(i, null, gridView);
            listItem.measure(0, 0); // 计算子项View 的宽高
            totalHeight += listItem.getMeasuredHeight(); // 统计所有子项的总高度
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        params.height = totalHeight + horizontalBorderHeight * (rows - 1);// 最后加上分割线总高度
        gridView.setLayoutParams(params);
    }

    /**
     * 展示EditText的错误信息
     */
    public static void showEditError(EditText v, String msg) {
        v.requestFocus();
        v.setError(Html.fromHtml("<font color=#B2001F>" + msg + "</font>"));
    }

    /**
     * 判断EditText内容是否非空，为空显示提醒
     * false 非空      true 空
     */
    public static boolean checkEditEmpty(EditText v, String msg) {
        if(TextUtils.isEmpty(v.getText())) {
            showEditError(v, msg);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 展示EditText自带的清空按钮
     */
    public static void setEditWithClearButton(final EditText edt,
                                              final int imgRes) {

        edt.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Drawable[] drawables = edt.getCompoundDrawables();
                if (hasFocus && edt.getText().toString().length() > 0) {
                    edt.setTag(true);

                    edt.setCompoundDrawablesWithIntrinsicBounds(drawables[0],
                            drawables[1], edt.getContext().getResources()
                                    .getDrawable(imgRes), drawables[3]);

                } else {
                    edt.setTag(false);
                    edt.setCompoundDrawablesWithIntrinsicBounds(drawables[0],
                            drawables[1], null, drawables[3]);
                }
            }
        });
        final int padingRight = Dip2Px(edt.getContext(), 50);
        edt.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_UP:
                        int curX = (int) event.getX();
                        if (curX > v.getWidth() - padingRight
                                && !TextUtils.isEmpty(edt.getText())) {
                            if (edt.getTag() != null && (Boolean) edt.getTag()) {
                                edt.setText("");
                                int cacheInputType = edt.getInputType();
                                edt.setInputType(InputType.TYPE_NULL);
                                edt.onTouchEvent(event);
                                edt.setInputType(cacheInputType);
                                return true;
                            } else {
                                return false;
                            }
                        }
                        break;
                }
                return false;
            }
        });

        edt.addTextChangedListener(new TextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

                Drawable[] drawables = edt.getCompoundDrawables();
                if (edt.getText().toString().length() == 0) {
                    edt.setTag(false);
                    edt.setCompoundDrawablesWithIntrinsicBounds(drawables[0],
                            drawables[1], null, drawables[3]);

                } else {
                    edt.setTag(true);
                    edt.setCompoundDrawablesWithIntrinsicBounds(drawables[0],
                            drawables[1], edt.getContext().getResources()
                                    .getDrawable(imgRes), drawables[3]);

                }
            }
        });

    }

    /**
     * 获取视图的宽度
     */
    public static int getViewWidth(View view) {
        int measure = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(measure, measure);
        return view.getMeasuredWidth();
    }

    /**
     * 获取视图的高度
     */
    public static int getViewHeight(View view) {
        int measure = View.MeasureSpec.makeMeasureSpec(0,
                View.MeasureSpec.UNSPECIFIED);
        view.measure(measure, measure);
        return view.getMeasuredHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * dip to px
     */
    public static int Dip2Px(Context c, int dipValue) {
        float scale = c.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5F);
    }

    /**
     * 设置图片列表
     */
    public static void setPhotoList(final Context context, LinearLayout layout, String photoUrls) {
//    	if(!TextUtils.isEmpty(photoUrls)) {
//			List<String> pics = Arrays.asList(photoUrls.split(","));
//			if(pics!=null && pics.size()>0) {
//				for(String item : pics) {
//					final ImageView img = new ImageView(context);
//					img.setScaleType(ScaleType.CENTER_CROP);
//					layout.addView(img);
//					ImageLoadManager.getInstance(context).loadImage(item, new ImageLoadingListener() {
//						@Override
//						public void onLoadingStarted(String arg0, View arg1) {
//						}
//						
//						@Override
//						public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
//						}
//						
//						@Override
//						public void onLoadingComplete(String arg0, View arg1, Bitmap bm) {
//							double h = Double.valueOf(bm.getHeight());
//							double w = Double.valueOf(bm.getWidth());
//							double scale = Arith.div(h, w);
//							int height = (int) Arith.mul(Double.valueOf(BitmapTool.getScreenWidthPX(context)), scale);
//							LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, height);
//							img.setLayoutParams(lp);
//							img.setImageBitmap(bm);
//						}
//						
//						@Override
//						public void onLoadingCancelled(String arg0, View arg1) {
//						}
//					});
//				}
//			}
//		}
    }
}

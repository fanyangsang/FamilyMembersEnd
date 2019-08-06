package com.wlan.familymembers.view.activity.mall;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ScreenUtils;
import com.bumptech.glide.Glide;
import com.jude.rollviewpager.RollPagerView;
import com.rwq.framworkapp.base.BaseActivity;
import com.rwq.framworkapp.base.BaseView;
import com.rwq.framworkapp.net.HttpUtils;
import com.wlan.familymembers.R;
import com.wlan.familymembers.adapter.DetailRotationsAdapter;
import com.wlan.familymembers.bean.AttrVal;
import com.wlan.familymembers.bean.GoodsDetailBean;
import com.wlan.familymembers.bean.SelectAttrVal;
import com.wlan.familymembers.contract.Contract;
import com.wlan.familymembers.model.mall.MallDetailsModel;
import com.wlan.familymembers.presenter.mall.MallDetailsPresenter;
import com.wlan.familymembers.view.activity.goodsorder.PaymentDetailsActivity;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类作用：
 * Created by Administrator on 2018/9/15.
 */

public class MallDetailsActivity extends BaseActivity<MallDetailsModel, MallDetailsPresenter> implements Contract.MallDetailsContract.View {

    @BindView(R.id.iv_return_key)
    ImageView ivReturnKey;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.rpv_content)
    RollPagerView rpvContent;
    @BindView(R.id.tv_advance_payment)
    TextView tvAdvancePayment;
    @BindView(R.id.btn_immediately_order)
    Button btnImmediatelyOrder;
    //    @BindView(R.id.tv_webview)
    //    TextView tvwebview;
    @BindView(R.id.wv_content)
    WebView wvContent;
    private DetailRotationsAdapter detailRotationsAdapter;
    private Dialog alertDialog;
    private String goodsId;
    private String attrPath = "11,9";
    private int num = 0;
    private GoodsDetailBean goodsDetailBean;
    private ArrayList<String> sowings;
    private String htmlstring;
    private Map<String, List<AttrVal>> attrMap;
    private Map<String, SelectAttrVal> selectAttrValMap;
    private int attrSize;
    private AttrVal select1;
    private AttrVal select2;
    private ViewHolder holder;

    @Override
    public int getLayoutId() {
        return R.layout.activity_classisy_details;
    }

    @Override
    public void initView() {
        goodsDetailBean = new GoodsDetailBean();
        tvContent.setText("商品详情");
        sowings = new ArrayList<>();
        //设置轮播图播放时间间隔
        rpvContent.setPlayDelay(3000);
        //设置透明度
        rpvContent.setAnimationDurtion(1500);
        //设置圆点指示器颜色
        //        rpvContent.setHintView(new ColorPointHintView(this, Color.YELLOW,Color.WHITE));
        //设置适配器
        detailRotationsAdapter = new DetailRotationsAdapter(this, sowings);
        rpvContent.setAdapter(detailRotationsAdapter);

        //接收商品id
        goodsId = getIntent().getStringExtra("goodsId");
        //显示加载动画
        showLoadingDialog();
        //请求数据
        presenter.getMallDetails(goodsId);
    }

    private void createDialog() {
        //如果实体类为空 返回
        if (goodsDetailBean == null) {
            return;
        }

        View view;
        alertDialog = new Dialog(this, R.style.BottomDialog);
        //加载自定义view
        view = LayoutInflater.from(this).inflate(R.layout.dialog_mall_details, null);
        //新建适配器
        holder = new ViewHolder(view);
        //获取当前购买数量
        num = Integer.parseInt(holder.tvCount.getText().toString().trim());
        //加载图片
        Glide.with(this).load(HttpUtils.BASE_URL + goodsDetailBean.getGoodsPic()).into(holder.ivGoodsImg);
        //设置商品名称
        holder.tvGoodsName.setText(goodsDetailBean.getGoodsName());
        //设置商品价格
        //            holder.tvGoodsPrice.setText("￥" + goodsDetailBean.getGoodsPrice());
        holder.tvGoodsPrice.setText("请选择产品规格");
        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
        //对增加数量按钮设置点击监听
        holder.ivUpKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断规格是否全选
                if (checkSelectInfo()) return;
                //点击+时  数量+1
                num = num + 1;
                holder.tvCount.setText(String.valueOf(num));
                //根据num设置价格
                if (select2 != null) {
                    String key = select1.getId() + "," + select2.getId();
                    //例 获取“1,2”内的值
                    if (selectAttrValMap.get(key) == null) {
                        key = select2.getId() + "," + select1.getId();
                    }
                    SelectAttrVal attrVal = selectAttrValMap.get(key);
                    //设置价格
                    Double d = Double.valueOf(attrVal.getPrice());
                    String s = String.valueOf(d * num);
                    holder.tvGoodsPrice.setText("￥" + s);
                } else {
                    String key = select1.getId();
                    //例 获取“1,2”内的值
                    SelectAttrVal attrVal = selectAttrValMap.get(key);
                    //设置价格
                    Double d = Double.valueOf(attrVal.getPrice());
                    String s = String.valueOf(d * num);
                    holder.tvGoodsPrice.setText("￥" + s);
                }
            }
        });
        //对减少数量按钮设置点击监听
        holder.ivLowerKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //当数量为1时 不变
                if (num == 1) {
                    holder.tvCount.setText(String.valueOf(num));

                } else {
                    //判断规格是否全选
                    if (checkSelectInfo()) return;
                    //否则数量-1
                    num = num - 1;
                    holder.tvCount.setText(String.valueOf(num));
                    //根据num设置价格
                    if (select2 != null) {
                        String key = select1.getId() + "," + select2.getId();
                        //例 获取“1,2”内的值
                        if (selectAttrValMap.get(key) == null) {
                            key = select2.getId() + "," + select1.getId();
                        }
                        SelectAttrVal attrVal = selectAttrValMap.get(key);
                        //设置价格
                        Double d = Double.valueOf(attrVal.getPrice());
                        String s = String.valueOf(d * num);
                        holder.tvGoodsPrice.setText("￥" + s);
                    } else {
                        String key = select1.getId();
                        //例 获取“1,2”内的值
                        SelectAttrVal attrVal = selectAttrValMap.get(key);
                        //设置价格
                        Double d = Double.valueOf(attrVal.getPrice());
                        String s = String.valueOf(d * num);
                        holder.tvGoodsPrice.setText("￥" + s);
                    }
                }
            }
        });
        //对确定按钮设置点击监听
        holder.tvSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //判断规格是否全选
                if (checkSelectInfo()) return;
                //解散对话窗口
                alertDialog.dismiss();
                Intent intent = new Intent(MallDetailsActivity.this, PaymentDetailsActivity.class);
                //将当前商品id传过去
                intent.putExtra("goodsId", goodsId);

                String sb = "";
                //类型1不为空时
                if (select1 != null) {
                    //获取类型1的id
                    sb = select1.getId();
                }
                //类型2不为空的时候
                if (select2 != null) {
                    //获取类型1类型2的id
                    sb = select2.getId() + "," + select1.getId();
                }
                //类型id传过去 例如“1,2”
                intent.putExtra("attrPath", sb);
                //购买数量传过去
                intent.putExtra("num", num);
                startActivity(intent);
            }
        });
        //商品规格不等于空时
        if (attrMap != null) {
            //stringSet = 商品规格键名 如“颜色” “尺码”
            Set<String> stringSet = attrMap.keySet();
            String[] keyArray = stringSet.toArray(new String[0]);
            //有几个规格类型遍历几遍
            for (int i = 0; i < keyArray.length; i++) {
                //获取规格中内容
                final List<AttrVal> attrVals = attrMap.get(keyArray[i]);
                switch (i) {
                    case 0:
                        holder.tvAttrOne.setVisibility(View.VISIBLE);
                        //显示TagFlowLayout
                        holder.tflAttrOne.setVisibility(View.VISIBLE);
                        //设置规格名 名称
                        holder.tvAttrOne.setText(keyArray[i]);
                        //设置规格值
                        holder.tflAttrOne.setAdapter(createTagAdapter(attrVals, i));
                        //设置最大选择计数
                        holder.tflAttrOne.setMaxSelectCount(1);
                        //设置属性值点击
                        holder.tflAttrOne.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                Integer[] select = selectPosSet.toArray(new Integer[0]);
                                if (select.length == 1) {
                                    MallDetailsActivity.this.select1 = attrVals.get(select[0]);
                                    updateSelectAttr(1);
                                } else {
                                    select1 = null;
                                    updateSelectAttr(1);
                                }
                            }
                        });
                        break;

                    case 1:
                        holder.tvAttrTwo.setVisibility(View.VISIBLE);
                        holder.tflAttrTwo.setVisibility(View.VISIBLE);
                        holder.tvAttrTwo.setText(keyArray[i]);
                        holder.tflAttrTwo.setAdapter(createTagAdapter(attrVals, i));
                        holder.tflAttrTwo.setMaxSelectCount(1);
                        holder.tflAttrTwo.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
                            @Override
                            public void onSelected(Set<Integer> selectPosSet) {
                                Integer[] select = selectPosSet.toArray(new Integer[0]);
                                if (select.length == 1) {
                                    MallDetailsActivity.this.select2 = attrVals.get(select[0]);
                                    updateSelectAttr(2);
                                } else {
                                    select2 = null;
                                    updateSelectAttr(2);
                                }
                            }
                        });
                        break;
                }

            }
        }
        alertDialog.setContentView(view);
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = ScreenUtils.getScreenWidth();
        view.setLayoutParams(layoutParams);
        //设置可取消
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.setCancelable(true);
        alertDialog.getWindow().setWindowAnimations(R.style.AnimBottom);
    }
    //        alertDialog.show();
    //    }

    private boolean checkSelectInfo() {

        if (attrSize == 1) {
            if (select1 == null) {
                showToast("请选择规格参数!");
                return true;
            }
        } else if (attrSize == 2) {
            //类型1为空的话
            if (select1 == null) {
                if (holder != null) {
                    showToast("请选择" + holder.tvAttrOne.getText() + "!");
                } else {
                    showToast("请选择规格!");
                }
                return true;
            }
            //类型2为空的话
            if (select2 == null) {
                if (holder != null) {
                    showToast("请选择" + holder.tvAttrTwo.getText() + "!");
                } else {
                    showToast("请选择规格!");
                }
                return true;
            }
        }
        return false;
    }

    /**
     * @param type 1 第一个规格的选择触发  2 第二个规格的选择触发
     */
    private void updateSelectAttr(int type) {
        //有一个类型时
        if (attrSize == 1) {
            //类型1选中时
            if (select1 != null) {
                //获取类型1的 id （算钱用）
                SelectAttrVal attrVal = selectAttrValMap.get(select1.getId());
                //获取 SelectAttrVal中键名为1 的价格 设置上去
                holder.tvGoodsPrice.setText("￥" + attrVal.getPrice());
                //获取积分
                holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                //当图片地址不为空时 加载图片
                if (!TextUtils.isEmpty(attrVal.getPic())) {
                    Glide.with(this).load(HttpUtils.BASE_URL + attrVal.getPic().substring(1)).into(holder.ivGoodsImg);
                }
            } else {
                //类型1未选中时 设置未选规格时的价格
                holder.tvGoodsPrice.setText("￥" + goodsDetailBean.getGoodsPrice());
                holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
            }
        } else {
            //有两个类型时
            if (type == 1) {
                //类型1选中时
                if (select1 != null) {
                    //类型1类型2选中时
                    if (select2 != null) {
                        //key=类型1id+类型2id 用来算钱
                        String key = select1.getId() + "," + select2.getId();
                        //例 获取“1,2”内的值
                        if (selectAttrValMap.get(key) == null) {
                            key = select2.getId() + "," + select1.getId();
                        }
                        SelectAttrVal attrVal = selectAttrValMap.get(key);
                        //设置价格
                        Double d = Double.valueOf(attrVal.getPrice());
                        String s = String.valueOf(d * num);
                        holder.tvGoodsPrice.setText("￥" + s);
                        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                        //当图片地址不为空时 加载图片
                        if (!TextUtils.isEmpty(attrVal.getPic())) {
                            Glide.with(this).load(HttpUtils.BASE_URL + attrVal.getPic().substring(1)).into(holder.ivGoodsImg);
                        }
                    } else {
                        //类型1选中 类型2未选中时
                        holder.tvGoodsPrice.setText("请选择" + holder.tvAttrTwo.getText());
                        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                    }
                } else {
                    //类型1未选中时
                    holder.tvGoodsPrice.setText("请选择" + holder.tvAttrOne.getText());
                    holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                }
            } else {
                //类型2选中时
                if (select2 != null) {
                    //类型2类型1选中时
                    if (select1 != null) {
                        //key=类型1id+类型2id 用来算钱
                        String key = select1.getId() + "," + select2.getId();
                        //例 获取“1,2”内的值
                        if (selectAttrValMap.get(key) == null) {
                            key = select2.getId() + "," + select1.getId();
                        }
                        SelectAttrVal attrVal = selectAttrValMap.get(key);
                        Double d = Double.valueOf(attrVal.getPrice());
                        String s = String.valueOf(d * num);
                        //设置价格
                        holder.tvGoodsPrice.setText("￥" + s);
                        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                        //当图片地址不为空时 加载图片
                        if (!TextUtils.isEmpty(attrVal.getPic())) {
                            Glide.with(this).load(HttpUtils.BASE_URL + attrVal.getPic().substring(1)).into(holder.ivGoodsImg);
                        }
                    } else {
                        //类型2选中类型1未选中时
                        holder.tvGoodsPrice.setText("请选择" + holder.tvAttrOne.getText());
                        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                    }
                } else {

                    //都未选中
                    if (select1 == null) {

                        holder.tvGoodsPrice.setText("请选择" + holder.tvAttrOne.getText());
                        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                    } else {
                        //类型2未选中 类型1选中时
                        holder.tvGoodsPrice.setText("请选择" + holder.tvAttrTwo.getText());
                        holder.tvGoodsIntegral.setText("(" + goodsDetailBean.getGoodsIntegral() + "积分)");
                    }
                }
            }
        }
    }

    //设置类型选项
    private TagAdapter createTagAdapter(List<AttrVal> attrVals, int type) {
        TagAdapter<AttrVal> adapter = new TagAdapter<AttrVal>(attrVals) {
            @Override
            public View getView(FlowLayout parent, int position, AttrVal attrVal) {
                TextView textView = (TextView) LayoutInflater.from(MallDetailsActivity.this).inflate(R.layout.tag, null);
                textView.setText(attrVal.getVal());
                return textView;
            }
        };
        return adapter;
    }

    @Override
    public void onEvent() {
        ivReturnKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        //点击立即下单时，弹出购买的那个弹窗
        btnImmediatelyOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建对话框
                createDialog();
                //显示对话框
                alertDialog.setCanceledOnTouchOutside(true);
                alertDialog.show();

            }
        });

    }

    @Override
    public BaseView getBaseView() {
        return this;
    }

    @Override
    public void showErrorWithStatus(String msg) {
        hideLoadingDialog();
        showToast(msg);

    }

    //获取数据成功后调用
    @Override
    public void getMallDetailsSuccess(GoodsDetailBean goodsDetailBean, Map<String, List<AttrVal>> attrMap, Map<String, SelectAttrVal> selectAttrValMap) {
        hideLoadingDialog();
        //商品详情
        this.goodsDetailBean = goodsDetailBean;
        this.attrMap = attrMap;//商品规格 如颜色 版本等
        this.selectAttrValMap = selectAttrValMap;//算钱规格
        this.attrSize = attrMap.keySet().size(); //大小， 0，1，2
        //清理轮播图集合
        this.sowings.clear();
        //添加轮播图数据
        this.sowings.addAll(goodsDetailBean.getGoodsBanner());
        // 通知轮播图适配器数据更改
        detailRotationsAdapter.notifyDataSetChanged();
        //获取html数据
        String htmlInfo = goodsDetailBean.getGoodsDetail();
        String data = Html.fromHtml(htmlInfo).toString();
        String replace_data = data.replace("src=\"", "style=\"width:100%\" src=\"" + HttpUtils.BASE_URL);
        String html = "<html><head><meta charset=\"utf-8\" /> <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\"/><style>p,div,table,span,img{width:100%;}</style></head><body>" + replace_data + "</body>" +
                "</html>";
        wvContent.getSettings().setDefaultTextEncodingName("UTF-8");
        wvContent.loadData(html, "text/html;charset=UTF-8", null);

        //设置价格
        tvAdvancePayment.setText("预付款：￥" + goodsDetailBean.getGoodsPrice());
    }

    //对话框适配器
    static class ViewHolder {
        @BindView(R.id.tv_goods_name)
        TextView tvGoodsName;
        @BindView(R.id.tv_goods_attr)
        TextView tvGoodsAttr;
        @BindView(R.id.tv_goods_price)
        TextView tvGoodsPrice;
        @BindView(R.id.tv_goods_integral)
        TextView tvGoodsIntegral;
        @BindView(R.id.tv_attr_one)
        TextView tvAttrOne;
        @BindView(R.id.tfl_attr_one)
        TagFlowLayout tflAttrOne;
        @BindView(R.id.tv_attr_two)
        TextView tvAttrTwo;
        @BindView(R.id.tfl_attr_two)
        TagFlowLayout tflAttrTwo;
        @BindView(R.id.iv_up_key)
        ImageView ivUpKey;
        @BindView(R.id.tv_count)
        TextView tvCount;
        @BindView(R.id.iv_lower_key)
        ImageView ivLowerKey;
        @BindView(R.id.tv_sure)
        TextView tvSure;
        @BindView(R.id.iv_goods_img)
        ImageView ivGoodsImg;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}

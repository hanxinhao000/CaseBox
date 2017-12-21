package com.xinhao_han.xhboxchecd;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;



import java.util.ArrayList;

/**
 * 选择储物柜的自定义控件
 */

public class CaseView extends FrameLayout {
    private Context context;

    private int size;

    private View view;

    private ArrayList<CaseBean> arrayList;

    //存储已选单位
    private ArrayList<CaseBean> arrayClick;

    //不可选
    public static final int USER = 1;
    //可选
    public static final int SELECTABLE = 2;
    //已选
    public static final int THE_SELECTED = 3;
    private XHBaseAdapter xhBaseAdapter;

    private ImageView move_red;

    private int checkPage;
    private ArrayList<CaseViewBean> arrCase;

    private CaseClickListenet l;
    //ViewPager
    private ViewPager viewPager;
    //箱子的页数
    private int pager;

    //选择柜子所需要的容器
    public LinearLayout check_case;

    //箱子的页脚布局
    public LinearLayout add_image;

    public CaseView(Context context) {
        super(context);
        init(context);
    }

    public CaseView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public CaseView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }
    //初始化


    private void init(Context context) {
        this.context = context;
        view = XHUIUtils.getView(R.layout.view_case);
        check_case = view.findViewById(R.id.check_case);
        viewPager = view.findViewById(R.id.viewPager);
        arrayList = new ArrayList<>();
        arrayClick = new ArrayList<>();
        arrCase = new ArrayList<>();
        move_red = view.findViewById(R.id.move_red);
        add_image = view.findViewById(R.id.add_image);
        caseTitle = view.findViewById(R.id.caseTitle);
        this.addView(view);
    }


   /* @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        view.layout(l, t, r, b);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int w = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
        int h = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);

        view.measure(w,h);

        super.onMeasure(w, h);


    }
*/

    private String[] title;

    /**
     * 设置箱子标题
     */

    public void setCaseTitle(String[] title) {
        this.title = title;

    }

    /**
     * 开始获取数据
     */

    public ArrayList<CaseBean> getCheckData() {
        return arrayClick;

    }

    /**
     * 箱子的个数 以及箱子的页数
     *
     * @param size
     */
    public CaseView setMaxNum(int size, int pager) {
        this.size = size;

        this.pager = pager;
        return this;
    }

    /**
     * 开始设置箱子的位置是否可选,包括哪一页
     *
     * @param index
     * @param user
     */

    public void setIndexUser(int index, int user, int checkPage) {
        ProcessTheData(index, user, checkPage);
    }

    /**
     * 每一次点击都有响应时间
     */

    public void setOnClickListener(CaseClickListenet l) {

        this.l = l;
    }


    public interface CaseClickListenet {
        void onClick(int index, CaseBean caseBean);
    }

    //设置ViewPager的一些方法
    private void setViewPager() {


        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return pager;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, final int position1) {


                xhBaseAdapter = new XHBaseAdapter<ArrayList<CaseViewBean>>(R.layout.item_view_case, context, arrCase, false, "") {

                    @Override
                    public int getCount() {


                        return arrCase.get(position1).getArrCaseBean().size();
                    }

                    @Override
                    public void itemPosition(final int position, final ArrayList<CaseViewBean> arrayItem, final ArrayList<CaseViewBean> caseViewBeans, ViewHolder viewHolder) {


                        ImageView imageView = (ImageView) viewHolder.findViewById(R.id.imageView);
                        TextView index_text = (TextView) viewHolder.findViewById(R.id.index_text);
                        //满图标
                        ImageView man = (ImageView) viewHolder.findViewById(R.id.man);

                        switch (arrayItem.get(position1).getArrCaseBean().get(position).getStat()) {
                            //不可选
                            case USER:
                                imageView.setImageResource(R.drawable.redbox);
                                index_text.setText(arrayItem.get(position1).getArrCaseBean().get(position).getIndex());
                                man.setVisibility(VISIBLE);
                                viewHolder.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                                break;
                            //可选
                            case SELECTABLE:
                              //  LOGE.e("集合大小", arrayItem.get(position1).getArrCaseBean().get(position).getStat());
                                imageView.setImageResource(R.drawable.graybox);
                                viewHolder.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        if (!(CheckCaseActivityImpl.isAnimend || CheckCaseActivityImpl.isAnimStart)) {
                                            //加入已选项目
                                            arrayClick.add(arrayItem.get(position1).getArrCaseBean().get(position));
                                            //设置原来选项的状态
                                            arrayItem.get(position1).getArrCaseBean().get(position).setStat(THE_SELECTED);

                                            //
                                            arrayItem.get(position1).getXhBaseAdapter().notifyDataSetChanged();

                                            //设置记录位置
                                            arrayItem.get(position1).getArrCaseBean().get(position).setPositionA(position1);
                                            arrayItem.get(position1).getArrCaseBean().get(position).setPositionB(position);

                                            checked();
                                            if (l != null)
                                                l.onClick(position, arrayItem.get(position1).getArrCaseBean().get(position));
                                        }
                                    }
                                });
                                index_text.setText(arrayItem.get(position1).getArrCaseBean().get(position).getIndex());
                                man.setVisibility(GONE);
                                break;
                            //已选
                            case THE_SELECTED:
                                imageView.setImageResource(R.drawable.yixuan);
                                index_text.setText(arrayItem.get(position1).getArrCaseBean().get(position).getIndex());
                                man.setVisibility(GONE);
                                viewHolder.setOnClickListener(new OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                    }
                                });
                                break;
                        }


                    }
                };


                ((MyGridView) arrCase.get(position1).view).setAdapter(xhBaseAdapter);

                arrCase.get(position1).setXhBaseAdapter(xhBaseAdapter);


                container.addView((MyGridView) arrCase.get(position1).view);
                return (MyGridView) arrCase.get(position1).view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });
        buildAnim();
    }


    private int positionIndex = 0;

    private int mid;

    /**
     * 设置下标动画
     */
    private void buildAnim() {

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                try {
                    mid = add_image.getChildAt(1).getLeft() - add_image.getChildAt(0).getLeft();
                } catch (Exception e) {

                }
                view.getViewTreeObserver().removeGlobalOnLayoutListener(this);
            }
        });

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //获取当前View

                //获取间距


                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) move_red.getLayoutParams();
                layoutParams.leftMargin = (int) ((positionOffset * mid) + add_image.getChildAt(position).getLeft());
                move_red.setLayoutParams(layoutParams);


            }

            @Override
            public void onPageSelected(int position) {
                positionIndex = position;
                caseTitle.setText(title[position]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    /**
     * 开始,适配器在这一部开始运行!
     */
    public void build() {

        //开始整合一些数据
        //设置GridView的一些方法

        ProcessTheData(0, 0, 0);

        //设置ViewPager方法
        setViewPager();

        //设置标题
        caseTitle.setText(title[0]);

    }

    private TextView caseTitle;


    /**
     * 处理数据
     *
     * @param index
     * @param user
     */
    private void ProcessTheData(int index, int user, int checkPage) {


        if (index == 0 && user == 0) {
            //证明这是初始化
            for (int i = 0; i < pager; i++) {

                ArrayList<CaseBean> arrayList = new ArrayList<>();
                CaseViewBean caseViewBean = new CaseViewBean();
                for (int i1 = 0; i1 < size; i1++) {
                    CaseBean caseBean = new CaseBean();

                    caseBean.setMoney(messageBeans.get(i).get(i1).getMoney());
                    caseBean.setSpecification(messageBeans.get(i).get(i1).getSpecification());
                    caseBean.setNumber(messageBeans.get(i).get(i1).getNumber());


                    caseBean.setIndex(String.valueOf(i1 + 1));
                    caseBean.setPosition(i1);
                    caseBean.setStat(SELECTABLE);
                    arrayList.add(caseBean);
                }
                /**
                 * 初始化默认全部为可选
                 */

                caseViewBean.setArrCaseBean(arrayList);
                MyGridView myGridView = new MyGridView(context);
                myGridView.setNumColumns(4);
                caseViewBean.setView(myGridView);
                arrCase.add(caseViewBean);


            }

            /**
             * 加入页脚
             */

            for (int i1 = 0; i1 < pager; i1++) {

                if (i1 == 0) {
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.leftMargin = XHUIUtils.dp2Px(0);
                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(R.drawable.image_no_checd);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(ll);
                    add_image.addView(imageView);
                } else {
                    LinearLayout.LayoutParams ll = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    ll.leftMargin = XHUIUtils.dp2Px(2);
                    ImageView imageView = new ImageView(context);
                    imageView.setImageResource(R.drawable.image_no_checd);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    imageView.setLayoutParams(ll);
                    add_image.addView(imageView);
                }


            }
        } else {
            arrCase.get(checkPage).getArrCaseBean().get(index).setStat(user);
            //  xhBaseAdapter.notifyDataSetChanged();
            if (arrCase.get(checkPage).getXhBaseAdapter() != null)
                arrCase.get(checkPage).getXhBaseAdapter().notifyDataSetChanged();
            this.checkPage = checkPage;
        }


    }


    private ArrayList<ArrayList<MessageBean>> messageBeans;

    public void setMessageData(ArrayList<ArrayList<MessageBean>> messageBeans) {

        this.messageBeans = messageBeans;
    }


    public static class MessageBean {
        //箱号
        private String number = "A0018";
        //金额
        private String money = "¥:1.00";
        //箱子规格
        private String specification = "规格:30CM*50CM";

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }
    }

    /**
     * 是否有选中数据
     */


    public void setIsCheckData(IsCheckListener il) {

        this.il = il;
    }

    private IsCheckListener il;

    public interface IsCheckListener {
        //true 有 false没有
        void isData(boolean isdata);
    }

    //记录 有数据
    private static boolean isOne = true;

    /**
     * 没惦记一下就刷新所选区域
     */

    private void checked() {

        if (arrayClick.size() == 0) {
            if (il != null) {
                if (!isOne) {
                    isOne = true;
                    il.isData(false);
                }

            }

        } else {
            if (il != null) {
                if (isOne) {
                    il.isData(true);
                    isOne = false;
                }
            }
        }


        check_case.removeAllViews();
        for (int i = 0; i < arrayClick.size(); i++) {

            View view = XHUIUtils.getView(R.layout.item_checked_case);
            //箱号
            TextView number = view.findViewById(R.id.number);
            number.setText(arrayClick.get(i).getNumber());
            //金额
            TextView money_text = view.findViewById(R.id.money_text);
            money_text.setText(arrayClick.get(i).getMoney());
            //规格
            TextView specification = view.findViewById(R.id.specification);
            specification.setText(arrayClick.get(i).getSpecification());
            ImageView close = view.findViewById(R.id.close);

            check_case.addView(view);

            final int finalI = i;
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!(CheckCaseActivityImpl.isAnimend || CheckCaseActivityImpl.isAnimStart)) {
                       /* //变为可选状态
                        int position = arrayClick.get(finalI1).getPosition();
                        arrayList.get(position).setStat(SELECTABLE);
                        xhBaseAdapter.notifyDataSetChanged();
                        arrayClick.remove(finalI);*/

                        int positionA = arrayClick.get(finalI).getPositionA();
                        int positionB = arrayClick.get(finalI).getPositionB();


                        arrCase.get(positionA).getArrCaseBean().get(positionB).setStat(SELECTABLE);
                        arrCase.get(positionA).getXhBaseAdapter().notifyDataSetChanged();
                        arrayClick.remove(finalI);


                        checked();
                    }


                }
            });

        }


    }


    static class CaseViewBean {
        private ArrayList<CaseBean> arrCaseBean;
        private View view;
        private XHBaseAdapter xhBaseAdapter;

        public XHBaseAdapter getXhBaseAdapter() {
            return xhBaseAdapter;
        }

        public void setXhBaseAdapter(XHBaseAdapter xhBaseAdapter) {
            this.xhBaseAdapter = xhBaseAdapter;
        }

        public ArrayList<CaseBean> getArrCaseBean() {
            return arrCaseBean;
        }

        public void setArrCaseBean(ArrayList<CaseBean> arrCaseBean) {
            this.arrCaseBean = arrCaseBean;
        }

        public View getView() {
            return view;
        }

        public void setView(View view) {
            this.view = view;
        }
    }

    //数据Bean

    public static class CaseBean {


        @Override
        public String toString() {
            return "CaseBean{" +
                    "positionA=" + positionA +
                    ", positionB=" + positionB +
                    ", index='" + index + '\'' +
                    ", position=" + position +
                    ", stat=" + stat +
                    ", number='" + number + '\'' +
                    ", money='" + money + '\'' +
                    ", specification='" + specification + '\'' +
                    '}';
        }

        private int positionA;
        private int positionB;

        public int getPositionA() {
            return positionA;
        }

        public void setPositionA(int positionA) {
            this.positionA = positionA;
        }

        public int getPositionB() {
            return positionB;
        }

        public void setPositionB(int positionB) {
            this.positionB = positionB;
        }

        //要显示的位置
        private String index;
        //可能后期会用的位置
        private int position;
        //可选状态
        private int stat = SELECTABLE;
        //箱号
        private String number = "A0018";
        //金额
        private String money = "¥:1.00";
        //箱子规格
        private String specification = "规格:30CM*50CM";

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getSpecification() {
            return specification;
        }

        public void setSpecification(String specification) {
            this.specification = specification;
        }

        public String getNumber() {

            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getIndex() {
            return index;
        }

        public void setIndex(String index) {
            this.index = index;
        }

        public int getPosition() {
            return position;
        }

        public void setPosition(int position) {
            this.position = position;
        }

        public int getStat() {
            return stat;
        }

        public void setStat(int stat) {
            this.stat = stat;
        }
    }

}

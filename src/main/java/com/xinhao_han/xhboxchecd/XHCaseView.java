package com.xinhao_han.xhboxchecd;

import java.util.ArrayList;

/**
 * XINHAO_HAN   箱子View的包装类
 */

public class XHCaseView {

    private CaseView caseView;
    private XHCaseAdapterBean xhCaseAdapterBean;
    //数据
    private ArrayList<ArrayList<CaseView.MessageBean>> messageBeans;
    //标题
    private String[] titleString;

    //最大上线
    private int maxCount = 20;
    private CaseView.IsCheckListener il;

    public XHCaseView(CaseView caseView) {
        this.caseView = caseView;

    }

    public void setCaseViewAdapter(XHCaseAdapterBean xhCaseAdapterBean) {
        this.xhCaseAdapterBean = xhCaseAdapterBean;
        startBuild();
    }

    public void setIsCheckData(CaseView.IsCheckListener il) {

        caseView.setIsCheckData(il);
    }

    /**
     * 开始填充数据
     */
    private void startBuild() {
        titleString = new String[xhCaseAdapterBean.getCaseCount()];
        messageBeans = new ArrayList<>();
        for (int i = 0; i < xhCaseAdapterBean.getCaseCount(); i++) {

            titleString[i] = xhCaseAdapterBean.getCaseTtitle(i);
            ArrayList<CaseView.MessageBean> arrayList = new ArrayList<>();
            for (int i1 = 0; i1 < maxCount; i1++) {
                CaseView.MessageBean messageBean = new CaseView.MessageBean();


                messageBean.setMoney(xhCaseAdapterBean.getCaseMoney(i, i1));
                messageBean.setNumber(xhCaseAdapterBean.getCaseNumber(i, i1));
                messageBean.setSpecification(xhCaseAdapterBean.getCaseModelNumber(i, i1));
                arrayList.add(messageBean);


                arrayList.add(messageBean);


            }
            messageBeans.add(arrayList);
        }
        caseView.setMaxNum(maxCount, xhCaseAdapterBean.getCaseCount());
        caseView.setMessageData(messageBeans);
        caseView.setCaseTitle(titleString);
        caseView.build();
        //设置占用
        for (int i = 0; i < xhCaseAdapterBean.getCaseCount(); i++) {
            for (int i1 = 0; i1 < maxCount; i1++) {

                if (xhCaseAdapterBean.getUse(i1, i))
                    //设置占用
                    caseView.setIndexUser(i1, CaseView.USER, i);
            }

        }


    }

    //启用此方法开始获取数据
    public void startGet() {

        ArrayList<CaseView.CaseBean> checkData = caseView.getCheckData();

        if (startListener != null) {
            startListener.getData(checkData);
        }

    }

    private StartListener startListener;

    public void setStartGetListener(StartListener startListener) {

        this.startListener = startListener;
    }

    public static interface StartListener {
        void getData(ArrayList<CaseView.CaseBean> checkData);
    }


    public abstract static class XHCaseAdapterBean {

        //获取箱子标题
        public abstract String getCaseTtitle(int position);

        //箱子主要的规格
        public abstract String getCaseModelNumber(int position, int index);

        //箱子主要价格
        public abstract String getCaseMoney(int position, int index);

        //箱子主要箱号
        public abstract String getCaseNumber(int position, int index);

        //有多少页箱子
        public abstract int getCaseCount();

        //表示哪些箱子已经被占用
        public abstract boolean getUse(int position, int pageIndex);

    }


    public interface CaseChecd {
        void electedCase(CaseView.CaseBean caseBean);
    }

}

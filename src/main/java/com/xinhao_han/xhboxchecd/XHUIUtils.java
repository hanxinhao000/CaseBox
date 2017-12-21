package com.xinhao_han.xhboxchecd;

import android.content.Context;
import android.view.View;

/**
 * Created by 14178 on 2017/12/21.
 */

public class XHUIUtils {

    private static Context contextStat;

    public static void init(Context context) {
        contextStat = context;
    }


    /**
     * 获取View
     *
     * @param id
     * @return
     */
    public static View getView(int id) {


        return View.inflate(contextStat, id, null);

    }
    /**
     * dp转换
     */
    public static int dp2Px(float dp) {
        final float scale = contextStat.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}

package com.klein.library;

/**
 * @author With You
 * @version 5.0.0
 * @date 2020-03-20 14:03
 * @email 1713397546@qq.com
 * @description
 */
public interface OnAgreementClickListener {

    /**
     * @param tag
     * @param clickText 点击的文本
     * @param isCheck   协议是否勾选
     */
    void clickListener(String tag, String clickText, boolean isCheck);

}

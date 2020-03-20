package com.hubufan.agreement;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.appcompat.widget.AppCompatTextView;

import java.util.LinkedList;

/**
 * @author With You
 * @version 5.0.0
 * @date 2020-03-20 14:06
 * @email 1713397546@qq.com
 * @description
 */
public class UrAgreementTextView extends AppCompatTextView {


    private final static String AgreementHintTag = "AgreementHintTag";

    private SpannableString spannableString = null;
    /**
     * 协议全内容
     */
    private String agreementContext = "";
    /**
     * 协议提示文本
     */
    private String agreementHintText = "";
    /***
     * 协议内容
     */
    private LinkedList<CharSequence> charSequences = new LinkedList<>();

    /**
     * 协议全内容字体颜色
     */
    private int agreementContextColor;
    /**
     * 协议提示文本颜色
     */
    private int agreementHintColor;
    /**
     * 协议字体颜色
     */
    private int agreementsColor;

    private boolean isChecked = false;
    private Drawable agreementCheckDrawable;
    private Drawable agreementUnCheckDrawable;

    public boolean isChecked() {
        return isChecked;
    }

    public UrAgreementTextView(Context context) {
        super(context);
    }

    public UrAgreementTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }

    public UrAgreementTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    private void initView(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UrAgreementTextView);
        String agreementContext = typedArray.getString(R.styleable.UrAgreementTextView_agreementContext);
        String agreementHintText = typedArray.getString(R.styleable.UrAgreementTextView_agreementHintText);
        CharSequence[] agreements = typedArray.getTextArray(R.styleable.UrAgreementTextView_agreementArraysId);
        agreementContextColor = typedArray.getColor(R.styleable.UrAgreementTextView_agreementContextColor, 0xFF000000);
        agreementHintColor = typedArray.getColor(R.styleable.UrAgreementTextView_agreementHintColor, 0xFF000000);
        agreementsColor = typedArray.getColor(R.styleable.UrAgreementTextView_agreementsColor, 0xFFFF0000);
        isChecked = typedArray.getBoolean(R.styleable.UrAgreementTextView_isChecked, false);
        agreementCheckDrawable = typedArray.getDrawable(R.styleable.UrAgreementTextView_agreementCheckedId);
        agreementUnCheckDrawable = typedArray.getDrawable(R.styleable.UrAgreementTextView_agreementUnCheckedId);
        typedArray.recycle();
        if (!TextUtils.isEmpty(agreementContext))
            this.agreementContext = agreementContext;
        if (!TextUtils.isEmpty(agreementHintText))
            this.agreementHintText = agreementHintText;
        if (agreements != null) {
            for (int i = 0; i < agreements.length; i++) {
                charSequences.add(agreements[i].toString());
            }
        }
        this.setHighlightColor(getResources().getColor(android.R.color.transparent));
        spannableString = new SpannableString(agreementContext);
        setDrawable(isChecked ? agreementCheckDrawable : agreementUnCheckDrawable);
        setAgreement();
    }

    /**
     * 设置勾选按钮图片
     *
     * @param drawable
     */
    private void setDrawable(Drawable drawable) {
        if (drawable != null) {
            drawable.setBounds(0, 0, dip2px(14), dip2px(14));
            spannableString.setSpan(new UrImageSpan(drawable), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannableString.setSpan(new MyClickableSpan(AgreementHintTag, "", 0), 0, 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        setText(spannableString);
    }

    /**
     * 设置协议内容
     */
    public void setAgreement() {
        //设置协议显示效果
        for (int i = 0; i < charSequences.size(); i++) {
            CharSequence agreement = charSequences.get(i);
            int firstIndex = agreementContext.indexOf(agreement.toString());
            int lastIndex = firstIndex + agreement.length();
            spannableString.setSpan(new MyClickableSpan(String.valueOf(i), agreement.toString(), agreementsColor),
                    firstIndex, lastIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        //设置协议提示文本显示及点击效果
        if (!TextUtils.isEmpty(agreementHintText)) {
            int firstHintIndex = agreementContext.indexOf(agreementHintText);
            int lastHintIndex = firstHintIndex + agreementHintText.length();
            spannableString.setSpan(new MyClickableSpan(AgreementHintTag, agreementHintText, agreementHintColor),
                    firstHintIndex, lastHintIndex, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        }
        setText(spannableString);
        setMovementMethod(LinkMovementMethod.getInstance());
    }

    private class MyClickableSpan extends ClickableSpan {
        private String tag;
        private String agreement;
        private int color;

        public MyClickableSpan(String tag, String agreement, int color) {
            this.tag = tag;
            this.agreement = agreement;
            this.color = color;
        }

        @Override
        public void onClick(View widget) {
            if (agreementClickListener != null) {
                if (tag.equals(AgreementHintTag)) //当点击同意协议时，勾选按钮
                {
                    setDrawable(!isChecked ? agreementCheckDrawable : agreementUnCheckDrawable);
                    isChecked = !isChecked;
                    agreementClickListener.clickListener(tag, agreement, isChecked);
                } else {
                    agreementClickListener.clickListener(tag, agreement, isChecked);
                }
            }
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(color);
            ds.setUnderlineText(false);
        }
    }

    private OnAgreementClickListener agreementClickListener;

    public void setAgreementClickListener(OnAgreementClickListener agreementClickListener) {
        this.agreementClickListener = agreementClickListener;
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param dipValue （DisplayMetrics类中属性density）
     * @return
     */
    public int dip2px(float dipValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}

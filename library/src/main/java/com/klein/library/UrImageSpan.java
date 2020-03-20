package com.klein.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.style.ImageSpan;

/**
 * 自定义ImageSpan实现TextView中文图混排时文图的居中对齐
 *
 * @author With You
 * @version 5.0.0
 * @date 2020-03-20 14:05
 * @email 1713397546@qq.com
 * @description
 */
public class UrImageSpan extends ImageSpan {

    public UrImageSpan(Context context, Bitmap bitmap) {
        super(context, bitmap);
    }

    public UrImageSpan(Context context, Bitmap bitmap, int verticalAlignment) {
        super(context, bitmap, verticalAlignment);
    }

    public UrImageSpan(Drawable drawable) {
        super(drawable);
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        try {
            Drawable d = getDrawable();
            Rect rect = d.getBounds();
            int size = rect.width();
            return size;
        } catch (Exception e) {
            return 20;
        }
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        try {
            Drawable d = getDrawable();
            canvas.save();
            int transY = 0;
            transY = ((bottom - top) - d.getBounds().bottom) / 2 + top;
            canvas.translate(x, transY);
            d.draw(canvas);
            canvas.restore();
        } catch (Exception e) {
        }
    }

}

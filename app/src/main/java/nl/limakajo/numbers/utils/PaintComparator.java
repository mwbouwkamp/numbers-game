package nl.limakajo.numbers.utils;

import android.graphics.Paint;
import android.text.TextUtils;

import org.w3c.dom.Text;

import java.util.Comparator;

public class PaintComparator implements Comparator<Paint> {

    @Override
    public int compare(Paint paint1, Paint paint2) {
        if (hasEqualAttributes(paint1, paint2)) {
            return 0;
        }
        else {
            return -1;
        }
    }

    public boolean hasEqualAttributes(Paint paint1, Paint paint2) {
        return
                paint1.getColorFilter() == paint2.getColorFilter() &&
                        paint1.getMaskFilter() == paint2.getMaskFilter() &&
                        paint1.getPathEffect() == paint2.getPathEffect() &&
                        paint1.getShader() == paint2.getShader() &&
                        paint1.getTypeface() == paint2.getTypeface() &&
                        paint1.getXfermode() == paint2.getXfermode() &&
                        //TODO: See if it is a problem that some of these are not included as there is no getter
                        //mHasCompatScaling
                        //mCompatScaling
                        //mInvCompatScaling
                        //mBidiFlags
                        //mLocales
                        TextUtils.equals(paint1.getFontFeatureSettings(), paint2.getFontFeatureSettings()) &&
                        //TODO: See what to do with the following (requires API level 26)
                        //TextUtils.equals(paint1.getFontVariationSettings(), paint2.getFontVariationSettings()) &&
                        //TODO: See if it is a problem that some of these are not included as there is no getter
                        //mShadowLayerRadius
                        //mShadowLayerDx
                        //mShadowLayerDy
                        //mShadowLayerColor
                        paint1.getFlags() == paint2.getFlags() &&
                        paint1.getHinting() == paint2.getHinting() &&
                        paint1.getStyle() == paint2.getStyle() &&
                        paint1.getColor() == paint2.getColor() &&
                        paint1.getStrokeWidth() == paint2.getStrokeWidth() &&
                        paint1.getStrokeMiter() == paint2.getStrokeMiter() &&
                        paint1.getStrokeCap() == paint2.getStrokeCap() &&
                        paint1.getStrokeJoin() == paint2.getStrokeJoin() &&
                        paint1.getTextAlign() == paint2.getTextAlign() &&
                        paint1.isElegantTextHeight() == paint2.isElegantTextHeight() &&
                        paint1.getTextSize() == paint2.getTextSize() &&
                        paint1.getTextScaleX() == paint2.getTextScaleX() &&
                        paint1.getTextSkewX() == paint2.getTextSkewX() &&
                        paint1.getLetterSpacing() == paint2.getLetterSpacing();
                        //TODO: See what to do with the following (hidden getter)
                        //getWordSpacing()
                        //getHyphenEdit()
    }
}

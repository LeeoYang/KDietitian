package com.dobbby.kdietitian.util;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;
import com.dobbby.kdietitian.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dobbby on 2017/6/21.
 */
public class WbNumberPickerView extends View {
    private int textSize;
    private int textColor;
    private int textPadding;
    private float textMaxScale;
    private float textMinAlpha;
    private boolean isCycle;
    private int maxDisplayCount;

    private TextPaint textPaint;
    private Paint.FontMetrics fm;

    private Scroller scroller;
    private VelocityTracker velocityTracker;
    private int minVelocity;
    private int maxVelocity;
    private int scaledTouchSlop;

    private List<String> displayValues = new ArrayList<>();

    private int cx;
    private int cy;

    private float maxTextWidth;
    private int textHeight;
    private int contentWidth;
    private int contentHeight;

    private float pressedY;
    private float offsetY;
    private float oldOffsetY;
    private int currentIndex;
    private int offsetIndex;

    private float bounceDistance;
    private boolean isSliding = false;

    private OnPickListener onPickListener;

    public WbNumberPickerView(Context context) {
        this(context, null);
    }

    public WbNumberPickerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WbNumberPickerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray t = context.getTheme().obtainStyledAttributes(attrs, R.styleable.WbNumberPickerView, defStyleAttr, 0);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();

        textSize = t.getDimensionPixelSize(
                R.styleable.WbNumberPickerView_pickerTextSize,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, displayMetrics)
        );
        textColor = t.getColor(R.styleable.WbNumberPickerView_pickerTextColor, Color.BLACK);
        textPadding = t.getDimensionPixelOffset(
                R.styleable.WbNumberPickerView_pickerTextPadding,
                (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 16, displayMetrics)
        );
        textMaxScale = t.getFloat(R.styleable.WbNumberPickerView_pickerTextMaxScale, 2f);
        textMinAlpha = t.getFloat(R.styleable.WbNumberPickerView_pickerTextMinAlpha, 0.4f);
        isCycle = t.getBoolean(R.styleable.WbNumberPickerView_pickerIsCycle, true);
        maxDisplayCount = t.getInt(R.styleable.WbNumberPickerView_pickerMaxDisplayCount, 3);

        t.recycle();

        textPaint = new TextPaint();
        textPaint.setColor(textColor);
        textPaint.setTextSize(textSize);
        textPaint.setAntiAlias(true);
        fm = textPaint.getFontMetrics();

        scroller = new Scroller(context);
        minVelocity = ViewConfiguration.get(getContext()).getScaledMinimumFlingVelocity();
        maxVelocity = ViewConfiguration.get(getContext()).getScaledMaximumFlingVelocity();
        scaledTouchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    public void setDisplayValues(List<String> displayValues) {
        this.displayValues.clear();
        this.displayValues.addAll(displayValues);

        if (displayValues != null && displayValues.size() > 0) {
            for (String value : displayValues) {
                float tmpWidth = textPaint.measureText(value);

                if (tmpWidth > maxTextWidth) {
                    maxTextWidth = tmpWidth;
                }
            }
            currentIndex = 0;
        }

        requestLayout();
        invalidate();
    }

    public void setStartIndex(int startIndex) {
        currentIndex = startIndex;
    }

    public void addOnPickListener(OnPickListener onPickListener) {
        this.onPickListener = onPickListener;
    }

    public int getCurrentIndex() {
        return currentIndex - offsetIndex;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        contentWidth = (int) (maxTextWidth * textMaxScale + getPaddingStart() + getPaddingEnd());

        if (widthMode != MeasureSpec.EXACTLY) {
            width = contentWidth;
        }

        textHeight = (int) (fm.bottom - fm.top);
        contentHeight = (textHeight + textPadding) * maxDisplayCount;

        if (heightMode != MeasureSpec.EXACTLY) {
            height = contentHeight + getPaddingTop() + getPaddingBottom();
        }

        cx = width / 2;
        cy = height / 2;

        setMeasuredDimension(width, height);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        getParent().requestDisallowInterceptTouchEvent(true);
        return super.dispatchTouchEvent(event);
    }

    public void scrollTo(int index) {
        if (index < 0 || index >= displayValues.size() || currentIndex == index) {
            return;
        }

        if (!scroller.isFinished()) {
            scroller.forceFinished(true);
        }

        finishScroll();

        int dy = 0;
        int centerPadding = textHeight + textPadding;

        if (!isCycle) {
            dy = (currentIndex - index) * centerPadding;
        } else {
            int offsetIndex = currentIndex - index;
            int d1 = Math.abs(offsetIndex) * centerPadding;
            int d2 = (displayValues.size() - Math.abs(offsetIndex)) * centerPadding;

            if (offsetIndex > 0) {
                if (d1 < d2) {
                    dy = d1; // ascent
                } else {
                    dy = -d2; // descent
                }
            } else {
                if (d1 < d2) {
                    dy = -d1; // descent
                } else {
                    dy = d2; // ascent
                }
            }
        }

        scroller.startScroll(0, 0, 0, dy, 500);
        invalidate();
    }

    public void scrollBy(int offset) {
        scrollTo(calCurrentIndex(offset));
    }

    private void addVelocityTracker(MotionEvent event) {
        if (velocityTracker == null) {
            velocityTracker = VelocityTracker.obtain();
        }
        velocityTracker.addMovement(event);
    }

    private void recycleVelocityTracker() {
        if (velocityTracker != null) {
            velocityTracker.recycle();
            velocityTracker = null;
        }
    }

    private int getScrollYVelocity() {
        velocityTracker.computeCurrentVelocity(1000, maxVelocity);
        return (int) velocityTracker.getYVelocity();
    }

    private void reDraw() {
        int i = (int) (offsetY / (textHeight + textPadding));

        if (isCycle || (currentIndex - i >= 0 && currentIndex - i < displayValues.size())) {
            if (offsetIndex != i) {
                offsetIndex = i;

                if (onPickListener != null) {
                    onPickListener.onScrollChanged(calCurrentIndex(-offsetIndex));
                }
            }
            postInvalidate();
        } else {
            finishScroll();
        }
    }

    private void finishScroll() {
        int centerPadding = textHeight + textPadding;
        float v = offsetY % centerPadding;

        if (v > 0.5f * centerPadding) {
            ++offsetIndex;
        } else if (v < -0.5f * centerPadding) {
            --offsetIndex;
        }

        currentIndex = calCurrentIndex(-offsetIndex);

        bounceDistance = offsetIndex * centerPadding - offsetY;
        offsetY += bounceDistance;

        if (onPickListener != null) {
            onPickListener.onScrollFinished(currentIndex);
        }

        reset();
        postInvalidate();
    }

    private int calCurrentIndex(int offsetIndex) {
        int index = currentIndex + offsetIndex;

        if (isCycle) {
            if (index < 0) {
                index = (index + 1) % displayValues.size() + displayValues.size() - 1;
            } else if (index >= displayValues.size()) {
                index %= displayValues.size();
            }
        } else {
            if (index < 0) {
                index = 0;
            } else if (index >= displayValues.size()) {
                index = displayValues.size() - 1;
            }
        }

        return index;
    }

    private void reset() {
        offsetY = 0;
        oldOffsetY = 0;
        offsetIndex = 0;
        bounceDistance = 0;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        addVelocityTracker(event);

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (!scroller.isFinished()) {
                    scroller.forceFinished(true);
                    finishScroll();
                }
                pressedY = event.getY();
                break;

            case MotionEvent.ACTION_MOVE:
                offsetY = event.getY() - pressedY;
                if (isSliding || Math.abs(offsetY) > scaledTouchSlop) {
                    isSliding = true;
                    reDraw();
                }
                break;

            case MotionEvent.ACTION_UP:
                int scrollYVelocity = 2 * getScrollYVelocity() / 3;
                if (Math.abs(scrollYVelocity) > minVelocity) {
                    oldOffsetY = offsetY;
                    scroller.fling(0, 0, 0, scrollYVelocity, 0, 0, -Integer.MAX_VALUE, Integer.MAX_VALUE);
                    invalidate();
                } else {
                    finishScroll();
                }

                if (!isSliding) {
                    if (pressedY < contentHeight / 3) {
                        scrollBy(-1);
                    } else if (pressedY > 2 * contentHeight / 3) {
                        scrollBy(1);
                    }
                }

                isSliding = false;
                recycleVelocityTracker();
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (displayValues != null && displayValues.size() > 0) {
            canvas.clipRect(
                    cx - contentWidth / 2,
                    cy - contentHeight / 2,
                    cx + contentWidth / 2,
                    cy + contentHeight / 2
            );

            int centerPadding = textHeight + textPadding;
            int half = maxDisplayCount / 2 + 1;
            for (int i = -half; i <= half; i++) {
                int index = currentIndex - offsetIndex + i;

                if (isCycle) {
                    if (index < 0) {
                        index = (index + 1) % displayValues.size() + displayValues.size() - 1;
                    } else if (index > displayValues.size() - 1) {
                        index = index % displayValues.size();
                    }
                }

                if (index >= 0 && index < displayValues.size()) {
                    int tempY = cy + i * centerPadding;
                    tempY += offsetY % centerPadding;

                    float scale = 1.0f - (1.0f * Math.abs(tempY - cy) / centerPadding);

                    float tempScale = scale * (textMaxScale - 1.0f) + 1.0f;
                    tempScale = tempScale < 1.0f ? 1.0f : tempScale;

                    float textAlpha = textMinAlpha;
                    if (textMaxScale != 1) {
                        float tempAlpha = (tempScale - 1) / (textMaxScale - 1);
                        textAlpha = (1 - textMinAlpha) * tempAlpha + textMinAlpha;
                    }

                    textPaint.setTextSize(textSize * tempScale);
                    textPaint.setAlpha((int) (255 * textAlpha));

                    Paint.FontMetrics tempFm = textPaint.getFontMetrics();
                    String text = displayValues.get(index);
                    float textWidth = textPaint.measureText(text);
                    canvas.drawText(text, cx - textWidth / 2, tempY - (tempFm.ascent + tempFm.descent) / 2, textPaint);
                }
            }
        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()) {
            offsetY = oldOffsetY + scroller.getCurrY();

            if (!scroller.isFinished()) {
                reDraw();
            } else {
                finishScroll();
            }
        }
    }

    // util
    public static List<String> generateDisplayList(int start, int end, String unit) {
        List<String> days = new ArrayList<>();

        for (int i = start; i <= end; ++i) {
            days.add(unit != null ? i + unit : String.valueOf(i));
        }

        return days;
    }

    public interface OnPickListener {
        void onScrollChanged(int curIndex);

        void onScrollFinished(int curIndex);
    }
}

package z.com.pvo.newAdapter;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

//http://stackoverflow.com/questions/15261088/gridview-with-two-columns-and-auto-resized-images
public class ZSquareImageView extends ImageView {
    public ZSquareImageView(Context context) {
        super(context);
    }

    public ZSquareImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ZSquareImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(), getMeasuredWidth()); //Snap to width
    }
}
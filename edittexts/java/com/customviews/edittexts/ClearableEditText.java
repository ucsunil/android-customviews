package com.customviews.edittexts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

import com.customviews.R;

/**
 * Created by Sunilsingh D on 11/12/14.
 *
 * This class creates an EditText widget that displays a clear button if there is input inside the
 * EditText field.
 */
public class ClearableEditText extends EditText {

    // Set the image that you want to use to clear the text here
    // I have used the stock dialog_ic_close_normal_holo_light.png that comes with the Android SDK
    // You can use whatever custom image you want
    private Drawable closeDrawable = getResources().getDrawable(R.drawable.dialog_ic_close_normal_holo_light);

    public ClearableEditText(Context context) {
        super(context);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClearableEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        // Specify the bounding rectangle for the drawable
        closeDrawable.setBounds(0, 0, closeDrawable.getIntrinsicWidth(), closeDrawable.getIntrinsicHeight());
        decideAndDisplayButton();

        this.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ClearableEditText editText = ClearableEditText.this;

                if(editText.getCompoundDrawables()[2] == null) {
                    // Means that the clear button is not anyway displayed
                    return false;
                }
                if(motionEvent.getAction() != MotionEvent.ACTION_UP) {
                    // For all other actions, do nothing
                    return false;
                }
                if(motionEvent.getX() > (editText.getWidth() - editText.getPaddingRight() - closeDrawable.getIntrinsicWidth())) {
                    // This check ensures that it is the drawable that is being clicked
                    // See how the right padding is accounted for
                    editText.setText("");
                    decideAndDisplayButton();
                }
                return false;
            }
        });

        // Add a TextChangedListener to determine if the clear button should be displayed
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int start, int before, int count) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                ClearableEditText.this.decideAndDisplayButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }

    private void decideAndDisplayButton() {
        if(TextUtils.isEmpty(this.getText())) {
            // There is no text and hence the clear button should not be displayed
            // The trim ensures that the clear button does not show up when blank spaces are encountered
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1],
                    null, this.getCompoundDrawables()[3]);
        } else {
            // There is text and hence the clear button should be displayed
            this.setCompoundDrawables(this.getCompoundDrawables()[0], this.getCompoundDrawables()[1],
                    closeDrawable, this.getCompoundDrawables()[3]);
        }
    }
}

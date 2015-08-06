package com.sq.jzq.views;


import com.sq.jzq.R;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class TitleBarView extends RelativeLayout {
	
	public interface OnClickBackButtonListener{
		public void onClickBackButton(View v);
	}
	
	public interface OnClickEnterButtonListener{
		public void onClickEnterButton(View v);
	}

	private static final String BACK = "BACK";
	private static final String ENTER = "ENTER";
	private static final String Android = "http://schemas.android.com/apk/res/android";
	public static Boolean Signed = false;
	public static Boolean SignedBack = true;
	private ImageButtonTouchListener EventInstance;
	private TextView txvTitle;
	private ImageButton imbBack;
	private ImageButton imbEnter;
	private OnClickBackButtonListener _Back_Handler_;
	private OnClickEnterButtonListener _Enter_Handler_;
	private static Drawable BackImage;
	private static Drawable BackPressImage;
	private static Drawable RightImage;
	private static Drawable RightImagePress;
	
	public void setClickBackButtonListener(OnClickBackButtonListener handler){
		_Back_Handler_ = handler;
	}
	
	public void setClickEnterButtonListener(OnClickEnterButtonListener handler){
		_Enter_Handler_ = handler;
	}
	
	public CharSequence getText() {
		if(txvTitle == null) return "";
		return txvTitle.getText();
	}

	public void setText(CharSequence text) {
		if(txvTitle == null) return;
		txvTitle.setText(text);
	}

	public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		ApplyStyle(context, attrs);
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		ApplyStyle(context, attrs);
	}
	
	public TitleBarView(Context context) {
		super(context);
		ApplyStyle(context, null);
	}
	
	@SuppressWarnings("deprecation")
	private void ApplyStyle(Context context, AttributeSet attrs) {
		
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.title_bar_view, this);
		
		txvTitle = (TextView)findViewById(R.id.tbv_title);
		imbBack = (ImageButton)findViewById(R.id.tbv_back);
		imbEnter = (ImageButton)findViewById(R.id.tbv_enter);
		BackImage = context.getResources().getDrawable(R.drawable.back);
		BackPressImage = context.getResources().getDrawable(R.drawable.back_press);
		EventInstance = new ImageButtonTouchListener();
		//cxtActivity = context;
		
		if(attrs != null) {
			int index = attrs.getAttributeResourceValue(Android, "text" ,-1);
			if(index == -1){
				txvTitle.setText(attrs.getAttributeValue(Android, "text"));
			}else{
				txvTitle.setText(context.getResources().getText(index));
			}
		}
		
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.TitleBarView);

		if(array.getBoolean(R.styleable.TitleBarView_leftButtonEnable, SignedBack)){
			imbBack.setVisibility(View.VISIBLE);
			imbBack.setImageDrawable(
					context.getResources().getDrawable(
							array.getResourceId(R.styleable.TitleBarView_leftButtonBgImg, R.drawable.back_press
									)));
			
			imbBack.getBackground().setAlpha(0);
			imbBack.setPadding(0, 0, 0, 0);
			imbBack.setTag(BACK);
			imbBack.setOnTouchListener(EventInstance);
		}else{
			imbBack.setVisibility(View.GONE);
		}
		
		if(array.getBoolean(R.styleable.TitleBarView_rightButtonEnable, Signed)){
			RightImage = context.getResources().getDrawable(array.getResourceId(R.styleable.TitleBarView_rightButtonBgImg, R.drawable.sure));
			RightImagePress = context.getResources().getDrawable(array.getResourceId(R.styleable.TitleBarView_rightButtonBgImgPress, R.drawable.sure_press));
			imbEnter.setVisibility(View.VISIBLE);
			imbEnter.setImageDrawable(
					context.getResources().getDrawable(
							array.getResourceId(R.styleable.TitleBarView_rightButtonBgImg, R.drawable.sure
									)));
			imbEnter.getBackground().setAlpha(0);
			imbEnter.setPadding(0, 0, 0, 0);
			imbEnter.setTag(ENTER);
			imbEnter.setOnTouchListener(EventInstance);
		}else{
			imbEnter.setVisibility(View.GONE);
		}
	}
	
//	@Override
//	protected void onDraw(Canvas canvas) {
//		int h = getMeasuredHeight();
//		int w = getMeasuredWidth();
//		
//		canvas.drawLine( 0, h - lineWidth, w, h - lineWidth, Line);
//		super.onDraw(canvas);
//	}
	
	class ImageButtonTouchListener implements View.OnTouchListener{

		@Override
		public boolean onTouch(View v, MotionEvent event) {
			if(event.getAction() == MotionEvent.ACTION_DOWN){       
				//重新设置按下时的背景图片    
				if(v.getTag().toString().equals(BACK)){
					((ImageButton)v).setImageDrawable(BackPressImage);
				}else{
					((ImageButton)v).setImageDrawable(RightImagePress);
				}
	        }else if(event.getAction() == MotionEvent.ACTION_UP){       
	        	//再修改为抬起时的正常图片
	        	if(v.getTag().toString().equals(BACK)){
					((ImageButton)v).setImageDrawable(BackImage);
					if(_Back_Handler_ == null){
						((Activity) TitleBarView.this.getContext()).finish();
					}else{
						_Back_Handler_.onClickBackButton(v);
					}
				}else{
					((ImageButton)v).setImageDrawable(RightImage);
					if(_Enter_Handler_ == null){
//						((Activity) TitleBarView.this.getContext()).startActivity(new Intent(
//								TitleBarView.this.getContext(),
//								SplashScreenActivity.class
//								));
					}else{
						_Enter_Handler_.onClickEnterButton(v);
					}
				}
	        }  else {
	        	;
	        }
			return false;
		}
		
	}
}

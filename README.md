## GradientTextView 效果

![grad](https://cloud.githubusercontent.com/assets/27534854/26811851/e7ca915a-4aa6-11e7-993a-0f60f53132fa.gif)

## GradientTextView 功能
* GradientTextView是一个文字颜色逐步改变的自定义view，目前只有两种颜色。

## 如何使用 GradientTextView

* Step 1. Add it in your root build.gradle at the end of repositories:

```
  allprojects {
    	repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
* Step 2. Add the dependency

```
 dependencies {
            compile 'com.github.livesun:GradientTextView:v1.0'
	}
```
* Step 3 xml布局

```
<gradient.mylibrary.GradienTextView
        android:id="@+id/gradienTextView"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content" 
        app:originalCololr="@color/colorPrimary"
        app:change_color="@color/colorAccent"
        android:textSize="15sp"
        />
```
* Step 4 代码调用
```
//简单使用

 GradienTextView gradienTextView = (GradienTextView) findViewById(R.id.gradienTextView);
 gradienTextView.start(Orientation.LEFT_TO_RIGHT,1000);
 
 
 //也可以自定义
 
  gradienTextView.setOrientation(Orientation.LEFT_TO_RIGHT);
        ValueAnimator animator = ValueAnimator.ofFloat(new float[]{0.0F, 1.0F});
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = ((Float)animation.getAnimatedValue()).floatValue();

                gradienTextView.setCurrentProgress(value);
            }
        });
        animator.start();
        
        
    //也可以静态展示
    
     gradienTextView.start(Orientation.LEFT_TO_RIGHT,1000);
     gradienTextView.setCurrentProgress(0.5f);
```


##有问题反馈
在使用中有任何问题，欢迎反馈给我，可以用以下联系方式跟我交流

* QQ: 2399747642
我的博客：https://livesun.github.io/

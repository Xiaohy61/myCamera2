   &emsp;&emsp;前段时间，公司项目有个要求，拍照要获取矩形里面的照片，而且还可以任意裁剪后再上传的，做出来后遇到了一个坑，在华为荣耀手机api为21的手机里无论怎么调，它返回的照片就是400*320的，其它手机没问题，折腾好久，用camera2解决了这个问题，.....不知道是不是他们工程师把图片分辨率返回那个接口搞死了还是怎样，不多说了先上图片
   <img width="50%" height="50%" src="http://om8encbr2.bkt.clouddn.com/2017-03-16-03mzone.gif"/>


 ps:框是红色的，是录制gif的时候，颜色变了。
 &emsp;&emsp;思路：在预览照片的屏幕上面，用draw画出是个矩形角，然后获取在屏幕上的四个矩形角分别距离原图（原则上是屏幕边缘）边缘有多少距离，然后原图的宽和高分别减去他们，剩下的就是矩形里面的宽高（即矩形框里面照片的宽高），拍照保存之前算出这些参数，然后再利用Bitmap.createBitmap（）去创建图片

在屏幕上画矩形的代码：

```
public class DrawRectView extends ImageView{
    private int width;
    private int height;
    private int maskWidth;
    private int maskHeight;
    private Paint linePaint;


    public DrawRectView(Context context, int width) {
        super(context);
        init();
    }

    public DrawRectView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public DrawRectView(Context context, AttributeSet attrs, int defStyleAttr, int width) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.RED);
        linePaint.setStyle(Style.FILL);
        linePaint.setStrokeWidth(8f);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int h = Math.abs((height-maskHeight)/2);
        int w = Math.abs((width-maskWidth)/2);
//        Log.i("myLog","w "+ w+" h " +h);

        //画矩形框的四个角
        //左上角
        canvas.drawLine(w,h,w+30,h,linePaint);// _
        canvas.drawLine(w,h,w,h+30,linePaint);// |
        //左下角
        canvas.drawLine(w,h+maskHeight,w,h+maskHeight-30,linePaint);// |
        canvas.drawLine(w,h+maskHeight,w+30,h+maskHeight,linePaint);// _
        //右上角
        canvas.drawLine(w+maskWidth,h,w+maskWidth-30,h,linePaint);// _
        canvas.drawLine(w+maskWidth,h,w+maskWidth,h+30,linePaint);// |
        //右下角
        canvas.drawLine(w+maskWidth,h+maskHeight,w+maskWidth,h+maskHeight-30,linePaint);// |
        canvas.drawLine(w+maskWidth,h+maskHeight,w+maskWidth-30,h+maskHeight,linePaint);// _
    }


    public void PixSize(Integer w, Integer h){
        width = w;
        height = h;
    }
    public void setMaskSize(Integer widths, Integer heights){
        maskWidth = widths;
        maskHeight = heights;
    }

    public int[] getMaskSize(){
        return new MaskSize().size;
    }

    private class MaskSize{
            private final int[] size;
            private MaskSize(){
            this.size = new int[]{maskWidth, maskHeight, width, height};
        }
    }

```

然后拍照保存前的最重要的一段代码：获取拍照时的图片，用原图的宽和高分别减去他们，剩下的就是矩形里面的宽高区域，之后重新Bitmap.createBitmap（）图片,保存在显示到屏幕上，就是我们想要的效果了

```
private Bitmap cutImage(byte[] data) {
        Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap, 1080, 1440, true);
        int[] sizes = this.mDrawIV.getMaskSize();
        if (sizes[0] == 0 || sizes[1] == 0) {
            return bitmap1;
        } else {
            int w = bitmap1.getWidth();
            int h = bitmap1.getHeight();
            // Log.i("myLog", "bitmapWidth  " + w);
            //Log.i("myLog", "bitmapHeight  " + h);
            int x = (w - sizes[0]) / 2;
            int y = (h - sizes[1]) / 2;
            Log.i("myLog", "x " + x + " y " + y);

            //Log.i("myLog","sizes[0] "+sizes[0]+" sizes[1]"+sizes[1]);

            return Bitmap.createBitmap(bitmap1, x, y, sizes[0], sizes[1]);
        }
    }
```
camera2的java代码比较多，就不贴出来了，里面有比较详细的解析，放在git上面了，有兴趣的clone下来look a look，https://github.com/Xiaohy61/myCamera2。

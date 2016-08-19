package com.example.jamesdouble.checkinspecture;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

public class DrawView extends View {

    public DrawView(Context context) {
        super(context);
    }
    int windowx;
    int windowy;
    String name;
    String store;
    Bitmap pic;
    String state;
    public void setall(int x,int y,String n,String s,Bitmap b,String st)
    {
        state = st;
        windowx = x;
        windowy = y;
        name = n;
        store = s;
        pic=b;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        Paint p = new Paint();
        p.setColor(Color.BLACK);//
        p.setTextSize(49);
        p.setStrokeWidth(5); //粗細
        canvas.drawText(name, (windowx*101/1000), (windowy*374/1000), p);  //動態一名子

        canvas.drawText(store,(windowx*32/100),(windowy*374/1000),p);  //動態一景點
        canvas.drawText(store,(windowx*19/100),(windowy*74/100),p);  //標示景點

        canvas.drawText(state,(windowx*50/1000),(windowy*460/1000),p);  //動態內容

        p.setAntiAlias(true);// 设置画笔的锯齿效果。 true是去除，大家一看效果就明白了

        if(pic!=null)
        {
            pic = Bitmap.createScaledBitmap(pic,(windowx*139/1000),(windowx*139/1000),false);
            Log.e("pink","I got a photo");
            canvas.drawBitmap(pic,(windowx*21/1000),(windowy*714/1000),p);
        }

    }
}

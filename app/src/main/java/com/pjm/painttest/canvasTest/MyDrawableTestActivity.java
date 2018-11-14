package com.pjm.painttest.canvasTest;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.canvasTest.customView.MyDrawable;
import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *  图片过滤效果测试
 */
public class MyDrawableTestActivity extends BaseActivity {

    @BindView(R.id.rv)
    RecyclerView rv;
    private BitmapDrawable emptyDrawable;
    private int emptyWidth;
    private int imageWidth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_drawable_test);
        unbinder = ButterKnife.bind(this);
        init();

    }

    private void init() {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int width = metrics.widthPixels;
        imageWidth = (int) (200 * metrics.density);
        emptyWidth = (int) ((width - imageWidth) / 2f);
        Bitmap bitmap = Bitmap.createBitmap(emptyWidth, emptyWidth, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFF161616);
        emptyDrawable = new BitmapDrawable(getResources(),bitmap);
        //emptyDrawable =
//        emptyDrawable = getResources().getDrawable(R.drawable.empty);
        emptyDrawable.setBounds(0, 0, emptyWidth, imageWidth);
        L.e(emptyDrawable.getBounds().width() + ", height = " + emptyDrawable.getBounds().height());
        int[] drawableIds = {0, R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R.mipmap.p4, R.mipmap.p5, R.mipmap.p6,R.mipmap.p7, 0};
//        int[] drawableIds = {R.mipmap.p1, R.mipmap.p2, R.mipmap.p3, R.mipmap.p4, R.mipmap.p5, R.mipmap.p6,R.mipmap.p7};
//        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rv.getLayoutParams();
        final LinearLayoutManager manager =  new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false);
        rv.setLayoutManager(manager);
        MyAdapter adapter = new MyAdapter(drawableIds);
        rv.setAdapter(adapter);
        rv.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int count = rv.getAdapter().getItemCount();
                for(int i= 0; i< count ; i++){
                    ImageView iv = (ImageView) rv.getChildAt(i);
                    if(iv != null){
                        int [] screen = new int[2];
                        iv.getLocationOnScreen(screen);

                        float left = screen[0];
                        float right = left + imageWidth;
                        if(left > emptyWidth + imageWidth){
                            iv.getDrawable().setLevel(0);
                        }else if (right <= emptyWidth){
                            iv.getDrawable().setLevel(0);
                        }else{
                            int level;
                            if(left > emptyWidth){
                                level = 5000 + (int) (5000f*(left - emptyWidth)/ imageWidth);
                            }else {
                                level = 5000 - (int) (5000f*(emptyWidth - left)/imageWidth);
                            }
                            L.e(", 第"+ i + "个view： left = " + screen[0] + " , level = " + level);
                            iv.getDrawable().setLevel(level);
                        }
                    }
                }

            }
        });
        L.i("count = " + rv.getAdapter().getItemCount());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ImageView iv = (ImageView) manager.findViewByPosition(1).findViewById(R.id.iv);
                iv.getDrawable().setLevel(5000);
            }
        },100);
    }


    class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyHolder> {

        private int[] drawableIds;

        public MyAdapter(int[] drawableIds){
            this.drawableIds = drawableIds;
        }


        @Override
        public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_rv, parent, false);
            return new MyHolder(itemView);
        }

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            BitmapDrawable drawable;
            ViewGroup.LayoutParams params =  holder.iv.getLayoutParams();
            if(drawableIds[position] == 0){
                drawable = emptyDrawable;
                params.width = emptyWidth;
            }else{
                drawable = (BitmapDrawable)getResources().getDrawable(drawableIds[position]);
                params.width = getResources().getDimensionPixelOffset(R.dimen.ivWidth);
            }
            MyDrawable myDrawable = new MyDrawable(drawable);
            holder.iv.setImageDrawable(myDrawable);
            holder.iv.setTag(position);
        }

        @Override
        public int getItemCount() {
            return drawableIds.length;
        }

        @Override
        public int getItemViewType(int position) {
            return 1;
        }

        class MyHolder extends RecyclerView.ViewHolder {
            @BindView(R.id.iv)
            ImageView iv;

            public MyHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

        }


    }


}

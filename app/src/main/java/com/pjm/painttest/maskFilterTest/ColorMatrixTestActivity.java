package com.pjm.painttest.maskFilterTest;

import android.graphics.ColorMatrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.maskFilterTest.customView.ColorMatrixView;
import com.pjm.painttest.R;
import com.pjm.painttest.utils.L;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 参考：http://blog.csdn.net/harvic880925/article/details/51187277/
 */
public class ColorMatrixTestActivity extends BaseActivity {

    @BindView(R.id.btn0)
    Button btn0;
    @BindView(R.id.btn1)
    Button btn1;
    @BindView(R.id.btn2)
    Button btn2;
    @BindView(R.id.btn3)
    Button btn3;
    @BindView(R.id.btn4)
    Button btn4;
    @BindView(R.id.btn5)
    Button btn5;
    @BindView(R.id.colorMatrixView)
    ColorMatrixView colorMatrixView;
    @BindView(R.id.btn6)
    Button btn6;
    @BindView(R.id.btn7)
    Button btn7;
    @BindView(R.id.btn8)
    Button btn8;
    @BindView(R.id.btn9)
    Button btn9;
    @BindView(R.id.btn10)
    Button btn10;
    @BindView(R.id.btn11)
    Button btn11;
    @BindView(R.id.btn12)
    Button btn12;
    @BindView(R.id.btn13)
    Button btn13;
    @BindView(R.id.btn14)
    Button btn14;
    @BindView(R.id.sb)
    SeekBar sb;

    private ColorMatrix colorMatrix;
    private float factory;
    private int selectId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_matrix_test);
        unbinder = ButterKnife.bind(this);
        factory = 1.2f;
        colorMatrix = new ColorMatrix();
        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
               factory  = progress / 100f;
                L.i("factory = " + factory);
                switch (selectId){
                    case 11:
                        // set Scale 调整亮度
                        colorMatrix.setScale(factory, factory, factory, 1);
                        colorMatrixView.setColorMatrix(colorMatrix);
                        break;
                    case 12:
                        //setSaturation 调整饱和度
                        colorMatrix.setSaturation(factory);
                        colorMatrixView.setColorMatrix(colorMatrix);
                        break;
                    case 13:
                        //setRotate 设置色调 axis: 0 -> RED color , 1-> GREEN color, 2-> BLUE color
                        // 360/5 *
                        colorMatrix.setRotate(0, 72*factory);
                        colorMatrixView.setColorMatrix(colorMatrix);
                        break;
                    case 14:
                        //setRotate 设置色调 axis: 0 -> RED color , 1-> GREEN color, 2-> BLUE color
                        // 360/5 *
                        colorMatrix.setRotate(1, 72*factory);
                        colorMatrixView.setColorMatrix(colorMatrix);
                        break;
                    case 15:
                        //setRotate 设置色调 axis: 0 -> RED color , 1-> GREEN color, 2-> BLUE color
                        // 360/5 *
                        colorMatrix.setRotate(2, 72*factory);
                        colorMatrixView.setColorMatrix(colorMatrix);
                        break;
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

    @OnClick({R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4, R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
            R.id.btn10, R.id.btn11, R.id.btn12, R.id.btn13, R.id.btn14, R.id.btn15})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn0:
                colorMatrixView.setColorMatrix(null);
                break;
            case R.id.btn1: // 黑白
                //方法一：设置饱和度为0
               /* ColorMatrix matrix = new ColorMatrix();
                matrix.setSaturation(0);
                colorMatrixView.setColorMatrix(matrix);*/
                /*
                  方法二：只要把RGB三通道的色彩信息设置成一样；即：R＝G＝B，那么图像就变成了灰色，
                  并且，为了保证图像亮度不变，同一个通道中的R+G+B=1:如：0.213+0.715+0.072＝1；
                  在作用于人眼的光线中，彩色光要明显强于无色光。对一个图像按RGB平分理论给图像去色的话，
                  人眼就会明显感觉到图像变暗了（当然可能有心理上的原因，也有光波的科学依据）另外，在彩色图像中能识别的一下细节也可能会丢失。
                  所以google最终给我们的颜色值就是上面的比例：0.213, 0.715, 0.072；
                  三个数字的由来：0.213, 0.715, 0.072； 查看matrix.setSaturation(0);
                */

                float[] array = {
                        0.213f, 0.715f, 0.072f, 0, 0,
                        0.213f, 0.715f, 0.072f, 0, 0,
                        0.213f, 0.715f, 0.072f, 0, 0,
                        0, 0, 0, 1, 0,
                };
                colorMatrix.set(array);
                colorMatrixView.setColorMatrix(colorMatrix);
                break;

            case R.id.btn2: // 底片
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        -1,0,0,1,1,
                        0,-1,0,1,1,
                        0,0,-1,1,1,
                        0,0, 0,1,0
                }));
                break;


            case R.id.btn3: // 旧照片
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        1 / 2f, 1 / 2f, 1 / 2f, 0, 0,
                        1 / 3f, 1 / 3f, 1 / 3f, 0, 0,
                        1 / 4f, 1 / 4f, 1 / 4f, 0, 0,
                        0, 0, 0, 1, 0,
                }));

                break;

            case R.id.btn4: // 怀旧
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        0.393f, 0.769f, 0.189f, 0, 0,
                        0.349f, 0.686f, 0.168f, 0, 0,
                        0.272f, 0.534f, 0.131f, 0, 0,
                        0, 0, 0, 1, 0
                }));

                break;

            case R.id.btn5: // 浮雕
                colorMatrixView.setEmbossFilter();

               /*
                B.r=C.r-B.r+127
                B.g=C.g-B.g+127
                B.b=C.b-B.b+127
                */

                break;
            case R.id.btn6: // 宝丽来彩色
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        1.438f, -0.062f, -0.062f, 0, 0,
                        -0.122f, 1.378f, -0.122f, 0, 0,
                        -0.016f, -0.016f, 1.483f, 0, 0,
                        -0.03f, 0.05f, -0.02f, 1, 0
                }));

                break;
            case R.id.btn7: // 泛红

                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        2, 0, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0
                }));

                break;
            case R.id.btn8: //泛黄
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        1, 0, 0, 0, 50,
                        0, 1, 0, 0, 50,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0
                }));

                break;
            case R.id.btn9: // 荧光绿
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 1.4f, 0, 0, 0,
                        0, 0, 1, 0, 0,
                        0, 0, 0, 1, 0
                }));
                break;
            case R.id.btn10: //  宝石蓝
                colorMatrixView.setColorMatrix(new ColorMatrix(new float[]{
                        1, 0, 0, 0, 0,
                        0, 1, 0, 0, 0,
                        0, 0, 1.6f, 0, 0,
                        0, 0, 0, 1, 0
                }));
                break;
            case R.id.btn11: // 亮度增强
                selectId = 11;
                sb.setProgress(100);
                colorMatrix.setScale(factory, factory, factory, 1);
                colorMatrixView.setColorMatrix(colorMatrix);
                break;

            case R.id.btn12: // 饱和度增强
                selectId = 12;
                sb.setProgress(100);
                colorMatrix.setSaturation(factory);
                colorMatrixView.setColorMatrix(colorMatrix);
                break;

            case R.id.btn13: // 对比度增强
                selectId = 13;
                sb.setProgress(0);
                colorMatrix.setRotate(0,72 * factory);
                colorMatrixView.setColorMatrix(colorMatrix);
                break;

            case R.id.btn14: // 底片
                selectId = 14;
                sb.setProgress(0);
                colorMatrix.setRotate(1,72 * factory);
                colorMatrixView.setColorMatrix(colorMatrix);

                break;

            case R.id.btn15: // 底片
                selectId = 15;
                sb.setProgress(0);
                colorMatrix.setRotate(2 ,72 * factory);
                colorMatrixView.setColorMatrix(colorMatrix);
                break;

        }
    }

     /*



            1、黑白
            ColorMatrix colorMatrix = new ColorMatrix();
            colorMatrix.setSaturation(0);
            或者：
            0.33F,  0.59F,  0.11F,  0,  0,
            0.33F,  0.59F,  0.11F,  0,  0,
            0.33F,  0.59F,  0.11F,  0,  0,
            0,  0,  0,  1,  0

           2、老照片效果

            B.r=(int) (0.393*B.r+0.769*B.g+0.189*B.b);
            B.g=(int) (0.349*B.r+0.686*B.g+0.168*B.b);
            B.b=(int)(0.272*B.r+0.534*B.g+0.131*B.b);


            3、浮雕效果

            B.r=C.r-B.r+127
            B.g=C.g-B.g+127
            B.b=C.b-B.b+127

            4、怀旧效果
            0.393f,0.769f,0.189f,0,0,
            0.349f,0.686f,0.168f,0,0,
            0.272f,0.534f,0.131f,0,0,
            0,0,0,1,0

           5、 宝丽来彩色[Polaroid Color]
             1.438, -0.062, -0.062, 0, 0,
             -0.122, 1.378, -0.122, 0, 0,
            -0.016, -0.016, 1.483, 0, 0,
            -0.03, 0.05, -0.02, 1, 0;

            6，泛红
            2,0,0,0,0,
            0,1,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0

            7，泛绿（荧光绿）
            1,0,0,0,0,
            0,1.4,0,0,0,
            0,0,1,0,0,
            0,0,0,1,0

            8、泛蓝（宝石蓝）
            1,0,0,0,0,
            0,1,0,0,0,
            0,0,1.6,0,0,
            0,0,0,1,0

            9、泛黄（把红色 跟  绿色分量都加50）
            1,0,0,0,50,
            0,1,0,0,50,
            0,0,1,0,0,
            0,0,0,1,0

            */

}

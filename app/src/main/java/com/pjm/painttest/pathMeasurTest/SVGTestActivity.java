package com.pjm.painttest.pathMeasurTest;

import android.graphics.Color;
import android.graphics.Path;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mcxtzhang.pathanimlib.PathAnimView;
import com.pjm.painttest.BaseActivity;
import com.pjm.painttest.pathMeasurTest.pathParser.PathParser;
import com.pjm.painttest.R;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 *  参考：http://blog.csdn.net/zxt0601/article/details/53040506
 */
public class SVGTestActivity extends BaseActivity {

    @BindView(R.id.pathAnimView1)
    PathAnimView pathAnimView1;
    @BindView(R.id.btn0)
    Button btn0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svgtest);
        ButterKnife.bind(this);
        try {
            InputStream is = getResources().openRawResource(R.raw.logo1);
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = builder.parse(is);
            NodeList list = document.getElementsByTagName("path");
            String pathData0 = ((Element) list.item(0)).getAttribute("android:pathData");
//            String pathData1 = ((Element) list.item(1)).getAttribute("android:pathData");
            Path path = PathParser.createPathFromPathData(pathData0);
            pathAnimView1.setSourcePath(path);
        } catch (Exception e) {
            e.printStackTrace();
        }

       // pathAnimView1.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("viper", 1.1f, 16)));

    }

    @OnClick({R.id.btn0})
    public void onClick(View v) {

        //设置颜色
        pathAnimView1.setColorBg(Color.TRANSPARENT).setColorFg(Color.BLACK);
        pathAnimView1.setVisibility(View.VISIBLE);
        //设置了动画总时长，只执行一次的动画
        pathAnimView1.setAnimTime(4000).setAnimInfinite(false).startAnim();

    }


}

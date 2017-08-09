package com.punuo.sys.app.flowervideo;

import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import biz.zhidu.zdsdk.FDInfo;
import biz.zhidu.zdsdk.VideoStreamsView;
import biz.zhidu.zdsdk.ZhiduEye;
import biz.zhidu.zdsdk.ZhiduEyeAgent;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.start)
    Button start;
    @Bind(R.id.stop)
    Button stop;
    @Bind(R.id.up)
    Button up;
    @Bind(R.id.down)
    Button down;
    @Bind(R.id.left)
    Button left;
    @Bind(R.id.right)
    Button right;
    @Bind(R.id.record)
    Button record;
    @Bind(R.id.play)
    Button play;

    private ZhiduEye zhiduEye;

    private ZhiduEyeAgent eyeAgent;

    private Boolean recordFlag = false;

    private long gPlayRcHandle = 0;

    private VideoStreamsView viewStream;

    private LinearLayout surfaceView;

    private String id = "340200-340200200000100008-0001-0001";

    public int result;

    private FDInfo[] fdInfos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        surfaceView = (LinearLayout) findViewById(R.id.surfaceView);

        zhiduEye = new ZhiduEye(this);
        viewStream = zhiduEye.createVideoView(new Point(1920, 1080));
        surfaceView = (LinearLayout) findViewById(R.id.surfaceView);
        surfaceView.addView(viewStream);

        eyeAgent = zhiduEye.createAgent();
        eyeAgent.startLogin("114.215.169.7", (short)5555, "zhidu004@zhidu.biz", "12345");
                // init();

    }

    @OnClick({R.id.start, R.id.stop, R.id.up, R.id.down, R.id.left, R.id.right, R.id.record, R.id.play})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                fdInfos = eyeAgent.getFDInfoList();
                if (fdInfos != null){
                    System.out.println("infoLength" + fdInfos.length);

                }
//                int width = surfaceView.getWidth();
//                int height = surfaceView.getHeight();
//                viewStream = zhiduEye.createVideoView(new Point(1080, 1417));
//                surfaceView.addView(viewStream);
                result = eyeAgent.startMonitorChannel(viewStream, id);
                System.out.println("width " + viewStream.getWidth() + " height " + viewStream.getHeight() + " startChannel " +result);

//                surfaceView.addView(viewStream);
                break;
            case R.id.stop:
                result = eyeAgent.stopMonitorChannel(id);
                System.out.println("stop " +result);
                break;
            case R.id.up:
                eyeAgent.controlCamera(id,eyeAgent.CAMERA_CONTROL_ACTION_UP,(byte)0);
                eyeAgent.controlCamera(id,eyeAgent.CAMERA_CONTROL_ACTION_STOP,(byte)0);
                break;
            case R.id.down:
                eyeAgent.controlCamera(id,eyeAgent.CAMERA_CONTROL_ACTION_DOWN,(byte)0);
                break;
            case R.id.left:
                eyeAgent.controlCamera(id,eyeAgent.CAMERA_CONTROL_ACTION_LEFT,(byte)0);
                break;
            case R.id.right:
                eyeAgent.controlCamera(id,eyeAgent.CAMERA_CONTROL_ACTION_RIGHT,(byte)0);
                break;
            case R.id.record:
                if (recordFlag = true){
                   int result = eyeAgent.stopPlayback(gPlayRcHandle);
                    recordFlag = false;

                }else if (recordFlag = false){
                   int size = eyeAgent.startQueryRecord(id,false,2017-8-7, 2017-8-7);
                    recordFlag = true;
                    System.out.println("record size is " + size);
                }
                break;
            case R.id.play:
                break;
        }
    }

    private void init(){
        zhiduEye = new ZhiduEye(this);
        eyeAgent = zhiduEye.createAgent();

        int login = eyeAgent.startLogin("114.215.169.7", (short)5555, "zhidu004@zhidu.biz", "12345");
        System.out.println("login "+login);
    }
}

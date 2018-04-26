package com.william_zhang.pi_car.mvp.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LoggingCodeGeneratorCallback;
import com.google.blockly.model.DefaultBlocks;
import com.william_zhang.base.utils.ScreenUtil;
import com.william_zhang.pi_car.Manager.CarHtmlManager;
import com.william_zhang.pi_car.R;
import com.william_zhang.pi_car.constant.Key;
import com.william_zhang.pi_car.mvp.contact.BlocklyContact;
import com.william_zhang.pi_car.mvp.presenter.BlocklyPresenter;
import com.william_zhang.pi_car.service.BlocklyService;
import com.william_zhang.pi_car.service.CarAidlInterface;
import com.william_zhang.pi_car.utils.JavascriptUtil;

import java.util.Arrays;
import java.util.List;


/**
 * Created by william_zhang on 2018/3/25.
 */

public class BlocklyActivity extends BaseBlocklyActivity<BlocklyContact.presenter> implements BlocklyContact.view {
    private static final String TAG = "BlocklyActivity";
    private ImageButton app_socket_status;
    private CarHtmlManager mJSBridge;
    private CarAidlInterface carAidl;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            carAidl = CarAidlInterface.Stub.asInterface(iBinder);
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            carAidl = null;
        }
    };

    private BridgeWebView mBridgeWebView;

    @Override
    protected View onCreateContentView(int containerId) {
        View view = getLayoutInflater().inflate(R.layout.activity_blockly, null);
        mBridgeWebView = (BridgeWebView) view.findViewById(R.id.app_blockly_webview);
        return view;
    }


    @Override
    protected void onResume() {
        super.onResume();
        setBottomView();
    }

    /**
     * 设置底部显示框位置
     */
    private void setBottomView() {
        RelativeLayout rl_bottomview = (RelativeLayout) findViewById(R.id.rl_bottomview);
        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) rl_bottomview.getLayoutParams();
        layoutParams.topMargin = ScreenUtil.getScreenHeight(getApplicationContext()) - ScreenUtil.dip2px(getApplicationContext(), 56f) - ScreenUtil.getStatusHeight(getApplicationContext()) - ScreenUtil.dip2px(getApplicationContext(), 20f);
        rl_bottomview.setLayoutParams(layoutParams);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        this.getApplicationContext().bindService(new Intent(this, BlocklyService.class), serviceConnection, Context.BIND_AUTO_CREATE);
        super.onCreate(savedInstanceState);
        presenter.init();
    }

    /**
     * 连接服务
     */
    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 断开服务
     */
    @Override
    protected void onDestroy() {
        this.getApplicationContext().unbindService(serviceConnection);
        super.onDestroy();
    }

    /**
     * 绑定presenter
     *
     * @return
     */
    @Override
    public BlocklyContact.presenter initPresenter() {
        return new BlocklyPresenter(this);
    }

    //下面是blockly相关 代码
    CodeGenerationRequest.CodeGeneratorCallback codeGeneratorCallback = new CodeGenerationRequest.CodeGeneratorCallback() {
        @Override
        public void onFinishCodeGeneration(String generatedCode) {
            Toast.makeText(BlocklyActivity.this, generatedCode, Toast.LENGTH_LONG).show();
            Log.e(TAG, generatedCode);
            //mJSBridge.executeCode(JavascriptUtil.makeJsString(generatedCode));
        }
    };
    private static final List<String> JAVASCRIPT_GENERATORS = Arrays.asList(
            "car/generators.js"
    );
    private static final String BLOCK_TOOLBOX = DefaultBlocks.TOOLBOX_PATH;
    private static final List<String> BLOCK_DEFINITIONS = DefaultBlocks.getAllBlockDefinitions();
    static final List<String> CAR_BLOCK_DEFINITIONS = Arrays.asList(
            DefaultBlocks.COLOR_BLOCKS_PATH,
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            DefaultBlocks.LOOP_BLOCKS_PATH,
            DefaultBlocks.MATH_BLOCKS_PATH,
            DefaultBlocks.TEXT_BLOCKS_PATH,
            DefaultBlocks.VARIABLE_BLOCKS_PATH,
            "car/car_blocks.json"
    );

    /**
     * 返回assets/定义工具箱内容的XML路径。
     *
     * @return
     */
    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "car/car_advanced.xml";
    }

    /**
     * 将assets/目录中的路径返回到块定义.json文件。
     * 下面的实现导入Blockly库提供的默认块。
     *
     * @return
     */
    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return CAR_BLOCK_DEFINITIONS;
    }

    /**
     * 将assets/目录中的路径返回到.js定义块实现的生成器文件。
     * 当前的实现包括Blockly默认块的所有JavaScript实现。
     *
     * @return
     */
    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return JAVASCRIPT_GENERATORS;
    }

    /**
     * 代码最终回调
     *
     * @return
     */
    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        return codeGeneratorCallback;
    }

    @Override
    public void init() {
        initView();
    }


    /**
     * 初始化view
     */
    private void initView() {
        WebSettings webSettings = mBridgeWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);//知识js
        mBridgeWebView.setDefaultHandler(new DefaultHandler());
        mBridgeWebView.setWebViewClient(new BridgeWebViewClient(mBridgeWebView));
        mJSBridge = new CarHtmlManager(mBridgeWebView);
        mBridgeWebView.loadUrl(Key.CARHTML);
        app_socket_status = (ImageButton) findViewById(R.id.app_blockly_status);
        //((ImageButton) findViewById(R.id.blockly_center_view_button)).setImageDrawable(AppUtil.setIconColor(getResources().getDrawable(R.drawable.reset_view), AppUtil.getColorPrimary(getApplicationContext())));
        //((ImageButton) findViewById(R.id.blockly_zoom_out_button)).setImageDrawable(AppUtil.setIconColor(getResources().getDrawable(R.drawable.zoom_out), AppUtil.getColorPrimary(getApplicationContext())));
        //((ImageButton) findViewById(R.id.blockly_zoom_in_button)).setImageDrawable(AppUtil.setIconColor(getResources().getDrawable(R.drawable.zoom_in), AppUtil.getColorPrimary(getApplicationContext())));
        //Drawable drawable =AppUtil.setIconColor(ContextCompat.getDrawable(getApplicationContext(), R.drawable.reset_view), AppUtil.getColorPrimary(getApplicationContext()));

    }

    @Override
    public void setCSBHtml(String csb) {
        mJSBridge.setCBS(csb);
//        mBridgeWebView.evaluateJavascript("javascript:callJS(\""+csb+"\")", new ValueCallback<String>() {
//            @Override
//            public void onReceiveValue(String value) {
//                //此处为 js 返回的结果
//            }
//        });
    }

    /**
     * 嘗試鏈接
     */
    @Override
    public void connectSocket() {
        //默认会尝试连接socket
        try {
            carAidl.initDefaultSocket();
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {

        }
    }

    @Override
    public void connectFail() {
        dismissLoadingDialog();
        new AlertDialog.Builder(BlocklyActivity.this)
                .setTitle("提示")
                .setMessage("小车连接失败，请检查网络连接")
                .setPositiveButton("确定", null)
                .show();
        app_socket_status.setImageResource(R.drawable.gantanhao_d81e06);
        app_socket_status.setTag("no");
    }

    @Override
    public void connectSuccess() {
        dismissLoadingDialog();
        new AlertDialog.Builder(BlocklyActivity.this)
                .setTitle("提示")
                .setMessage("小车连接成功")
                .setPositiveButton("确定", null)
                .show();
        app_socket_status.setImageResource(R.drawable.gou_388e3c);
        app_socket_status.setTag("yes");
    }


    public void refreshStatus(final View v) {
        String type = (String) v.getTag();
        if (type != null && type.equals("no")) {
            new AlertDialog.Builder(BlocklyActivity.this)
                    .setTitle("提示")
                    .setMessage("小车未连接，是否重尝试连接")
                    .setPositiveButton("马上连接", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showLoadingDialog("小车");
                            connectSocket();
                        }
                    })
                    .show();
        } else {
            new AlertDialog.Builder(BlocklyActivity.this)
                    .setTitle("提示")
                    .setMessage("小车连接正常")
                    .setPositiveButton("知道啦", null)
                    .show();
        }
//        new AlertDialog.Builder(BlocklyActivity.this)
//                .setPositiveButton("超声波", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        mJSBridge.setCBS(Integer.toString((int) (Math.random()*100)));
//                    }
//                })
//                .setNegativeButton("init", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialogInterface, int i) {
//                        mJSBridge.init();
//                    }
//                }).show();
    }
}

package com.william_zhang.pi_car.mvp.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.blockly.android.AbstractBlocklyActivity;
import com.google.blockly.android.codegen.CodeGenerationRequest;
import com.google.blockly.android.codegen.LanguageDefinition;
import com.google.blockly.model.DefaultBlocks;
import com.william_zhang.pi_car.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by william_zhang on 2018/4/8.
 */

public class PythonActivity extends AbstractBlocklyActivity {
    private static final String TAG = "PythonActivity";

    private static final String SAVE_FILENAME = "python_workspace.xml";
    private static final String AUTOSAVE_FILENAME = "python_workspace_temp.xml";
    // Add custom blocks to this list.
    private static final List<String> BLOCK_DEFINITIONS = DefaultBlocks.getAllBlockDefinitions();
    private static final List<String> PYTHON_GENERATORS = Arrays.asList("python/generators.js");

    private static final LanguageDefinition PYTHON_LANGUAGE_DEF
            = new LanguageDefinition("python/python_compressed.js", "Blockly.Python");

    private TextView mGeneratedTextView;
    private Handler mHandler;

    private String mNoCodeText;

    CodeGenerationRequest.CodeGeneratorCallback mCodeGeneratorCallback =
            new CodeGenerationRequest.CodeGeneratorCallback() {
                @Override
                public void onFinishCodeGeneration(final String generatedCode) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PythonActivity.this, generatedCode, Toast.LENGTH_LONG).show();
                            mGeneratedTextView.setText(generatedCode);
                            updateTextMinWidth();
                            new AlertDialog.Builder(PythonActivity.this).setTitle("code").setMessage(generatedCode).setPositiveButton("运行", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            }).setNegativeButton("取消", null).show();
                        }
                    });
                }
            };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHandler = new Handler();
    }

    @Override
    protected View onCreateContentView(int parentId) {
        View root = getLayoutInflater().inflate(R.layout.split_content, null);
        mGeneratedTextView = (TextView) root.findViewById(R.id.generated_code);
        updateTextMinWidth();

        mNoCodeText = mGeneratedTextView.getText().toString(); // Capture initial value.

        return root;
    }

    @Override
    protected int getActionBarMenuResId() {
        return R.menu.split_actionbar;
    }


    static final List<String> CAR_BLOCK_DEFINITIONS = Arrays.asList(
            DefaultBlocks.COLOR_BLOCKS_PATH,
            DefaultBlocks.LOGIC_BLOCKS_PATH,
            DefaultBlocks.LOOP_BLOCKS_PATH,
            DefaultBlocks.MATH_BLOCKS_PATH,
            DefaultBlocks.TEXT_BLOCKS_PATH,
            DefaultBlocks.VARIABLE_BLOCKS_PATH,
            "python/car_blocks.json"
    );

    @NonNull
    @Override
    protected List<String> getBlockDefinitionsJsonPaths() {
        return CAR_BLOCK_DEFINITIONS;
    }

    @NonNull
    @Override
    protected LanguageDefinition getBlockGeneratorLanguage() {
        return PYTHON_LANGUAGE_DEF;
    }

    @NonNull
    @Override
    protected String getToolboxContentsXmlPath() {
        return "python/car_advanced.xml";
    }

    @NonNull
    @Override
    protected List<String> getGeneratorsJsPaths() {
        return PYTHON_GENERATORS;
    }

    @NonNull
    @Override
    protected CodeGenerationRequest.CodeGeneratorCallback getCodeGenerationCallback() {
        // Uses the same callback for every generation call.
        return mCodeGeneratorCallback;
    }

    @Override
    public void onClearWorkspace() {
        super.onClearWorkspace();
        mGeneratedTextView.setText(mNoCodeText);
        updateTextMinWidth();
    }

    /**
     * Estimate the pixel size of the longest line of text, and set that to the TextView's minimum
     * width.
     */
    private void updateTextMinWidth() {
        String text = mGeneratedTextView.getText().toString();
        int maxline = 0;
        int start = 0;
        int index = text.indexOf('\n', start);
        while (index > 0) {
            maxline = Math.max(maxline, index - start);
            start = index + 1;
            index = text.indexOf('\n', start);
        }
        int remainder = text.length() - start;
        if (remainder > 0) {
            maxline = Math.max(maxline, remainder);
        }

        float density = getResources().getDisplayMetrics().density;
        mGeneratedTextView.setMinWidth((int) (maxline * 13 * density));
    }

    /**
     * Optional override of the save path, since this demo Activity has multiple Blockly
     * configurations.
     *
     * @return Workspace save path used by this Activity.
     */
    @Override
    @NonNull
    protected String getWorkspaceSavePath() {
        return SAVE_FILENAME;
    }

    /**
     * Optional override of the auto-save path, since this demo Activity has multiple Blockly
     * configurations.
     *
     * @return Workspace auto-save path used by this Activity.
     */
    @Override
    @NonNull
    protected String getWorkspaceAutosavePath() {
        return AUTOSAVE_FILENAME;
    }
}

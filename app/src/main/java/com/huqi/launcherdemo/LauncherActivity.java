package com.huqi.launcherdemo;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class LauncherActivity extends Activity {

    private RecyclerView mRvTest;
    private List<ResolveInfo> mlsResolveInfo;
    private RvTestAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        mRvTest = findViewById(R.id.rv_test);
        mlsResolveInfo = new ArrayList<>();

        mRvTest.setLayoutManager(new GridLayoutManager(this, 4));
        mAdapter = new RvTestAdapter(LauncherActivity.this, mlsResolveInfo, new RvTestAdapter.OnItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                ResolveInfo resolveInfo = mlsResolveInfo.get(position);
                startAppByResolveInfo(resolveInfo);
                LauncherActivity.this.finish();
            }
        });

        mRvTest.setAdapter(mAdapter);

        List<ResolveInfo> resolveInfos = queryMainActivitiesInfo();
        mlsResolveInfo.addAll(resolveInfos);
        mAdapter.notifyDataSetChanged();

    }


    /**
     * 查询所有包含启动 intent 的 Activity 信息（去掉本应用）
     *
     * @return
     */
    private List<ResolveInfo> queryMainActivitiesInfo() {
        Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
        List<ResolveInfo> resolveInfos = getPackageManager().queryIntentActivities(mainIntent, 0);
        // 去掉本应用
        Iterator<ResolveInfo> iterator = resolveInfos.iterator();
        while (iterator.hasNext()) {
            ResolveInfo resolveInfo = iterator.next();
            String packageName = resolveInfo.activityInfo.packageName;
            if (packageName.equals(getApplication().getPackageName())) {
                iterator.remove();
            }
        }
        return resolveInfos;
    }


    private void startAppByResolveInfo(ResolveInfo resolveInfo) {
        String pkg = resolveInfo.activityInfo.packageName;
        String cls = resolveInfo.activityInfo.name;
        ComponentName componet = new ComponentName(pkg, cls);
        //打开该应用的主activity
        Intent intent = new Intent();
        intent.setComponent(componet);
        startActivity(intent);
    }

}
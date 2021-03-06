package com.example.hu.maogaiproject.Activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.db.manager.QylEntityDao;
import com.db.manager.TaskEntityDao;
import com.example.hu.maogaiproject.Adapter.Data1ActivityAdapter;
import com.example.hu.maogaiproject.Adapter.Data2ActivityAdapter;
import com.example.hu.maogaiproject.Application.MyApp;
import com.example.hu.maogaiproject.Entity.QylEntity;
import com.example.hu.maogaiproject.Entity.TaskEntity;
import com.example.hu.maogaiproject.R;

import java.io.File;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import jxl.CellType;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCell;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class DataManagerActivity extends AppCompatActivity {

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.item0)
    TextView item0;
    @BindView(R.id.item1)
    TextView item1;
    @BindView(R.id.item2)
    TextView item2;
    @BindView(R.id.item3)
    TextView item3;
    @BindView(R.id.ll_tittle_name)
    LinearLayout llTittleName;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.listview)
    ListView listview;
    @BindView(R.id.line3)
    View line3;
    @BindView(R.id.tv_pingJun)
    TextView tvPingJun;
    @BindView(R.id.tv_pingJunValue)
    TextView tvPingJunValue;
    @BindView(R.id.ll_pingJun)
    LinearLayout llPingJun;
    @BindView(R.id.line4)
    View line4;
    @BindView(R.id.tv_leiJi)
    TextView tvLeiJi;
    @BindView(R.id.tv_leiJiValue)
    TextView tvLeiJiValue;
    @BindView(R.id.ll_leiJi)
    LinearLayout llLeiJi;
    @BindView(R.id.line5)
    View line5;
    @BindView(R.id.et1)
    EditText et1;
    @BindView(R.id.et2)
    EditText et2;
    @BindView(R.id.et3)
    EditText et3;
    @BindView(R.id.et4)
    EditText et4;
    @BindView(R.id.btn_chaXun)
    Button btnChaXun;
    @BindView(R.id.btn_quanBu)
    Button btnQuanBu;
    @BindView(R.id.listView2)
    ListView listView2;

    private List<TaskEntity> taskEntities;
    private List<QylEntity> qylEntities;
    private TaskEntityDao taskEntityDao;
    private QylEntityDao qylEntityDao;
    private Context context;
    private Data1ActivityAdapter data1Adapter;
    private Data2ActivityAdapter data2Adapter;
    private DecimalFormat df2 = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_manager);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //决定左上角的图标是否可以点击
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        this.setTitle("数据管理");

        context = this;
        taskEntities = new ArrayList<>();
        qylEntities = new ArrayList<>();
        taskEntityDao = MyApp.getInstance().getmDaoSession().getTaskEntityDao();
        qylEntityDao = MyApp.getInstance().getmDaoSession().getQylEntityDao();
        taskEntities = taskEntityDao.loadAll();
        //刚进入时，上面的listview不显示数据
        listview.setVisibility(View.INVISIBLE);
        tvPingJunValue.setText("--");
        tvLeiJiValue.setText("--");
//        qylEntities = qylEntityDao.loadAll();
        if (taskEntities.size() != 0) {
            Collections.reverse(taskEntities);
            listView2.setVisibility(View.VISIBLE);
            data2Adapter = new Data2ActivityAdapter(context, taskEntities, DataManagerActivity.this);
            listView2.setAdapter(data2Adapter);
        } else {
            listView2.setVisibility(View.INVISIBLE);
            Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
        }
//        if (qylEntities.size() != 0) {
//            listview.setVisibility(View.VISIBLE);
//            Collections.reverse(qylEntities);
//            data1Adapter = new Data1ActivityAdapter(context, qylEntities, DataManagerActivity.this);
//            listview.setAdapter(data1Adapter);
//            String pingJun = "";//平均
//            float leiJi = 0;//累积
//            for (int i = 0; i < qylEntities.size(); i++) {
//                leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
//            }
//            pingJun = df2.format(leiJi / qylEntities.size());
//            tvPingJunValue.setText(pingJun);
//            tvLeiJiValue.setText(df2.format(leiJi));
//        } else {
//            listview.setVisibility(View.INVISIBLE);
//            tvPingJunValue.setText("--");
//            tvLeiJiValue.setText("--");
//            Toast.makeText(context, "暂无力数据", Toast.LENGTH_SHORT).show();
//        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.datamenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_xinxi:
                String str = "测试条数：" + taskEntityDao.loadAll().size();
                //弹框显示任务条数
                new AlertDialog.Builder(context).setMessage(str).create().show();
                break;
            case R.id.menu_baogao:
                updateExcel();//生成报告
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //生成报告
    private void updateExcel() {
        try {
            //先创建文件夹
            File fileDir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/矿用锚杆无线测力仪/测试报告/");
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            //保存时间
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String format = sdf.format(new Date());
            //再创建文件
            File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath() +
                    "/矿用锚杆无线测力仪/测试报告/" + "矿用锚杆无线测力仪导出数据" + format + ".xls");
            if (!file.exists()) {
                file.createNewFile();
            }
            InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("assets/maogai.xls");
            //读到模板文件
            Workbook wb = Workbook.getWorkbook(inputStream);
            //我们要操作的中间临时文件
            WritableWorkbook workbook = Workbook.createWorkbook(file, wb);
            WritableSheet sheet0 = workbook.getSheet(0);//力数据界面
            List<TaskEntity> taskList = taskEntities;//导出的数据是listview2所显示的
            if (taskList.size() != 0) {
//                Collections.reverse(taskList);//倒序，最后检的最先显示
                for (int i = 0; i < taskList.size(); i++) {
                    TaskEntity taskEntity = taskList.get(i);
                    Long id = taskEntity.getId();
                    List<QylEntity> qylList = qylEntityDao.queryBuilder().where(QylEntityDao.Properties.Key.eq(id)).list();
                    WritableCell cell_2y = sheet0.getWritableCell(2, i + 3);//受检单位
                    WritableCell cell_3y = sheet0.getWritableCell(3, i + 3);//测试人员
                    WritableCell cell_4y = sheet0.getWritableCell(4, i + 3);//测试时间
                    WritableCell cell_5y = sheet0.getWritableCell(5, i + 3);//平均力
                    WritableCell cell_6y = sheet0.getWritableCell(6, i + 3);//备注
                    if (cell_2y.getType() == CellType.LABEL && cell_3y.getType() == CellType.LABEL &&
                            cell_4y.getType() == CellType.LABEL && cell_5y.getType() == CellType.LABEL &&
                            cell_6y.getType() == CellType.LABEL) {
                        Label label2_y = (Label) cell_2y;
                        Label label3_y = (Label) cell_3y;
                        Label label4_y = (Label) cell_4y;
                        Label label5_y = (Label) cell_5y;
                        Label label6_y = (Label) cell_6y;

                        label2_y.setString(taskEntity.getUnitName());//受检单位
                        label3_y.setString(taskEntity.getPeopleName());//测试人员
                        label4_y.setString(taskEntity.getCreateTaskTime());//测试时间
                        if (qylList.size() != 0) {
                            //计算平均和累积
                            String pingJun = "";//平均
                            float leiJi = 0;//累积
                            for (int j = 0; j < qylList.size(); j++) {
                                leiJi = qylList.get(j).getCurrentLi() + leiJi;
                            }
                            pingJun = df2.format(leiJi / qylList.size());
                            label5_y.setString(pingJun);//平均力
                        } else {
                            label5_y.setString("-");//平均力
                        }
                        label6_y.setString(taskEntity.getBeiZhu());//备注
                    }
                }


            }
            if (inputStream != null) {
                inputStream.close();
            }
            workbook.write();
            workbook.close();
            wb.close();

            try {
                Intent intent = getWordFileIntent(Environment.getExternalStorageDirectory().getAbsolutePath()
                        + "/矿用锚杆无线测力仪/测试报告/" + "矿用锚杆无线测力仪导出数据" + format + ".xls");
                startActivity(intent);
            } catch (Exception e) {
                Toasty.error(context, "打开WPS失败").show();
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 打开WPS
    public static Intent getWordFileIntent(String param) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        Uri uri = Uri.fromFile(new File(param));
        intent.setDataAndType(uri, "application/msword");
        return intent;
    }


    @OnClick({R.id.btn_chaXun, R.id.btn_quanBu})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_chaXun://查询点击事件
                String Et1 = et1.getText().toString().trim();
                String Et2 = et2.getText().toString().trim();
                String Et3 = et3.getText().toString().trim();
                String Et4 = et4.getText().toString().trim();
                if (Et1.equals("") && !Et2.equals("") && !Et4.equals("")) {//et1为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.PeopleName.like("%" + Et2 + "%"),
                            TaskEntityDao.Properties.BeiZhu.like("%" + Et4 + "%")).list();
                } else if (!Et1.equals("") && Et2.equals("") && !Et4.equals("")) {//et2为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like("%" + Et1 + "%"),
                            TaskEntityDao.Properties.BeiZhu.like("%" + Et4 + "%")).list();
                } else if (!Et1.equals("") && !Et2.equals("") && Et4.equals("")) {//et4为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like("%" + Et1 + "%"),
                            TaskEntityDao.Properties.PeopleName.like("%" + Et2 + "%")).list();
                } else if (Et1.equals("") && Et2.equals("") && !Et4.equals("")) {//et1、et2为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.BeiZhu.like("%" + Et4 + "%")).list();
                } else if (Et1.equals("") && !Et2.equals("") && Et4.equals("")) {//et1、et4为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.PeopleName.like("%" + Et2 + "%")).list();
                } else if (!Et1.equals("") && Et2.equals("") && Et4.equals("")) {//et2、et4为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like("%" + Et1 + "%")).list();
                } else if (Et1.equals("") && Et2.equals("") && Et4.equals("")) {//et1、et2、et4全为空
                    taskEntities = taskEntityDao.loadAll();
                } else if (!Et1.equals("") && !Et2.equals("") && !Et4.equals("")) { //et1、et2、et4全不为空
                    taskEntities = taskEntityDao.queryBuilder().where(TaskEntityDao.Properties.UnitName.like("%" + Et1 + "%"),
                            TaskEntityDao.Properties.PeopleName.like("%" + Et2 + "%"), TaskEntityDao.Properties.BeiZhu.like("%" + Et4 + "%")).list();
                }
                if (taskEntities.size() != 0) {
                    Collections.reverse(taskEntities);
                    data2Adapter = new Data2ActivityAdapter(context, taskEntities, DataManagerActivity.this);
                    listView2.setAdapter(data2Adapter);
                    //查询之后，listview隐藏
                    listview.setVisibility(View.INVISIBLE);
                    tvPingJunValue.setText("--");
                    tvLeiJiValue.setText("--");
                } else {
                    Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
                }
                // 隐藏软键盘
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                break;
            case R.id.btn_quanBu://全部点击事件
                taskEntities = taskEntityDao.loadAll();
                //edittext全部置为""
                et1.setText("");
                et2.setText("");
                et3.setText("");
                et4.setText("");
                if (taskEntities.size() != 0) {
                    Collections.reverse(taskEntities);
                    data2Adapter = new Data2ActivityAdapter(context, taskEntities, DataManagerActivity.this);
                    listView2.setAdapter(data2Adapter);
                    //查询之后，listview隐藏
                    listview.setVisibility(View.INVISIBLE);
                    tvPingJunValue.setText("--");
                    tvLeiJiValue.setText("--");
                } else {
                    Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
                }
                // 隐藏软键盘
                InputMethodManager imm1 = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm1.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
                break;
        }
    }

    //data1的删除按钮的点击事件
    public void showInitRes(QylEntity qylEntity, List<QylEntity> list) {
        qylEntities = list;
        qylEntityDao.delete(qylEntity);
        if (qylEntities.size() != 0) {
            //计算平均和累积
            String pingJun = "";//平均
            float leiJi = 0;//累积
            for (int i = 0; i < qylEntities.size(); i++) {
                leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
            }
            pingJun = df2.format(leiJi / qylEntities.size());
            tvPingJunValue.setText(pingJun);
            tvLeiJiValue.setText(df2.format(leiJi));
        } else {
            tvPingJunValue.setText("--");
            tvLeiJiValue.setText("--");
        }
    }

    //data2的item的点击事件(点击下面的listview的每个item时，上面的listview更新数据)
    public void onItemClick(TaskEntity taskEntity) {
        qylEntities.clear();
        qylEntities = qylEntityDao.queryBuilder().where(QylEntityDao.Properties.Key.eq(taskEntity.getId())).list();
        if (qylEntities.size() != 0) {
            listview.setVisibility(View.VISIBLE);
            Collections.reverse(qylEntities);
            data1Adapter = new Data1ActivityAdapter(context, qylEntities, DataManagerActivity.this);
            listview.setAdapter(data1Adapter);
            //计算平均和累积
            String pingJun = "";//平均
            float leiJi = 0;//累积
            for (int i = 0; i < qylEntities.size(); i++) {
                leiJi = qylEntities.get(i).getCurrentLi() + leiJi;
            }
            pingJun = df2.format(leiJi / qylEntities.size());
            tvPingJunValue.setText(pingJun);
            tvLeiJiValue.setText(df2.format(leiJi));
        } else {
            listview.setVisibility(View.INVISIBLE);
            tvPingJunValue.setText("--");
            tvLeiJiValue.setText("--");
            Toast.makeText(context, "本条没有数据！", Toast.LENGTH_SHORT).show();
        }

    }

    //长按点击事件
    public void onItemLongClick(final TaskEntity taskEntity) {
        AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle("删除");
        normalDialog.setMessage("确认删除本条数据?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //删除任务时，对应的力数据也要删除
                        List<QylEntity> qylList = qylEntityDao.queryBuilder().
                                where(QylEntityDao.Properties.Key.eq(taskEntity.getId())).list();
                        if (qylList.size() != 0) {
                            for (int i = 0; i < qylList.size(); i++) {
                                qylEntityDao.delete(qylList.get(i));
                            }
                        }
//                        if (qylEntities.size() != 0) {
//                        如果此时删除的就是上面的listview所展示的，那么把listview隐藏
//                            if (qylEntities.get(0).getKey() == taskEntity.getId()) {
                        listview.setVisibility(View.INVISIBLE);
                        tvPingJunValue.setText("--");
                        tvLeiJiValue.setText("--");
//                            }
//                        }

                        taskEntityDao.delete(taskEntity);
                        taskEntities = taskEntityDao.loadAll();
                        if (taskEntities.size() != 0) {
                            listView2.setVisibility(View.VISIBLE);
                            Collections.reverse(taskEntities);
                            data2Adapter = new Data2ActivityAdapter(context, taskEntities, DataManagerActivity.this);
                            listView2.setAdapter(data2Adapter);
                            Toast.makeText(context, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            //删完了把listview2隐藏
                            listView2.setVisibility(View.INVISIBLE);
                            Toast.makeText(context, "暂无任务数据", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        // 创建实例并显示
        normalDialog.show();
    }
}

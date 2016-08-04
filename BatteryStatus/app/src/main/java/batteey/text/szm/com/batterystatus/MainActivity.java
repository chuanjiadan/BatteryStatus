package batteey.text.szm.com.batterystatus;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // 定义电池信息的按钮
    private Button btnBattery;
    // 定义显示电池信息的textview
    private TextView tvBattery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 得到布局中的所有对象
        findView();
        // 设置对象的监听器
        setListener();


    }

    private void setListener() {

        btnBattery.setOnClickListener(listener);
    }

    private void findView() {

        btnBattery = (Button) findViewById(R.id.btn_battery);
        tvBattery = (TextView) findViewById(R.id.tv_battery);
    }


    View.OnClickListener listener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch (v.getId()) {

                // 当前的音量
                case R.id.btn_battery:

                    IntentFilter filter = new IntentFilter();
                    filter.addAction(Intent.ACTION_BATTERY_CHANGED);
                    registerReceiver(mBroadcastReceiver, filter);
                    break;
            }
        }
    };
    // 声明广播接受者对象
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            // TODO Auto-generated method stub
            String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                // 得到电池状态：
//                 BatteryManager.BATTERY_STATUS_CHARGING：充电状态。
                // BatteryManager.BATTERY_STATUS_DISCHARGING：放电状态。
                // BatteryManager.BATTERY_STATUS_NOT_CHARGING：未充满。
                // BatteryManager.BATTERY_STATUS_FULL：充满电。
                // BatteryManager.BATTERY_STATUS_UNKNOWN：未知状态。
                int status = intent.getIntExtra("status", 0);
                Log.d("szm", "电池状态" + status);
                // 得到健康状态：
                // BatteryManager.BATTERY_HEALTH_GOOD：状态良好。
                // BatteryManager.BATTERY_HEALTH_DEAD：电池没有电。
                // BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE：电池电压过高。
                // BatteryManager.BATTERY_HEALTH_OVERHEAT：电池过热。
                // BatteryManager.BATTERY_HEALTH_UNKNOWN：未知状态。
                int health = intent.getIntExtra("health", 0);
                Log.d("szm", "健康状态" + health);
                // boolean类型
                boolean present = intent.getBooleanExtra("present", false);
                // 得到电池剩余容量
                int level = intent.getIntExtra("level", 0);
                Log.d("szm", "剩余容量" + level);
                // 得到电池最大值。通常为100。
                int scale = intent.getIntExtra("scale", 0);
                // 得到图标ID
                int icon_small = intent.getIntExtra("icon-small", 0);
                // 充电方式：　BatteryManager.BATTERY_PLUGGED_AC：AC充电。　BatteryManager.BATTERY_PLUGGED_USB：USB充电。
                int plugged = intent.getIntExtra("plugged", 0);
                // 得到电池的电压
                int voltage = intent.getIntExtra("voltage", 0);
                // 得到电池的温度,0.1度单位。例如 表示197的时候，意思为19.7度
                int temperature = intent.getIntExtra("temperature", 0);
                // 得到电池的类型
                String technology = intent.getStringExtra("technology");
                // 得到电池状态
                String statusString = "";
                // 根据状态id，得到状态字符串
                switch (status) {
                    case BatteryManager.BATTERY_STATUS_UNKNOWN:
                        statusString = "unknown";
                        break;
                    case BatteryManager.BATTERY_STATUS_CHARGING:
                        statusString = "charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_DISCHARGING:
                        statusString = "discharging";
                        break;
                    case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                        statusString = "not charging";
                        break;
                    case BatteryManager.BATTERY_STATUS_FULL:
                        statusString = "full";
                        break;
                }
                //得到电池的寿命状态
                String healthString = "";
                //根据状态id，得到电池寿命
                switch (health) {
                    case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                        healthString = "unknown";
                        break;
                    case BatteryManager.BATTERY_HEALTH_GOOD:
                        healthString = "good";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                        healthString = "overheat";
                        break;
                    case BatteryManager.BATTERY_HEALTH_DEAD:
                        healthString = "dead";
                        break;
                    case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                        healthString = "voltage";
                        break;
                    case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                        healthString = "unspecified failure";
                        break;
                }
                //得到充电模式
                String acString = "";
                //根据充电状态id，得到充电模式
                switch (plugged) {
                    case BatteryManager.BATTERY_PLUGGED_AC:
                        acString = "plugged ac";
                        break;
                    case BatteryManager.BATTERY_PLUGGED_USB:
                        acString = "plugged usb";
                        break;
                }

                //显示电池信息
                tvBattery.setText("电池的状态：" + statusString
                        + "\n健康值: " + healthString
                        + "\n电池剩余容量： " + level
                        + "\n电池的最大值：" + scale
                        + "\n小图标：" + icon_small
                        + "\n充电方式：" + plugged
                        + "\n充电方式: " + acString
                        + "\n电池的电压：" + voltage
                        + "\n电池的温度：" + (float) temperature * 0.1
                        + "\n电池的类型：" + technology);
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        // 解除注册监听
        unregisterReceiver(mBroadcastReceiver);
    }
}

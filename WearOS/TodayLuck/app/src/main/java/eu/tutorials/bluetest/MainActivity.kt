package eu.tutorials.bluetest

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.UUID
import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    companion object {
        const val MY_PERMISSIONS_REQUEST: Int = 1
    }

    var myBluetoothService: MyBluetoothService? = null  // 이 부분을 추가

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btn = findViewById<Button>(R.id.sendButton)

        // 퍼미션 체크
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADMIN)
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT)
            != PackageManager.PERMISSION_GRANTED) {

            // 퍼미션 요청

        }

        ActivityCompat.requestPermissions(this, arrayOf(
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.BLUETOOTH_CONNECT), MY_PERMISSIONS_REQUEST)
        // Coroutine 시작
        lifecycleScope.launch(Dispatchers.IO) {
            val handler = Handler(Looper.getMainLooper())
            myBluetoothService = MyBluetoothService(handler)  // 이 부분을 수정

            // Bluetooth 장치와 소켓을 얻는 코드 (이 부분은 실제 장치와 연결되어 있어야 합니다)
            val bluetoothAdapter: BluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
            Log.d("asd", bluetoothAdapter.name)
            val deviceAddress: String = "58:A6:39:89:42:0A"  // 실제 장치의 MAC 주소
            val device=  bluetoothAdapter.getRemoteDevice(deviceAddress)
            delay(3000)
            if(device.name == null) {
                Log.d("LOGD", "기기 NULL")
            }
            Log.d("asdasd", device.fetchUuidsWithSdp().toString());

            Log.d("asd",device.name)
            val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")  // 일반적인 SPP UUID
            var bluetoothSocket: BluetoothSocket? = null
            try {
                bluetoothSocket = device.createRfcommSocketToServiceRecord(uuid)
                bluetoothSocket.connect()
            } catch (e: IOException) {
                e.printStackTrace()
                // 연결 실패 처리
            }

            // BluetoothSocket 초기화와 연결

            if (bluetoothSocket != null && bluetoothSocket.isConnected) {
                myBluetoothService?.connect(bluetoothSocket)
            }
        }
        btn.setOnClickListener {
            Log.d("zxc", myBluetoothService.toString())
            myBluetoothService?.sendNumberToWearOS()  // 이 부분을 추가
        }
    }
}

/* While this template provides a good starting point for using Wear Compose, you can always
 * take a look at https://github.com/android/wear-os-samples/tree/main/ComposeStarter and
 * https://github.com/android/wear-os-samples/tree/main/ComposeAdvanced to find the most up to date
 * changes to the libraries and their usages.
 */

package com.example.todayluck.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothGatt
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattServer
import android.bluetooth.BluetoothGattServerCallback
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothManager
import android.bluetooth.BluetoothProfile
import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.ParcelUuid
import android.util.Log
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.wear.compose.material.Text
import com.bumptech.glide.Glide
import com.example.todayluck.R
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {

    private lateinit var bluetoothManager: BluetoothManager
    private lateinit var bluetoothGattService: BluetoothGattService
    private lateinit var bluetoothGattServer: BluetoothGattServer
    private lateinit var characteristic: BluetoothGattCharacteristic
    private var findDevice : BluetoothDevice? = null
    var heartRateValue = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            if (checkSelfPermission(Manifest.permission.BODY_SENSORS) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf<String>(Manifest.permission.BODY_SENSORS), 306);
                // 허용 하고 개인적으론 재시작 시켜버리고 싶음
            }
            if (checkSelfPermission(Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf<String>(Manifest.permission.BLUETOOTH_CONNECT), 306);
                // 허용 하고 개인적으론 재시작 시켜버리고 싶음
            }
            // BluetoothManager 초기화
            bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager

            // BLE 서비스 및 특성 설정(말했던 bluetoothgatt 초기 설정)
            setupGattService()
            if (checkSelfPermission(Manifest.permission.BODY_SENSORS) == PackageManager.PERMISSION_GRANTED) {
                checkBPM(context = this);
            }
            HeartRateDisplay(heartRateValue, this)
        }
    }



    @SuppressLint("MissingPermission")
    private fun setupGattService() {
        // BLE 서비스 생성
        val serviceUuid = ParcelUuid.fromString("d950a7fd-6f09-4ac5-dec6-677de893cce2") // 우리 서비스 UUID
        bluetoothGattService = BluetoothGattService(serviceUuid.uuid, BluetoothGattService.SERVICE_TYPE_PRIMARY)

        // 특성 생성(심박수)
        val characteristicUuid = ParcelUuid.fromString("6c4bf4ac-8a65-42bf-abfd-dd43f2dd3a22") //우리 서비스 UUID
        characteristic = BluetoothGattCharacteristic(
            characteristicUuid.uuid,
            BluetoothGattCharacteristic.PROPERTY_READ or BluetoothGattCharacteristic.PROPERTY_NOTIFY,
                    BluetoothGattCharacteristic.PERMISSION_READ or BluetoothGattCharacteristic.PROPERTY_NOTIFY
        )

        // 특성에 초기 데이터 설정 (예: 심박수 데이터)
        characteristic.value = byteArrayOf(heartRateValue.toByte())

        val descriptorUuid =  ParcelUuid.fromString("d950a7fd-6f09-4ac5-dec6-677de893cce4") // Client Characteristic Configuration Descriptor UUID
        val descriptor = BluetoothGattDescriptor(descriptorUuid.uuid, BluetoothGattDescriptor.PERMISSION_WRITE)
        descriptor.value = BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE // 알림 활성화

        // 특성에 디스크립터 추가
        characteristic.addDescriptor(descriptor)

        // 서비스에 특성 추가
        bluetoothGattService.addCharacteristic(characteristic)

        // gatt Server에 서비스 추가
        bluetoothGattServer = bluetoothManager.openGattServer(this, gattServerCallback)
        bluetoothGattServer.addService(bluetoothGattService)

    }

    // server에 변동사항이 생기면 다 해당 함수에서 처리가 됩니다.
    private val gattServerCallback = object : BluetoothGattServerCallback() {

        @SuppressLint("MissingPermission")
        override fun onConnectionStateChange(device: BluetoothDevice?, status: Int, newState: Int) {
            super.onConnectionStateChange(device, status, newState)
            when (newState) {
                BluetoothProfile.STATE_CONNECTED -> {
                    if (device != null) {
                        findDevice = device
                        Log.d("새로운 기기가 연결되었습니다",device.name.toString())
                    }
                }
                BluetoothProfile.STATE_DISCONNECTED -> {
                    Log.d("기기 연결이 해지되었습니다.","state_disconnected")
                }
            }
        }

        @SuppressLint("MissingPermission")
        override fun onCharacteristicReadRequest(
            device: BluetoothDevice?,
            requestId: Int,
            offset: Int,
            characteristic: BluetoothGattCharacteristic?
        ) {
            super.onCharacteristicReadRequest(device, requestId, offset, characteristic)

                    bluetoothGattServer?.sendResponse(device, requestId, BluetoothGatt.GATT_SUCCESS, 1, byteArrayOf(
                heartRateValue.toByte()
            ) )

        }

    }

    private fun checkBPM(context: Context) {

        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        // 심박수 센서 가져오기
        val heartRateSensor = sensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE)

        // SensorEventListener 설정
        val heartRateListener: SensorEventListener = object : SensorEventListener {
            override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
                // 센서 정확도 변경 시 처리
            }

            @SuppressLint("MissingPermission")
            override fun onSensorChanged(event: SensorEvent) {
                if (event.sensor.type == Sensor.TYPE_HEART_RATE) {
                    val sensorHeartRate = event.values[0]
                    this@MainActivity.heartRateValue = sensorHeartRate.toInt()
                    setContent {
                        HeartRateDisplay(heartRate = heartRateValue, this@MainActivity)
                    }
                    val byteArray = heartRateValue.toString().toByteArray();
                    characteristic.value = byteArray
                    if(findDevice!=null) { bluetoothGattServer.notifyCharacteristicChanged(findDevice, characteristic, false)
                        Log.d("심박수 변경 데이터 전송",heartRateValue.toString())
                    }
                    }
            }
        }

        // SensorEventListener를 등록
        sensorManager.registerListener(
            heartRateListener,
            heartRateSensor,
            SensorManager.SENSOR_DELAY_NORMAL
        )
    }
}


// 여기서 부터는 화면에 심박수 띄우는 코드. 아예 노상관 안보셔도 댑미당
@Composable
fun GifImage(
    context: Context,
    resId: Int,
    modifier: Modifier = Modifier,
) {
    val imageView = remember { ImageView(context) }
    AndroidView({ imageView }, modifier) { view ->
        Glide.with(context).load(resId).into(view)
    }
}

@Composable
fun HeartRateDisplay(heartRate: Int, context : Context) {
    var stopwatchTime by remember { mutableStateOf(0L) }
    var stopwatchStart by remember { mutableStateOf(0L) }
    var isStopwatchRunning by remember { mutableStateOf(false) }

    LaunchedEffect(isStopwatchRunning) {
        while (isStopwatchRunning) {
            delay(1000)
            stopwatchTime = System.currentTimeMillis() - stopwatchStart
        }
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "❤️ $heartRate",
            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
        )
0
        GifImage(context, R.drawable.muscle_monster,  Modifier.size(150.dp).clickable{
            isStopwatchRunning=!isStopwatchRunning
            if(isStopwatchRunning){
                if(stopwatchTime==0L){
                    stopwatchStart=System.currentTimeMillis()
                }else{
                    stopwatchStart=System.currentTimeMillis()-stopwatchTime
                }
            }
        }) // GifImage 클릭 시 스탑워치 시작/정지 토글.



        Text(
            text =
            "\uD83D\uDD52 ${String.format("%01d",stopwatchTime / 3600000 % 24)}:" +
                    "${String.format("%02d",stopwatchTime / 60000 % 60)}:" +
                    "${String.format("%02d",stopwatchTime / 1000 % 60)}",

            color = Color.White,
            textAlign = TextAlign.Center,
            fontSize = 16.sp
)}}
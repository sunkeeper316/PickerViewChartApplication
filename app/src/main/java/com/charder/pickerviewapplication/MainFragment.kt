package com.charder.pickerviewapplication

import android.app.Activity
import android.os.Bundle
import android.os.ProxyFileDescriptorCallback
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bigkoo.pickerview.builder.TimePickerBuilder
import java.text.SimpleDateFormat
import java.util.*

class MainFragment : Fragment() {

    lateinit var tv_date : TextView
    lateinit var tv_time : TextView

    var targetDate:Date = Date()
    var targetTime:Date = Date()

    override fun onCreateView(inflater: LayoutInflater,container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_date = view.findViewById(R.id.tv_date)
        tv_time = view.findViewById(R.id.tv_time)
        tv_date.setOnClickListener {
            var c = Calendar.getInstance()
            c.time = targetDate
            datePicker(requireActivity() ,c,callback = {
                targetDate = it
                tv_date.text = getDate(targetDate)
            } )
        }
        tv_time.setOnClickListener {
            var c = Calendar.getInstance()
            c.time = targetTime
            timePicker(requireActivity() ,c,callback = {
                targetTime = it
                tv_time.text = getTime(targetTime)
            } )
        }
    }

    fun timePicker(activity: Activity , selectedDate:Calendar , callback: (Date) -> Unit){
        selectedDate.set(0,0,0,selectedDate.get(Calendar.HOUR),0,0)
        val pvTime = TimePickerBuilder(requireContext()) { date: Date?, v: View? ->
            date?.let {
                callback(it)
            }
        }
            .setType(booleanArrayOf(false, false, false, true, true, false)) //年月日时分秒 的显示与否，不设置则默认全部显示
            .setLabel("", "", "", "時", "分", "") //默认设置为年月日时分秒
            .isCenterLabel(true)
            .setDate(selectedDate)
            .setRangDate(selectedDate, selectedDate)
            .build()
        pvTime.show()
    }
    fun datePicker(activity: Activity , selectedDate:Calendar , callback: (Date) -> Unit){
        //仿ios日期滾輪
//        val selectedDate = Calendar.getInstance() //系统当前时间
        val startDate = Calendar.getInstance()
        startDate.set(selectedDate.get(Calendar.YEAR) - 100,selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.DAY_OF_MONTH))
        val endDate = Calendar.getInstance()
        endDate.set(selectedDate.get(Calendar.YEAR) + 100,selectedDate.get(Calendar.MONTH),selectedDate.get(Calendar.DAY_OF_MONTH))
        val pvTime = TimePickerBuilder(requireContext()) { date: Date?, v: View? ->
            date?.let {
                callback(it)
            }
        }
            .setType(booleanArrayOf(true, true, true, false, false, false)) //年月日时分秒 的显示与否，不设置则默认全部显示
            .setLabel("年", "月", "日", "", "", "") //默认设置为年月日时分秒
            .isCenterLabel(true)
            .setDate(selectedDate)
            .setRangDate(startDate, endDate)
            .build()
        pvTime.show()
    }
    private fun getDate(date: Date): String? {
        val format = SimpleDateFormat("yyyy/MM/dd")
        return format.format(date)
    }
    private fun getTime(date: Date): String? {
        val format = SimpleDateFormat("HH:mm:ss")
        return format.format(date)
    }
}
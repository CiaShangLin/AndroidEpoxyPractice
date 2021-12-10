package com.shang.epoxypractice

import android.graphics.Canvas
import android.graphics.Rect
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View

import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyItemSpacingDecorator
import com.airbnb.epoxy.EpoxyRecyclerView
import com.scwang.smart.refresh.layout.SmartRefreshLayout

//文檔:https://github.com/airbnb/epoxy/wiki
class MainActivity : AppCompatActivity() {

    lateinit var toolbar: Toolbar
    lateinit var epoxyRecyclerView: EpoxyRecyclerView
    lateinit var controller: PhotoController
    lateinit var smartLayout: SmartRefreshLayout

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initToolbar()
        initEpoxyRecyclerView()
        initSmartLayout()
    }

    private fun initToolbar() {
        toolbar = findViewById(R.id.toolbar)
        toolbar.setOnMenuItemClickListener(@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        object : Toolbar.OnMenuItemClickListener {
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when (item?.itemId) {
                    R.id.showData -> {
                        controller.setLoading(false)
                        controller.setPhoto(listOf(1, 2, 3, 4, 5, 6, 7))
                    }
                    R.id.switchLayout -> {

                    }
                }
                return true
            }
        })
    }

    //都用了Epoxy當然就順便用他們的EpoxyRecyclerView
    private fun initEpoxyRecyclerView() {
        controller = PhotoController()
        epoxyRecyclerView = findViewById<EpoxyRecyclerView>(R.id.rv)
        //EpoxyRecyclerView預設的layoutManager就是Linear垂直的了
        epoxyRecyclerView.layoutManager = GridLayoutManager(this, 2)
        //設置Controller順便buildModel
        epoxyRecyclerView.setControllerAndBuildModels(controller)

        //設置ItemDecoration
        epoxyRecyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                super.getItemOffsets(outRect, view, parent, state)
                val position = parent.getChildLayoutPosition(view)
                val model = controller.adapter.getModelAtPosition(position)

                if (model is PhotoEpoxyModel_) {
                    if (model.position % 2 == 0) {
                        outRect.left = 16
                        outRect.right = 8
                    } else {
                        outRect.left = 8
                        outRect.right = 16
                    }
                    outRect.bottom = 12
                }

            }
        })
    }

    //測試搭配SmartLayout載入更多方法
    private fun initSmartLayout() {
        smartLayout = findViewById(R.id.smartLayout)
        smartLayout.setHeaderHeight(0f)
        smartLayout.setEnableRefresh(false)
//        smartLayout.setEnableLoadMore(false)
        smartLayout.setOnLoadMoreListener {
            Handler().postDelayed({
                controller.setPhoto(listOf(1, 1))
                smartLayout.finishLoadMore()
            }, 1000)
        }
    }
}
package com.shang.epoxypractice

import android.util.Log
import com.airbnb.epoxy.AutoModel
import com.airbnb.epoxy.Carousel
import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController

class PhotoController : EpoxyController() {
    @AutoModel
    lateinit var header: HeaderEpoxyModel_

    @AutoModel
    lateinit var loading: LoadingEpoxyModel_


    private var mIsLoading = true
    private var photoList = mutableListOf<Int>()

    fun setLoading(isLoading: Boolean) {
        mIsLoading = isLoading
        requestModelBuild()
    }

    fun setPhoto(list: List<Int>) {
        photoList.addAll(list)
        requestModelBuild()
    }


    override fun buildModels() {
        if (mIsLoading) {
            buildLoading()
        } else {
            buildHeader()
            buildCarousel()
            buildPhoto()
        }
    }

    private fun buildLoading() {
        loading.spanSizeOverride { totalSpanCount, position, itemCount ->
            return@spanSizeOverride 2
        }
        loading.addIf(mIsLoading, this)
    }

    private fun buildHeader() {
        //在EpoxyModel宣告的@EpoxyAttribute會轉換成Attribute()直接就可以設置了,應該是Builder Design pattern
        header
            .text("I am Header")
            .addTo(this)
        header.spanSizeOverride { totalSpanCount, position, itemCount ->
            return@spanSizeOverride 2
        }

    }

    private fun buildCarousel(){
        val models = mutableListOf<PhotoEpoxyModel_>()
        photoList.forEachIndexed { index, i ->
            val model = PhotoEpoxyModel_()
                .id(i)
            models.add(model)
        }
        //可以處理水平嵌套,他預設是有padding的
        CarouselModel_()
            .id("1")
            .models(models)
            .padding(Carousel.Padding(8,12,8,12,16))
            .addTo(this)
    }

    private fun buildPhoto(){
        photoList.forEachIndexed { index, i ->
            //com.airbnb.epoxy.IllegalEpoxyUsage: You must set an id on a model before adding it.
            //Use the @AutoModel annotation if you want an id to be automatically generated for you.
            //要幫他主動設置ID不然會丟出上面的例外,@AutoModel應該是幫我們做了設置ID的動作,因為他只會有一個
            //id後面可以設置其他的key,道理應該就跟DiffUtil一樣,當他不同時就會去刷新
            PhotoEpoxyModel_()
                .position(index)
                .id(index)
                .onBind { model, view, position ->
                    //這裡或許可以做loadMore callback到外面
                    Log.d("DEBUG","$position")
                }
                .addTo(this)
        }
    }
}
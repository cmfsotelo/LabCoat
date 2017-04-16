package com.commit451.gitlab.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.Toolbar
import android.widget.ImageView
import butterknife.BindView
import butterknife.ButterKnife
import com.commit451.gitlab.App
import com.commit451.gitlab.R
import com.commit451.gitlab.model.api.Project
import com.github.chrisbanes.photoview.PhotoViewAttacher
import com.squareup.picasso.Callback
import org.parceler.Parcels

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenImageActivity : BaseActivity() {

    @BindView(R.id.toolbar) lateinit var toolbar: Toolbar

    lateinit var contentView: ImageView
    lateinit var photoViewAttacher: PhotoViewAttacher
    lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_image)
        ButterKnife.bind(this)
        setToolbar()

        project = Parcels.unwrap<Project>(intent.getParcelableExtra<Parcelable>(FullscreenImageActivity.EXTRA_PROJECT))

        photoViewAttacher = PhotoViewAttacher(contentView)
        photoViewAttacher.scaleType = ImageView.ScaleType.FIT_CENTER

        var imageUrl: String = intent.getStringExtra(IMAGE_URL)
        if (imageUrl.startsWith("/")) {
            imageUrl = App.get().getAccount().serverUrl.toString() + project?.pathWithNamespace + imageUrl
        }
        App.get().picasso.load(imageUrl).into(contentView, object:Callback {
            override fun onSuccess() {
                photoViewAttacher.update()
            }
            override fun onError() {}
        })
    }


    private fun setToolbar() {
        toolbar.setNavigationIcon(R.drawable.ic_back_24dp)
        toolbar.title = ""
        setSupportActionBar(toolbar)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {

        val IMAGE_URL = "url"

        private val EXTRA_PROJECT = "extra_project"

        fun newIntent(context: Context, project: Project): Intent {
            val intent = Intent(context, FullscreenImageActivity::class.java)
            intent.putExtra(EXTRA_PROJECT, Parcels.wrap(project))
            return intent
        }
    }
}

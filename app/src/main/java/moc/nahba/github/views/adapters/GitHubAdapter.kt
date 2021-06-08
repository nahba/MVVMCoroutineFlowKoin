package moc.nahba.github.views.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import moc.nahba.github.R
import moc.nahba.github.models.dto.GitHubRepo

class GitHubAdapter : RecyclerView.Adapter<GitHubAdapter.ViewHolder>() {
    private val repos = mutableListOf<GitHubRepo>()
    internal fun setRepo(passed: List<GitHubRepo>) {
        repos.clear()
        repos.addAll(passed)
        notifyDataSetChanged()
    }
    
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textName: TextView = view.findViewById(R.id.textName)
        val imageView: ImageView = view.findViewById(R.id.imageView)
    }
    
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_github, viewGroup, false)
        return ViewHolder(view)
    }
    
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.textName.text = getItem(position).name
        val avatar = getItem(position).owner.avatarUrl
        if (!avatar.isNullOrBlank()) {
            viewHolder.imageView.let { view ->
                val placeHolder = ContextCompat.getDrawable(view.context, R.drawable.ic_user)
                Glide.with(view.context)
                    .load(avatar)
                    .optionalFitCenter()
                    .apply(RequestOptions.circleCropTransform())
                    .dontAnimate()
                    .placeholder(placeHolder)
                    .into(view)
            }
        }
    }
    
    override fun getItemCount() = repos.size
    private fun getItem(position: Int) = repos[position]
}
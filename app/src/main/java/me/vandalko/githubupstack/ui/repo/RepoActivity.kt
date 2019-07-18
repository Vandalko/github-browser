package me.vandalko.githubupstack.ui.repo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_repo.*
import me.vandalko.githubupstack.R
import me.vandalko.githubupstack.data.model.Repo

class RepoActivity : AppCompatActivity() {
    
    private lateinit var repoViewModel: RepoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_repo)

        list.layoutManager = LinearLayoutManager(this)
        list.adapter = Adapter()

        repoViewModel = ViewModelProviders.of(this, RepoViewModelFactory())
            .get(RepoViewModel::class.java)
        
        repoViewModel.repositories.observe(this,
            Observer<List<Repo>> { t -> (list.adapter as Adapter).repositories = t.orEmpty() })
    }

    override fun onStart() {
        super.onStart()
        repoViewModel.refresh(intent.getStringExtra("user"), intent.getStringExtra("token"))
    }

    class Adapter : RecyclerView.Adapter<Holder>() {

        var repositories = emptyList<Repo>()
            set(value) {
                field = value
                notifyDataSetChanged()
            }
        
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
            val view = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return Holder(view)
        }

        override fun getItemCount() = repositories.size

        override fun onBindViewHolder(holder: Holder, position: Int) {
            val repo = repositories[position]
            holder.text1.text = repo.name
            holder.text2.text = repo.url
        }
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1 = itemView.findViewById<TextView>(android.R.id.text1)
        val text2 = itemView.findViewById<TextView>(android.R.id.text2)
    }
}

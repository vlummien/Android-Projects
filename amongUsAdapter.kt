import amongsounds.R
import android.content.Context
import android.graphics.Color
import android.media.MediaPlayer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class TaskAdapter(val context: Context, var tasks: List<Task>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() { // <TaskAdapter.MyViewHolder>

    var mediaPlayer: MediaPlayer? = null
    var soundIsPlaying: Boolean = false
    var activeView: View? = null



    class TaskViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val mainLayout: ConstraintLayout = itemView.findViewById(R.id.mainLayout_Filter)
        val taskCardView: CardView = itemView.findViewById(R.id.filterCard)
        val name: TextView = itemView.findViewById(R.id.task_name)
        val location: TextView = itemView.findViewById(R.id.location)
        val image: ImageView = itemView.findViewById(R.id.task_image)
        val tasklayout: ConstraintLayout = itemView.findViewById(R.id.taskLayout)
        val favoriseButton: Button = itemView.findViewById(R.id.favourise_button)
    }

    class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val skeldButton: Button = itemView.findViewById(R.id.skeld_mapButton) // Map 1
        val miraButton: Button = itemView.findViewById(R.id.mira_mapButton) // Map 2
        val polusButton: Button = itemView.findViewById(R.id.polus_mapButton) // Map 3
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(context)
        var view: View = when(viewType) {
            0 -> inflater.inflate(R.layout.filter_row, parent, false)
            else -> inflater.inflate(R.layout.task_row, parent, false)
        }

        return when (viewType) {
            0 -> FilterViewHolder(view)
            else -> TaskViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            0 -> bindFilter(holder)
            else -> bindTasks(holder, position)
        }
    }

    private fun bindTasks(holder: RecyclerView.ViewHolder, position: Int) {
        val viewHolder = holder as TaskViewHolder
        val task = tasks[position - 1]
        val design = when (Task.activeMap) {
            1 -> R.drawable.cyan_silver
            2 -> R.drawable.red_silver
            else -> R.drawable.blue_silver
        }
        holder.apply {

            name.setText(task.name)
            location.setText(task.location)
            image.setImageResource(task.image)
            tasklayout.setBackgroundResource(design)

            when (task in Task.favTasks) {
                true -> favoriseButton.setBackgroundResource(R.drawable.heart_full)
                false -> favoriseButton.setBackgroundResource(R.drawable.heart_empty)
            }

            favoriseButton.setOnClickListener {
                Task.manageTaskInFavourites(task)
                when (task in Task.favTasks) {
                    true -> favoriseButton.setBackgroundResource(R.drawable.heart_full)
                    false -> favoriseButton.setBackgroundResource(R.drawable.heart_empty)
                }

            }

            mainLayout.setOnClickListener {

                if (soundIsPlaying) {
                    stopPlayer()
                }
                if (holder.taskCardView != activeView) { // Another Sound plays
                    play(task.sound, holder.taskCardView)
                } else { // Sound stopped
                    activeView = null
                    Toast.makeText(context, "Task canceled", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun bindFilter(holder: RecyclerView.ViewHolder) {
        val viewHolderFilter = holder as FilterViewHolder

        // Active Map-Button
        holder.skeldButton.setTextColor(Color.BLACK)
        holder.miraButton.setTextColor(Color.BLACK)
        holder.polusButton.setTextColor(Color.BLACK)
        when (Task.activeMap) {
            1 -> holder.skeldButton.setTextColor(Color.WHITE)
            2 -> holder.miraButton.setTextColor(Color.WHITE)
            else -> holder.polusButton.setTextColor(Color.WHITE)
        }

        holder.skeldButton.setOnClickListener {
            tasks = Task.skeldTasks
            Task.activeMap = 1
            this.notifyDataSetChanged()
        }
        holder.miraButton.setOnClickListener {
            tasks = Task.miraTasks
            Task.activeMap = 2
            this.notifyDataSetChanged()
        }
        holder.polusButton.setOnClickListener {
            tasks = Task.polusTasks
            Task.activeMap = 3
            this.notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return tasks.size + 1 // + Filter Row
    }

    fun play(sound: Int, view: View){
        mediaPlayer = MediaPlayer.create(context, sound)
        mediaPlayer?.setOnCompletionListener {
            Toast.makeText(context, "Task completed", Toast.LENGTH_SHORT).show()
            view.clearAnimation()
            stopPlayer()
            activeView = null
        }
        mediaPlayer?.start()
        activeView = view
        soundIsPlaying = true

        val animation: Animation = AnimationUtils.loadAnimation(context, R.anim.blink)
        view.startAnimation(animation)
    }

    fun stopPlayer() {
        mediaPlayer?.release()
        mediaPlayer = null

        soundIsPlaying = false
        activeView?.clearAnimation()
        context
    }

}
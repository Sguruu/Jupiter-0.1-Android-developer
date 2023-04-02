package com.weather.ls_14

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class UserAdapter(
    // принимаем список пользователей
    private var listUser: List<User>,
    // коллбэк для нажатия на элемент списка
    private val callbackDeleteItem: ((position: Int) -> Unit)? = null
) : RecyclerView.Adapter<UserAdapter.Holder>() {

    /**
     * @param parent родительская view в которую будут установлены view элементов из списка, этот
     * parent и есть recyclerView
     * @param viewType Тип представления нового представления, о нем поговрим чуть позже
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        /*
        В этом методе нобходимо вернуть Holder который будет принимать view элемента списка, т.е нам
        нужно создать элемент класса Holder,
        для того чтобы создать view элемента списка нам нужно его inflate из нашей разметки item_user
        для этого необходимо получить доступ к inflate ру
         */
        // получаем наш inflate
        val inflate = LayoutInflater.from(parent.context)
        // получаем наше view
        // attachToRoot - указываем что не будем инфлейтить родительскую view
        val view = inflate.inflate(R.layout.item_user, parent, false)
        return Holder(view, callbackDeleteItem)
    }

    /**
     * Вызывается RecyclerView для отображения данных в указанной позиции.
     * Этот метод должен обновить содержимое RecyclerView.ViewHolder.itemView,
     * чтобы отразить элемент в заданной позиции.
     */
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val user = listUser[position]
        holder.bind(user)
    }

    // возвращает количество элементов в списке
    override fun getItemCount(): Int {
        return listUser.size
    }

    class Holder(view: View, onItemClicked: ((position: Int) -> Unit)?) :
        RecyclerView.ViewHolder(view) {
        private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
        private val developerTextView: TextView = view.findViewById(R.id.developerTextView)
        private val imageView: ImageView = view.findViewById(R.id.avatarImageView)

        init {
            view.setOnClickListener {
                // adapterPosition передает позицию адаптера
                onItemClicked?.invoke(adapterPosition)
            }
        }

        fun bind(user: User) {
            nameTextView.text = user.name
            ageTextView.text = "Возраст = ${user.age}"
            developerTextView.isVisible = user.isDeveloper

            // привязываем к жц
            Glide.with(itemView)
                // загрузка изображения
                .load(user.avatarLink)
                // настройка отображения пока наша картинка грузиться из интернета
                .placeholder(R.drawable.ic_launcher_foreground)
                // установка изображеия
                .into(imageView)
        }
    }

    fun updateUsers(newUsers: List<User>) {
        listUser = newUsers
        // оповещает адаптер что список изменился и необходимо перерисовать наш лист
        notifyDataSetChanged()
    }

    // вариант два
    //  class Holder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root)
}

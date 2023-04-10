package com.weather.ls_15.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.weather.ls_15.R
import com.weather.ls_15.model.Person

class PersonAdapter(
    private val onItemClick: (name: String, age: Int) -> Unit,
    private val onLongItemClick: ((position: Int) -> Unit)?
) :
// так как мы будем использовать разные viewHolder для разных типов пользователей укажем базовый
// viewHolder
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        private var personsList: List<Person> = emptyList()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val inflate = LayoutInflater.from(parent.context)
            when (viewType) {
                TYPE_USER -> {
                    val view = inflate.inflate(R.layout.item_user, parent, false)
                    return UserHolder(view, onItemClick, onLongItemClick)
                }
                TYPE_DEVELOPER -> {
                    val view = inflate.inflate(R.layout.item_developer, parent, false)
                    return DeveloperHolder(view, onItemClick, onLongItemClick)
                }
                else -> error("Incorrect viewType=$viewType")
            }
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            when (holder) {
                is UserHolder -> {
                    // получаем пользователи и пробуем преобразовать к нужному типу,
                    // если не получится выкидываем ошибку, что персона не является пользователем
                    val person = personsList[position].let { it as? Person.User }
                        ?: error("Person at position = $position is not a user")
                    // тут идет авто приведение типа
                    holder.bind(person)
                }
                is DeveloperHolder -> {
                    val person = personsList[position].let { it as? Person.Developer }
                        ?: error("Person at position = $position it not developer")
                    holder.bind(person)
                }
                else -> error("Incorrect view holder = $holder")
            }
        }

        override fun getItemCount(): Int {
            return personsList.size
        }

        /**
         * @param position позиция элемент
         */
        override fun getItemViewType(position: Int): Int {
            return when (personsList[position]) {
                is Person.User -> TYPE_USER
                is Person.Developer -> TYPE_DEVELOPER
            }
        }

        fun updatePersons(newPersons: List<Person>) {
            personsList = newPersons
        }

        // создадим абстрактный класс, чтобы его нельзя было создать, только для наследования
        // тут реализуем только общее поведение
        abstract class BasePersonHolder(
            view: View,
            private val onItemClick: (name: String, age: Int) -> Unit,
            onLongItemClick: ((position: Int) -> Unit)?
        ) : RecyclerView.ViewHolder(view) {
            private val nameTextView: TextView = view.findViewById(R.id.nameTextView)
            private val ageTextView: TextView = view.findViewById(R.id.ageTextView)
            private val imageView: ImageView = view.findViewById(R.id.avatarImageView)

            init {
                view.setOnLongClickListener {
                    onLongItemClick?.invoke(adapterPosition)
                    return@setOnLongClickListener true
                }
            }

            // сделали его protected, чтобы классы наследники могли его вызывать
            protected fun bindMainInfo(
                name: String,
                avatarLink: String,
                age: Int
            ) {
                nameTextView.text = name
                ageTextView.text = itemView.context.resources.getString(R.string.age, age)

                Glide.with(itemView)
                    .load(avatarLink)
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .into(imageView)

                itemView.setOnClickListener {
                    onItemClick.invoke(name, age)
                }
            }
        }

        // используется для отображение персоны пользователя (Person.User)
        class UserHolder(
            view: View,
            onItemClick: (name: String, age: Int) -> Unit,
            onLongItemClick: ((position: Int) -> Unit)?
        ) : BasePersonHolder(view, onItemClick, onLongItemClick) {
            init {
                // делаем невидимой view
                view.findViewById<TextView>(R.id.developerTextView).isVisible = false
            }

            fun bind(personUser: Person.User) {
                bindMainInfo(personUser.name, personUser.avatarLink, personUser.age)
            }
        }

        // используется для отображения персоны программиста (Person.User)
        class DeveloperHolder(
            view: View,
            onItemClick: (name: String, age: Int) -> Unit,
            onLongItemClick: ((position: Int) -> Unit)?
        ) : BasePersonHolder(view, onItemClick, onLongItemClick) {
            private val programmingLanguageTextView: TextView =
                view.findViewById(R.id.programmingLanguageTextView)

            fun bind(personDeveloper: Person.Developer) {
                bindMainInfo(personDeveloper.name, personDeveloper.avatarLink, personDeveloper.age)
                programmingLanguageTextView.text = itemView.context.resources.getString(
                    R.string.programming_language,
                    personDeveloper.programmingLanguage
                )
            }
        }

        companion object {
            private const val TYPE_USER = 1
            private const val TYPE_DEVELOPER = 2
        }
    }

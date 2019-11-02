package models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import helpers.exists
import helpers.read
import helpers.write
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import java.util.*

val UserJSON_FILE = "users.json"
val userGsonBuilder = GsonBuilder().setPrettyPrinting().create()
val userListType = object : TypeToken<ArrayList<UserModel>>() {}.type

fun generateRandomUserId(): Long {
    return Random().nextLong()
}

class UserJSONStore: UserStore, AnkoLogger {

    val context: Context
    var users = mutableListOf<UserModel>()

    constructor(context: Context) {
        this.context = context
        if (exists(context, UserJSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<UserModel> {
        return users
    }

    override fun create(user: UserModel) {
        user.id = generateRandomUserId()
        users.add(user)
        serialize()
    }

    override fun update(user: UserModel) {
        var founduser: UserModel? = users.find { u -> u.id == user.id }
        if (founduser != null) {
            founduser.email = user.email
            founduser.password = user.password
        }
        serialize()
    }

    override fun login(email: String, password: String): UserModel? {
        var foundUser: UserModel? = users.find { p -> p.email == email }
        if (foundUser?.password == password) {
            return foundUser
        }
        else {
            return null
        }
    }

    fun logAll() {
        users.forEach{ info("${it}") }
    }

    private fun serialize() {
        val jsonString = userGsonBuilder.toJson(users, userListType)
        write(context, UserJSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, UserJSON_FILE)
        users = Gson().fromJson(jsonString, userListType)
    }
}


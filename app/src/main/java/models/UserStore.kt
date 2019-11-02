package models

interface UserStore {
    fun findAll(): List<UserModel>
    fun signup(user: UserModel): Boolean
    fun update(user: UserModel)
    fun login(email: String, password: String): UserModel?
}
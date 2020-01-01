package models

interface UserStore {
  fun findAll(): List<UserModel>
  fun create(user: UserModel)
  fun update(user: UserModel)
  fun login(email: String, password: String): UserModel?
}
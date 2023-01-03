package dev.hodol.sample.user

import dev.hodol.sample.user.dto.GetUserResponse
import dev.hodol.sample.user.dto.GetUsersResponse
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/users")
class UserController(
    private val userService: UserService
) {
    @GetMapping
    fun getUsers(): ResponseEntity<GetUsersResponse> {
        val users = userService.getUsers()
        return ResponseEntity.ok(GetUsersResponse.of(users))
    }

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): ResponseEntity<GetUserResponse> {
        val user = userService.getUserById(userId)
        return ResponseEntity.ok(GetUserResponse.of(user))
    }
}
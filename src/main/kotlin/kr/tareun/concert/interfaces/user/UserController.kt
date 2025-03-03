package kr.tareun.concert.interfaces.user

import kr.tareun.concert.application.user.UserService
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.common.enums.ResponseResultType
import kr.tareun.concert.interfaces.user.model.UserResponse
import org.springframework.web.bind.annotation.*

@RequestMapping("/users")
@RestController
class UserController(
    private val userService: UserService,
) {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): Response<UserResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            UserResponse.from(userService.retrieveUserDetail(userId))
        )
    }
}
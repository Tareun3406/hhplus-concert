package kr.tareun.concert.interfaces.user

import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.user.model.UserResponse
import org.springframework.web.bind.annotation.*

@RestController("/users")
class UserController {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): Response<UserResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            UserResponse(userId, "test@test.com", "이용자 1", 10_000)
        )
    }
}
package kr.tareun.concert.interfaces.user

import kr.tareun.concert.domain.user.model.UserInfo
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import org.springframework.web.bind.annotation.*

@RestController("/users")
class UserController {

    @GetMapping("/{userId}")
    fun getUser(@PathVariable userId: Long): Response<UserInfo> {
        return Response(
            ResponseResultType.SUCCESS,
            UserInfo(userId, "test@test.com", "이용자 1")
        )
    }
}
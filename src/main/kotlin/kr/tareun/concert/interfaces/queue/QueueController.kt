package kr.tareun.concert.interfaces.queue

import kr.tareun.concert.domain.queue.model.QueueTokenInfo
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("/queue")
@RestController
class QueueController {

    @PostMapping
    fun getUserQueue(@RequestParam userId: Long): Response<QueueTokenInfo> {
        return Response(
            ResponseResultType.SUCCESS,
            QueueTokenInfo(UUID.randomUUID(), userId, 100)
        )
    }
}
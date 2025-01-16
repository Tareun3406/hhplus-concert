package kr.tareun.concert.interfaces.queue

import kr.tareun.concert.application.queue.QueueService
import kr.tareun.concert.interfaces.common.response.Response
import kr.tareun.concert.interfaces.common.response.ResponseResultType
import kr.tareun.concert.interfaces.queue.model.QueueResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RequestMapping("/queue")
@RestController
class QueueController(
    private val queueService: QueueService,
) {

    @PostMapping
    fun createQueueToken(@RequestParam userId: Long): Response<QueueResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            QueueResponse.from(queueService.createQueueToken(userId))
        )
    }

    @GetMapping
    fun getQueueToken(@RequestParam uuid: UUID): Response<QueueResponse> {
        return Response(
            ResponseResultType.SUCCESS,
            QueueResponse.from(queueService.getQueueToken(uuid))
        )
    }
}
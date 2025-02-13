package kr.tareun.concert.infrastructure.persistence.payment.model

import jakarta.persistence.*
import kr.tareun.concert.domain.payment.model.Point

@Table(name = "Point")
@Entity
class PointEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var userId : Long, // index 추가

    @Column(nullable = false)
    var point : Int,
) {
    companion object {
        fun from(point: Point): PointEntity {
            return PointEntity(point.pointId, point.userId, point.point)
        }
    }
    fun toPoint(): Point {
        return Point(id, userId, point)
    }
}
package kr.tareun.concert.infrastructure.persistence.concert.entity

import jakarta.persistence.*
import kr.tareun.concert.domain.concert.model.Concert

@Table(name = "concert")
@Entity
class ConcertEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name: String = "",

    @Column(nullable = false)
    var performer: String = "",
) {
    companion object {
        fun from(concert: Concert): ConcertEntity {
            return ConcertEntity(
                concert.concertId,
                concert.concertName,
                concert.performer,
            )
        }
    }
    fun toConcert(): Concert {
        return Concert(id, name, performer)
    }
}
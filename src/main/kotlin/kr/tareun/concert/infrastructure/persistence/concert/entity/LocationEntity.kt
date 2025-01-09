package kr.tareun.concert.infrastructure.persistence.concert.entity

import jakarta.persistence.*

@Table(name = "location")
@Entity
class LocationEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,

    @Column(nullable = false)
    var name : String = "",

    @Column(nullable = false)
    var locationCapacity : Int,
)
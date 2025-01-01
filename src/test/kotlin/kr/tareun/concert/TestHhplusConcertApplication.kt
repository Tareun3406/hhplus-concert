package kr.tareun.concert

import org.springframework.boot.fromApplication
import org.springframework.boot.with


fun main(args: Array<String>) {
	fromApplication<HhplusConcertApplication>().with(TestcontainersConfiguration::class).run(*args)
}

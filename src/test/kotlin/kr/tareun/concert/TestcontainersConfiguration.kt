package kr.tareun.concert

import kr.tareun.concert.common.config.properties.RedisProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

@Configuration
class TestcontainersConfiguration {

	@Bean
	fun mariaDbContainer(): MariaDBContainer<*> {
		return MariaDBContainer(DockerImageName.parse("mariadb:11.4"))
			.withUsername("root")
			.withReuse(false)
	}

	@Bean
	fun dataSource(mariaDbContainer: MariaDBContainer<*>): DataSource {
		val dataSource = DriverManagerDataSource()
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver")
		dataSource.url = mariaDbContainer.jdbcUrl
		dataSource.username = mariaDbContainer.username
		dataSource.password = mariaDbContainer.password
		return dataSource
	}

	@Bean
	fun redisContainer(): GenericContainer<*> {
		return GenericContainer(DockerImageName.parse("redis:7.4"))
			.withExposedPorts(6379) // Redis 기본 포트
			.withReuse(false)
			.apply { start() }
	}

	@Bean
	fun redisProperties(redisContainer: GenericContainer<*>): RedisProperties {
		val redisProperties = RedisProperties()
		redisProperties.host = redisContainer.host
		redisProperties.port = redisContainer.getMappedPort(6379)
		return redisProperties
	}
}

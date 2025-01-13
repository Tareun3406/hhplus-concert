package kr.tareun.concert

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.testcontainers.containers.MariaDBContainer
import org.testcontainers.utility.DockerImageName
import javax.sql.DataSource

@Configuration(proxyBeanMethods = false)
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
}

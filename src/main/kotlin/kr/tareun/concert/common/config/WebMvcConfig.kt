package kr.tareun.concert.common.config

import kr.tareun.concert.common.interceptor.QueueTokenInterceptor
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfig(
    private val queueTokenInterceptor: QueueTokenInterceptor
): WebMvcConfigurer {

    override fun addInterceptors(registry: InterceptorRegistry) {
        registry.addInterceptor(queueTokenInterceptor)
            .addPathPatterns("/concerts")
    }
}
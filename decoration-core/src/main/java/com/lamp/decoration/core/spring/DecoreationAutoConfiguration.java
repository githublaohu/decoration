package com.lamp.decoration.core.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lamp.decoration.core.exception.CustomExceptionResult;
import com.lamp.decoration.core.exception.DecorationExceptionHandler;
import com.lamp.decoration.core.exception.ExceptionResult;

@ConditionalOnClass(name = { "org.springframework.web.servlet.HandlerInterceptor" })
@Configuration
@EnableConfigurationProperties(DecorationProperties.class)
public class DecoreationAutoConfiguration {

	@Bean
	public OperationSpringMVCBehavior OperationSpringMVCBehavior() {
		return new OperationSpringMVCBehavior();
	}

	@Bean
	public QueryClauselnteWebMvcConfigurer queryClauselnteWebMvcConfigurer() {
		return new QueryClauselnteWebMvcConfigurer();
	}

	@Bean
	@ConditionalOnProperty(prefix = DecorationProperties.DECORATION_PREFIX, value = { "defaultExceptionResult" })
	public DecorationExceptionHandler decorationExceptionHandler(DecorationProperties decorationProperties)
			throws InstantiationException, IllegalAccessException, ClassNotFoundException {
		List<ExceptionResult> exceptionResultList = new ArrayList<>();

		CustomExceptionResult exceptionResult = (CustomExceptionResult) Class
				.forName(decorationProperties.getDefaultExceptionResult()).newInstance();
		if (Objects.isNull(exceptionResult.getDefaultExceptionResult())) {
			throw new RuntimeException("defaultExceptionResult not  null ");
		}
		if(Objects.nonNull(exceptionResult.getExceptionResultList())) {
			exceptionResultList.addAll(exceptionResult.getExceptionResultList());
		}

		List<String> exceptionResultClassNameList = decorationProperties.getExceptionResult();

		if (Objects.nonNull(exceptionResultClassNameList) && !exceptionResultClassNameList.isEmpty()) {
			for (String className : exceptionResultClassNameList) {
				exceptionResult = (CustomExceptionResult) Class.forName(className).newInstance();
				if(Objects.nonNull(exceptionResult.getExceptionResultList())) {
					exceptionResultList.addAll(exceptionResult.getExceptionResultList());
				}
			}
		}
		return new DecorationExceptionHandler(exceptionResultList, exceptionResult.getDefaultExceptionResult());
	}
}

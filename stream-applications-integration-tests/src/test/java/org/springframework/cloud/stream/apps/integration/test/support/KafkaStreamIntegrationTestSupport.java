/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.springframework.cloud.stream.apps.integration.test.support;

import java.time.Duration;

import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

import org.springframework.cloud.stream.app.test.integration.kafka.AbstractKafkaStreamApplicationIntegrationTests;

public abstract class KafkaStreamIntegrationTestSupport extends AbstractKafkaStreamApplicationIntegrationTests {

	protected static final String VERSION = "3.0.0-SNAPSHOT";

	protected static final String DOCKER_ORG = "springcloudstream";

	private static DockerImageName defaultKafkaImageFor(String appName) {
		return DockerImageName.parse(DOCKER_ORG + "/" + appName + "-kafka:" + VERSION);
	}

	protected static GenericContainer defaultKafkaContainerFor(String appName) {
		return new GenericContainer(defaultKafkaImageFor(appName));
	}

	protected static GenericContainer httpSource(int serverPort) {
		return new GenericContainer(defaultKafkaImageFor("http-source"))
				.withEnv("SERVER_PORT", String.valueOf(serverPort))
				.withExposedPorts(serverPort)
				.waitingFor(Wait.forListeningPort().withStartupTimeout(Duration.ofMinutes(2)));
	}
}

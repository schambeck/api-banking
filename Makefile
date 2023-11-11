APP = api-banking
VERSION = 1.0.0
JAR = ${APP}-${VERSION}.jar
TARGET_JAR = target/${JAR}

DOCKER_IMAGE = ${APP}:latest
DOCKER_FOLDER = .
DOCKER_CONF = ${DOCKER_FOLDER}/Dockerfile
COMPOSE_CONF = ${DOCKER_FOLDER}/compose.yaml

# maven

clean:
	./mvnw clean

dist-run: dist run

dist:
	./mvnw clean package -DskipTests=true -Dmaven.plugin.validation=BRIEF

run:
	java -jar target/api-*.jar

check:
	./mvnw clean test -Dmaven.plugin.validation=BRIEF

# jacoco

jacoco-report:
	./mvnw clean test jacoco:report -Dmaven.plugin.validation=BRIEF -Pcoverage

# sonar

sonar-start:
	docker run -d --name sonarqube -p 9000:9000 sonarqube:10.2.1-community

sonar-run:
	./mvnw clean verify sonar:sonar \
      -Dsonar.projectKey=api-banking \
      -Dsonar.projectName='api-banking' \
      -Dsonar.host.url=http://localhost:9000 \
      -Dsonar.token= \
      -Dmaven.plugin.validation=BRIEF

# compose

compose-down-up: compose-down compose-up

compose-up:
	docker-compose up -d

compose-up-postgres:
	docker-compose up -d postgres

compose-down:
	docker-compose down

# archetype

create-archetype-from-project:
	./mvnw clean archetype:create-from-project \
		-DkeepParent=true \
		-DpropertyFile=archetype.properties \
		-DpackageName=com.schambeck \
		-Darchetype.filteredExtensions=java,xml,md \
		-Dmaven.plugin.validation=BRIEF

install-archetype:
	cd target/generated-sources/archetype && mvn clean install

create-project-from-archetype:
	mvn archetype:generate \
		-DgroupId=br.com.castgroup.banking \
		-DartifactId=api-banking \
		-Dversion=1.0.0 \
		-DarchetypeGroupId=com.schambeck \
		-DarchetypeArtifactId=api-template-archetype \
		-DinteractiveMode=false \
		-Dpackage=br.com.castgroup.banking

# Docker

dist-docker-build: dist docker-build

dist-docker-build-push: dist docker-build docker-push

docker-build-push: docker-build docker-push

docker-build:
	DOCKER_BUILDKIT=1 docker build -f ${DOCKER_CONF} -t ${DOCKER_IMAGE} --build-arg=JAR_FILE=${JAR} target

docker-run:
	docker run -d \
		--name ${APP} \
	  	--env SPRING_DATASOURCE_URL=jdbc:postgresql://postgres:5432/banking \
		--publish 8080:8080 \
		${DOCKER_IMAGE}

docker-tag:
	docker tag ${APP} ${DOCKER_IMAGE}

docker-push:
	docker push ${DOCKER_IMAGE}

docker-pull:
	docker pull ${DOCKER_IMAGE}

# Keycloak

KC_APP = kc-banking
KC_DOCKER_IMAGE = ${KC_APP}:latest
KC_DOCKERFILE = Dockerfile.kc

kc-docker-build-push: kc-docker-build kc-docker-push

kc-docker-build:
	docker build -f ${KC_DOCKERFILE} -t ${KC_DOCKER_IMAGE} .

kc-docker-push:
	docker push ${KC_DOCKER_IMAGE}

kc-docker-run:
	docker run -p 9000:9000 \
		--name ${KC_APP} \
		-e KC_HTTP_PORT=9000 \
		-e KEYCLOAK_ADMIN=admin \
		-e KEYCLOAK_ADMIN_PASSWORD=admin \
		${KC_DOCKER_IMAGE} start-dev --import-realm

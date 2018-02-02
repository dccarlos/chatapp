

FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp

ARG JAR_FILE

# RUN ["sh", "-c", "echo ${JAR_FILE}"]

#### TIMEZONE ####
RUN apk update
RUN apk add tzdata
ENV TZ="America/Mexico_City"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
##################

ADD ${JAR_FILE} app.jar
RUN sh -c 'touch /app.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /app.jar" ]
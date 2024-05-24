#maven环境
FROM harbor.deepwisdomai.com/deepwisdom/maven-jdk1.8:20210722
MAINTAINER Justin

COPY pom.xml /build/
COPY /drpc-proto /build/drpc-proto
COPY /drpc-server /build/drpc-server
WORKDIR /build/

RUN mvn clean package -Dmaven.test.skip=true

WORKDIR /app
RUN cp -r /build/drpc-server/target/*.jar /app/app.jar
RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime && echo Asia/Shanghai > /etc/timezone
ENV LANG zh_CN.UTF-8
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "-Dfile.encoding=UTF-8", "/app/app.jar"]

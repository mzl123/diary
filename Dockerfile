# Docker image for spring boot file run
# VERSION 0.0.1
# Author: mzl
# 基础镜像使用java
FROM java:8
# 作者
MAINTAINER mzl <1059115590@qq.com>
# VOLUME 指定了临时文件目录为/tmp。
# 其效果是在主机 /var/lib/docker 目录下创建了一个临时文件，并链接到容器的/tmp
#VOLUME /tmp
# 将jar包添加到容器中并更名为app.jar
#ADD target/diary-0.0.1-SNAPSHOT.jar app.jar
# 时区
#ENV TZ=Asia/Shanghai
# 运行jar包
#RUN bash -c 'touch /app.jar'
#ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]

ARG JAR_FILE=./target/*.jar
COPY ${JAR_FILE} app.jar
ENV TZ=Asia/Shanghai
ENTRYPOINT ["java","-jar","/app.jar"]
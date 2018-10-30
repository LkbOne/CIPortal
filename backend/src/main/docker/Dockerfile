FROM frolvlad/alpine-oraclejdk8:slim
VOLUME /tmp
ADD ciportal-3.0.0.jar CIPortalApplication.jar
RUN sh -c 'touch /CIPortalApplication.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar /CIPortalApplication.jar" ]
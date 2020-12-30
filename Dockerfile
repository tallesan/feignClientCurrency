FROM openjdk:11
VOLUME /tmp
ADD build/libs/feignClient-myProject.jar feignClientCurrency.jar
ENTRYPOINT ["java", "-jar", "/feignClientCurrency.jar" ]
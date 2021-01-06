FROM openjdk:11
VOLUME /tmp
ADD build/libs/feignClient-1.0.jar feignClientCurrency.jar
ENTRYPOINT ["java", "-jar", "/feignClientCurrency.jar" ]
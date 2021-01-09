# FeignClientCurrency

1. Ключ базовый. Позволяет получить только курсы на базовую валюту(USD).
2. В application.yaml можно указать Базовую валюту(по умолчанию USD), валюту с которой сравниваем (
   EUR), альтернативную валюту в которую конвертируем (RUB). Также можно указать день курса с
   которым сравниваем (по умолчанию 1 день назад)
3. Все корневые адреса вынесены в application.yaml (urlCurrency - адрес курса валют, urlImg - адрес -
   gif картинок)
4. Созданы 2 вспомогательных класса (ParamQuery для курсов и GiphyParam для картинок), которые подгружают параметры для курсов валют и для загрузки
   картинок (параметры берутся из application.yaml).
5. Тесты Написаны с помощью WireMock и выполняются на 8888 порту.   
6. Docker контейнер docker run -d -p 8080:8080 tallesan/feign-client-currency:currencyClient

# Примеры запуска

1. Можно просто запустить приложение запустив из терминала gradlew run или выбрав соответствующий
   пункт в меню;
2. Можно клонировать docker контейнер выполнив в терминале docker run -d -p 8080:8080 tallesan/feign-client-currency:currencyClient
3. Можно создать свой docker контейнер выполнив в корне проекта DockerFile(нужно передать параметры
   Image tag(например feign-client-currency), Container name(например feignClientCurrency), Binds
   port(например 127.0.0.1:8080:8080)). В Этом случае будет собран контейнер и запущен на порту 8080;
4. Можно выполнить gradlew dockerBuildImage в терминале. В этом случае будет собран локальный
   контейнер. Запустить контейнер можно docker run -p 8080:8080 <Id-контейнера>. Узнать Id можно ил в
   последней строчке сборки (Created image with ID 'e22c992fecfb'.) или в папке
   build/.docker/dockerBuildImage-imageId.txt.

# Поведение приложения

1. В приложении по умолчанию прописана базовая валюта USD (ограничение тестового ключа).
2. В качестве альтернативной валюты установлен RUB при нажатии на кнопку <проверить для альтернативной> выводится информация для курса рубля.
3. В обоих случаях мы сравниваем с EUR.
4. Управлять параметрами можно в application.yaml
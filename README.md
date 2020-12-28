# feignClientCurrency
Ключ базовый. Позволяет получить только курсы на базовую валюту(USD).
В application.yaml можно указать Базовую валюту(по умолчанию USD), волюту с которой сравниваем (EUR), альтернативную валюту в которую конвертируем (RUB).
Также можно указать день курса с которым сравниваем(по умолчанию 1 день назад)
Все корневые адреса вынесены в appliction.yaml (urlCurrency - адрес курса валют, urlImg - адрес - gif картинок)
Созданы 2 вспомогательных класса которые подгружают параметры для курсов валют и для загрузки картинок.

# FeignClientCurrency

### 1. Ключ базовый. Позволяет получить только курсы на базовую валюту(USD).

### 2. В application.yaml можно указать Базовую валюту(по умолчанию USD), валюту с которой сравниваем (EUR), альтернативную валюту в которую конвертируем (RUB). Также можно указать день курса с которым сравниваем(по умолчанию 1 день назад)

### 3. Все корневые адреса вынесены в appliction.yaml (urlCurrency - адрес курса валют, urlImg - адрес - gif картинок)

### 4. Созданы 2 вспомогательных класса которые подгружают параметры для курсов валют и для загрузки картинок (параметры беруться из application.yaml).

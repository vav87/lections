# Istio

## Service Mesh

В мире микросервисной архитектуры каждый сервис может быть написан на разных языках, разном стеке технологий и в разное время (например, legacy).
В рамках одной операции микросервисы могут ходить друг к другу за данными, в итоге мы обычно получаем связность все-со-всеми, т.е. сеть.
Все это нужно мониторить, реализовывать согласованную отказоусточивость (например, для Circuit Breaker нужно единое поведение и
согласованные между всеми сервисами правила), но реализовывать это все во всех сервисах очень тяжелая задача.

Поэтому возник service mesh – технология, которая добавляет уровень абстракции над сетью. Мы перехватываем весь или часть
трафика в кластере и производим определенный набор операций с ним. Решаемые задачи:
* Routing – таймауты, повторные попытки, балансировка нагрузки, canary releases;
* Security – аутентификация и авторизация конечного пользователя, tls для межсервисного взаимодействия;
* Monitoring – трассировка, мониторинг, логгирование.

## Istio

Istio состоит из двух основных зон — Control Plane и Data Plane.

![Istio Architecture](images/Istio%20Architecture.svg)

Control Plane содержит в себе основные компоненты,
которые обеспечивают корректную работу остальных. Control Plane имеет четыре основных компонента:
* Istiod – главный узел, занимается конвертацией высокоуровневых правил роутинга в конфигурации для Envoy и распространяет их во все sidecar.
* Pilot – абстрагируем платформо-зависимые механизмы Service Discovery и преобразует их в формат, который может обрабатывать Envoy API.
* Galley – управление конфигурациями в Istio.
* Citadel – работа с сертификатами и контроль трафика.

Data Plane реализуется с помощью sidecar контейнеров-прокси. По умолчанию используется прокси-сервер Envoy. Для того,
чтобы Istio работал полностью прозрачно для приложений, есть система автоматического inject'инга (используется механизм k8s
Mutational Admission Webhook).

![Data Plane](images/Data%20Plane.gif)

Sidecar контейнеры соединяются с Pilot по протоколу GRPC, который позволяет оптимизировать модель пушинга изменений,
происходящих в кластере. GRPC начал использоваться в Envoy начиная с версии 1.6, в Istio он используется с версии 0.8
и представляет из себя pilot-agent — обертку на golang над envoy, которая конфигурирует параметры запуска.  
В итоге мы получаем целую сеть envoy прокси-серверов, которые мы можем настраивать из одной точки (Pilot).
Все inbound и outbound запросы проходят через envoy. Причем перехватывается только TCP трафик.

### Типы ресурсов
* DestinationRule – определяет политики управления трафиком к сервису после прохождения роутинга;
* Gateway – Load Balancer на границе mesh обрабатывающий входящие/исходящие соединения;
* Sidecar – описывает Proxy, который является посредником между входящим/исходящим трафиком и самим обрабатывающим контейнером.
* VirtualService – используется для описания роутинга трафика;
* AuthorizationPolicy – определяет политики доступа до контейнера;
* PeerAuthentication – определяет как трафик тунелируется в sidecar;
* RequestAuthentication – определяет какие методы аутентификации поддерживаются для запросов к контейнеру.

## Пример развертывания в кластере Kubernetes
### Подготовка окружения
```shell script
git clone git@github.com:Romanow/ansible-kubernetes.git
cd ansible-kubernetes/vagrant
# Запускаем 3 виртуальных машины (2 ядра, 2Гб памяти на каждую)
# vagrant box https://app.vagrantup.com/romanow/boxes/ansible-box
vagrant up
# Запускаем кластер на этих машинах
cd ../ansible
ansible-playbook -i inventories/local/static.yml --vault-password-file=.vault_pass kubernetes.yml
# Копируем параметры кластера в локальный kubectl
scp -r ansible@192.168.52.10:~/.kube ~/
# Проверяем что кластер поднят
kubectl get nodes

# Установка Istio
# Открываем в другой вкладке ssh к мастер ноде
ssh ansible@192.168.52.10
export ISTIO_VERSION=1.7.4
cd /opt
curl -L https://istio.io/downloadIstio | sudo sh -
sudo mv /opt/istio-1.7.4 istio
sudo chown -R ansible:ansible /opt/istio
export PATH=$PWD/bin:$PATH
istioctl install --set profile=demo
# Включаем автоматическое добавление sidecar-контейнера в pod'ы
kubectl label namespace default istio-injection=enabled
# Включаем мониторинг
kubectl apply -f samples/addons
```

### Пример мониторинга кластера
```shell script
# С host-машины
# Создаем postgres, store-service, order-service, warehouse-service, warranty-service
# (описание работы https://github.com/Romanow/micro-services-v2)
# docker образы http://hub.docker.com/r/romanowalex/
kubectl apply -f postgres-deployment.yml
# Т.к. istio по-умолчанию использует TLS для взаимодействия между нодами (ssl-termination происходит в sidecar proxy),
# для прямого подключения к БД выключаем TLS для сервиса
kubectl apply -f postgres-istio.yml
kubectl apply -f servives-deployment.yml
# Публикуем главный сервис наружу (store.services.local)
kubectl apply -f services-istio.yml
# Достаем внешний ip для istio gateway
kubectl get svc -n istio-system istio-ingressgateway
sudo tee -a /etc/hosts > /dev/null <<EOT
192.168.52.101     store.services.local
192.168.52.101     kiali.services.local
192.168.52.101     grafana.services.local
192.168.52.101     tracing.services.local
EOT
```

### Пример Istio

#### Timeout
```shell script
kubectl apply -f book-store-deployment.yml
kubectl apply -f book-store-gateway.yml
# Проверяем что все успешно поднялось
echo -n '192.168.52.101     store-service.local' | sudo tee -a /etc/hosts
curl http://book-store.local/api/v1/books -v | jq
# Запускаем нагрузку через Yandex Tank
cd yandex-tank
./load-testing.sh # будет обстреливать /api/v1/books с нагрузкой 20 rps в течении 10 минут
# Устанавливаем timeout 15 секунд, часть запросов отваливается с Protocol error (Client abort exception)
kubectl apply -f book-store-timeout.yml
```

#### Routing
```shell script
# Для запросов с Header: 'X-API-VERSION: v2' отдаем 301:Redirect на /api/v2 
curl http://book-store.local/api/v1 -v
curl http://book-store.local/api/v2
curl http://book-store.local/api/v1 -H 'x-api-version: v2' -v --location
```

#### Canary Release
```shell script
# Запускаем скрипт, видим что отдает 200 OK
while true; do curl http://book-store.local/api/v1/ping; echo ''; sleep 1; done
# Создаем новую выкладку с образом romanowalex/book-store:v1.2-break-ping, 50% траффика направляем
# туда, видим что часть запросов стала падать с 500 ошибкой
kubectl apply -f book-store-deployment-v2.yml
kubectl apply -f book-store-weight-release.yml
```

#### Circuit Breaker
```shell script
# Убираем лишние deployment из предыдущих примеров, здесь нужен Pod с version:v1 и образом romanowalex/book-store:v1.2
kubectl get deployment
kubectl delete deployment book-store-v2
# Запускаем скрипт, видим что отдает 200 OK
while true; do curl http://book-store.local/api/v1/ping; echo ''; sleep 1; done
# Применяем правило для Circuit Breaker с параметрами (используется реализация [Envoy Circuit Breaker](https://www.envoyproxy.io/docs/envoy/latest/intro/arch_overview/upstream/circuit_breaking))
# 10 ошибок и хост изымается из пула на 30 секунд
kubectl apply -f book-store-circuit-breaker.yml
# Обновляем образ в deployment и видим что запросы стали возвращать 500, а через некоторое время istio
# стал возвращать 503 Service Unavailable, т.е. сработала перегородка
kubectl set image deployment/book-store-v1 book-store=romanowalex/book-store:v1.2-break-ping
```

## Ссылки
1. Как запустить Istio, используя Kubernetes в production. [Часть 1](https://habr.com/ru/company/avito/blog/419319/), [Часть 2](https://habr.com/ru/company/avito/blog/433650/)
1. Назад к микросервисам вместе с Istio. [Часть 1](https://habr.com/ru/company/flant/blog/438426/), [Часть 2](https://habr.com/ru/company/flant/blog/440378/), [Часть 3](https://habr.com/ru/company/flant/blog/443668/)
1. [Istio — это просто: Sidecar](https://habr.com/ru/company/southbridge/blog/525590/)
1. [Ликбез по запуску Istio](https://habr.com/ru/company/southbridge/blog/441616/)
1. [Istio circuit breaker](https://banzaicloud.com/blog/istio-circuit-breaking/)
1. [Envoy Circuit breaking](https://www.envoyproxy.io/docs/envoy/latest/intro/arch_overview/upstream/circuit_breaking)
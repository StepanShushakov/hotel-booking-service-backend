#!/bin/sh
#export DOCKERHOST=$(ifconfig | grep -E "([0-9]{1,3}\.){3}[0-9]{1,3}" | grep -v 127.0.0.1 | awk '{ print $2 }' | cut -f2 -d: | head -n1)

# Попытка получить IP-адрес хоста
export DOCKERHOST=$(hostname -I | awk '{print $1}')

# Проверка, если DOCKERHOST пустой
if [ -z "$DOCKERHOST" ]; then
  echo "Не удалось определить IP-адрес хоста, используем 127.0.0.1 по умолчанию."
  export DOCKERHOST="127.0.0.1"
else
  echo "DOCKERHOST установлен на: $DOCKERHOST"
fi

docker-compose -f docker-compose.yml up
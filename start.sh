#!/bin/bash

echo "inicia instalacion contenedor!"
mvn clean package -Dmaven.test.skip=true

docker run \
 --name dk_receivermq \
 -v /volume/docker/receivermq/tmp:/tmp  \
 -v /volume/docker/receivermq/data/serv_contabilidad:/data/serv_contabilidad  \
 dk_receivermq

exit

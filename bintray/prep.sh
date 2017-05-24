cp /usr/local/bin/jfrog .
cd bintrayrocks
tar -cvf amaze-1.0-x86.tgz exec
cd ..
cp bintrayrocks/amaze-1.0-x86.tgz .
jfrog rt u amaze-1.0.tgz 35.188.114.255:8084/jfrog/bintray/bintray-webinar/amaze/
docker build -t 35.188.114.255:8084/amaze:1.0 .
docker push 35.188.114.255:8084/amaze:1.0

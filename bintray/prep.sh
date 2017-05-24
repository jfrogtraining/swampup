cp /usr/local/bin/jfrog .
cd bintrayrocks
tar -cvf amaze-1.0.tgz exec
cd ..
cp bintrayrocks/amaze-1.0.tgz .
jfrog rt u amaze-1.0.tgz jfrog.local/jfrog/bintray/bintray-webinar/amaze/
docker build -t jfrog.local:5001/amaze:1.0 .
docker push jfrog.local:5001/amaze:1.0

FROM jfrog.local:5001/busybox

MAINTAINER markg@jfrog.com

ADD jfrog /root/

COPY ./swampup-bintray-1.0-x86.tgz /root/

RUN /bin/tar -xvf /root/swampup-bintray-1.0-x86.tgz

RUN mv /exec /root/exec

CMD /root/exec/bintrayrocks.sh

FROM jfrog.local:5001/busybox

MAINTAINER markg@jfrog.com

ADD jfrog /root/

COPY ./amaze-1.0-x86.tgz /root/

RUN /bin/tar -xvf /root/amaze-1.0-86.tgz

RUN mv /exec /root/exec

CMD /root/exec/bintrayrocks.sh

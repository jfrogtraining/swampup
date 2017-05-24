FROM artifactory-us.jfrog.info/busybox

MAINTAINER markg@jfrog.com

ADD jfrog /root/

COPY ./amaze-1.0.tgz /root/

RUN /bin/tar -xvf /root/amaze-1.0.tgz

RUN mv /exec /root/exec

CMD /root/exec/bintrayrocks.sh

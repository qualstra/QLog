FROM openjdk:8
VOLUME /tmp
ARG JAVA_OPTS
ENV JAVA_OPTS=$JAVA_OPTS

#ENV DOWNLOAD http://www.h2database.com/h2-2016-10-31.zip
ENV DATA_DIR /opt/h2-data

ADD psc.mv.db /opt/h2-data/psc.mv.db
ADD h2.jar /opt/h2/bin/h2.jar
#RUN wget ${DOWNLOAD} -O h2.zip \
#    && unzip h2.zip -d /opt/ \
#    && rm h2.zip \
#    && mkdir -p ${DATA_DIR}

EXPOSE 8082 9092

ENTRYPOINT java -cp /opt/h2/bin/h2*.jar org.h2.tools.Server \
    -web -webAllowOthers -webPort 8082 \
    -tcp -tcpAllowOthers -tcpPort 9092 \
    -baseDir ${DATA_DIR}

VOLUME ${DATA_DIR}


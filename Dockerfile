FROM gradle:jdk11

RUN mkdir testapp

WORKDIR /testapp

COPY . .
COPY ./start.sh /usr/local/bin

ENTRYPOINT ["start.sh"]
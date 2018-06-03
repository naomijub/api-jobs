FROM clojure:alpine

ENV LANG C.UTF-8

EXPOSE 3000

WORKDIR /api

COPY project.clj profiles.clj ./
RUN lein deps

# Base image
FROM joshdev/alpine-oraclejre8


# Set environment
ENV SBT_HOME /usr/lib/sbt
ENV PATH $PATH:$SBT_HOME/bin
ENV SBT_VERSION 0.13.15

# Install sbt, python & aws-cli
RUN apk add --no-cache bash \
  && apk add --no-cache --virtual=build-dependencies wget ca-certificates \
  && apk add --no-cache git \
  && cd /usr/lib \
  && wget -q --no-cookies https://dl.bintray.com/sbt/native-packages/sbt/$SBT_VERSION/sbt-$SBT_VERSION.tgz -O - | gunzip | tar x \
  && apk del build-dependencies \
  && rm -rf /tmp/* \
  && apk add --no-cache python3 \
  && pip3 install awscli \
  && aws --version

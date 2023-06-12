# syntax=docker/dockerfile:1
ARG YB_VERSION=2.18.0.1
ARG YB_BUILD=b4
ARG YB_RELEASE=$YB_VERSION-$YB_BUILD
ARG YB_AMD64_PKG=https://downloads.yugabyte.com/releases/$YB_VERSION/yugabyte-$YB_RELEASE-linux-x86_64.tar.gz
ARG YB_ARM64_PKG=https://downloads.yugabyte.com/releases/$YB_VERSION/yugabyte-$YB_RELEASE-el8-aarch64.tar.gz

FROM ubuntu:focal
ARG YB_AMD64_PKG
ARG YB_ARM64_PKG
ARG TARGETPLATFORM

RUN <<EOT
  set -e
  useradd -ms /bin/bash yugabyte
  apt-get update -qqy
  apt-get install -qqy python-is-python3 python3-minimal wget libatomic1 chrony
  apt-get clean autoclean -qqy
  apt-get autoremove -qqy
  rm -rf /var/lib/{apt,dpkg,cache,log}/
  mkdir -p /home/yugabyte/yb-data
  chown -R yugabyte:yugabyte /home/yugabyte/yb-data
EOT
COPY <<-EOT /etc/security/limits.d/99-yugabyte-limits.conf
yugabyte	soft 	core	unlimited
yugabyte	hard	core 	unlimited
yugabyte  soft	data	unlimited
yugabyte	hard	data	unlimited
yugabyte	soft	priority	0
yugabyte	hard	priority	0
yugabyte	soft	fsize	unlimited
yugabyte	hard	fsize	unlimited
yugabyte	soft	sigpending	119934
yugabyte	hard	sigpending	119934
yugabyte	soft    memlock	64
yugabyte	hard 	memlock	64
yugabyte	soft  	nofile	1048576
yugabyte	hard  	nofile	1048576
yugabyte	soft	stack	8192
yugabyte	hard	stack	8192
yugabyte	soft	rtprio	0
yugabyte	hard	rtprio	0
yugabyte	soft	nproc	12000
yugabyte	hard	nproc	12000
EOT

USER yugabyte
WORKDIR /home/yugabyte


RUN <<-EOT
  set -xve
  mkdir yb-software
  PKG=$YB_AMD64_PKG
  if [ "$TARGETPLATFORM" = "linux/arm64" ]; then PKG=$YB_ARM64_PKG; fi
  wget -qO- $PKG | tar -xz --strip-component=1 -C yb-software
  echo "export PATH=$PATH:/home/yugabyte/yb-software/bin:/home/yugabyte/yb-software/postgres/bin" >> /home/yugabyte/.bashrc
  export PATH=$PATH:/home/yugabyte/yb-software/bin:/home/yugabyte/yb-software/postgres/bin
EOT

WORKDIR /home/yugabyte/yb-software
VOLUME /home/yugabyte/yb-data
HEALTHCHECK --start-period=30s --interval=30s --timeout=1s --retries=10 \
  CMD  /home/yugabyte/yb-software/bin/ysqlsh -c "select version();"
EXPOSE 7000
EXPOSE 7100
EXPOSE 9000
EXPOSE 9100
EXPOSE 5433
EXPOSE 9042
EXPOSE 6379
EXPOSE 15433
EXPOSE 54422
EXPOSE 18018
EXPOSE 9300
EXPOSE 13000
EXPOSE 12000
EXPOSE 11000

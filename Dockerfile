FROM jenkins/jenkins

USER root

RUN DEBIAN_FRONTEND=noninteractive \
    apt-get clean && apt-get update && \
    apt-get install -y python3 python3-pip git \
                       && apt-get -y autoremove && apt-get clean \
                       && rm -rf /var/lib/apt/lists/* \

RUN echo 'jenkins ALL=NOPASSWD: ALL' >> /etc/sudoers

USER jenkins

COPY plugins.txt /var/lib/jenkins/plugins.txt

RUN /usr/local/bin/install-plugins.sh < /var/lib/jenkins/plugins.txt

COPY init-files/* /usr/share/jenkins/ref/init.groovy.d/

# Disables setup wizard and jenkins CLI
ENV JAVA_OPTS="-Djenkins.install.runSetupWizard=false"

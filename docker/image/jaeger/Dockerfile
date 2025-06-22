FROM jaegertracing/jaeger
COPY ./config-spm.yml /etc/jaeger/config-spm.yml
EXPOSE 16686 4317 4318 5778 9411 8889
ENTRYPOINT ["/cmd/jaeger/jaeger-linux","--config","/etc/jaeger/config-spm.yml"]
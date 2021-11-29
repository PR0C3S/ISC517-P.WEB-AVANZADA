FROM alpine AS certmaker
ENV DOMAIN=dietaketo.me
COPY /*.pem /temp/
RUN cat /temp/fullchain.pem  /temp/privkey.pem > /temp/$DOMAIN.pem
RUN cat /temp/dietaketo.me.pem


FROM haproxy:2.4.8-alpine
COPY haproxy.cfg /usr/local/etc/haproxy/haproxy.cfg
COPY --from=certmaker /temp/*.pem /usr/local/etc/haproxy/certs/
EXPOSE 80
EXPOSE 443
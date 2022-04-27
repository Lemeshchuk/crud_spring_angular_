FROM node:14-alpine

COPY . /usr/src/app
WORKDIR /usr/src/app

RUN npm install
RUN npm install -g @angular/cli

EXPOSE 4200

CMD ["ng", "serve", "--host", "0.0.0.0"]

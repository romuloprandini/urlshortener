FROM node:8-alpine

# set working directory
RUN mkdir /app
WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

# install and cache app dependencies
ADD package.json /app/package.json
RUN yarn global add react-scripts && yarn 

# build the app
CMD ["yarn", "build"]

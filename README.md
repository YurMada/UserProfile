[![forthebadge](https://forthebadge.com/images/badges/built-with-love.svg)](https://forthebadge.com)
[![forthebadge](https://forthebadge.com/images/badges/made-with-java.svg)](https://forthebadge.com)

[![Java CI with Maven](https://github.com/YurMada/userprofile/actions/workflows/maven.yml/badge.svg)](https://github.com/YurMada/userprofile/actions/workflows/maven.yml)
# USER-SERVICE : Laboration 3 - Web Services

This is a microservice application built using Spring boot, MySQL.

## How to use :
1. Create network:
```docker network create user```
2. Start consul:
```docker run -d -p 8500:8500 -p 8600:8600/udp --name=consul --network=user consul agent -server -ui -node=server-1 -bootstrap-expect=1 -client='0.0.0.0'``` 


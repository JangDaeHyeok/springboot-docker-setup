#!/bin/bash

start_server() {
  ./gradlew build --continuous -x test & ./gradlew bootRun
}

start_server
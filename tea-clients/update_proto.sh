#!/bin/sh
pip3 install grpcio grpcio-tools
python3 -m grpc_tools.protoc -I ../proto --python_out=. --pyi_out=. --grpc_python_out=. ../proto/application/core_util.proto
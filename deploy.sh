# Create the Docker Images
docker build -t peelmicro/multi-client:latest -t peelmicro/multi-client:$SHA -f ./client/Dockerfile ./client
docker build -t peelmicro/multi-server:latest -t peelmicro/multi-server:$SHA -f ./server/Dockerfile ./server
docker build -t peelmicro/multi-worker:latest -t peelmicro/multi-worker:$SHA -f ./worker/Dockerfile ./worker

# Take those images and push them to docker hub
docker push peelmicro/multi-client:latest
docker push peelmicro/multi-client:$SHA
docker push peelmicro/multi-server:latest
docker push peelmicro/multi-server:$SHA
docker push peelmicro/multi-worker:latest
docker push peelmicro/multi-worker:$SHA
# Apply all configs in the 'k8s' folder
kubectl apply -f k8s
# Imperatively set lastest images on each deployment
kubectl set image deployments/client-deployment client=peelmicro/multi-client:$SHA
kubectl set image deployments/server-deployment server=peelmicro/multi-server:$SHA
kubectl set image deployments/worker-deployment worker=peelmicro/multi-worker:$SHA
# Create the Docker Images
docker build -t peelmicro/java-multi-client:latest -t peelmicro/java-multi-client:$SHA -f ./client/Dockerfile ./client
docker build -t peelmicro/java-multi-server:latest -t peelmicro/java-multi-server:$SHA -f ./server/Dockerfile ./server
docker build -t peelmicro/java-multi-worker:latest -t peelmicro/java-multi-worker:$SHA -f ./worker/Dockerfile ./worker

# Take those images and push them to docker hub
docker push peelmicro/java-multi-client:latest
docker push peelmicro/java-multi-client:$SHA
docker push peelmicro/java-multi-server:latest
docker push peelmicro/java-multi-server:$SHA
docker push peelmicro/java-multi-worker:latest
docker push peelmicro/java-multi-worker:$SHA
# Apply all configs in the 'k8s' folder
kubectl apply -f k8s
# Imperatively set lastest images on each deployment
kubectl set image deployments/client-deployment client=peelmicro/java-multi-client:$SHA
kubectl set image deployments/server-deployment server=peelmicro/java-multi-server:$SHA
kubectl set image deployments/worker-deployment worker=peelmicro/java-multi-worker:$SHA
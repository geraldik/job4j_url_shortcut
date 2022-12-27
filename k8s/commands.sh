# Создание Secret
kubectl apply -f postgresdb-secret.yml
sleep 5
kubectl get secrets

# Создание ConfigMap
kubectl apply -f postgresdb-configmap.yml
sleep 5
kubectl get configmaps

# Создание развертывания postgresdb-deployment
kubectl apply -f postgresdb-deployment.yml
sleep 5

# Создание развертывания spring-boot-deployment
kubectl apply -f spring-deployment.yml
sleep 5


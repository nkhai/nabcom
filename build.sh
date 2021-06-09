cd Icom-cart
docker build -t icom-cart .
cd ..
cd Icom-core
docker build -t icom-core .
cd ..
cd Icom-gateway
docker build -t icom-gateway .
cd ..
cd Icom-history
docker build -t icom-history .
cd ..
cd Icom-product
docker build -t icom-product .
cd ..
cd Icom-registry
docker build -t icom-registry .
cd ..
cd Icom-user
docker build -t icom-user .
cd ..


docker run -d -p 8080:8080 --name backendTest daemon87/assesment-backend
docker network connect assesmentdatabase_default backendTest

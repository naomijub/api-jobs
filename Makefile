docker-build:
	docker-compose build

docker-run:
	docker-compose up api

docker-test:
	docker-compose run apitest

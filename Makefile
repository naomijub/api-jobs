dependency-check:
	lein ancient :plugins

docker-build:
	docker-compose build

docker-run:
	docker-compose up api

docker-test:
	docker-compose run apitest

docker-ci: dependency-check
	docker-compose run api-ci

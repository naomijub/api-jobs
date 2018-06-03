(ns api-job.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [api-job.handler :refer :all]))

(deftest test-app
  (testing "jobs route"
    (let [response (app (mock/request :get "/"))
          {:keys [status body]} response]
      (is (= status 200))
      (is (= body "Hello World"))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

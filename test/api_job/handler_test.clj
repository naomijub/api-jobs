(ns api-job.handler-test
  (:use clj-http.fake)
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clj-http.client :as client]
            [api-job.handler :refer :all]))

(defn fake-response []
  (slurp "resources/public/clojure-response.json"))

(deftest app-test
  (testing "jobs route"
    (testing "language value"
      (with-fake-routes
        {"https://jobs.github.com/positions.json?search=clojure"
         (fn [request] {:status 200
                        :headers {:content-type "application/json;charset=utf-8"}
                        :body (fake-response)})}
        (let [response (app (mock/request :get "/jobs/?lang=clojure"))
              {:keys [status body]} response]
          (is (= status 200))
          (is (not (empty? body)))))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

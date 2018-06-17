(ns api-job.handler-test
  (:use clj-http.fake)
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [clj-http.client :as client]
            [api-job.handler :refer :all]))

(defn fake-git-response []
  (slurp "resources/public/clojure-response.json"))

(defn fake-haskell-git-response []
  (slurp "resources/public/haskell-response.json"))

(defn fake-api-response []
  (clojure.string/trim-newline
   (slurp "resources/public/clojure-api-response.json")))

(defn fake-haskell-api-response []
  (clojure.string/trim-newline
   (slurp "resources/public/haskell-api-response.json")))

(defn fake-langs-api-response []
  (clojure.string/trim-newline
   (slurp "resources/public/langs-api-response.json")))

(defn fake-filtered-api-response []
  (clojure.string/trim-newline
   (slurp "resources/public/clojure-filtered-api-response.json")))

(deftest app-test
  (testing "jobs route"
    (with-fake-routes
      {"https://jobs.github.com/positions.json?search=clojure"
       (fn [request] {:status 200
                      :headers {:content-type "application/json;charset=utf-8"}
                      :body (fake-git-response)})}
      (testing "language value"
        (let [response (app (mock/request :get "/jobs/?lang=clojure"))
              {:keys [status body]} response]
          (is (= status 200))
          (is (not (empty? body)))
          (is (= body (fake-api-response)))))
      (testing "filtered by company language value"
        (let [response (app (mock/request :get "/jobs/filtered?lang=clojure&company=circleci"))
              {:keys [status body]} response]
          (is (= status 200))
          (is (= body (fake-filtered-api-response)))))))
  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404))))
  (testing "Language for different langs"
    (with-fake-routes
      {"https://jobs.github.com/positions.json?search=haskell"
       (fn [request] {:status 200
                      :headers {:content-type "application/json;charset=utf-8"}
                      :body (fake-haskell-git-response)})}
      (testing "language value"
        (let [response (app (mock/request :get "/jobs/?lang=haskell"))
              {:keys [status body]} response]
          (is (= status 200))
          (is (not (empty? body)))
          (is (= body (fake-haskell-api-response)))))))
  (testing "Language for many langs"
    (with-fake-routes
      {"https://jobs.github.com/positions.json?search=haskell"
       (fn [request] {:status 200
                      :headers {:content-type "application/json;charset=utf-8"}
                      :body (fake-haskell-git-response)})}
      {"https://jobs.github.com/positions.json?search=clojure"
       (fn [request] {:status 200
                      :headers {:content-type "application/json;charset=utf-8"}
                      :body (fake-git-response)}
         (testing "language value"
           (let [response (app (mock/request :get "/jobs/langs?lang=clojure&lang=haskell"))
                 {:keys [status body]} response]
             (is (= status 200))
             (is (= body (fake-langs-api-response))))))})))

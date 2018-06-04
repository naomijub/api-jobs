(ns api-job.handlers.language-test
  (:use clj-http.fake)
  (:require [clojure.test :refer :all]
            [cheshire.core :as json]
            [clj-http.client :as client]
            [api-job.handlers.language :refer :all]
            [api-job.utils :refer [every-not-empty?]]))

(deftest language-handler-testing
  (testing "browse-by-lang"
    (let [client-response (browse-by-lang "clojure")
          response (json/parse-string client-response true)]
      (is (= (:language response) "clojure"))
      (is (vector? (:jobs response)))))
  (testing "edns-to-json"
    (is (= (edns-to-json {:language "clojure" :jobs ["a" "b"]} {})
           "{\"language\":\"clojure\",\"jobs\":[\"a\",\"b\"]}"))
    (is (= (edns-to-json {:language "clojure" :jobs ["a" "b"]} {:jobs ["c"]})
           "{\"language\":\"clojure\",\"jobs\":[\"a\",\"b\",\"c\"]}")))
  (testing "language-client"
    (with-fake-routes
      {"https://jobs.github.com/positions.json?search=clojure"
       (fn [request] {:status 200
                      :headers {:content-type "application/json;charset=utf-8"}
                      :body (slurp "resources/public/clojure-response.json")})}
      (let [response (language-client "clojure")]
        (is (= (count response) 2))
        (is (every-not-empty?
             (set (keys (nth response 0)))
             #{:title :company :location :created_at :url}))))))

(ns api-job.handler-test
  (:require [clojure.test :refer :all]
            [ring.mock.request :as mock]
            [api-job.handler :refer :all]))

;;(defn fake-response []
;;  (slurp "resources/public/clojure-response.json"))

(deftest app-test
  (testing "jobs route"
    (testing "language value"
      (let [response (app (mock/request :get "/jobs/?lang=clojure"))
            {:keys [status body]} response]
        (is (= status 200))
        (is (= (:language body) "clojure")))))

  (testing "not-found route"
    (let [response (app (mock/request :get "/invalid"))]
      (is (= (:status response) 404)))))

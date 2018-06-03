(ns api-job.handlers.language-test
  (:require [clojure.test :refer :all]
            [cheshire.core :as json]
            [api-job.handlers.language :refer :all]))

(deftest language-handler-testing
  (testing "browse-by-lang"
    (let [client-response (browse-by-lang "clojure")
          response (json/parse-string client-response true)]
      (is (= (:language response) "clojure"))
      (is (vector? (:jobs response)))))
  (testing "edns-to-json"
    (is (= (edns-to-json {:language "clojure" :jobs ["a" "b"]} {})
           "{\"language\":\"clojure\",\"jobs\":[\"a\",\"b\"]}"))
    (is (= (edns-to-json {:language "clojure" :jobs ["a" "b"]} {:jobs ["c"]}))
        "{\"language\":\"clojure\",\"jobs\":[\"a\",\"b\",\"c\"]}")))

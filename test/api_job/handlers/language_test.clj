(ns api-job.handlers.language-test
  (:require [clojure.test :refer :all]
            [api-job.handlers.language :refer :all]))

(deftest language-handler-testing
  (testing "browse-by-lang"
    (let [response (browse-by-lang "clojure")]
      (is (= (:language response) "clojure"))
      (is (vector? (:jobs response))))))

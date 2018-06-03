(ns api-job.handlers.language
  (:require [cheshire.core :as json]))

(defn edns-to-json [m1 m2]
  (let [merged-map (merge m1 m2)]
    (json/generate-string merged-map)))

(defn browse-by-lang [lang]
  (let [m1 {:language lang :jobs []}
        m2 {}]
    (edns-to-json m1 m2)))
